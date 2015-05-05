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
            
        	if(content.startsWith("����") || content.startsWith("����") || content.startsWith("���") ){
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
		String content = "������˼�".substring(2,"������˼�".length());
		System.out.println(content);
		System.out.println(bma.handleResult(content, "test", "ziji", bma.getSongInfo(bma.getSongId(content))));
	
	}
	
}
