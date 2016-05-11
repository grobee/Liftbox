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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

        HashMap<Date, Integer> downloadsMap = new HashMap<>();
        HashMap<Date, Integer> uploadsMap = new HashMap<>();

        SimpleDateFormat format = new SimpleDateFormat("YYYY.MM.dd");

        for(Upload upload : uploadList) {
            downloadsMap.put(upload.getDate(), 0);
        }

        for(Download download : downloadList) {
            uploadsMap.put(download.getDate(), 0);
        }

        for(Download download : downloadList) {
            if(downloadsMap.containsKey(download.getDate())) {
                downloadsMap.put(download.getDate(), downloadsMap.get(download.getDate()) + 1);
            } else {
                downloadsMap.put(download.getDate(), 1);
            }
        }

        for(Upload upload : uploadList) {
            if(downloadsMap.containsKey(upload.getDate())) {
                uploadsMap.put(upload.getDate(), uploadsMap.get(upload.getDate()) + 1);
            } else {
                uploadsMap.put(upload.getDate(), 1);
            }
        }

        LOGGER.info(downloadList.size() + " " + uploadList.size());

        ChartSeries downloads = new ChartSeries();
        ChartSeries uploads = new ChartSeries();
        downloads.setLabel("Download");
        uploads.setLabel("Upload");

        for(Map.Entry<Date, Integer> entry : downloadsMap.entrySet()) {
            downloads.set(format.format(entry.getKey()), entry.getValue());
        }

        for(Map.Entry<Date, Integer> entry : uploadsMap.entrySet()) {
            uploads.set(format.format(entry.getKey()), entry.getValue());
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
        yAxis.setMax(50);
    }

    @PostConstruct
    public void initialize() {

    }

    public BarChartModel getModel() {
        return model;
    }
}
