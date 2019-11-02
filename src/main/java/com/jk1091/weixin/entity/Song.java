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
@NoArgsConstructor
@AllArgsConstructor
public class Song {
    private String name;
    private Long id;
    private String alName;
    private String picUrl;
}
