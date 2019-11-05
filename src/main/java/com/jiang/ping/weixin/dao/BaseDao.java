package com.jiang.ping.weixin.dao;

import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jiang.ping.weixin.model.TalkHistory;
import com.jiang.ping.weixin.model.User;

/**
 * baseDao
 *
 * @author jphua
 * @date 2018-05-29 16:15
 **/
public class BaseDao {

    public static void start() {
        PropKit.use("config.txt");
        String userName = PropKit.get("userName");
        String password = PropKit.get("password");
        DruidPlugin dp = new DruidPlugin("jdbc:mysql://localhost/test?serverTimezone=GMT&useUnicode=yes&characterEncoding=UTF-8&autoReconnect=true&useSSL=false", userName, password);
        ActiveRecordPlugin arp = new ActiveRecordPlugin(dp);
        arp.addMapping("talk_history",TalkHistory.class);
        arp.addMapping("user",User.class);
        dp.start();
        arp.start();
    }
}
