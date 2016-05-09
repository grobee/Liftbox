package com.cat.prf.controller;

import com.cat.prf.dao.FileDAO;
import com.cat.prf.dao.FolderDAO;
import com.cat.prf.dao.FolderFileDAO;
import com.cat.prf.entity.File;
import com.cat.prf.entity.Folder;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.FileInputStream;
import java.io.IOException;
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
    private String newFolderName;

    private long currentId = 0;

    // List of files on the page
    private List<File> files = new ArrayList<>();
    // List of folders on the page
    private List<Folder> folders = new ArrayList<>();
    // Page history needed for navigation
    private List<Folder> history = new ArrayList<>();

    @Inject
    private FolderDAO folderDAO;

    @Inject
    private FileDAO fileDAO;

    @Inject
    private FolderFileDAO folderFileDAO;

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

    public String getNewFolderName() {
        return newFolderName;
    }

    public void setNewFolderName(String newFolderName) {
        this.newFolderName = newFolderName;
    }

    public void goNextPage(long id, String username) {

        // Check whether the user is on the root page
        if (!showBackButton && history.size() == 0) {
            long rootId = folderDAO.getCurrentId(username);
            history.add(folderDAO.read(rootId));
        }

        history.add(folderDAO.read(id));
        showBackButton = true;
        changeListById(id);

    }

    public void goBackPage() {

        history.remove(history.size() - 1);
        long id = history.get(history.size() - 1).getId();

        if (history.size() == 1) {
            showBackButton = false;
        }

        changeListById(id);

    }

    // Database request with specified folder id
    private void changeListById(long id) {
        folders.clear();
        files.clear();

        folders.addAll(folderDAO.getFoldersDAO(id));
        files.addAll(folderDAO.getFilesDAO(id));
    }

    public long getCurrentId(String username) {
        if (history.size() <= 1) {
            currentId = folderDAO.getCurrentId(username);
        } else {
            currentId = history.get(history.size() - 1).getId();
        }

        return currentId;
    }

    public void setCurrentId(int currentId) {
        this.currentId = currentId;
    }

    public void createFolder() {
        //LOGGER.info("Cratefolder " + newFolderName + " currentID " + currentId);
        folderDAO.createNewFolder(newFolderName,currentId);

        folders.clear();
        folders.addAll(folderDAO.getFoldersDAO(currentId));
    }

    public void download(long id) {
        FacesContext context = FacesContext.getCurrentInstance();

        HttpServletRequest req = (HttpServletRequest) context.getExternalContext().getRequest();
        HttpServletResponse resp = (HttpServletResponse) context.getExternalContext().getResponse();

        File file = fileDAO.read(id);

        String username = req.getUserPrincipal().getName();
        String filePath = req.getServletContext().getRealPath("") + java.io.File.separator
                + "Files" + java.io.File.separator + username;

        for (Folder parent : history) {
            filePath += "/" + parent.getName();
        }

        filePath += "/" + file.getName();

        java.io.File fileDown = new java.io.File(filePath);
        ServletOutputStream mbaos = null;

        resp.reset();

        resp.setHeader("Content-Disposition", "attachment; filename=" + fileDown.getName());
        resp.setContentType("application/octet-stream");
        resp.setContentLength((int) fileDown.length());

        try (FileInputStream fis = new FileInputStream(fileDown)) {
            mbaos = resp.getOutputStream();

            byte[] buffer = new byte[1024];
            int r;

            while ((r = fis.read(buffer)) != -1) {
                mbaos.write(buffer, 0, r);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        context.responseComplete();
    }

    @Transactional
    public String delete(long id) {
        FacesContext context = FacesContext.getCurrentInstance();

        HttpServletRequest req = (HttpServletRequest) context.getExternalContext().getRequest();

        File file = fileDAO.read(id);

        String username = req.getUserPrincipal().getName();
        String filePath = req.getServletContext().getRealPath("") + java.io.File.separator
                + "Files" + java.io.File.separator + username;

        for (Folder parent : history) {
            filePath += "/" + parent.getName();
        }

        filePath += "/" + file.getName();

        java.io.File fileDel = new java.io.File(filePath);
        fileDel.delete();

        folderFileDAO.deleteByFile(id);
        fileDAO.delete(id);

        return "listfiles.xhtml?faces-redirect=true";
    }
}
