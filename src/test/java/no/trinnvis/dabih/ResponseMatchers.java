package no.trinnvis.dabih;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import javax.ws.rs.core.Response;

public class ResponseMatchers {
    public static Matcher<? super Response> hasStatus(int i) {

        return new BaseMatcher<Response>() {
            @Override
            public boolean matches(Object item) {
                Response r = (Response) item;
                return r.getStatus() == i;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("Status should be ").appendValue(i);
            }

            @Override
            public void describeMismatch(Object item, Description description) {
                Response r = (Response) item;
                String responseAsString = r.readEntity(String.class);
                description.appendText("was ").appendValue(r.getStatus()).appendText(" with ").appendValue(responseAsString);
            }
        };
    }
}
