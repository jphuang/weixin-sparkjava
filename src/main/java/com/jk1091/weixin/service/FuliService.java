package com.jk1091.weixin.service;

import com.jk1091.weixin.util.HttpUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

/**
 * Copyright (C), 2017-2017, 真知码
 * FileName: FuliService
 * Author:   jphuang
 * Date:     2017/9/22 10:43
 * Description: 福利service
 */
public class FuliService {
    String api  = "http://gank.io/api/data/%E7%A6%8F%E5%88%A9/20/1";
    private  Logger log = LoggerFactory.getLogger(FuliService.class);
    private   Random  random  = new Random();

    public JSONArray list(){
        String post = HttpUtil.get(api);
        JSONObject jsonArray = JSONObject.fromObject(post);
        return jsonArray.getJSONArray("results");
    }

    public JSONObject getImage(Integer i){
        String api  = "http://gank.io/api/data/%E7%A6%8F%E5%88%A9/1/" + i;
        String post = HttpUtil.get(api);
        JSONObject jsonArray = JSONObject.fromObject(post);
        JSONArray array =  jsonArray.getJSONArray("results");
        return array.getJSONObject(0);
    }
    public String fuli(String toUser,String fromUser){
        Integer i =random.nextInt(593) + 1;
        JSONObject json = getImage(i);
        String url = json.getString("url");
        String result = "<xml>" +
                "<ToUserName><![CDATA[%s]]></ToUserName>" +
                "<FromUserName><![CDATA[%s]]></FromUserName>" +
                "<CreateTime>%d</CreateTime>" +
                "<MsgType><![CDATA[news]]></MsgType>" +
                "<ArticleCount>1</ArticleCount>" +
                "<Articles>" +
                "<item>" +
                "<Title><![CDATA[%S]]></Title> " +
                "<Description><![CDATA[%s]]></Description>" +
                "<PicUrl><![CDATA[%s]]></PicUrl>" +
                "<Url><![CDATA[%s]]></Url>" +
                "</item>" +
                "</Articles>" +
                "</xml>";
        return String.format(result, toUser,fromUser,System.currentTimeMillis(),json.getString("who"),"",url,url);
    }
}
