package com.cat.prf.controller;

import com.cat.prf.dao.UserDAO;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Logger;

@Named("loginBean")
@SessionScoped
public class LoginBean implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(LoginBean.class.getSimpleName());

    @Inject
    private UserDAO userDAO;

    private String uname;
    private String pass;
    private boolean isError;

    public LoginBean() {
        setError(false);
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

    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        isError = error;
    }


    public void submit() {
        LOGGER.info("\nuname: " + getUname() + " \npass: "+ getPass());
        if (userDAO.isUser(getUname(), getPass())) {
            setError(false);
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else  {
            setError(true);
        }

    }
}
