package no.trinnvis.dabih.db;

import java.time.LocalDateTime;
import java.util.Optional;
import no.trinnvis.dabih.domain.User;

/**
 * Created by Kristian1 on 07.10.15.
 */
public interface UserStore {
    Optional<User> findByEmail(String email);

    void createUser(String email, String firstName, String lastName, String hashPassword, LocalDateTime lastPasswordUpdated);
}
