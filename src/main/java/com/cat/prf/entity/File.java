package com.cat.prf.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "files")
@NamedQueries(
        @NamedQuery(name = "listFiles",
                query = "select f from File f")
)
public class File {
    private long id;
    private String name;
    private long size;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NotNull
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull
    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
