package util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;

public class EncryptUtil {
	
	final static char[] digits = {
		'0' , '1' , '2' , '3' , '4' , '5' ,	'6' , '7' ,
		'8' , '9' , 'a' , 'b' , 'c' , 'd' , 'e' , 'f' 
	};
	private static Random rnd = new Random();
	final static String digitsBase36 = "0123456789abcdefghijklmnopqrstuvwxyz";
	public static String md5(String s) {
		return md5(s, null );
	}
	
	public static String md5(String data, String key) {
		return doHash( data, key, "MD5" );
    }
	
	public static String doHash( String data, String key, String digestName ) {
		String ret = "";
		if( StringUtils.isEmpty(data) ) return ret;
		
		try {
			MessageDigest mgd = MessageDigest.getInstance( digestName );
			mgd.update(data.getBytes());
			byte[] bytes = null;
			if ( StringUtils.isEmpty(key) ) {
				bytes = mgd.digest();
			} else{
				bytes = mgd.digest(key.getBytes());
			}
			mgd.reset();
			ret = toHex( bytes,0, bytes.length );
		} catch (NoSuchAlgorithmException e) {
		}
		return ret;
	}
	
	public static  String toHex(byte byteData[], int offset, int len) {
		char buf[] = new char[len*2];
		int k=0;
		for (int i= offset; i < len; i++ ) {
		    buf[k++] = digits[((int) byteData[i] & 0xff)>>4 ];
		    buf[k++] = digits[((int) byteData[i] & 0xff)%16];
		}
		return new String(buf);
	}
	
	public static String getRandomNumberStringBase36(int strLen ){
		StringBuilder sb = new StringBuilder();
		for( int i=0; i<strLen; i++ ){
			sb.append( digitsBase36.charAt( rnd.nextInt(36) ) );
		}
		return sb.toString();
	}
	
}
