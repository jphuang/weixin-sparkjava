package com.jk1091.weixin.main;

import com.jk1091.weixin.controller.FuliController;
import com.jk1091.weixin.controller.WeixinController;
import com.jk1091.weixin.dao.BaseDao;
import com.jk1091.weixin.model.TalkHistory;
import com.jk1091.weixin.process.WechatProcess;
import com.jk1091.weixin.service.FuliService;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.ModelAndView;
import spark.Request;
import spark.Route;
import spark.template.freemarker.FreeMarkerEngine;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.SparkBase.port;

public class HelloWorld {

    private static Logger log = LoggerFactory.getLogger(HelloWorld.class);

    public static void main(String[] args) {
        port(8078);
        post("/", WeixinController.index());
        get("/fuli", FuliController.fuli());
        BaseDao.start();
    }



    private static boolean shouldReturnHtml(Request request) {
        String accept = request.headers("Accept");
        return accept != null && accept.contains("text/html");
    }
}
