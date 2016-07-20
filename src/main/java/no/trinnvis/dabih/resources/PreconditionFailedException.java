package no.trinnvis.dabih.resources;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


public class PreconditionFailedException extends WebApplicationException {
    public PreconditionFailedException(String message) {
        super(Response.status(Response.Status.PRECONDITION_FAILED)
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
