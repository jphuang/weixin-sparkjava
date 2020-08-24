package com.jiang.ping.weixin.process;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jiang.ping.weixin.entity.Joke;
import com.jiang.ping.weixin.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Slf4j
public class JokeProcess {
	private static Map<String,List<Joke>> map = new HashMap<String, List<Joke>>();
	
	public void init(){
		String api = "http://api.laifujiangxiaohua.com/open/xiaohua.json";
		map.clear();
		String result = HttpUtil.get(api);
		System.out.println("result:" + result);
		if(result != null){
			JSONArray jsonArray = JSONArray.fromObject(result); 
			List<Joke> jobeList = new ArrayList<>();
			for(Object o : jsonArray){
				JSONObject obj = (JSONObject)o;
				Joke joke = new Joke();
				joke.setTitle(obj.getString("title"));
				joke.setContent(obj.getString("content").replaceAll("<br/>",""));
				joke.setPoster(obj.getString("poster"));
				joke.setUrl(obj.getString("url"));
				jobeList.add(joke);
			}
			map.put(new SimpleDateFormat("yyyyMMdd").format(new Date()), jobeList);
		}
	}
	
	public String getJoke(String toUser,String fromUser){
		if(map.get(new SimpleDateFormat("yyyyMMdd").format(new Date())) == null){
			init();
		}
		List<Joke> list  = map.get(new SimpleDateFormat("yyyyMMdd").format(new Date()));
		int  i =(int)(Math.random()*list.size());
		return list.get(i).toString(toUser, fromUser);
	}
	
	public static void main(String[] args) {
		System.out.println(new JokeProcess().getJoke("", ""));
	}
}
