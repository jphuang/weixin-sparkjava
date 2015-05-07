package process;

import entity.Brow;
import entity.ReceiveXmlEntity;

public class WechatProcess {
	/** 
     * 解析处理xml、获取智能回复结果
     * @param xml 接收到的微信数据 
     * @return  最终的解析结果（xml格式数据） 
     */  
    public String processWechatMag(String xml){  
        /** 解析xml数据 */  
        ReceiveXmlEntity xmlEntity = new ReceiveXmlProcess().getMsgEntity(xml);  
          
        /** 以文本消息为例，调用图灵机器人api接口，获取回复内容 */  
        String result = "";  
        if("text".endsWith(xmlEntity.getMsgType())){ 
        	
        	String content = replace(xmlEntity.getContent());
            
        	String  fromUserName = xmlEntity.getToUserName();
        	String toUserName = xmlEntity.getFromUserName();
        	
        	result = handle(content, fromUserName, toUserName);
        }else if ("voice".endsWith(xmlEntity.getMsgType())){ 
        	
        	String content = replace(xmlEntity.getRecognition());
            
        	String  fromUserName = xmlEntity.getToUserName();
        	String toUserName = xmlEntity.getFromUserName();
        	
        	result = handle(content, fromUserName, toUserName);
        } 
        
        return result;  
    }

	public  String handle(String content, String fromUserName, String toUserName) {
		String result;
		if(content.startsWith("音乐") || content.startsWith("歌曲") || content.startsWith("点歌") ){
			MusicApiProcess  bma = new MusicApiProcess();
			content = content.substring(2,content.length());
			result = bma.handleResult(content, fromUserName, toUserName, bma.getSongInfo(bma.getSongId(content)));
		}else if(content.indexOf("笑话")!= -1){
			result = new JokeProcess().getJoke(toUserName, fromUserName);
		}else if(content.indexOf("趣图")!= -1){
			result = new OddPhotosProcess().getOddPhotos(toUserName, fromUserName);
		}else{
			result = new TulingApiProcess().getTulingResult(content,fromUserName,toUserName);  
		}
		return result;
	}

	private String replace(String content) {
		for(String key : Brow.me.keySet()){
			content = content.replace(key, Brow.me.get(key));
		}
		return content;
	}  
	
	public static void main(String[] args) {
		WechatProcess wp = new WechatProcess();
		System.out.println(wp.handle("笑话", "", ""));
	}
	
}
