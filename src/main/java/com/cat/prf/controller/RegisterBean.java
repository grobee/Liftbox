package com.cat.prf.controller;

import com.cat.prf.constants.Roles;
import com.cat.prf.dao.FolderDAO;
import com.cat.prf.dao.UserDAO;
import com.cat.prf.dao.UserRoleDAO;
import com.cat.prf.entity.User;
import com.cat.prf.entity.UserRole;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.logging.Logger;

@Named("registerBean")
@SessionScoped
public class RegisterBean implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(RegisterBean.class.getSimpleName());

    @Inject
    private UserDAO userDAO;

    @Inject
    UserRoleDAO userRoleDAO;

    @Inject
    FolderDAO folderDAO;

    private String uname;
    private String pass;
    private String email;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    @Transactional
    public String submit() {
        User user = userDAO.addUser(getUname(), getEmail(), getPass());

        user.setRootfolder(folderDAO.createNewFolder(user.getUsername()));
        userDAO.merge(user);

        UserRole role = new UserRole(user.getUsername(), Roles.USER.getName());
        userRoleDAO.create(role);

        return "login.xhtml?faces-redirect=true";
    }
}