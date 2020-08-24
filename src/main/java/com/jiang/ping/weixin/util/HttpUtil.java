package com.jiang.ping.weixin.util;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author jphua
 */
public class HttpUtil {
    private static  Logger logger = LoggerFactory.getLogger(HttpUtil.class);
    private  static  CloseableHttpClient httpclient = HttpClients.createDefault();
    private static  final int  SUCCESS_CODE  = 200;
    public static String get(String url) {
        HttpGet request = new HttpGet(url);
        //解决中文问题。
        request.addHeader("Content-type", "application/json; charset=utf-8");
        //request.setHeader("Accept", "application/json");
        //响应正文
        String result = "";
        try {
            HttpResponse response = httpclient.execute(request);
            if (response.getStatusLine().getStatusCode() == SUCCESS_CODE) {
                result = getString(response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    public static String post(String url,Map<String,Object> map) {
        HttpPost request = new HttpPost(url);
        //解决中文问题。
        request.addHeader("Content-type", "application/json; charset=utf-8");
        request.setHeader("Accept", "application/json");
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        map.forEach((k,v) ->{
            nameValuePairs.add(new BasicNameValuePair(k, v.toString()));
        });
        logger.info("pair->{}",nameValuePairs);
        try {
            request.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        } catch (UnsupportedEncodingException e) {
           logger.error("error:{}",e.getMessage(),e);
        }
        //响应正文
        String result ="";
        try {
            HttpResponse response = HttpClients.createDefault().execute(request);
            if (response.getStatusLine().getStatusCode() == SUCCESS_CODE) {
                result  =  getString(response);
            }
        } catch (IOException e) {
            logger.error("error:{}",e.getMessage(),e);
        }
        return result;
    }

    private static String getString(HttpResponse response) throws IOException {
        StringBuilder result = new StringBuilder();
        InputStream instream = response.getEntity().getContent();
        BufferedReader br = new BufferedReader(new InputStreamReader(
                instream, "utf-8"));
        String temp ;
        while ((temp = br.readLine()) != null) {
            result.append(temp).append("\n");
        }
        return result.toString();
    }
}
