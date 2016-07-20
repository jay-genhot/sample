package no.trinnvis.dabih.resources;

import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.IntParam;
import io.dropwizard.jersey.params.UUIDParam;
import no.trinnvis.dabih.api.message.DocumentUpdateMessage;
import no.trinnvis.dabih.api.model.DocumentViewModel;
import no.trinnvis.dabih.core.AuthenticatedUser;
import no.trinnvis.dabih.db.AccountDAO;
import no.trinnvis.dabih.domain.Account;
import no.trinnvis.dabih.domain.Document;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.*;

@Path("/accounts/{accountId}/documents")
@Produces(MediaType.APPLICATION_JSON)
public class AccountDocumentResource {
    private final AccountDAO accountDAO;

    public AccountDocumentResource(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    /**
     * Update or create document with the specified document id, and the search index for the document will also be updated or created.
     *
     * @param authenticatedUser The current authorized authenticatedUser
     * @param documentId        The document uuid
     * @param message
     * @throws ForbiddenException The authenticatedUser is not authorized to access this organizations
     */
    @PUT
    @UnitOfWork
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{documentId}")
    public void put(@Auth AuthenticatedUser authenticatedUser, @PathParam("accountId") IntParam accountId, @PathParam("documentId") UUIDParam documentId, @Valid DocumentUpdateMessage message) {

        if (documentExists(accountId, documentId.get())) {
            updateDocument(authenticatedUser, accountId, documentId, message);
        } else {
            createDocument(authenticatedUser, accountId, documentId, message);
        }

    }

    private void createDocument(AuthenticatedUser authenticatedUser, IntParam accountId, UUIDParam documentId, DocumentUpdateMessage message) {
        Account account = findSafely(accountId.get());

        Document document = new Document();
        document.setAccount(account);

        document.setCreatedBy(authenticatedUser.getEmail());
        document.setModifiedBy(authenticatedUser.getEmail());
        document.setUuid(documentId.get());

        updateDocumentFromMessage(document, message);

        account.getDocuments().add(document);
    }

    /**
     * Delete document with the specified document id, the search index of the document will also be deleted.
     *
     * @param authenticatedUser The current authorized authenticatedUser
     * @param accountId    The organization id
     * @param documentId        The existing document uuid
     * @throws NotFoundException  The specified document does not exist in the system
     * @throws ForbiddenException The authenticatedUser is not authorized to access this organizations
     */
    @DELETE
    @UnitOfWork
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{documentId}")
    public void delete(@Auth AuthenticatedUser authenticatedUser, @PathParam("accountId") IntParam accountId, @PathParam("documentId") UUIDParam documentId) {

        deleteDocument(authenticatedUser, accountId, documentId);
    }

    private void deleteDocument(AuthenticatedUser authenticatedUser, IntParam organizationId, UUIDParam documentId) {
        Account account = findSafely(organizationId.get());
        Document document = findDocumentSafely(account, documentId.get());

        document.setDeleted(true);
    }


    private void updateDocument(AuthenticatedUser authenticatedUser, IntParam accountId, UUIDParam documentId, DocumentUpdateMessage message) {
        Account account= findSafely(accountId.get());
        Document document = findDocumentSafely(account, documentId.get());

        document.setModifiedBy(authenticatedUser.getEmail());
        updateDocumentFromMessage(document, message);
    }

    private void updateDocumentFromMessage(Document document, DocumentUpdateMessage message) {
        document.setCategory(message.getCategory());
        document.setTemplate(message.getTemplate());
        document.setName(message.getName());
        document.setStatus(message.getStatus());
        document.setContent(message.getContent());
        document.setType(message.getType());

        document.setDeleted(false);
    }

    /**
     * Besides existence verification, do classification verification as well
     *
     * @param uuid
     * @return
     */
    private Document findDocumentSafely(Account account, UUID uuid) {
        for (Document document : account.getDocuments()) {
            if (document.getUuid().equals(uuid)) {
                return document;
            }
        }
        throw new NotFoundException("No such document.");
    }

    private boolean documentExists(IntParam accountId, UUID uuid) {
        Account account = findSafely(accountId.get());

        for (Document document : account.getDocuments()) {
            if (document.getUuid().equals(uuid)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get all the documents within the specified organization
     *
     * @param authenticatedUser The current authorized authenticatedUser
     * @param accountId    The organization id
     * @return All documents
     * @throws ForbiddenException The authenticatedUser is not authorized to access this organizations
     */
    @GET
    @UnitOfWork
    public List<DocumentViewModel> getDocumentViewModels(@Auth AuthenticatedUser authenticatedUser, @PathParam("accountId") IntParam accountId) {

        Account account = findSafely(accountId.get());

        List<DocumentViewModel> allModels = new ArrayList<>();

        for (Document document : account.getDocuments()) {
            if (!document.isDeleted()) {
                DocumentViewModel documentViewModel = new DocumentViewModel(document);
                allModels.add(documentViewModel);
            }
        }

        return allModels;
    }

    Account findSafely(int accountId) {
        final Optional<Account> account = accountDAO.findById(accountId);
        if (!account.isPresent()) {
            throw new NotFoundException("No such account.");
        }
        return account.get();
    }

}
