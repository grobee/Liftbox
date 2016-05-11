package com.cat.prf.controller;

import com.cat.prf.dao.*;
import com.cat.prf.entity.Folder;
import com.cat.prf.entity.FolderFile;
import com.cat.prf.entity.Upload;
import com.cat.prf.entity.User;
import org.jboss.logging.Logger;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.sql.Date;

@Named
@RequestScoped
public class FileUploadBean {

    @Inject
    UploadDAO uploadDAO;

    @Inject
    ListFilesBean listfilesBean;

    @Inject
    FolderDAO folderDAO;

    @Inject
    FileDAO fileDAO;

    @Inject
    FolderFileDAO folderFileDAO;

    @Inject
    UserDAO userDAO;

    private static final Logger LOGGER = Logger.getLogger(FileUploadBean.class.getSimpleName());

    private UploadedFile file;

    public void handleFileUpload(FileUploadEvent event) {
        this.file = event.getFile();

        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest req = (HttpServletRequest) context.getExternalContext().getRequest();

        String fileName = null;
        try {
            fileName = new String(file.getFileName().getBytes("iso-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            LOGGER.error("FILE NAME COULD NOT BE CONVERTED!");
            return;
        }

        String filePath = req.getServletContext().getRealPath("") + File.separator
                + "Files";

        for (Folder parent : listfilesBean.getPath()) {
            filePath += java.io.File.separator + parent.getName();
        }

        File uploadFolder = new File(filePath);

        if (!uploadFolder.isDirectory()) {
            uploadFolder.mkdirs();
        }

        try (ByteArrayInputStream bais = new ByteArrayInputStream(file.getContents());
             DataOutputStream dos = new DataOutputStream(new FileOutputStream(uploadFolder + File.separator + fileName))) {
            byte[] buffer = new byte[1024];
            int r;

            while ((r = bais.read(buffer)) != -1) {
                dos.write(buffer, 0, r);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Folder parent = folderDAO.read(listfilesBean.getCurrentId());
        com.cat.prf.entity.File uploaded = new com.cat.prf.entity.File(fileName, file.getSize(), parent);

        fileDAO.create(uploaded);
        folderFileDAO.create(new FolderFile(parent.getId(), uploaded.getId()));

        User user = userDAO.getUserByName(listfilesBean.getUsername());
        uploadDAO.create(new Upload(user, new Date(System.currentTimeMillis())));
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }
}
