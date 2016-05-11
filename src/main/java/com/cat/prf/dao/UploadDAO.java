package com.cat.prf.dao;

import com.cat.prf.entity.Upload;
import com.cat.prf.entity.User;

import javax.persistence.TypedQuery;
import java.util.List;

public class UploadDAO extends GenericDAO<Upload, Long> {
    public UploadDAO() {
        super(Upload.class);
    }

    public List<Upload> getUploadsByUser(User user) {
        TypedQuery<Upload> query = getEntityManager().createNamedQuery("getUploadsByUser", Upload.class);
        query.setParameter("user", user);
        return query.getResultList();
    }
}
