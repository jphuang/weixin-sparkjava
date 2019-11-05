package com.jiang.ping.weixin.process;

import com.jiang.ping.weixin.model.TalkHistory;
import com.jiang.ping.weixin.util.HttpUtil;
import com.jiang.ping.weixin.util.MessageUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class TulingProcess {
	private Logger logger = LoggerFactory.getLogger(getClass());
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
		logger.info("tuling return {}",jsonArray);
		String rersult = jsonArray.getString("text");
        MessageUtil.fixedThreadPool.submit(()-> {
			try {
				new TalkHistory().put("from_user", toUser).put("to_user", fromUser).put("content", text).put("result", rersult).put("create_time", new Date()).save();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		return String.format(result, toUser,fromUser,System.currentTimeMillis(), rersult);
	}

	public static void main(String[] args) {
		System.out.println(new TulingProcess().get("1","1","笑话"));
	}
}
