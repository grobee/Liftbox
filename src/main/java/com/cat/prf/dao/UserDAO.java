package com.cat.prf.dao;

import com.cat.prf.entity.User;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

public class UserDAO extends GenericDAO<User, Long> {

    private static final long serialVersionUID = -5859058016736013679L;

    public UserDAO() {
        super(User.class);
    }

    public List<User> listFiles() {
        TypedQuery<User> query = getEntityManager().createNamedQuery("listUsers", getEntityClass());
        return query.getResultList();
    }

    public boolean isUser(String username, String password) {
        Query query = getEntityManager().createNamedQuery("findUser");

        query.setParameter("username", username);
        query.setParameter("password", password);

        return query.getResultList().size() != 0;
    }
}
