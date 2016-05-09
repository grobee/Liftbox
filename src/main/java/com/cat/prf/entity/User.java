package com.cat.prf.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "users")
@NamedQueries(value = {
        @NamedQuery(name = "listUsers",
                query = "select u from User u"),
        @NamedQuery(name = "findUser",
                query = "select u from User u where u.username = :username and u.password = :password"),
        @NamedQuery(name = "getFolderId",
                query = "select u.rootfolder from User u where u.username = :username"),
        @NamedQuery(name = "getEmailByUname",
                query = "select u from User u where u.username = :username")

})
public class User {

    private int id;
    private String username;
    private String password;
    private String email;
    private Folder rootfolder;

    public User() {
    }

    public User(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(unique = true)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(nullable = false, name = "passwd")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @NotNull
    @Column(unique = true, nullable = false)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (username != null ? !username.equals(user.username) : user.username != null) return false;
        return password != null ? password.equals(user.password) : user.password == null;
    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }

    @OneToOne(targetEntity = Folder.class)
    public Folder getRootfolder() {
        return rootfolder;
    }

    public void setRootfolder(Folder rootfolder) {
        this.rootfolder = rootfolder;
    }
}
