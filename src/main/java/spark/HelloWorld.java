package spark;

import static spark.Spark.post;
import static spark.SparkBase.port;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;


import process.WechatProcess;






public class HelloWorld {

	private static final String token = "HSEHVTflgeuccUXa";
	
	

	public static void main(String[] args) {
		port(8078);
		post("/", (req, res) -> {
			String result = "";
			try {
				String signature = req.queryParams("signature");
				String timestamp = req.queryParams("timestamp");
				String nonce = req.queryParams("nonce");
				String echostr = req.queryParams("echostr");
				
				/** 读取接收到的xml消息 */  
		        String bady = req.body();  
		         
		        
		        System.out.println("xml:" + bady);
		        
		        
				if(check(signature, timestamp, nonce)){
					if (echostr != null && echostr.length() > 1) {  
			            result = echostr;  
			        } else {  
			            //正常的微信处理流程  
			            result = new WechatProcess().processWechatMag(bady);  
			        }  
				}
				
				System.out.println("result:" + result);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return result;
			}
			return result;
		});
		
		
	}

	private static boolean check(String signature, String timestamp, String nonce) {
		String[] ArrTmp = { token, timestamp, nonce };  
		Arrays.sort(ArrTmp);  
		StringBuffer sb = new StringBuffer();  
		for (int i = 0; i < ArrTmp.length; i++) {  
		    sb.append(ArrTmp[i]);  
		}  
		
		String tempStr = encrypt(sb.toString());
		if(signature.equals(tempStr)){
			return true;
		}
		return false;
	}
	
	private static String encrypt(String strSrc) {  
        MessageDigest md = null;  
        String strDes = null;  
  
        byte[] bt = strSrc.getBytes();  
        try {  
            md = MessageDigest.getInstance("SHA-1");  
            md.update(bt);  
            strDes = bytes2Hex(md.digest()); //to HexString  
        } catch (NoSuchAlgorithmException e) {  
            System.out.println("Invalid algorithm.");  
            return null;  
        }  
        return strDes;  
    }  
  
    public static String bytes2Hex(byte[] bts) {  
        String des = "";  
        String tmp = null;  
        for (int i = 0; i < bts.length; i++) {  
            tmp = (Integer.toHexString(bts[i] & 0xFF));  
            if (tmp.length() == 1) {  
                des += "0";  
            }  
            des += tmp;  
        }  
        return des;  
    }  
    
    
}
