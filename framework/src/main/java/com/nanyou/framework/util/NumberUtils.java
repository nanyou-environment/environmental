package com.nanyou.framework.util;

import java.math.BigDecimal;

public class NumberUtils {
	public static Long getLongValue(Object obj) {
		if (obj == null)
			return null;
		if (obj instanceof Long) {
			return (Long)obj;
				
		}
		else {
			try {
				return new Long(obj.toString());
					
			} catch (Exception e) {
				return null;
			}
					
		}
	}
	
	public static Integer getIntValue(Object obj) {
		if (obj == null)
			return null;
		if (obj instanceof Integer) {
			return (Integer)obj;
				
		}
		else {
			try {
				return new Integer(obj.toString());
					
			} catch (Exception e) {
				return null;
			}
					
		}
	}
	
	public static Double getDoubleValue(Object obj) {
		if (obj == null)
			return null;
		if (obj instanceof Double) {
			return (Double)obj;
				
		}
		else {
			try {
				return new Double(obj.toString());
					
			} catch (Exception e) {
				return null;
			}
					
		}
	}
	
	public static Double format(Double value,Integer scale){
		if(value==null){
			value=0.0;
		}
		if(scale==null){
			scale=0;
		}
		return new BigDecimal(value).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	public static Double devide(Double a,Double b,Integer scale){
		if(a==null)a=0.0;
		if(b==null||b==0)return 0.0;
		return format(a/b,scale);
	}
}
