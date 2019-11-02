package com.jk1091.weixin.entity;

/**
 * Song
 * song
 *
 * @author jphuang
 * @date 2019/11/2-17:22
 **/
public class Song {
    private String name;
    private Long id;
    private String alName;
    private String picUrl;

    public Song() {

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAlName(String alName) {
        this.alName = alName;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public Song(String name, Long id, String alName, String picUrl) {
        this.name = name;
        this.id = id;
        this.alName = alName;
        this.picUrl = picUrl;
    }
}
