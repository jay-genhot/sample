package no.trinnvis.dabih.db;

import com.jcabi.aspects.Loggable;
import io.dropwizard.hibernate.AbstractDAO;
import no.trinnvis.dabih.domain.Account;
import org.hibernate.SessionFactory;

import java.util.Optional;

public class AccountDAO extends AbstractDAO<Account> {
    public AccountDAO(SessionFactory factory) {
        super(factory);
    }

    @Loggable
    public Optional<Account> findByEmail(String email) {
        return Optional.ofNullable(uniqueResult(namedQuery("Account.findByEmail").setString("email", email)));
    }

    @Loggable
    public Optional<Account> findById(Integer id) {
        return Optional.ofNullable(get(id));
    }

    @Loggable
    public Account create(Account account) {
        return persist(account);
    }

}
