package com.jiang.ping.weixin.main;

import com.jiang.ping.weixin.controller.FuliController;
import com.jiang.ping.weixin.controller.WeixinController;
import com.jiang.ping.weixin.dao.BaseDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.SparkBase.port;

public class HelloWorld {

    private static Logger log = LoggerFactory.getLogger(HelloWorld.class);

    public static void main(String[] args) {
        port(8078);
        post("/", WeixinController.index());
        get("/", WeixinController.index());
        get("/fuli", FuliController.fuli());
        BaseDao.start();
    }



    private static boolean shouldReturnHtml(Request request) {
        String accept = request.headers("Accept");
        return accept != null && accept.contains("text/html");
    }
}
