package com.cat.prf.controller;

/**
 * Created by tommy on 2016. 05. 09..
 * Project name: liftbox
 */

import com.cat.prf.dao.FolderDAO;
import com.cat.prf.dao.UserDAO;
import com.cat.prf.dao.UserRoleDAO;
import com.cat.prf.entity.File;
import com.cat.prf.entity.Folder;
import com.cat.prf.entity.User;
import com.cat.prf.entity.UserRole;

import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


@Named("adminPanelBean")
@ViewScoped
public class AdminPanelBean implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(AdminPanelBean.class.getSimpleName());


    private List<User> users = new ArrayList<>();

    @Inject
    UserDAO userDAO;

    @Inject
    UserRoleDAO userRoleDAO;


    public List<User> getUsers() {
        users=userDAO.listFiles();

        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String getRole(String uname) {
        UserRole role = userRoleDAO.getRoleByUsername(uname);

        return role.getRole();
    }

    public void setRole(String uname) {

        UserRole role = userRoleDAO.getRoleByUsername(uname);

        if (role.getRole().equals("USER")) {

            role.setRole("ADMIN");
        }
        else {
            role.setRole("USER");
        }

    }


}

