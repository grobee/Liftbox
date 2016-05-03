package com.cat.prf.controller;

import com.cat.prf.dao.UserDAO;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.logging.Logger;

/**
 * Created by tommy on 2016. 05. 02..
 * Project name: liftbox
 */

@Named("registerBean")
@SessionScoped

public class RegisterBean implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(RegisterBean.class.getSimpleName());

    @Inject
    private UserDAO userDAO;

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


    public void submit() {
        userDAO.addUser(getUname(),getEmail(),getPass());
    }
}