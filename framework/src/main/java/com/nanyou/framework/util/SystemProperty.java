package com.nanyou.framework.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;

public class SystemProperty {
	static Properties prop = new Properties();
	static {
		InputStream is = null;
		try {
			is=new ClassPathResource("system.properties").getInputStream();
			prop.load(new InputStreamReader(is,"UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static String getValue(String key){
		return prop.getProperty(key);
	}
	
	public static Boolean isDebug(){
		return "true".equals(getValue("debug"));
	}
}
