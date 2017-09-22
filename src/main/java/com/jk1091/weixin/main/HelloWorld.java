package com.jk1091.weixin.main;

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

    private static final String token = "HSEHVTflgeuccUXa";
    private static Logger log = LoggerFactory.getLogger(HelloWorld.class);

    private static FreeMarkerEngine freeMarkerEngine = new FreeMarkerEngine();
    private static Configuration freeMarkerConfiguration = new Configuration();


    public static void main(String[] args) {
        port(8078);
        post("/", weixinController());
        get("/fuli", fuliController());
    }

    private static Route fuliController() {
        freeMarkerConfiguration.setTemplateLoader(new ClassTemplateLoader(HelloWorld.class, "/"));
        freeMarkerEngine.setConfiguration(freeMarkerConfiguration);
        return (req, res) -> {
            Map attributes = new HashMap<>();
            attributes.put("list",new FuliService().list());
            return freeMarkerEngine.render(new ModelAndView(attributes, "posts.ftl"));
        };

    }

    private static Route weixinController() {
        return (req, res) -> {
            String result = "";
            try {
                String signature = req.queryParams("signature");
                String timestamp = req.queryParams("timestamp");
                String nonce = req.queryParams("nonce");
                String echostr = req.queryParams("echostr");
                String bady = req.body();
                log.debug("xml:" + bady);
                if (check(signature, timestamp, nonce)) {
                    if (echostr != null && echostr.length() > 1) {
                        result = echostr;
                    } else {
                        result = new WechatProcess().processWechatMag(bady);
                    }
                }
                log.debug("result:" + result);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return result;
            }
            return result;
        };
    }

    private static boolean check(String signature, String timestamp, String nonce) {
        String[] ArrTmp = {token, timestamp, nonce};
        Arrays.sort(ArrTmp);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < ArrTmp.length; i++) {
            sb.append(ArrTmp[i]);
        }
        String tempStr = encrypt(sb.toString());
        if (signature.equals(tempStr)) {
            return true;
        }
        return false;
    }

    private static String encrypt(String strSrc) {
        MessageDigest md = null;
        String strDes = null;

        byte[] bt = strSrc.getBytes();
        try {
            md = MessageDigest.getInstance("SHA-1");
            md.update(bt);
            strDes = bytes2Hex(md.digest()); //to HexString  
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Invalid algorithm.");
            return null;
        }
        return strDes;
    }

    public static String bytes2Hex(byte[] bts) {
        String des = "";
        String tmp = null;
        for (int i = 0; i < bts.length; i++) {
            tmp = (Integer.toHexString(bts[i] & 0xFF));
            if (tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }
        return des;
    }

    private static boolean shouldReturnHtml(Request request) {
        String accept = request.headers("Accept");
        return accept != null && accept.contains("text/html");
    }
}
