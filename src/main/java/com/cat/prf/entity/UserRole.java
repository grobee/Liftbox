package com.cat.prf.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_roles")
public class UserRole implements Serializable {
    private User user;
    private String role;

    @Id
    @OneToOne(fetch = FetchType.LAZY,
            targetEntity = User.class)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(nullable = false)
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
