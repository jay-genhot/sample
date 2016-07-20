package no.trinnvis.dabih.api.message;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AccountStatusUpdateMessage {

    private final Boolean closed;

    @JsonCreator
    public AccountStatusUpdateMessage(@JsonProperty("closed") Boolean closed) {
        this.closed = closed;
    }

    public Boolean getClosed() {
        return closed;
    }

}
