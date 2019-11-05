package com.jiang.ping.weixin.entity;

import lombok.Data;

/**
 * Song
 * song
 *
 * @author jphuang
 * @date 2019/11/2-17:22
 **/
public class SongBean {
    private String name;
    private Long id;
    private String alName;
    private String picUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAlName() {
        return alName;
    }

    public void setAlName(String alName) {
        this.alName = alName;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public SongBean(String name, Long id, String alName, String picUrl) {
        this.name = name;
        this.id = id;
        this.alName = alName;
        this.picUrl = picUrl;
    }

    public SongBean() {
    }
}
