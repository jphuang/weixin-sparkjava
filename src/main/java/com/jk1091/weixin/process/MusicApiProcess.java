package com.jk1091.weixin.process;

import com.jk1091.weixin.entity.SongBean;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

/**
 * @author Administrator
 */
public class MusicApiProcess {

	private Logger logger = LoggerFactory.getLogger(getClass());

	public  String getSongId(String content ) {
        String apiUrl = "http://mobilecdn.kugou.com/api/v3/search/song?format=jsonp&page=1&pagesize=1&showtype=1&keyword=";
        String param = "";
        try {
            param = apiUrl+URLEncoder.encode(content ,"utf-8");
        } catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
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
			logger.error("ClientProtocolException",e);
        } catch (IOException e) {
			logger.error("IOException",e);
        }
		return null;
	}
	public SongBean getSongIdFrom163(String content ) throws UnsupportedEncodingException {
        String apiUrl = "https://api.imjad.cn/cloudmusic/?type=search&search_type=1&s=";
        String param = apiUrl+URLEncoder.encode(content ,"utf-8");
        HttpGet request = new HttpGet(param);
        String result;
        try {
            HttpResponse response = HttpClients.createDefault().execute(request);
            if(response.getStatusLine().getStatusCode()==200){
                result = EntityUtils.toString(response.getEntity());
                JSONObject json = JSONObject.fromObject(result);
				JSONObject resultObject = json.getJSONObject("result");
				JSONArray songs = resultObject.getJSONArray("songs");
				JSONObject song = (JSONObject) songs.get(0);
				long songId = song.getLong("id");
				String songName = song.getString("name");
				JSONObject al = song.getJSONObject("al");
				String alName = al.getString("name");
				String picUrl = al.getString("picUrl");
				return new SongBean(songName,songId,alName,picUrl);
			}
        } catch (ClientProtocolException e) {
			logger.error("ClientProtocolException",e);
        } catch (IOException e) {
			logger.error("IOException",e);
        }
		return null;
	}
	public  JSONObject  getSongInfo(String songId) {
		String callback = "jQuery191037245986227035965_1572507044787";
		String url = "http://wwwapi.kugou.com/yy/index.php?r=play/getdata&callback="+callback+"&hash=" +songId +"&album_id=31794106&dfid=3w4QAa1EIBy30XdQvQ3htcYC&mid=044a162f05339f36a342e094535b8be1&platid=4&_=1572507044789";
		System.out.println("getSongInfo:" + url);
		HttpGet request = new HttpGet(url);
		String result;
		try {
			HttpResponse response = HttpClients.createDefault().execute(request);
			if(response.getStatusLine().getStatusCode()==200){
				result = EntityUtils.toString(response.getEntity());
				logger.info("result:{}", result);
				result = result.replace(callback.concat("("),"").replace(");","");
				logger.info("result:{}", result);
				return JSONObject.fromObject(result);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	private JSONObject  getSongInfoFrom163(Long songId) {
		String url = "https://api.imjad.cn/cloudmusic/?type=song&id=".concat(songId.toString());
		logger.info("getSongInfo url:{}" , url);
		HttpGet request = new HttpGet(url);
		String result;
		try {
			HttpResponse response = HttpClients.createDefault().execute(request);
			if(response.getStatusLine().getStatusCode()==200){
				result = EntityUtils.toString(response.getEntity());
				logger.info("result:{}", result);
				return JSONObject.fromObject(result);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public  String handleResult(String musicName ,String fromUserName, String toUserName) throws UnsupportedEncodingException {
		SongBean song = getSongIdFrom163(musicName);
		if(null == song){
            return returnFalse(musicName, fromUserName, toUserName);
        }
		JSONObject json = getSongInfoFrom163(song.getId());
		JSONObject data = json.getJSONObject("data");
		logger.info("data:{}", data);
		String songLink = data.getString("url");
		return formatMusicXml(fromUserName, toUserName, new Date(), song.getName(), song.getAlName(), songLink  );
	}

	public String returnFalse(String musicName, String fromUserName,
			String toUserName) {
		return    new FormatXmlProcess().formatXmlText(toUserName, fromUserName, "没有找到这个音乐:" + musicName);
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
		try {
			System.out.println(bma.handleResult("爱", "", ""));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
