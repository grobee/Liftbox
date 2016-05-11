package com.cat.prf.dao;

import com.cat.prf.entity.Folder;
import com.cat.prf.entity.FolderFolder;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

public class FolderFolderDAO extends GenericDAO<FolderFolder, Long> {
    public FolderFolderDAO() {
        super(FolderFolder.class);
    }

    public FolderFolder getRelationshipByChild(long id) {
        TypedQuery<FolderFolder> query = getEntityManager()
                .createNamedQuery("getRelationshipByChildFolder", FolderFolder.class);
        query.setParameter("childid", id);
        return query.getSingleResult();
    }

    public void deleteChildFolder(long id) {
        FolderFolder folderFolder = getRelationshipByChild(id);
        deleteEntity(folderFolder);
    }
}
