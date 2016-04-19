package com.cat.prf.controller;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@Named
@ApplicationScoped
public class MenuBean {
    private List<Page> pages = new ArrayList<>();

    @PostConstruct
    public void initialize() {
        pages.add(new Page("Files", "/listfiles.xhtml"));
    }

    public List<Page> getPages() {
        return pages;
    }

    public class Page {
        private String title;
        private String viewId;

        Page(String title, String viewId) {
            this.title = title;
            this.viewId = viewId;
        }

        public String getTitle() {
            return title;
        }

        public String getViewId() {
            return viewId;
        }
    }
}
