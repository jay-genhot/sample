package no.trinnvis.dabih.auth;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;
import io.dropwizard.hibernate.UnitOfWork;
import java.util.Optional;
import no.trinnvis.dabih.core.AuthenticatedUser;
import no.trinnvis.dabih.db.UserStore;
import no.trinnvis.dabih.domain.User;
import no.trinnvis.dabih.util.PasswordUtil;

public class UserAuthenticator implements Authenticator<BasicCredentials, AuthenticatedUser> {
    private final UserStore dao;

    public UserAuthenticator(UserStore dao) {
        this.dao = dao;
    }

    @Override
    @UnitOfWork
    public Optional<AuthenticatedUser> authenticate(BasicCredentials credentials) throws AuthenticationException {

        Optional<User> userOptional = dao.findByEmail(credentials.getUsername());

        if (userOptional.isPresent()) {
            no.trinnvis.dabih.domain.User user = userOptional.get();

            if (PasswordUtil.checkPassword(credentials.getPassword(), user.getPassword())) {
                return Optional.of(new AuthenticatedUser(user.getId(), user.getEmail()));
            }
        }


        return Optional.empty();
    }
}
