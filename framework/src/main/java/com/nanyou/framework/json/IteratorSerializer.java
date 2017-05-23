package com.nanyou.framework.json;

import java.io.Writer;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class IteratorSerializer implements JSONSerializer {

	Log log = LogFactory.getLog(IteratorSerializer.class);

	public void serialize(Object data, Writer os) {
		Iterator iter = (Iterator)data;

		try {
			os.write("[");
			while(iter.hasNext()){
				Object obj = iter.next();
				SerializerManager.serialize(obj, os);
				if (iter.hasNext()) {
					os.write(ConversionConstants.INBOUND_ARRAY_SEPARATOR);
				}
			}
			os.write("]");//$NON-NLS-1$
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	public Object deseialize(Class paramType, String value) {
		return null;
	}

}
