package com.nanyou.framework.util;

import java.util.Locale;

import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;

import com.nanyou.framework.spring.SpringContextUtil;

public class MessageUtils {
	public static String getMessage(String key,Object[] objs) {
		String errorMsg = null;
		ApplicationContext context = SpringContextUtil.getContext();
		if (context!=null){
			MessageSource ms = (MessageSource)context.getBean("messageSource");
			errorMsg=  ms.getMessage(key,objs,key,null);
		}else{
            errorMsg= key;
        }
		return errorMsg;
	}
	
	public static String getMessage(String key,Object[] objs,Locale locale) {
		String errorMsg = null;
		ApplicationContext context = SpringContextUtil.getContext();
		if (context!=null){
			MessageSource ms = (MessageSource)context.getBean("messageSource");
			errorMsg=  ms.getMessage(key,objs,key,locale);
		}else{
            errorMsg= key;
        }
		return errorMsg;
	}
}
