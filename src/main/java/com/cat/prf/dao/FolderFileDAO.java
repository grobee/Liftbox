package com.cat.prf.dao;

import com.cat.prf.entity.FolderFile;

import javax.inject.Named;

@Named
public class FolderFileDAO extends GenericDAO<FolderFile, Long> {

    private static final long serialVersionUID = -5859058213757813679L;

    public FolderFileDAO() {
    }

    public FolderFileDAO(Class<FolderFile> entityClass) {
        super(FolderFile.class);
    }

    public void doSomething() {

    }
}
