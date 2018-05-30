package com.jk1091.weixin.process;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jk1091.weixin.entity.Brow;
import com.jk1091.weixin.entity.ReceiveXmlEntity;
import com.jk1091.weixin.message.resp.TextMessage;
import com.jk1091.weixin.model.TalkHistory;
import com.jk1091.weixin.model.User;
import com.jk1091.weixin.service.FuliService;
import com.jk1091.weixin.util.MessageUtil;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author jphua
 */
public class WechatProcess {



	/**
	 * 解析处理xml、获取智能回复结果
	 * @param xml 接收到的微信数据
	 * @return  最终的解析结果（xml格式数据）
	 */
	public String processWechatMag(String xml){
        /* 解析xml数据 */
		ReceiveXmlEntity xmlEntity = new ReceiveXmlProcess().getMsgEntity(xml);

        /* 以文本消息为例，调用图灵机器人api接口，获取回复内容 */
		String text = "text";
		String voice = "voice";
		String  fromUserName = xmlEntity.getToUserName();
		String toUserName = xmlEntity.getFromUserName();
		String content = "";
		if(text.endsWith(xmlEntity.getMsgType())){
			content = replace(xmlEntity.getContent());
		}
		if(voice.endsWith(xmlEntity.getMsgType())){
			content = replace(xmlEntity.getRecognition());
		}
        return handle(content, fromUserName, toUserName);
	}

	private  String handle(String content, String fromUserName, String toUserName) {
        String music = "音乐";
        String prefix = "歌曲";
        String musicAction = "点歌";
        String joke = "笑话";
        String odd = "趣图";
        String girl = "美女";
        String rank = "排行榜";
        String iam = "我是";

        if(content.startsWith(music) || content.startsWith(prefix) || content.startsWith(musicAction) ){
            MusicApiProcess  bma = new MusicApiProcess();
            content = content.substring(2,content.length());
            return  bma.handleResult(content, fromUserName, toUserName, bma.getSongInfo(bma.getSongId(content)));
        }
        if(content.contains(joke)){
            return  new JokeProcess().getJoke(toUserName, fromUserName);
        }
        if(content.contains(odd)){
            return  new OddPhotosProcess().getOddPhotos(toUserName, fromUserName);
        }
        if(content.equalsIgnoreCase(girl)){
            return  new FuliService().fuli(toUserName,fromUserName);
        }
		if(content.startsWith(iam)){
			String sub = content.substring(2, content.length());
			if(sub.length() >16){
				sub = sub.substring(0,16);
			}
			boolean save = new User().put("user_id", fromUserName).put("name", sub).save();
			if(save){
				content = "你已经登记你的呢称为：" + sub;
			}else{
				content = "系统好像出了点问题,骚后再试！！！";
			}

			TextMessage textMessage = new TextMessage(fromUserName, toUserName, content);
			return  textMessage.textMessageToXml();
		}

        if(content.equalsIgnoreCase(rank)){
            String sql = "select hi.*, user.name  from  (\n" +
					"select from_user,count(1)  num  from talk_history GROUP BY from_user    ORDER BY  num desc limit 10 \n" +
					") hi LEFT join user  on user.user_id = hi.from_user ";

			List<Record> records = Db.find(sql);
			StringBuffer sb = new StringBuffer();
			String format = "%-10s|%-10s|%-30s";
			sb.append("如果你没看到你的名字，你可以发送： 我是xxx 来登记你的呢称xxx \r\n");
			sb.append(String.format(format,"呢称","聊天数量","openid") + "\r\n");
			for (Record record : records) {
				sb.append(String.format(format, record.get("name"), record.get("num"), record.get("from_user")));
			}
			TextMessage textMessage = new TextMessage(fromUserName, toUserName, sb.toString());
			return  textMessage.textMessageToXml();
		}
		String result = new TulingProcess().get(fromUserName, toUserName, content);
		return result;
	}

	private String replace(String content) {
		for(String key : Brow.me.keySet()){
			content = content.replace(key, Brow.me.get(key));
		}
		return content;
	}

}
