package process;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import util.HttpUtil;
import entity.OddPhotos;

public class OddPhotosProcess {
	private static Map<String,List<OddPhotos>> map = new HashMap<String, List<OddPhotos>>();
	
	public void init(){
		String api = "http://api.laifudao.com/open/tupian.json";
		map.clear();
		String result = HttpUtil.get(api);
		if(result != null){
			System.out.println("结果：" + result);
			JSONArray jsonArray = JSONArray.fromObject(result); 
			List<OddPhotos> jobeList = new ArrayList<>();
			for(Object o : jsonArray){
				JSONObject obj = (JSONObject)o;
				OddPhotos oddPhotos = new OddPhotos();
				oddPhotos.setTitle(obj.getString("title"));
				oddPhotos.setThumburl(obj.getString("thumburl"));
				oddPhotos.setUrl(obj.getString("url"));
				jobeList.add(oddPhotos);
			}
			map.put(new SimpleDateFormat("yyyyMMdd").format(new Date()), jobeList);
		}
	}
	
	public String getOddPhotos(String toUser,String fromUser){
		if(map.get(new SimpleDateFormat("yyyyMMdd").format(new Date())) == null){
			init();
		}
		List<OddPhotos> list  = map.get(new SimpleDateFormat("yyyyMMdd").format(new Date()));
		int  i =(int)(Math.random()*list.size());
		return list.get(i).toString(toUser, fromUser);
	}
	
	public static void main(String[] args) {
		System.out.println(new OddPhotosProcess().getOddPhotos("", ""));
	}
}
