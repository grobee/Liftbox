package com.cat.prf.controller;

import com.cat.prf.dao.FolderDAO;
import com.cat.prf.entity.File;
import com.cat.prf.entity.Folder;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


@Named("listfilesBean")
@SessionScoped
public class ListfilesBean implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(ListfilesBean.class.getSimpleName());

    // It would duplicate the table at every site refresh without this
    private boolean firstRunFiles;
    private boolean firstRunFolders;

    private List<File> files = new ArrayList<>();
    private List<Folder> folders = new ArrayList<>();


    @Inject
    private FolderDAO folderDAO;

    public ListfilesBean() {
        firstRunFiles = true;
        firstRunFolders = true;
    }

    public List<File> getFiles() {

        if (firstRunFiles) {
            for (File f : folderDAO.getFiles()) {
                //LOGGER.info("\nid: " + f.getId() + " \nname: " + f.getName() + " \nsize: " + f.getSize());
                files.add(f);

            }
        }
        firstRunFiles = false;
        return files;
    }


    public List<Folder> getFolders() {

        if (firstRunFolders) {
            for (Folder f : folderDAO.getFolders()) {
                //LOGGER.info("\nid: " + f.getId() + " \nname: " + f.getName() + " \nsize: " + f.getSize());
                folders.add(f);

            }
        }
        firstRunFolders = false;
        return folders;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    public void setFolders(List<Folder> folders) {
        this.folders = folders;
    }


}
