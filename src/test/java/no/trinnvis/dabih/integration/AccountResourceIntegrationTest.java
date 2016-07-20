package no.trinnvis.dabih.integration;

import no.trinnvis.dabih.api.model.MessageViewModel;
import org.hamcrest.CoreMatchers;
import org.junit.Test;

import static org.junit.Assert.*;

public class AccountResourceIntegrationTest extends BaseResourceIntegrationTest {

    @Test
    public void testAccount() throws Exception {
        //create
        createNewPendingAccount();

        //get
        MessageViewModel messageViewModel = sendGetEntityRequest("/accounts/" + USERNAME + "/accountStatus", MessageViewModel.class);
        assertThat(messageViewModel.getMessage(), CoreMatchers.is("exists"));

        MessageViewModel messageViewModel2 = sendGetEntityRequest("/accounts/1/accountStatus", MessageViewModel.class);
        assertThat(messageViewModel2.getMessage(), CoreMatchers.is("exists"));

        MessageViewModel messageViewModel1 = sendGetEntityRequest("/accounts/aaa/accountStatus", MessageViewModel.class);
        assertThat(messageViewModel1.getMessage(), CoreMatchers.is("not found"));

        MessageViewModel messageViewModel3 = sendGetEntityRequest("/accounts/1111/accountStatus", MessageViewModel.class);
        assertThat(messageViewModel3.getMessage(), CoreMatchers.is("not found"));
    }
}
