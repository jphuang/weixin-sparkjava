package com.jiang.ping.weixin.controller;

import com.jiang.ping.weixin.main.HelloWorld;
import com.jiang.ping.weixin.service.FuliService;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import spark.ModelAndView;
import spark.Route;
import spark.template.freemarker.FreeMarkerEngine;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jphuang
 * @version V1.0
 * @Package com.jk1091.weixin.controller
 * @Description: fuli
 * @date 18/3/3 下午1:00
 */
public class FuliController {
    private static FreeMarkerEngine freeMarkerEngine = new FreeMarkerEngine();
    private static Configuration freeMarkerConfiguration = new Configuration();

    public static Route fuli() {
        freeMarkerConfiguration.setTemplateLoader(new ClassTemplateLoader(HelloWorld.class, "/"));
        freeMarkerEngine.setConfiguration(freeMarkerConfiguration);
        return (req, res) -> {
            Map attributes = new HashMap<>();
            attributes.put("list",new FuliService().list());
            return freeMarkerEngine.render(new ModelAndView(attributes, "posts.ftl"));
        };

    }
}
