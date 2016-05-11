package com.cat.prf.entity;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "uploads")
@NamedQueries(
        @NamedQuery(
                name = "getUploadsByUser",
                query = "select u from Upload u where u.user = :user order by u.date asc"
        )
)
public class Upload {
    private long id;
    private Date date;
    private User user;

    public Upload(User user, Date date) {
        this.date = date;
        this.user = user;
    }

    public Upload() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @OneToOne
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}