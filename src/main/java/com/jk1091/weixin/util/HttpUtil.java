package com.jk1091.weixin.util;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HttpUtil {
    public static String get(String url) {
        HttpGet request = new HttpGet(url);
        //解决中文问题。
        request.addHeader("Content-type", "application/json; charset=utf-8");
        request.setHeader("Accept", "application/json");
        StringBuilder result = new StringBuilder();//响应正文
        try {
            HttpResponse response = HttpClients.createDefault().execute(request);
            if (response.getStatusLine().getStatusCode() == 200) {
                InputStream instream = response.getEntity().getContent();

                BufferedReader br = new BufferedReader(new InputStreamReader(
                        instream, "utf-8"));
                String temp ;
                while ((temp = br.readLine()) != null) {
                    result.append(temp + "\n");
                }
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.deleteCharAt(0).toString();
    }
}
