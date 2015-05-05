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
            
        	if(content.startsWith("音乐") || content.startsWith("歌曲") || content.startsWith("点歌") ){
        		MusicApiProcess  bma = new MusicApiProcess();
        		content = content.substring(2,content.length());
        		result = bma.handleResult(content, xmlEntity.getToUserName(), xmlEntity.getFromUserName(), bma.getSongInfo(bma.getSongId(content)));
        	}else{
        		result = new TulingApiProcess().getTulingResult(content,xmlEntity);  
        	}
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
		MusicApiProcess  bma = new MusicApiProcess();
		String content = "点歌流浪记".substring(2,"点歌流浪记".length());
		System.out.println(content);
		System.out.println(bma.handleResult(content, "test", "ziji", bma.getSongInfo(bma.getSongId(content))));
	
	}
	
}
