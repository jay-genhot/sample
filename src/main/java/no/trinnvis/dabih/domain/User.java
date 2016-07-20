package no.trinnvis.dabih.domain;

import org.hibernate.envers.Audited;

import javax.persistence.*;

@Entity
@Audited(withModifiedFlag = true)
public class User {
    @Id
    @Column(name = "id", columnDefinition = "int")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Basic
    @Column(columnDefinition = "nvarchar(255)")
    private String email;

    @Basic
    @Column(name = "password", columnDefinition = "nvarchar(255)")
    private String password;

    public User() {
    }

    public User(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}