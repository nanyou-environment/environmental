package com.nanyou.framework.spring;

import org.springframework.context.ApplicationContext;

public class SpringContextHolder {
	private static ApplicationContext applicationContext;
	
	public static void setApplicationContext(ApplicationContext context){
		applicationContext = context;
	}
	
	public static ApplicationContext getApplicationContext(){
		return applicationContext;
	}
}
