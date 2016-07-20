package no.trinnvis.dabih.integration;

import no.trinnvis.dabih.api.message.DocumentUpdateMessage;
import no.trinnvis.dabih.api.model.DocumentViewModel;
import org.junit.Test;

import javax.ws.rs.core.Response;

import static no.trinnvis.dabih.ResponseMatchers.hasStatus;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created by Administrator on 2015/9/2.
 */
public class OrganizationDocumentResourceTest extends BaseResourceIntegrationTest {

    @Test
    public void testResource() throws Exception {
        //new account
        createNewPendingAccount();
        String base = "1";
        //put
        DocumentUpdateMessage message = personUpdateMessage("category1", "template1", "name1", "status1", "content1", "type1");
        Response response = sendPutRequest(base + "/documents/a922ea00-ed9d-48dd-9d88-22ed87fcd45d", message);
        assertThat(response, hasStatus(204));

        //get
        DocumentViewModel document = sendGetEntityRequest(base + "/documents/a922ea00-ed9d-48dd-9d88-22ed87fcd45d", DocumentViewModel.class);
        assertThat(document.getCategory(),is("category1"));
        assertThat(document.getTemplate(),is("template1"));
        assertThat(document.getName(), is("name1"));
        assertThat(document.getStatus(),is("status1"));
        assertThat(document.getContent(),is("content1"));
        assertThat(document.getType(),is("type1"));

        //update
        DocumentUpdateMessage message1 = personUpdateMessage("category", "template", "name", "status", "content", "type");
        Response response1 = sendPutRequest(base + "/documents/a922ea00-ed9d-48dd-9d88-22ed87fcd45d", message1);
        assertThat(response1.getStatus(), is(204));

        //get
        DocumentViewModel document1 = sendGetEntityRequest(base + "/documents/a922ea00-ed9d-48dd-9d88-22ed87fcd45d", DocumentViewModel.class);
        assertThat(document1.getCategory(),is("category"));
        assertThat(document1.getTemplate(),is("template"));
        assertThat(document1.getName(), is("name"));
        assertThat(document1.getStatus(),is("status"));
        assertThat(document1.getContent(),is("content"));
        assertThat(document1.getType(),is("type"));

        //delete
        sendDeleteRequest(base + "/documents/a922ea00-ed9d-48dd-9d88-22ed87fcd45d");
    }

    private DocumentUpdateMessage personUpdateMessage(String category, String template, String name, String status, String content, String type) {
        return new DocumentUpdateMessage(category, template, name, status, content, type);
    }
}
