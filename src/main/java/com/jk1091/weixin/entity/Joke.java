package com.jk1091.weixin.entity;

import org.apache.commons.lang3.StringUtils;

import java.util.Date;

public class Joke {
	private String title;
	private String content;
	private String poster;
	private String url;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPoster() {
		return poster;
	}
	public void setPoster(String poster) {
		this.poster = poster;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	public String toString(String toUser,String fromUser) {
		if(StringUtils.isNotBlank(poster)){
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
			return String.format(result, toUser,fromUser,new Date().getTime(),title,content,poster,url);
		}else{
			String result = "<xml>"+
				 "<ToUserName><![CDATA[%s]]></ToUserName>"+ 
				 "<FromUserName><![CDATA[%s]]></FromUserName>"+
				 "<CreateTime>%d</CreateTime>"+
				 "<MsgType><![CDATA[text]]></MsgType>"+
				 "<Content>%s</Content>"+
				 "</xml>";
			return String.format(result, toUser,fromUser,new Date().getTime(),content);
		}
	}
	
	
	
	
}
