package com.jk1091.weixin.service;

import com.jk1091.weixin.util.HttpUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Copyright (C), 2017-2017, 真知码
 * FileName: FuliService
 * Author:   jphuang
 * Date:     2017/9/22 10:43
 * Description: 福利service
 */
public class FuliService {
    String api  = "http://gank.io/api/data/%E7%A6%8F%E5%88%A9/20/1";

    public JSONArray list(){
        String post = HttpUtil.get(api);
        JSONObject jsonArray = JSONObject.fromObject(post);
        return jsonArray.getJSONArray("results");
    }
}
