package com.jiang.ping.weixin.process;

import com.jiang.ping.weixin.entity.SongBean;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;
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
	private final  static  int  SUCCESS_CODE  = 200;
	private SongBean getSongId(String content) throws UnsupportedEncodingException {
        String apiUrl = "https://c.y.qq.com/soso/fcgi-bin/client_search_cp?p=1&n=2&format=json&w=";
        String param = apiUrl+URLEncoder.encode(content ,"utf-8");
        HttpGet request = new HttpGet(param);
        String result;
        try {
            HttpResponse response = HttpClients.createDefault().execute(request);
            if(response.getStatusLine().getStatusCode()==SUCCESS_CODE){
                result = EntityUtils.toString(response.getEntity());
                JSONObject json = JSONObject.fromObject(result);
				JSONObject resultObject = json.getJSONObject("data");
				JSONObject song = resultObject.getJSONObject("song");
				JSONArray songList = song.getJSONArray("list");
				JSONObject songObj = (JSONObject) songList.get(0);
				long songId = songObj.getLong("songid");
				String songName = songObj.getString("songname");
				String alName = songObj.getString("albumname");
				String songmid = songObj.getString("songmid");
				return new SongBean(songName,songId,alName,songmid);
			}
        } catch (ClientProtocolException e) {
			logger.error("ClientProtocolException",e);
        } catch (IOException e) {
			logger.error("IOException",e);
        }
		return null;
	}
	private JSONObject  getSongInfo(String songId)  {
		String url = "https://u.y.qq.com/cgi-bin/musicu.fcg?format=json&data=%7B%22req_0%22%3A%7B%22module%22%3A%22vkey.GetVkeyServer%22%2C%22method%22%3A%22CgiGetVkey%22%2C%22param%22%3A%7B%22guid%22%3A%22358840384%22%2C%22songmid%22%3A%5B%22"+songId+"%22%5D%2C%22songtype%22%3A%5B0%5D%2C%22uin%22%3A%221443481947%22%2C%22loginflag%22%3A1%2C%22platform%22%3A%2220%22%7D%7D%2C%22comm%22%3A%7B%22uin%22%3A%2218585073516%22%2C%22format%22%3A%22json%22%2C%22ct%22%3A24%2C%22cv%22%3A0%7D%7D";
		logger.info("getSongInfo url:{}" , url);
		HttpGet request = new HttpGet(url);
		String result;
		try {
			HttpResponse response = HttpClients.createDefault().execute(request);
			if(response.getStatusLine().getStatusCode()==SUCCESS_CODE ){
				result = EntityUtils.toString(response.getEntity());
				logger.info("result:{}", result);
				return JSONObject.fromObject(result);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	String handleResult(String musicName, String fromUserName, String toUserName) throws UnsupportedEncodingException {
		SongBean song = getSongId(musicName);
		if(null == song){
            return returnFalse(musicName, fromUserName, toUserName);
        }
		String id = song.getSongmid();
		JSONObject json = getSongInfo(id);
		Assert.assertNotNull(json);
		JSONObject req = json.getJSONObject("req_0");
		JSONObject data = req.getJSONObject("data");
		logger.info("data:{}", data);
		JSONArray sip = data.getJSONArray("sip");
		JSONArray midurlinfo = data.getJSONArray("midurlinfo");
		JSONObject info = (JSONObject)midurlinfo.get(0);
		String songLink = sip.get(0).toString() + info.getString("purl");
		return formatMusicXml(fromUserName, toUserName, new Date(), song.getName(), song.getAlName(), songLink  );
	}

	public String returnFalse(String musicName, String fromUserName,
			String toUserName) {
		return    new FormatXmlProcess().formatXmlText(toUserName, fromUserName, "no data :" + musicName);
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
			System.out.println(bma.handleResult("love", "", ""));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
