package no.trinnvis.dabih.api.message;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AccountCreateMessage {
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String password;

    @JsonCreator
    public AccountCreateMessage(@JsonProperty("firstName") String firstName, @JsonProperty("lastName") String lastName,
                                 @JsonProperty("email") String email,
                                @JsonProperty("password") String password
        ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }


    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

}
