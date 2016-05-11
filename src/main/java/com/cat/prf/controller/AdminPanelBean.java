package com.cat.prf.controller;


import com.cat.prf.dao.DownloadDAO;
import com.cat.prf.dao.UploadDAO;
import com.cat.prf.dao.UserDAO;
import com.cat.prf.dao.UserRoleDAO;
import com.cat.prf.entity.Download;
import com.cat.prf.entity.Upload;
import com.cat.prf.entity.User;
import com.cat.prf.entity.UserRole;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
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

    @Inject
    UploadDAO uploadDAO;

    @Inject
    DownloadDAO downloadDAO;

    private String selectedRole;


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

    public String setRole(String uname) {

        UserRole role = userRoleDAO.getRoleByUsername(uname);

        if (role.getRole().equals("USER")) {

            role.setRole("ADMIN");
        }
        else {
            role.setRole("USER");
        }

        userRoleDAO.merge(role);
        return "adminpanel.xhtml?faces-redirect=true";
    }

    @Transactional
    public String deleteUser(int id) {
        User user = userDAO.read(id);

        UserRole role = userRoleDAO.getRoleByUsername(user.getUsername());
        userRoleDAO.deleteEntity(role);

        List<Download> downloads = downloadDAO.getDownloadsByUser(user);
        for(Download download : downloads) {
            downloadDAO.deleteEntity(download);
        }

        List<Upload> uploads = uploadDAO.getUploadsByUser(user);
        for(Upload upload : uploads) {
            uploadDAO.deleteEntity(upload);
        }

        userDAO.delete(id);

        return "adminpanel.xhtml?faces-redirect=true";
    }

    public String getSelectedRole() {
        return selectedRole;
    }

    public void setSelectedRole(String selectedRole) {
        this.selectedRole = selectedRole;
    }
}

