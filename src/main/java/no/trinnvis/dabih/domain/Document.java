package no.trinnvis.dabih.domain;


import org.hibernate.annotations.Type;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.*;

@SuppressWarnings("WeakerAccess")
@Entity
@Audited(withModifiedFlag = true)
public class Document{
    @Id
    @Column(name = "uuid")
    @Type(type = "uuid-char")
    private UUID uuid;

    @Basic
    @Column(name = "created_by", columnDefinition = "nvarchar(128)", nullable = true)
    private String createdBy;

    @Basic
    @Column(name = "category", columnDefinition = "nvarchar(255)")
    private String category;

    @Basic
    @Column(name = "template", columnDefinition = "nvarchar(255)")
    private String template;

    @Basic
    @Column(name = "modified_by", columnDefinition = "nvarchar(128)", nullable = true)
    private String modifiedBy;

    @Basic
    @Column(name = "name", columnDefinition = "nvarchar(255)")
    private String name;

    @Basic
    @Column(name = "status", columnDefinition = "nvarchar(255)")
    private String status;

	@Basic
	@Column(name="content", columnDefinition="MEDIUMTEXT")
	private String content;

    @Basic
    @Column(name = "type", columnDefinition = "nvarchar(255)")
    private String type;

    @Basic
    @Column(name = "deleted", columnDefinition = "bit default 0")
    private boolean deleted;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;


    public Document() {
    }

    public String getId() {
        return this.getUuid().toString();
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

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
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

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}