package com.jk1091.weixin.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpUtil {
    private  static  CloseableHttpClient httpclient = HttpClients.createDefault();
    public static String get(String url) {
        HttpGet request = new HttpGet(url);
        //解决中文问题。
        request.addHeader("Content-type", "application/json; charset=utf-8");
        request.setHeader("Accept", "application/json");
        StringBuilder result = new StringBuilder();//响应正文
        try {
            HttpResponse response = httpclient.execute(request);
            if (response.getStatusLine().getStatusCode() == 200) {
                getSB(result, response);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }


    public static String post(String url,Map<String,Object> map) {
        HttpPost request = new HttpPost(url);
        //解决中文问题。
        request.addHeader("Content-type", "application/json; charset=utf-8");
        request.setHeader("Accept", "application/json");
        List<NameValuePair> nvps = new ArrayList<>();
        map.forEach((k,v) ->{
            nvps.add(new BasicNameValuePair(k, v.toString()));
        });
        System.out.println(nvps);
        try {
            request.setEntity(new UrlEncodedFormEntity(nvps));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        StringBuilder result = new StringBuilder();//响应正文
        try {
            HttpResponse response = HttpClients.createDefault().execute(request);
            if (response.getStatusLine().getStatusCode() == 200) {
                getSB(result, response);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    private static void getSB(StringBuilder result, HttpResponse response) throws IOException {
        InputStream instream = response.getEntity().getContent();
        BufferedReader br = new BufferedReader(new InputStreamReader(
                instream, "utf-8"));
        String temp ;
        while ((temp = br.readLine()) != null) {
            result.append(temp + "\n");
        }
    }
}
