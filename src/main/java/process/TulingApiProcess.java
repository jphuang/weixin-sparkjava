package process;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import entity.ReceiveXmlEntity;

public class TulingApiProcess {
	/** 
     * ����ͼ�������api�ӿڣ���ȡ���ܻظ����ݣ�������ȡ�Լ������� 
     * @param content 
     * @return 
     */  
    public String getTulingResult(String content,ReceiveXmlEntity xmlEntity){  
    	String fromUserName = xmlEntity.getFromUserName();
    	String toUserName = xmlEntity.getToUserName();
        String result = getResultForTuling(content, fromUserName);  
        return handleResult(content, fromUserName, toUserName, result);  
    }

	private String handleResult(String content, String fromUserName,
			String toUserName, String result) {
		/** ����ʧ�ܴ��� */  
        if(null==result){  
            return "�Բ�����˵�Ļ�����̫�����ˡ���";  
        }  
          
        try {  
            JSONObject json = JSONObject.fromObject(result);
            //��code=100000 ,λ����Ϣ���ο�ͼ�������api�ĵ�  
            if(100000==json.getInt("code")){  
                result = json.getString("text");  
                result = new FormatXmlProcess().formatXmlText(fromUserName, toUserName, result);
            } 
            
            if(200000==json.getInt("code")){  
                result = json.getString("text");  
                String url =  json.getString("url");  
                result = new FormatXmlProcess().formatXmlLink(fromUserName, toUserName,  result, content, url);
            }
            
            if(308000==json.getInt("code")){  
            	result = json.getString("text");  
            	JSONArray list =  json.getJSONArray("list");
            	result = new FormatXmlProcess().formatXmlCookbook(fromUserName, toUserName, list);
            }
            
            if(302000==json.getInt("code")){  
            	result = json.getString("text");  
            	JSONArray list =  json.getJSONArray("list");
            	result = new FormatXmlProcess().formatXmlNew(fromUserName, toUserName, list);
            }
            
            
            
        } catch (JSONException e) {  
            e.printStackTrace();  
        }  
        return result.replace("&gt;", " >");
	}

	private String getResultForTuling(String content, String userName ) {
		/** �˴�Ϊͼ��api�ӿڣ�����key��Ҫ�Լ�ȥע������ */  
        String apiUrl = "http://182.92.68.93/openapi/api?key=9ae553bf1d1d99a849ba14c724cf0e44&userid="+userName+"&info=";  
        String param = "";  
        try {  
            param = apiUrl+URLEncoder.encode(content,"utf-8");  
        } catch (UnsupportedEncodingException e1) {  
            e1.printStackTrace();  
        } //������תΪurl����  
          
        /** ����httpget���� */  
        HttpGet request = new HttpGet(param);  
        String result = null;  
        try {  
            HttpResponse response = HttpClients.createDefault().execute(request);  
            if(response.getStatusLine().getStatusCode()==200){  
                result = EntityUtils.toString(response.getEntity());  
            }  
        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }
		return result;
	}  
	
	public static void main(String[] args) {
		TulingApiProcess tl = new TulingApiProcess();
		String result = tl.getResultForTuling("��������ô��", "��ƽ"); 
		System.out.println(tl.handleResult("��������ô��", "", "", result));
	}
}
