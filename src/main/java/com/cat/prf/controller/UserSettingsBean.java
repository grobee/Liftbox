package com.cat.prf.controller;

/**
 * Created by tommy on 2016. 05. 09..
 * Project name: liftbox
 */

import com.cat.prf.constants.Roles;
import com.cat.prf.dao.FolderDAO;
import com.cat.prf.dao.UserDAO;
import com.cat.prf.dao.UserRoleDAO;
import com.cat.prf.entity.User;
import com.cat.prf.entity.UserRole;
import org.apache.commons.codec.digest.DigestUtils;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.logging.Logger;

/**
 * Created by tommy on 2016. 05. 02..
 * Project name: liftbox
 */

@Named("userSettingsBean")
@SessionScoped
public class UserSettingsBean implements Serializable {

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

    public String getOldEmail(String uname) {
        return userDAO.getUserEmailAddress(uname);
    }


    public void init(String uname) {
        this.email = getOldEmail(uname);
        this.uname = uname;

    }

    @Transactional
    public void save() {
        User u = userDAO.getUserByName(uname);

        if (email.equals("")) {
            LOGGER.info(email);
            u.setEmail(email);
        }

        if (pass.equals("")) {
            LOGGER.info(pass);
            u.setPassword(DigestUtils.sha256Hex(pass));
        }

    }


}
