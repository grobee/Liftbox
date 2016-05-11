package com.cat.prf.entity;

import javax.persistence.*;
import java.util.Set;


@Entity
@Table(name = "folders")
@NamedQueries({
        @NamedQuery(name = "listFolders",
                query = "select f from Folder f"),
        @NamedQuery(name = "selectSpecificFolder",
                query = "select f from Folder f where f.id=:id")})
public class Folder {
    private long id;
    private String name;
    private Set<Folder> folders;
    private Set<File> files;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(fetch = FetchType.EAGER, targetEntity = Folder.class)
    public Set<Folder> getFolders() {
        return folders;
    }

    public void setFolders(Set<Folder> folders) {
        this.folders = folders;
    }

    @OneToMany(fetch = FetchType.EAGER, targetEntity = File.class)
    public Set<File> getFiles() {
        return files;
    }

    public void setFiles(Set<File> files) {
        this.files = files;
    }


}
