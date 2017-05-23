package com.nanyou.framework.json;

import java.io.Writer;

import com.nanyou.framework.util.JavaScriptUtil;

public class StringSerializer implements JSONSerializer {

	public void serialize(Object obj, Writer os) {
		String value = (String) obj;
		value = "\"" + JavaScriptUtil.escapeJavaScript(value) + "\"";
		try {
			os.write(value);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public Object deseialize(Class paramType, String json) {

		return JavaScriptUtil.unescapeJavaScript(json);
	}

}
