package com.jiang.ping.weixin.model;

import com.jfinal.plugin.activerecord.Model;

/**
 * 聊天歷史
 *
 * @author jphua
 * @date 2018-05-29 16:22
 **/
public class User extends Model<User> {
    public static final User dao = new User().dao();
}
