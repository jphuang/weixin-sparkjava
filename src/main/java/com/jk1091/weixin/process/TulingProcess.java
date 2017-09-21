package com.jk1091.weixin.process;

import com.jk1091.weixin.util.HttpUtil;
import net.sf.json.JSONObject;

import java.util.Date;

public class TulingProcess {

	public String  get(String fromUser,String toUser,String text){
		String api = "http://www.tuling123.com/openapi/api";
		String result = "<xml>"+
				"<ToUserName><![CDATA[%s]]></ToUserName>"+
				"<FromUserName><![CDATA[%s]]></FromUserName>"+
				"<CreateTime>%d</CreateTime>"+
				"<MsgType><![CDATA[text]]></MsgType>"+
				"<Content>%s</Content>"+
				"</xml>";
		String post = HttpUtil.get(api + "?key=3baaeb7f66af4e1cb62fed91a1a732d1" + "&userid" + toUser + "&info=" + text );
		JSONObject jsonArray = JSONObject.fromObject(post);
		return String.format(result, toUser,fromUser,new Date().getTime(),jsonArray.get("text"));
	}

	public static void main(String[] args) {
		System.out.println(new TulingProcess().get("1","1","笑话"));
	}
}
