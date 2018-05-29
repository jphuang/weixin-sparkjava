package com.jk1091.weixin.controller;

import com.jk1091.weixin.process.WechatProcess;
import com.jk1091.weixin.util.EncryptUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Route;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * @author jphuang
 * @version V1.0
 * @Package com.jk1091.weixin.controller
 * @Description: wexin
 * @date 18/3/3 下午1:01
 */
public class WeixinController {
    private static Logger log = LoggerFactory.getLogger(WeixinController.class);
    private static final String token = "HSEHVTflgeuccUXa";
    public static Route index() {
        return (req, res) -> {
            String result = "";
            String signature = req.queryParams("signature");
            String timestamp = req.queryParams("timestamp");
            String nonce = req.queryParams("nonce");
            String echoStr = req.queryParams("echoStr");
            String body = req.body();
            log.debug("xml:" + body);
            if (!EncryptUtil.check(token,signature, timestamp, nonce)) {
                return "非法请求";
            }
            if (echoStr != null && echoStr.length() > 1) {
                return echoStr;
            }
            try {
                result = new WechatProcess().processWechatMag(body);
                log.debug("result:" + result);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return result;
        };
    }




}
