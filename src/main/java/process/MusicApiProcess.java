package process;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class MusicApiProcess {
	
	public  String getSongId(String content ) {
        String apiUrl = "http://mobilecdn.kugou.com/api/v3/search/song?format=jsonp&page=1&pagesize=1&showtype=1&keyword=";  
        String param = "";  
        try {  
            param = apiUrl+URLEncoder.encode(content ,"utf-8");  
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
                result =  result.replace("(", "").replace(")", "");
                JSONObject json = JSONObject.fromObject(result); 
                Integer errorCode = json.getInt("status");
                if(1 == errorCode){
                	JSONArray list = JSONObject.fromObject(json.get("data")).getJSONArray("info");
                	return JSONObject.fromObject(list.get(0)).getString("hash");
                }else{
                	return null;
                }
            }  
        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }
		return null;
	} 
	public  JSONObject  getSongInfo(String songId) {
		String apiUrl = "http://m.kugou.com/app/i/getSongInfo.php?cmd=playInfo&hash="+ songId;  
		/** 发送httpget请求 */  
		System.out.println("getSongInfo:" + apiUrl);
		HttpGet request = new HttpGet(apiUrl);  
		String result = null;  
		try {  
			HttpResponse response = HttpClients.createDefault().execute(request);  
			if(response.getStatusLine().getStatusCode()==200){  
				result = EntityUtils.toString(response.getEntity());
				JSONObject json = JSONObject.fromObject(result); 
				return json;
			}  
		} catch (ClientProtocolException e) {  
			e.printStackTrace();  
		} catch (IOException e) {  
			e.printStackTrace();  
		}
		return null;
	} 
	
	public  String handleResult(String musicName ,String fromUserName,
			String toUserName, JSONObject json)  {
		/** 请求失败处理 */  
        if(null == json){  
            return returnFalse(musicName, fromUserName, toUserName);   
        }else{
        	String songName = json.getString("fileName");
        	String songLink = json.getString("url");
        	return formatMusicXml(fromUserName, toUserName, new Date(), songName, " ", songLink  );
        }  
          
        
	}

	public String returnFalse(String musicName, String fromUserName,
			String toUserName) {
		return    new FormatXmlProcess().formatXmlText(toUserName, fromUserName, "找不到歌曲" + musicName);
	}
	
	public String formatMusicXml(String fromUserName,
			String toUserName,Date date,String songName,String desc,String url){
		String result = "<xml>" + 
			"<ToUserName><![CDATA[%s]]></ToUserName>" + 
			"<FromUserName><![CDATA[%s]]></FromUserName>" + 
			"<CreateTime>%d</CreateTime>" + 
			"<MsgType><![CDATA[music]]></MsgType>" + 
			"<Music>" + 
			"<Title><![CDATA[%s]]></Title>" + 
			"<Description><![CDATA[%s]]></Description>" + 
			"<MusicUrl><![CDATA[%s]]></MusicUrl>" + 
			"<HQMusicUrl><![CDATA[%s]]></HQMusicUrl>" + 
			"</Music>" + 
			"</xml>" ;
		
		return String.format(result, toUserName,fromUserName,date.getTime(),songName,desc,url,url);
	}
	
	public static void main(String[] args) {
		MusicApiProcess  bma = new MusicApiProcess();
		System.out.println(bma.handleResult("流浪记", "", "", bma.getSongInfo(bma.getSongId("流浪记"))));
	}
}
