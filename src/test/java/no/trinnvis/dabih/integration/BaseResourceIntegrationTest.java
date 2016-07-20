package no.trinnvis.dabih.integration;

import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.setup.Environment;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import no.trinnvis.dabih.ResponseMatchers;
import no.trinnvis.dabih.api.message.AccountCreateMessage;
import org.glassfish.jersey.client.ClientProperties;
import static org.junit.Assert.assertThat;
import org.junit.Rule;

public class BaseResourceIntegrationTest {

    static final String USERNAME = "test@sample.no";
    private static final String HOST = "http://localhost:";
    private static final String PASSWORD = "secret";
    private static Client client;
    @Rule
    public final DropwizardAppRule<DabihApiTestConfiguration> RULE = new DropwizardAppRule(DabihApiTestApplication.class, ResourceHelpers.resourceFilePath("config-h2db-test.yml"));

    protected Response sendPutRequest(String url, InputStream message) throws IOException {
        return client(url).put(Entity.entity(message, MediaType.APPLICATION_OCTET_STREAM));
    }

    private Invocation.Builder client(String url) throws UnsupportedEncodingException {
        return client(url, USERNAME, PASSWORD);
    }

    private Invocation.Builder client(String url, String userName, String password) throws UnsupportedEncodingException {
        return getClient().target(HOST + RULE.getLocalPort() + url).request().header("Authorization", getAuthHeader(userName, password));
    }

    private Client getClient() {
        if (client == null) {
            Environment environment = RULE.getEnvironment();
            client = new JerseyClientBuilder(environment).build("test client");
            client.property(ClientProperties.CONNECT_TIMEOUT, 60000);
            client.property(ClientProperties.READ_TIMEOUT, 60000);
        }
        return client;
    }

    private String getAuthHeader(String name, String password) throws UnsupportedEncodingException {
        return "Basic " + Base64.getEncoder().encodeToString((name + ":" + password).getBytes("utf-8"));
    }

    protected Response sendPutRequest(String url, Object message) throws UnsupportedEncodingException {
        return client(url).put(Entity.entity(message, MediaType.APPLICATION_JSON_TYPE));
    }

    protected Response sendPutRequest(String url, Object message, String userName, String password) throws UnsupportedEncodingException {
        return client(url, userName, password).put(Entity.entity(message, MediaType.APPLICATION_JSON_TYPE));
    }

    private Response sendPostRequest(String url, Object message) throws UnsupportedEncodingException {
        return client(url).post(Entity.entity(message, MediaType.APPLICATION_JSON_TYPE));
    }

    <T> T sendPostRequest(String url, Class<T> aClass) throws UnsupportedEncodingException {
        return client(url).post(Entity.entity(null, MediaType.APPLICATION_JSON_TYPE), aClass);
    }

    protected Response sendPutRequest(String url, String userName, String password, Object message) throws UnsupportedEncodingException {
        return client(url, userName, password).put(Entity.entity(message, MediaType.APPLICATION_JSON_TYPE));
    }

    protected Response sendDeleteRequest(String url) throws UnsupportedEncodingException, NotFoundException {
        return client(url).delete();
    }

    protected <T> T sendGetEntityRequest(String url, Class<T> aClass) throws UnsupportedEncodingException, NotFoundException {
        return client(url).get(aClass);
    }

    protected <T> T sendGetEntityRequest(String url, Class<T> aClass, String password) throws UnsupportedEncodingException, NotFoundException {
        return client(url, USERNAME, password).get(aClass);
    }

    protected <T> List<T> sendGetListRequest(String url, final Class<T> aClass) throws UnsupportedEncodingException, NotFoundException {
        ParameterizedType parameterizedType = new ParameterizedType() {
            public Type[] getActualTypeArguments() {
                return new Type[]{aClass};
            }

            public Type getRawType() {
                return List.class;
            }

            public Type getOwnerType() {
                return List.class;
            }
        };
        GenericType<List<T>> genericType = new GenericType<List<T>>(parameterizedType) {
        };
        return client(url).get(genericType);
    }

    private AccountCreateMessage accountCreateMessage(String firstName, String lastName, String email, String password) {
        return new AccountCreateMessage(firstName, lastName, email, password);
    }

    public void createNewPendingAccount() throws UnsupportedEncodingException {
        AccountCreateMessage accountCreateMessage = accountCreateMessage("firstName", "lastName",  USERNAME, PASSWORD);
        Response accountResponse = sendPostRequest("/accounts", accountCreateMessage);
        assertThat(accountResponse, ResponseMatchers.hasStatus(204));


    }

}
