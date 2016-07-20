package no.trinnvis.dabih.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import no.trinnvis.dabih.api.message.AccountCreateMessage;
import no.trinnvis.dabih.api.message.AccountStatusUpdateMessage;
import no.trinnvis.dabih.api.model.MessageViewModel;
import no.trinnvis.dabih.core.AuthenticatedUser;
import no.trinnvis.dabih.db.AccountDAO;
import no.trinnvis.dabih.db.UserStore;
import no.trinnvis.dabih.domain.*;
import no.trinnvis.dabih.util.DateUtil;
import no.trinnvis.dabih.util.PasswordUtil;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.CacheMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The resource endpoint for information about the current logged in user.
 */
@Path("/accounts")
@Produces(MediaType.APPLICATION_JSON)
public class AccountsResource {
    private static final String ACCOUNT_EXISTS = "exists";
    private static final String ACCOUNT_NOT_FOUND = "not found";
    protected static final String TEAM = "team";

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final AccountDAO accountDAO;
    private final UserStore userStore;

    public AccountsResource(AccountDAO accountDAO, UserStore userStore) {
        this.accountDAO = accountDAO;
        this.userStore = userStore;
    }

    /**
     * Check if the account already exist or not for specified account email.
     *
     * @param accountEmail The account email
     * @return If the account already exist return exists else return not found.
     */
    @GET
    @Path("/{accountEmailOrId}/accountStatus")
    @UnitOfWork
    public MessageViewModel verifyAccount(@PathParam("accountEmailOrId") String accountEmail) {
        java.util.Optional<Account> account = findByUserEmailOrId(accountEmail);

        if (!account.isPresent()) {
            return new MessageViewModel(ACCOUNT_NOT_FOUND);
        }
        return new MessageViewModel(ACCOUNT_EXISTS);
    }

    private Optional<Account> findByUserEmailOrId(String accountEmailOrId) {

        java.util.Optional<Account> a = accountDAO.findByEmail(accountEmailOrId);

        if (a.isPresent()) {
            return a;
        }

        if (StringUtils.isNumeric(accountEmailOrId)) {
            a = accountDAO.findById(Integer.valueOf(accountEmailOrId));
            return a;
        }

        return Optional.empty();
    }

    /**
     * Create new account
     *
     * @param message The information for create a new account
     * @throws PreconditionFailedException the account with same email already exist
     */
    @POST
    @UnitOfWork(cacheMode = CacheMode.IGNORE)
    @Consumes(MediaType.APPLICATION_JSON)
    public void post(@Valid AccountCreateMessage message) throws IOException {
        Date startDate = new Date();
        java.util.Optional<Account> account = findByUserEmailOrId(message.getEmail());
        if (account.isPresent()) {
            throw new PreconditionFailedException("Account already exists: " + message.getEmail());
        }
        Account createdAccount = accountDAO.create(createAccount(message));

        LocalDateTime iat = DateUtil.getIatTime();
        userStore.createUser(message.getEmail(), message.getFirstName(), message.getLastName(), PasswordUtil.hashPassword(message.getPassword()), iat);

    }

    private Account createAccount(AccountCreateMessage accountCreateMessage) throws JsonProcessingException {
        logger.info("Start building account");

        Account account = new Account();
        account.setContactEmail(accountCreateMessage.getEmail());
        account.setFirstName(accountCreateMessage.getFirstName());
        account.setLastName(accountCreateMessage.getLastName());
        account.setCreatedBy(TEAM);
        account.setIsClosed(Byte.valueOf("0"));
        account.setContactName(accountCreateMessage.getFirstName() + " " + accountCreateMessage.getLastName());

        accountDAO.create(account);

        return account;
    }

    /**
     * This endpoint is used to update account status.
     *
     * @param user      admin user
     * @param accountId account id
     * @param message   account status update message
     */
    @PUT
    @UnitOfWork(cacheMode = CacheMode.IGNORE)
    @Path("/{accountId}/status")
    @Consumes(MediaType.APPLICATION_JSON)
    public void put(@Auth AuthenticatedUser user, @PathParam("accountId") String accountId, @Valid AccountStatusUpdateMessage message) {

        java.util.Optional<Account> account = findByUserEmailOrId(accountId);
        if (!account.isPresent()) {
            throw new NotFoundException("The account not found.");
        }

        account.get().setIsClosed(message.getClosed() ? Byte.valueOf("1") : Byte.valueOf("0"));
    }




}
