package com.nanyou.framework.json;

import java.io.Writer;


public class BooleanSerializer implements JSONSerializer {

	public void serialize(Object obj,Writer os) {
		
		String value = ((Boolean)obj).booleanValue()? "true":"false";
		try{
			os.write(value);
		}catch(Exception ex){
			throw new RuntimeException(ex);
		}
	}

	public Object deseialize(Class paramType, String json) {
		return  "true".equalsIgnoreCase(json) ? Boolean.TRUE : Boolean.FALSE;
	}

}
