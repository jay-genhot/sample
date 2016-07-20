package no.trinnvis.dabih.db;

import io.dropwizard.hibernate.AbstractDAO;
import java.time.LocalDateTime;
import java.util.Optional;
import no.trinnvis.dabih.domain.User;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

public class UserDAO extends AbstractDAO<User> implements UserStore {
    public UserDAO(SessionFactory factory) {
        super(factory);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        Criteria c = criteria();
        c.add(Restrictions.eq("email", email));
        return Optional.ofNullable(uniqueResult(c));
    }


    @Override
    public void createUser(String email, String firstName, String lastName, String hashPassword, LocalDateTime lastPasswordUpdated) {
        Optional<User> user = findByEmail(email);
        if (user.isPresent()) {
            throw new IllegalStateException();
        }

        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(hashPassword);

        persist(newUser);
    }
}
