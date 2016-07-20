package no.trinnvis.dabih.api.model;

import no.trinnvis.dabih.domain.Document;

import java.util.*;

public class DocumentViewModel {
    private String category;
    private String template;
    private String name;
    private String status;
    private String content;
    private String type;
    private UUID uuid;
    private boolean deleted;

    public DocumentViewModel() {
    }

    public DocumentViewModel(Document document) {
        if (document != null) {
            this.setCategory(document.getCategory());
            this.setTemplate(document.getTemplate());
            this.setName(document.getName());
            this.setStatus(document.getStatus());
            this.setContent(document.getContent());
            this.setType(document.getType());
            this.setUuid(document.getUuid());
            this.setDeleted(document.isDeleted());
        }
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
