package com.cat.prf.dao;

import com.cat.prf.entity.User;
import com.cat.prf.entity.UserRole;

import javax.persistence.TypedQuery;

public class UserRoleDAO extends GenericDAO<UserRole, Long> {

    public UserRoleDAO() {
        super(UserRole.class);
    }

    public UserRole getRoleByUsername(String username) {
        TypedQuery<UserRole> query = getEntityManager().createQuery("select u from UserRole u where u.username = :username", getEntityClass());
        query.setParameter("username", username);
        return query.getSingleResult();
    }

}
