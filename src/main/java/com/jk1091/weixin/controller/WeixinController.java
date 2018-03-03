package com.jk1091.weixin.controller;

import com.jk1091.weixin.process.WechatProcess;
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
            strDes = bytes2Hex(md.digest());
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
}
