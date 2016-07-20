package no.trinnvis.dabih.domain;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "Account.findByEmail",
                query = "SELECT p FROM Account p WHERE p.contactEmail = :email AND p.isClosed=0"
        ),
        @NamedQuery(
                name = "Account.findById",
                query = "SELECT p FROM Account p WHERE p.id = :id"
        )
})
@Audited(withModifiedFlag = true)
public class Account {
    @Id
    @Column(name = "id", columnDefinition = "int")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Basic
    @Column(name = "contact_email", columnDefinition = "nvarchar(255)")
    private String contactEmail;

    @Basic
    @Column(name = "contact_name", columnDefinition = "nvarchar(255)")
    private String contactName;

    @Basic
    @Column(name = "created_by", nullable = true, columnDefinition = "nvarchar(128)")
    private String createdBy;

    @Basic
    @Column(name = "first_name", columnDefinition = "nvarchar(255)")
    private String firstName;

    @Basic
    @Column(name = "is_closed", columnDefinition = "tinyint default 0")
    private Byte isClosed;

    @Basic
    @Column(name = "last_name", columnDefinition = "nvarchar(255)")
    private String lastName;

    @OneToMany(targetEntity = Document.class, mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Document> documents = new HashSet<>();


    public Account() {
    }

    public Account(Integer id) {
        this.id = id;
    }

    public String findFullName() {
        return this.getFirstName() + " " + this.getLastName();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Byte getIsClosed() {
        return isClosed;
    }

    public void setIsClosed(Byte isClosed) {
        this.isClosed = isClosed;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(Set<Document> documents) {
        this.documents = documents;
    }
}