package com.cat.prf.dao;

import com.cat.prf.entity.File;

import javax.persistence.TypedQuery;
import java.util.List;

public class FileDAO extends GenericDAO<File, Long> {

    private static final long serialVersionUID = -5859058016736013679L;

    public FileDAO() {
        super(File.class);
    }

    public List<File> listFiles() {
        TypedQuery<File> query = getEntityManager().createNamedQuery("listFiles", getEntityClass());
        return query.getResultList();
    }
}
