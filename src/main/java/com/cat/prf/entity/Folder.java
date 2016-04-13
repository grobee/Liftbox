package com.cat.prf.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "folders")
@NamedQueries({
        @NamedQuery(name = "listFolders",
                query = "select f from Folder f"),
        @NamedQuery(name = "selectSpecificFolder",
                query = "select f from Folder f where f.id=:id")})


public class Folder {

    private int id;
    private String name;
    private List<Folder> folders = new ArrayList<>();
    private List<File> files = new ArrayList<>();


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(fetch = FetchType.EAGER, targetEntity = Folder.class)
    public List<Folder> getFolders() {
        return folders;
    }

    public void setFolders(List<Folder> folders) {
        this.folders = folders;
    }

    @OneToMany(fetch = FetchType.EAGER, targetEntity = File.class)
    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }


}
