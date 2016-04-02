package com.cat.prf.controller;

import com.cat.prf.dao.FileDAO;
import com.cat.prf.entity.File;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


@Named("listfilesBean")
@SessionScoped
public class ListfilesBean implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(ListfilesBean.class.getSimpleName());

    // It would duplicate the table at every site refresh without this
    private boolean firstRun;

    private List<File> files = new ArrayList<>();

    @Inject
    private FileDAO fileDAO;

    public ListfilesBean() {
        firstRun = true;
    }

    public List<File> getFiles() {

        if (firstRun) {
            for (File f : fileDAO.listFiles()) {
                LOGGER.info("\nid: " + f.getId() + " \nname: " + f.getName() + " \nsize: " + f.getSize());
                files.add(f);

            }
        }
        firstRun = false;
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

}
