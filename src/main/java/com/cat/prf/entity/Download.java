package com.cat.prf.entity;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "downloads")
@NamedQueries(
        @NamedQuery(
                name = "getDownloadsByUser",
                query = "select d from Download d where d.user = :user order by d.date asc"
        )
)
public class Download {
    private long id;
    private Date date;
    private User user;

    public Download(User user, Date date) {
        this.date = date;
        this.user = user;
    }

    public Download() {
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
