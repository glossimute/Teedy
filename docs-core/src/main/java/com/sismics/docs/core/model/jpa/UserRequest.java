package com.sismics.docs.core.model.jpa;

import com.google.common.base.MoreObjects;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "T_USER_REQUEST")
public class UserRequest {
    @Id
    @Column(name = "URQ_ID_C", length = 36)
    private String id;

    @Column(name = "URQ_EMAIL_C", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "URQ_NAME_C", length = 50)
    private String name;

    @Column(name = "URQ_MESSAGE_C", columnDefinition = "TEXT")
    private String message;

    @Column(name = "URQ_STATUS_C", nullable = false, length = 20)
    private String status;

    @Column(name = "URQ_CREATEDATE_D", nullable = false)
    private Date createDate;

    public UserRequest() {
        this.id = java.util.UUID.randomUUID().toString();
        this.status = "PENDING";
        this.createDate = new Date();
    }

    // Getter and setters with chain return
    public String getId() {
        return id;
    }
    public UserRequest setId(String id) {
        this.id = id;
        return this;
    }

    public String getEmail() {
        return email;
    }
    public UserRequest setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getName() {
        return name;
    }
    public UserRequest setName(String name) {
        this.name = name;
        return this;
    }

    public String getMessage() {
        return message;
    }
    public UserRequest setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getStatus() {
        return status;
    }
    public UserRequest setStatus(String status) {
        this.status = status;
        return this;
    }

    public Date getCreateDate() {
        return createDate;
    }
    public UserRequest setCreateDate(Date createDate) {
        this.createDate = createDate;
        return this;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("email", email)
                .add("status", status)
                .toString();
    }
}
