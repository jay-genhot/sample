package no.trinnvis.dabih.core;

import java.security.Principal;

public class AuthenticatedUser implements Principal {
    private final long id;
    private final String email;

    public AuthenticatedUser(long id, String email) {
        this.id = id;
        this.email = email;
    }

    public AuthenticatedUser(String email) {
        this.id = 0;
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getName() {
        return this.email;
    }
}
