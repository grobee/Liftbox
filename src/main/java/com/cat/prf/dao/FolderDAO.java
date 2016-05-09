package com.cat.prf.dao;

import com.cat.prf.entity.File;
import com.cat.prf.entity.Folder;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by tommy on 2016. 04. 12..
 * Project name: liftbox
 */
public class FolderDAO extends GenericDAO<Folder, Long> {
    private static final Logger LOGGER = Logger.getLogger(FolderDAO.class.getSimpleName());
    private static final long serialVersionUID = -5859058016736013679L;

    public FolderDAO() {
        super(Folder.class);
    }

    public List<Folder> getFoldersDAO(long id) {
        TypedQuery<Folder> fileQuery = getEntityManager().createNamedQuery("selectSpecificFolder", Folder.class);
        fileQuery.setParameter("id", id);
        return fileQuery.getSingleResult().getFolders();
    }

    public int getCurrentId(String username) {
        TypedQuery<Folder> userQuery = getEntityManager().createNamedQuery("getFolderId", Folder.class);
        userQuery.setParameter("username", username);
        return (int) userQuery.getSingleResult().getId();
    }

    public List<File> getFilesDAO(long id) {
        TypedQuery<Folder> folderQuery = getEntityManager().createNamedQuery("selectSpecificFolder", Folder.class);
        folderQuery.setParameter("id", id);
        return folderQuery.getSingleResult().getFiles();
    }

    public List<Folder> getFoldersByUnameDAO(String username) {
        TypedQuery<Folder> userQuery = getEntityManager().createNamedQuery("getFolderId", Folder.class);
        userQuery.setParameter("username", username);
        return userQuery.getSingleResult().getFolders();
    }

    public List<File> getFilesByUnameDAO(String username) {
        TypedQuery<Folder> userQuery = getEntityManager().createNamedQuery("getFolderId", Folder.class);
        userQuery.setParameter("username", username);
        return userQuery.getSingleResult().getFiles();
    }

    @Transactional
    public Folder createNewFolder(String username) {
        Folder folder = new Folder();
        folder.setName(username);
        create(folder);

        return folder;
    }


    @Transactional
    public void createNewFolder(String foldername, long currid) {

        Folder folder = new Folder();
        folder.setName(foldername);
        create(folder);

        TypedQuery<Folder> fileQuery = getEntityManager().createNamedQuery("selectSpecificFolder", Folder.class);
        fileQuery.setParameter("id", currid);
        fileQuery.getSingleResult().getFolders().add(folder);

    }
}



