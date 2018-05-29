package com.jk1091.weixin.dao;

import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jk1091.weixin.model.TalkHistory;

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
        DruidPlugin dp = new DruidPlugin("jdbc:mysql://localhost/test?serverTimezone=GMT&useUnicode=yes&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&autoReconnect=true", userName, password);
        ActiveRecordPlugin arp = new ActiveRecordPlugin(dp);
        arp.addMapping("talk_history",TalkHistory.class);
        dp.start();
        arp.start();

        TalkHistory byId = TalkHistory.dao.findById(1);
        System.out.println(byId);
    }
}
