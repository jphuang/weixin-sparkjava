package com.jk1091.weixin.model;

import com.jfinal.plugin.activerecord.Model;

/**
 * 聊天歷史
 *
 * @author jphua
 * @date 2018-05-29 16:22
 **/
public class TalkHistory extends Model<TalkHistory> {
    public static final TalkHistory dao = new TalkHistory().dao();
}
