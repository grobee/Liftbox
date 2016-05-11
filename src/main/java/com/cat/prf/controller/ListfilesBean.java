package com.cat.prf.controller;

import com.cat.prf.dao.*;
import com.cat.prf.entity.Download;
import com.cat.prf.entity.File;
import com.cat.prf.entity.Folder;
import com.cat.prf.entity.User;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


@Named
@SessionScoped
public class ListFilesBean implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(ListFilesBean.class.getSimpleName());
    // It would duplicate the table at every site refresh without this

    // List of files on the page
    private List<File> files = new ArrayList<>();
    // List of folders on the page
    private List<Folder> folders = new ArrayList<>();
    // Page path needed for navigation
    private List<Folder> path = new ArrayList<>();
    private String newFolderName;

    @Inject
    private DownloadDAO downloadDAO;

    @Inject
    private FolderDAO folderDAO;

    @Inject
    private FileDAO fileDAO;

    @Inject
    private UserDAO userDAO;

    @Inject
    private FolderFileDAO folderFileDAO;

    public ListFilesBean() {
    }

    @PostConstruct
    public void initialize() {
        path.add(userDAO.getUserByName(getUsername()).getRootfolder());

        folders.addAll(getCurrentFolder().getFolders());
        files.addAll(getCurrentFolder().getFiles());
    }

    // Database request for root folders files
    public List<File> getFiles() {
        files.clear();
        files.addAll(folderDAO.getFilesDAO(getCurrentId()));

        return files;
    }

    //  Database request for root folders folders
    public List<Folder> getFolders() {
        folders.clear();
        folders.addAll(folderDAO.getFoldersDAO(getCurrentId()));

        return folders;
    }

    public int getPathSize() {
        return path.size();
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    public void setFolders(List<Folder> folders) {
        this.folders = folders;
    }

    public void goNextPage(long id) {
        LOGGER.info("" + id);

        Folder folder = folderDAO.read(id);
        path.add(folder);

        LOGGER.info(folder.getName() + " " + folder.getId());

        getFiles();
        getFolders();
    }

    public void goBackPage() {
        path.remove(path.size() - 1);

        getFiles();
        getFolders();
    }

    public String getNewFolderName() {
        return newFolderName;
    }

    public void setNewFolderName(String newFolderName) {
        this.newFolderName = newFolderName;
    }

    public Folder getCurrentFolder() {
        return path.get(path.size() - 1);
    }


    public long getCurrentId() {
        if (path.size() == 0) {
            return folderDAO.getCurrentId(getUsername());
        } else {
            return path.get(path.size() - 1).getId();
        }
    }

    public String getUsername() {
        return FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName();
    }

    public void download(long id) {
        FacesContext context = FacesContext.getCurrentInstance();

        HttpServletRequest req = (HttpServletRequest) context.getExternalContext().getRequest();
        HttpServletResponse resp = (HttpServletResponse) context.getExternalContext().getResponse();

        File file = fileDAO.read(id);

        String filePath = req.getServletContext().getRealPath("") + java.io.File.separator
                + "Files" + java.io.File.separator;

        for (Folder parent : path) {
            filePath += java.io.File.separator + parent.getName();
        }

        filePath += java.io.File.separator + file.getName();

        java.io.File fileDown = new java.io.File(filePath);
        ServletOutputStream mbaos;

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

        User user = userDAO.getUserByName(getUsername());
        downloadDAO.create(new Download(user, file, new Date(System.currentTimeMillis())));

        context.responseComplete();
    }

    @Transactional
    public String delete(long id) {
        FacesContext context = FacesContext.getCurrentInstance();

        HttpServletRequest req = (HttpServletRequest) context.getExternalContext().getRequest();

        File file = fileDAO.read(id);

        String username = req.getUserPrincipal().getName();
        String filePath = req.getServletContext().getRealPath("") + java.io.File.separator
                + "Files";

        for (Folder parent : path) {
            filePath += java.io.File.separator + parent.getName();
        }

        filePath += java.io.File.separator + file.getName();

        LOGGER.info(filePath);

        java.io.File fileDel = new java.io.File(filePath);
        fileDel.delete();

        folderFileDAO.deleteByFile(id);
        fileDAO.delete(id);

        return "listfiles.xhtml?faces-redirect=true";
    }

    public void createFolder() {
        //LOGGER.info("Cratefolder " + newFolderName + " currentID " + currentId);
        folderDAO.createNewFolder(newFolderName, getCurrentId());

        folders.clear();
        folders.addAll(folderDAO.getFoldersDAO(getCurrentId()));
    }

    public List<Folder> getPath() {
        return path;
    }
}