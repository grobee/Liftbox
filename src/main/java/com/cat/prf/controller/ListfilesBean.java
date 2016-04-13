package com.cat.prf.controller;

import com.cat.prf.dao.FolderDAO;
import com.cat.prf.entity.File;
import com.cat.prf.entity.Folder;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


@Named("listfilesBean")
@RequestScoped
public class ListfilesBean implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(ListfilesBean.class.getSimpleName());


    // It would duplicate the table at every site refresh without this
    private boolean firstRunFiles;
    private boolean firstRunFolders;
    private boolean showBackButton;


    private List<File> files = new ArrayList<>();
    private List<Folder> folders = new ArrayList<>();

    @Inject
    private FolderDAO folderDAO;

    public ListfilesBean() {
        firstRunFiles = true;
        firstRunFolders = true;
        showBackButton = false;
    }

    public List<File> getFiles(int id) {

        if (firstRunFiles) {
            for (File f : folderDAO.getFilesDAO(id)) {
                files.add(f);

            }
        }
        firstRunFiles = false;

        return files;
    }


    public List<File> getFiles() {

        if (firstRunFiles) {
            for (File f : folderDAO.getFilesByUnameDAO()) {
                files.add(f);

            }
        }
        firstRunFiles = false;
        return files;
    }


    public List<Folder> getFolders(int id) {

        if (firstRunFolders) {
            for (Folder f : folderDAO.getFoldersDAO(id)) {
                folders.add(f);

            }
        }
        firstRunFolders = false;
        return folders;
    }

    public List<Folder> getFolders() {

        if (firstRunFolders) {
            for (Folder f : folderDAO.getFoldersByUnameDAO()) {
                folders.add(f);

            }
        }
        firstRunFolders = false;
        return folders;
    }

    public boolean isShowBackButton() {
        return showBackButton;
    }

    public void setShowBackButton(boolean showBackButton) {
        this.showBackButton = showBackButton;
    }


    public void setFiles(List<File> files) {
        this.files = files;
    }

    public void setFolders(List<Folder> folders) {
        this.folders = folders;
    }

    public void goNextPage(int id) {
        folders.clear();
        files.clear();

        showBackButton = true;

        LOGGER.info(String.valueOf(id));

        for (Folder f : folderDAO.getFoldersDAO(id)) {
            folders.add(f);

        }

        for (File f : folderDAO.getFilesDAO(id)) {
            files.add(f);

        }

    }


}
