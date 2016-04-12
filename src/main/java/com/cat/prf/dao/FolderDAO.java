package com.cat.prf.dao;

import com.cat.prf.controller.ListfilesBean;
import com.cat.prf.entity.File;
import com.cat.prf.entity.Folder;
import com.cat.prf.entity.User;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by tommy on 2016. 04. 12..
 * Project name: liftbox
 */
public class FolderDAO extends GenericDAO<Folder, Long> {
    private static final Logger LOGGER = Logger.getLogger(ListfilesBean.class.getSimpleName());


    private static final long serialVersionUID = -5859058016736013679L;

    public FolderDAO() {
        super(Folder.class);
    }


    public List<Folder> getFolders() {
//        LOGGER.info("\tuserid " + userQuery.getSingleResult().getName());
//
//        for (File f : userQuery.getSingleResult().getFiles()) {
//            LOGGER.info("\tname: " + f.getName());
//        }
//
//
//        for (Folder f : userQuery.getSingleResult().getFolders()) {
//            LOGGER.info("\tname: " + f.getName());
//        }
        TypedQuery<Folder> userQuery = getEntityManager().createNamedQuery("getFolderId", Folder.class);
        userQuery.setParameter("username", "admin");
        return userQuery.getSingleResult().getFolders();


    }


    public List<File> getFiles() {
        TypedQuery<Folder> userQuery = getEntityManager().createNamedQuery("getFolderId", Folder.class);
        userQuery.setParameter("username", "admin");
        return userQuery.getSingleResult().getFiles();
    }

}



