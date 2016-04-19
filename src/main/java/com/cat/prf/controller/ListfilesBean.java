package com.cat.prf.controller;

import com.cat.prf.dao.FolderDAO;
import com.cat.prf.entity.File;
import com.cat.prf.entity.Folder;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


@Named("listfilesBean")
@ViewScoped
public class ListfilesBean implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(ListfilesBean.class.getSimpleName());


    // It would duplicate the table at every site refresh without this
    private boolean firstRunFiles;
    private boolean firstRunFolders;
    private boolean showBackButton;


    private int currentId = 0;

    // List of files on the page
    private List<File> files = new ArrayList<>();
    // List of folders on the page
    private List<Folder> folders = new ArrayList<>();
    // Page history needed for navigation
    private List<Integer> history = new ArrayList<>();

    @Inject
    private FolderDAO folderDAO;

    public ListfilesBean() {
        firstRunFiles = true;
        firstRunFolders = true;
        showBackButton = false;
    }

    // Database request for root folders files
    public List<File> getFiles(String username) {

        if (firstRunFiles) {
            files.addAll(folderDAO.getFilesByUnameDAO(username));
        }
        firstRunFiles = false;

        return files;
    }

    //  Database request for root folders folders
    public List<Folder> getFolders(String username) {

        if (firstRunFolders) {
            folders.addAll(folderDAO.getFoldersByUnameDAO(username));
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

    public void goNextPage(int id, String username) {

        // Check whether the user is on the root page
        if (!showBackButton && history.size() == 0) {
            history.add(folderDAO.getCurrentId(username));
        }

        history.add(id);
        showBackButton = true;
        changeListById(id);

    }

    public void goBackPage() {

        history.remove(history.size() - 1);
        int id = history.get(history.size() - 1);

        if (history.size() == 1) {
            showBackButton = false;
        }

        changeListById(id);

    }

    // Database request with specified folder id
    private void changeListById(int id) {
        folders.clear();
        files.clear();

        folders.addAll(folderDAO.getFoldersDAO(id));
        files.addAll(folderDAO.getFilesDAO(id));
    }

    public int getCurrentId(String username) {
        if (history.size() <= 1) {
            currentId = folderDAO.getCurrentId(username);
        } else {
            currentId = history.get(history.size() - 1);
        }

        return currentId;
    }

    public void setCurrentId(int currentId) {
        this.currentId = currentId;
    }

}
