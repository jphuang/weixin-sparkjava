package com.jiang.ping.weixin.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
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

	public static String encrypt(String strSrc) {
		MessageDigest md = null;
		String strDes = null;

		byte[] bt = strSrc.getBytes();
		try {
			md = MessageDigest.getInstance("SHA-1");
			md.update(bt);
			strDes = bytes2Hex(md.digest());
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

    public static boolean check(String token,String signature, String timestamp, String nonce) {
        String[] ArrTmp = {token, timestamp, nonce};
        Arrays.sort(ArrTmp);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < ArrTmp.length; i++) {
            sb.append(ArrTmp[i]);
        }
        String tempStr = EncryptUtil.encrypt(sb.toString());
        if (signature.equals(tempStr)) {
            return true;
        }
        return false;
    }
}
