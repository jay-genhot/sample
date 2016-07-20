package no.trinnvis.dabih.resources;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ForbiddenException extends WebApplicationException {
    @SuppressWarnings("SameParameterValue")
    public ForbiddenException(String message) {
        super(Response.status(Response.Status.FORBIDDEN)
                .entity(new ErrorEntity(message)).type(MediaType.APPLICATION_JSON).build());
    }


    private static class ErrorEntity {
        private final String error;

        ErrorEntity(String error) {
            this.error = error;
        }

        public String getError() {
            return error;
        }
    }
}
