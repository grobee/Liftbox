package com.cat.prf.dao;


import com.cat.prf.entity.Download;
import com.cat.prf.entity.User;

import javax.persistence.TypedQuery;
import java.util.List;

public class DownloadDAO extends GenericDAO<Download, Long> {
    public DownloadDAO() {
        super(Download.class);
    }

    public List<Download> getDownloadsByUser(User user) {
        TypedQuery<Download> query = getEntityManager().createNamedQuery("getDownloadsByUser", Download.class);
        query.setParameter("user", user);
        return query.getResultList();
    }
}
