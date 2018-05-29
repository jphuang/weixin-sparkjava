package com.jk1091.weixin.process;

import com.jk1091.weixin.entity.Brow;
import com.jk1091.weixin.entity.ReceiveXmlEntity;
import com.jk1091.weixin.model.TalkHistory;
import com.jk1091.weixin.service.FuliService;

import java.util.Date;
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
        String s = content;
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
