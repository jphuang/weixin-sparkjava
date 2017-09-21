package com.jk1091.weixin.process;

import java.util.Date;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class FormatXmlProcess {
	/**
	 * 封装文字类的返回消息
	 *
	 * @param to
	 * @param from
	 * @param content
	 * @return
	 */
	public String formatXmlText(String from, String to, String content) {
		StringBuffer sb = new StringBuffer();
		Date date = new Date();
		sb.append("<xml><ToUserName><![CDATA[");
		sb.append(to);
		sb.append("]]></ToUserName><FromUserName><![CDATA[");
		sb.append(from);
		sb.append("]]></FromUserName><CreateTime>");
		sb.append(date.getTime());
		sb.append("</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[");
		sb.append(content);
		sb.append("]]></Content><FuncFlag>0</FuncFlag></xml>");
		return sb.toString();
	}

	/**
	 * 封装链接的返回消息
	 *
	 * @param to
	 * @param from
	 * @param content
	 * @return
	 */
	public String formatXmlLink(String from, String to, String content,
								String description, String url) {
		StringBuffer sb = new StringBuffer();
		Date date = new Date();
		sb.append("<xml><ToUserName><![CDATA[");
		sb.append(to);
		sb.append("]]></ToUserName><FromUserName><![CDATA[");
		sb.append(from);
		sb.append("]]></FromUserName><CreateTime>");
		sb.append(date.getTime());
		sb.append("</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[");
		sb.append(content);
		sb.append(" <a target=\"_blank\" href=\"" + url + "\">" + description
				+ "</a>");

		sb.append("]]></Content><FuncFlag>0</FuncFlag></xml>");
		return sb.toString();
	}

	public String formatXmlCookbook(String fromUserName, String toUserName,
									JSONArray list) {
		StringBuffer sb = new StringBuffer();
		Date date = new Date();
		sb.append("<xml><ToUserName><![CDATA[");
		sb.append(toUserName);
		sb.append("]]></ToUserName><FromUserName><![CDATA[");
		sb.append(fromUserName);
		sb.append("]]></FromUserName><CreateTime>");
		sb.append(date.getTime());
		sb.append("</CreateTime><MsgType><![CDATA[news]]></MsgType><Content><![CDATA[]]></Content><ArticleCount>"
				+ ( list.size() > 8 ? 8 : list.size() ) + "</ArticleCount><Articles>");
		int i = 0 ;
		for (Object obj : list) {
			JSONObject o = (JSONObject) obj;
			String name = o.getString("name");
			String info = o.getString("info");
			String detailurl = o.getString("detailurl");
			String icon = o.getString("icon");

			sb.append("<item><Title><![CDATA[");
			sb.append(name);
			sb.append(" ]]></Title><Description><![CDATA[");
			sb.append(info);
			sb.append(" ]]></Description><PicUrl><![CDATA[");
			sb.append(icon);
			sb.append(" ]]></PicUrl><Url><![CDATA[");
			sb.append(detailurl);
			sb.append(" ]]></Url></item>");

			i++ ;
			if(i>=8){
				break;
			}
		}

		sb.append("</Articles><FuncFlag>1</FuncFlag></xml>");


		return sb.toString();
	}

	public String formatXmlNew(String fromUserName, String toUserName,
							   JSONArray list) {
		StringBuffer sb = new StringBuffer();
		Date date = new Date();
		sb.append("<xml><ToUserName><![CDATA[");
		sb.append(fromUserName);
		sb.append("]]></ToUserName><FromUserName><![CDATA[");
		sb.append(toUserName);
		sb.append("]]></FromUserName><CreateTime>");
		sb.append(date.getTime());
		sb.append("</CreateTime><MsgType><![CDATA[news]]></MsgType><Content><![CDATA[]]></Content><ArticleCount>"
				+ ( list.size() > 8 ? 8 : list.size() ) + "</ArticleCount><Articles>");
		int i = 0 ;
		for (Object obj : list) {
			JSONObject o = (JSONObject) obj;
			String name = o.getString("article");
			String info = o.getString("source");
			String detailurl = o.getString("detailurl");
			String icon = o.getString("icon");

			sb.append("<item><Title><![CDATA[");
			sb.append(name);
			sb.append(" ]]></Title><Description><![CDATA[");
			sb.append(info);
			sb.append(" ]]></Description><PicUrl><![CDATA[");
			sb.append(icon);
			sb.append(" ]]></PicUrl><Url><![CDATA[");
			sb.append(detailurl);
			sb.append(" ]]></Url></item>");
			i++ ;
			if(i>=8){
				break;
			}
		}

		sb.append("</Articles><FuncFlag>1</FuncFlag></xml>");

		return sb.toString();
	}

}
