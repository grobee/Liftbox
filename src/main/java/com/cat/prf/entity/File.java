package com.cat.prf.entity;

import javax.persistence.*;

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
    private Folder parent;

    public File() {
    }

    public File(String name, long size, Folder parent) {
        this.name = name;
        this.size = size;
        this.parent = parent;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(nullable = false)
    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    @OneToOne(targetEntity = Folder.class)
    public Folder getParent() {
        return parent;
    }

    public void setParent(Folder parent) {
        this.parent = parent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        File file = (File) o;

        if (id != file.id) return false;
        if (size != file.size) return false;
        return name != null ? name.equals(file.name) : file.name == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (int) (size ^ (size >>> 32));
        return result;
    }
}
