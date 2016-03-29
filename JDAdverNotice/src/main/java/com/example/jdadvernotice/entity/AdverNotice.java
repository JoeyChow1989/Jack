package com.example.jdadvernotice.entity;

/**
 * Created by Administrator on 2016/3/20.
 */
public class AdverNotice {
    public String title;
    public String url;

    public AdverNotice(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
