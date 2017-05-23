package com.nanyou.framework.json;

import java.io.Writer;


public interface JSONSerializer {
	

	public void serialize(Object obj,Writer os);

	public Object deseialize(Class paramType,String json);
}
