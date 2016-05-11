package com.cat.prf.controller;

import com.cat.prf.dao.DownloadDAO;
import com.cat.prf.dao.UploadDAO;
import com.cat.prf.dao.UserDAO;
import com.cat.prf.entity.Download;
import com.cat.prf.entity.Upload;
import com.cat.prf.entity.User;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;

@Named
@RequestScoped
public class StatisticsBean {
    private BarChartModel model;

    @Inject
    UserDAO userDAO;

    @Inject
    UploadDAO uploadDAO;

    @Inject
    DownloadDAO downloadDAO;

    FacesContext context = FacesContext.getCurrentInstance();

    private static final Logger LOGGER = Logger.getLogger(StatisticsBean.class.getSimpleName());

    public void generateView() {
        model = new BarChartModel();

        String username = context.getExternalContext().getUserPrincipal().getName();
        User user = userDAO.getUserByName(username);
        List<Download> downloadList = downloadDAO.getDownloadsByUser(user);
        List<Upload> uploadList = uploadDAO.getUploadsByUser(user);

        TreeMap<Date, Integer> downloadsMap = new TreeMap<>();
        TreeMap<Date, Integer> uploadsMap = new TreeMap<>();

        SimpleDateFormat format = new SimpleDateFormat("YYYY.MM.dd");

        for (Upload upload : uploadList) {
            downloadsMap.put(upload.getDate(), 0);
        }

        for (Download download : downloadList) {
            uploadsMap.put(download.getDate(), 0);
        }

        int max = Integer.MIN_VALUE;

        for (Download download : downloadList) {
            if (downloadsMap.containsKey(download.getDate())) {
                downloadsMap.put(download.getDate(), downloadsMap.get(download.getDate()) + 1);
            } else {
                downloadsMap.put(download.getDate(), 1);
            }

            if(downloadsMap.get(download.getDate()) > max) {
                max = downloadsMap.get(download.getDate());
            }
        }

        for (Upload upload : uploadList) {
            if (uploadsMap.containsKey(upload.getDate())) {
                uploadsMap.put(upload.getDate(), uploadsMap.get(upload.getDate()) + 1);
            } else {
                uploadsMap.put(upload.getDate(), 1);
            }

            if(uploadsMap.get(upload.getDate()) > max) {
                max = downloadsMap.get(upload.getDate());
            }
        }

        ChartSeries downloads = new ChartSeries();
        ChartSeries uploads = new ChartSeries();
        downloads.setLabel("Download");
        uploads.setLabel("Upload");

        for (Map.Entry<Date, Integer> entry : downloadsMap.entrySet()) {
            downloads.set(format.format(entry.getKey()), entry.getValue());
            LOGGER.info(String.valueOf(entry.getKey()));
        }

        for (Map.Entry<Date, Integer> entry : uploadsMap.entrySet()) {
            uploads.set(format.format(entry.getKey()), entry.getValue());
            LOGGER.info(String.valueOf(entry.getKey()));
        }

        model.addSeries(downloads);
        model.addSeries(uploads);
        model.setTitle("User Activity");
        model.setLegendPosition("ne");

        Axis xAxis = model.getAxis(AxisType.X);
        xAxis.setLabel("Date");

        Axis yAxis = model.getAxis(AxisType.Y);
        yAxis.setLabel("Download");
        yAxis.setMin(0);
        yAxis.setMax(max);
    }

    @PostConstruct
    public void initialize() {

    }

    public BarChartModel getModel() {
        return model;
    }
}
