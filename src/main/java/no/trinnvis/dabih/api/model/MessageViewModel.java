package no.trinnvis.dabih.api.model;


@SuppressWarnings("WeakerAccess")
public class MessageViewModel {

    private String message;

    public MessageViewModel() {
    }

    public MessageViewModel(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
