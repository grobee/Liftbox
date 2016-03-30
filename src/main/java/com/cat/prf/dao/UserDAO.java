package com.cat.prf.dao;

import com.cat.prf.entity.User;

import javax.persistence.TypedQuery;
import java.util.List;

public class UserDAO extends GenericDAO<User, Long> {
    public UserDAO() {
        super(User.class);
    }

    public List<User> listFiles() {
        TypedQuery<User> query = getEntityManager().createNamedQuery("listUsers", getEntityClass());
        return query.getResultList();
    }
}
