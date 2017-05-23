package com.nanyou.weixin.jfinal.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.jfinal.kit.PropKit;
import com.jfinal.weixin.sdk.api.ApiConfig;
import com.jfinal.weixin.sdk.api.ApiConfigKit;
import com.jfinal.weixin.sdk.api.ApiResult;
import com.jfinal.weixin.sdk.api.CallbackIpApi;
import com.jfinal.weixin.sdk.api.CustomServiceApi;
import com.jfinal.weixin.sdk.api.CustomServiceApi.Articles;
import com.jfinal.weixin.sdk.api.MenuApi;
import com.jfinal.weixin.sdk.api.QrcodeApi;
import com.jfinal.weixin.sdk.api.ShorturlApi;
import com.jfinal.weixin.sdk.api.TemplateMsgApi;
import com.jfinal.weixin.sdk.api.UserApi;
import com.jfinal.weixin.sdk.cache.IAccessTokenCache;
import com.jfinal.weixin.sdk.jfinal.ApiController;
import com.nanyou.weixin.jfinal.model.Menu;
import com.nanyou.weixin.utils.AppConfig;

public class WeixinApiController extends ApiController {

    static IAccessTokenCache accessTokenCache = ApiConfigKit.getAccessTokenCache();
    
    /**
	 * 如果要支持多公众账号，只需要在此返回各个公众号对应的  ApiConfig 对象即可
	 * 可以通过在请求 url 中挂参数来动态从数据库中获取 ApiConfig 属性值
	 */
	public ApiConfig getApiConfig() {
		ApiConfig ac = new ApiConfig();
		
		// 配置微信 API 相关常量
		ac.setToken(PropKit.get("token"));
		ac.setAppId(PropKit.get("appId"));
		ac.setAppSecret(PropKit.get("appSecret"));
		
		/**
		 *  是否对消息进行加密，对应于微信平台的消息加解密方式：
		 *  1：true进行加密且必须配置 encodingAesKey
		 *  2：false采用明文模式，同时也支持混合模式
		 */
		ac.setEncryptMessage(PropKit.getBoolean("encryptMessage", false));
		ac.setEncodingAesKey(PropKit.get("encodingAesKey", "setting it in config file"));
		return ac;
	}
	
    public void getToken() {
        String key = getPara("key");
        String json = accessTokenCache.get(key);
        renderText(json);
    }
    
    /**
     * 获取公众号菜单
     */
    public void getMenu() {
        ApiResult apiResult = MenuApi.getMenu();
        if (apiResult.isSucceed())
            renderText(apiResult.getJson());
        else
            renderText(apiResult.getErrorMsg());
    }

    /**
     * 创建菜单
     */
    public void createMenu()
    {
    	String str = createStr();
		ApiResult apiResult = MenuApi.createMenu(str);
		if (apiResult.isSucceed())
			renderText(apiResult.getJson());
		else
			renderText(apiResult.getErrorMsg());
    }

    /**
     * 获取公众号关注用户
     */
    public void getFollowers()
    {
        ApiResult apiResult = UserApi.getFollows();
        renderText(apiResult.getJson());
    }


    /**
     * 获取微信服务器IP地址
     */
    public void getCallbackIp()
    {
        ApiResult apiResult = CallbackIpApi.getCallbackIp();
        renderText(apiResult.getJson());
    }
    
	public String createStr(){
		List<Menu> list = new ArrayList<Menu>();
		//第一个菜单
		Menu sellWaste = new Menu();
		sellWaste.setName("卖废品");
		sellWaste.setUrl(AppConfig.getSellWaste());
		sellWaste.setType("view");
		list.add(sellWaste);
		
		//第二个菜单
		Menu referencePrice = new Menu();
		referencePrice.setName("参考价格");
		referencePrice.setUrl(AppConfig.getReferencePrice());
		referencePrice.setType("view");
		list.add(referencePrice);
		
		//第三个菜单
		List<Menu> serviceList = new ArrayList<Menu>();
		
		Menu integralMall = new Menu();
		integralMall.setName("积分商城");
		integralMall.setUrl(AppConfig.getIntegralMall());
		integralMall.setType("view");
		serviceList.add(integralMall);
		
		Menu contact = new Menu();
		contact.setName("联系我们");
		contact.setKey("contact");
		contact.setType("click");
		serviceList.add(contact);
		
		Menu service = new Menu();
		service.setName("服务");
		service.setSub_button(serviceList);
		list.add(service);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("button", list);
		JSONObject jsonObject = new JSONObject(map);
		String str = JSON.toJSONString(jsonObject, SerializerFeature.DisableCircularReferenceDetect);
		System.out.println(str);
		return str;
	}
	
}

