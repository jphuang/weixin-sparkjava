package com.jk1091.weixin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Song
 * song
 *
 * @author jphuang
 * @date 2019/11/2-17:22
 **/
@Data
public class SongBean {
    private String name;
    private Long id;
    private String alName;
    private String picUrl;

    public SongBean(String name, Long id, String alName, String picUrl) {
        this.name = name;
        this.id = id;
        this.alName = alName;
        this.picUrl = picUrl;
    }

    public SongBean() {
    }
}
