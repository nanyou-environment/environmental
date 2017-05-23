package com.nanyou.framework.util;


public class ServerUtils {
	
	private static String contextPath="";
	
	/**
	 * 获取服务器地�?带contextPath)
	 * example http://www.ganzhuchina.com/wxjz
	 * @param request
	 * @return
	 */
	public static String getBasePath() {
		if(SystemProperty.isDebug()){
			return "http://localhost"+contextPath;
		}
		return getServerAddress()+contextPath;
	}
	
	public static void setContext(String aContextPath){
		contextPath=aContextPath;
	}
	
	/**
	 * 获取服务器地�?不带contextPath)
	 * example http://www.baidu.com
	 * @param request
	 * @return
	 */
	public static String getServerAddress(){
		return SystemProperty.getValue("server_address");
	}
	
	public static String getAuthPath(){
		return getAuthPath(null);
	}
	
	public static String getAuthPath(String appid){
		if(appid==null){
			appid=SystemProperty.getValue("appid");
		}
		return "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appid+"&redirect_uri={0}&response_type=code&scope=snsapi_base&state=1#wechat_redirect";
	}
	
}
