package com.imooc.festivalsms.bean;

/**
 * Created by Administrator on 2015/11/30.
 */
public class Msg {

    private int id;
    private int festivalId;
    private String content;

    public Msg(int id, int festivalId, String content) {
        this.id = id;
        this.festivalId = festivalId;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFestivalId() {
        return festivalId;
    }

    public void setFestivalId(int festivalId) {
        this.festivalId = festivalId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
