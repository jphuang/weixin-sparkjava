package process;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import entity.ReceiveXmlEntity;

public class TulingApiProcess {
	/** 
     * 调用图灵机器人api接口，获取智能回复内容，解析获取自己所需结果 
     * @param content 
     * @return 
     */  
    public String getTulingResult(String content,ReceiveXmlEntity xmlEntity){  
    	String fromUserName = xmlEntity.getFromUserName();
    	String toUserName = xmlEntity.getToUserName();
        String result = getResultForTuling(content, fromUserName);  
        return handleResult(content, fromUserName, toUserName, result);  
    }

	private String handleResult(String content, String fromUserName,
			String toUserName, String result) {
		/** 请求失败处理 */  
        if(null==result){  
            return "对不起，你说的话真是太高深了……";  
        }  
          
        try {  
            JSONObject json = JSONObject.fromObject(result);
            //以code=100000 ,位子信息，参考图灵机器人api文档  
            if(100000==json.getInt("code")){  
                result = json.getString("text");  
                result = new FormatXmlProcess().formatXmlText(fromUserName, toUserName, result);
            } 
            
            if(200000==json.getInt("code")){  
                result = json.getString("text");  
                String url =  json.getString("url");  
                result = new FormatXmlProcess().formatXmlLink(fromUserName, toUserName,  result, content, url);
            }
            
            if(308000==json.getInt("code")){  
            	result = json.getString("text");  
            	JSONArray list =  json.getJSONArray("list");
            	result = new FormatXmlProcess().formatXmlCookbook(fromUserName, toUserName, list);
            }
            
            if(302000==json.getInt("code")){  
            	result = json.getString("text");  
            	JSONArray list =  json.getJSONArray("list");
            	result = new FormatXmlProcess().formatXmlNew(fromUserName, toUserName, list);
            }
            
            
            
        } catch (JSONException e) {  
            e.printStackTrace();  
        }  
        return result.replace("&gt;", " >");
	}

	private String getResultForTuling(String content, String userName ) {
		/** 此处为图灵api接口，参数key需要自己去注册申请 */  
        String apiUrl = "http://182.92.68.93/openapi/api?key=xxx&userid="+userName+"&info=";  
        String param = "";  
        try {  
            param = apiUrl+URLEncoder.encode(content,"utf-8");  
        } catch (UnsupportedEncodingException e1) {  
            e1.printStackTrace();  
        } //将参数转为url编码  
          
        /** 发送httpget请求 */  
        HttpGet request = new HttpGet(param);  
        String result = null;  
        try {  
            HttpResponse response = HttpClients.createDefault().execute(request);  
            if(response.getStatusLine().getStatusCode()==200){  
                result = EntityUtils.toString(response.getEntity());  
            }  
        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }
		return result;
	}  
	
	public static void main(String[] args) {
		TulingApiProcess tl = new TulingApiProcess();
		String result = tl.getResultForTuling("红烧肉怎么做", "江平"); 
		System.out.println(tl.handleResult("红烧肉怎么做", "", "", result));
	}
}
