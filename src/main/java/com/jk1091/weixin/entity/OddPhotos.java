package com.jk1091.weixin.entity;

public class OddPhotos {
	private String title;
	private String thumburl;
	private String url;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	
	public String getThumburl() {
		return thumburl;
	}
	public void setThumburl(String thumburl) {
		this.thumburl = thumburl;
	}
	public String toString(String toUser,String fromUser) {
			String result = "<xml>" + 
				"<ToUserName><![CDATA[%s]]></ToUserName>" + 
				"<FromUserName><![CDATA[%s]]></FromUserName>" + 
				"<CreateTime>%d</CreateTime>" + 
				"<MsgType><![CDATA[news]]></MsgType>" + 
				"<ArticleCount>1</ArticleCount>" + 
				"<Articles>" + 
				"<item>" + 
				"<Title><![CDATA[%S]]></Title> " + 
				"<Description><![CDATA[%s]]></Description>" + 
				"<PicUrl><![CDATA[%s]]></PicUrl>" + 
				"<Url><![CDATA[%s]]></Url>" + 
				"</item>" + 
				"</Articles>" + 
				"</xml>";
			return String.format(result, toUser,fromUser,System.currentTimeMillis(),title,"",thumburl,url);
		
	}
	
	
	
	
}
