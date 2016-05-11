package com.cat.prf.controller;

import com.cat.prf.dao.FileDAO;
import com.cat.prf.dao.FolderDAO;
import com.cat.prf.dao.FolderFileDAO;
import com.cat.prf.entity.Folder;
import com.cat.prf.entity.FolderFile;
import org.jboss.logging.Logger;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.io.*;

@Named
@RequestScoped
public class FileUploadBean {

    @Inject
    ListFilesBean listfilesBean;

    @Inject
    FolderDAO folderDAO;

    @Inject
    FileDAO fileDAO;

    @Inject
    FolderFileDAO folderFileDAO;

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

        String username = req.getUserPrincipal().getName();
        File uploadFolder = new File(req.getServletContext().getRealPath("") + File.separator
                + "Files" + File.separator + username);

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
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }
}
