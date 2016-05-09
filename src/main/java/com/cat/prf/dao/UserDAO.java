package com.cat.prf.dao;

import com.cat.prf.controller.ListfilesBean;
import com.cat.prf.entity.User;
import org.apache.commons.codec.digest.DigestUtils;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;
import java.util.logging.Logger;


public class UserDAO extends GenericDAO<User, Long> {

    private static final long serialVersionUID = -5859058016736013679L;
    private static final Logger LOGGER = Logger.getLogger(ListfilesBean.class.getSimpleName());


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

    public String getUserEmailAddress(String username) {
        TypedQuery<User> query = getEntityManager().createNamedQuery("getEmailByUname", getEntityClass());
        query.setParameter("username", username);
        return query.getSingleResult().getEmail();

    }

    @Transactional
    public User addUser(String username, String email, String password) {
        User user = new User();

        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(DigestUtils.sha256Hex(password));

        create(user);

        return user;
    }

    public User getUserByName(String username) {
        TypedQuery<User> query = getEntityManager().createNamedQuery("getEmailByUname", getEntityClass());
        query.setParameter("username", username);
        return query.getSingleResult();
    }
}
