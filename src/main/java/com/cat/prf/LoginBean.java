package com.cat.prf;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.logging.Logger;

@Named("loginBean")
@SessionScoped
public class LoginBean implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(LoginBean.class.getSimpleName());


    private String uname;
    private String pass;

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
        LOGGER.info("\nuname: " + getUname() +"\npass: " +getPass());
    }
}
