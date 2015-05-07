package process;

import entity.Brow;
import entity.ReceiveXmlEntity;

public class WechatProcess {
	/** 
     * ��������xml����ȡ���ܻظ����
     * @param xml ���յ���΢������ 
     * @return  ���յĽ��������xml��ʽ���ݣ� 
     */  
    public String processWechatMag(String xml){  
        /** ����xml���� */  
        ReceiveXmlEntity xmlEntity = new ReceiveXmlProcess().getMsgEntity(xml);  
          
        /** ���ı���ϢΪ��������ͼ�������api�ӿڣ���ȡ�ظ����� */  
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
		if(content.startsWith("����") || content.startsWith("����") || content.startsWith("���") ){
			MusicApiProcess  bma = new MusicApiProcess();
			content = content.substring(2,content.length());
			result = bma.handleResult(content, fromUserName, toUserName, bma.getSongInfo(bma.getSongId(content)));
		}else if(content.indexOf("Ц��")!= -1){
			result = new JokeProcess().getJoke(toUserName, fromUserName);
		}else if(content.indexOf("Ȥͼ")!= -1){
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
		System.out.println(wp.handle("Ц��", "", ""));
	}
	
}
