package com.cat.prf.dao;

import com.cat.prf.entity.FolderFile;

import javax.inject.Named;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

@Named
public class FolderFileDAO extends GenericDAO<FolderFile, Long> {

    private static final long serialVersionUID = -5859058213757813679L;

    public FolderFileDAO() {
    }

    public FolderFileDAO(Class<FolderFile> entityClass) {
        super(FolderFile.class);
    }

    @Transactional
    public void deleteByFile(long id) {
        TypedQuery<FolderFile> query = getEntityManager().createNamedQuery("findFolderFile", getEntityClass());
        query.setParameter("fileId", id);
        FolderFile folderFile = query.getSingleResult();
        deleteEntity(folderFile);
    }
}
