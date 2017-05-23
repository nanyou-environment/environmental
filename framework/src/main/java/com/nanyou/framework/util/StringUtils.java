package com.nanyou.framework.util;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class StringUtils {

	public static String getRequestParams(Map map) {
		Set keSet=map.entrySet();
	    String params="";
	    for(Iterator itr=keSet.iterator();itr.hasNext();){
	        Map.Entry me=(Map.Entry)itr.next();
	        Object ok=me.getKey();
	        Object ov=me.getValue();
	        String[] value=new String[1];
	        if(ov instanceof String[]){
	            value=(String[])ov;
	        }else{
	            value[0]=ov.toString();
	        }

	        for(int k=0;k<value.length;k++){
	        	if(params.length()>0){
	        		params+="&";
	        	}
	        	params+=ok+"="+value[k];
	        }
	      }
	    return params;
	}

	public static String htmlspecialchars(String str) {
		str = str.replaceAll("&", "&amp;");
		str = str.replaceAll("<", "&lt;");
		str = str.replaceAll(">", "&gt;");
		str = str.replaceAll("\"", "&quot;");
		return str;
	}
	
	public static String toString(String[] array){
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < array.length; i++){
			if(sb.length()>0){
				sb.append(",");
			}
			sb. append(array[i]);
		}
		return sb.toString();
	}
	
	public static String get(String str){
		return str==null?"":str;
	}
}
