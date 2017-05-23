package com.nanyou.framework.json;

import java.io.Writer;
import java.lang.reflect.Array;
import java.text.ParseException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ArraySerializer implements JSONSerializer {

	Log log = LogFactory.getLog(ArraySerializer.class);

	public void serialize(Object data, Writer os) {
		if (!data.getClass().isArray()) {
			throw new RuntimeException(data.getClass().getName()
					+ " is not an Array"); //$NON-NLS-1$
		}

		try {
			int size = Array.getLength(data);
			os.write("[");
			// buffer.append('[');
			for (int i = 0; i < size; i++) {
				try {
					SerializerManager.serialize(Array.get(data, i), os);
					if (i < size - 1) {
						os.write(ConversionConstants.INBOUND_ARRAY_SEPARATOR);
					}
				} catch (Exception ex) {
					log
							.warn(
									"Failed to convert array member " + i + ". Conversion error for type: " + data.getClass().getName(), ex); //$NON-NLS-1$ //$NON-NLS-2$
				}
			}

			os.write("]");//$NON-NLS-1$
		} catch (Exception e) {

		}

	}

	public Object deseialize(Class paramType, String value) {

		JSONArray jsonArray = null;
		try {
			jsonArray = new JSONArray(value);
		} catch (ParseException e) {
		}

		Class componentType = paramType.getComponentType();
		Object array = Array.newInstance(componentType, jsonArray.length());

		for (int i = 0; i < jsonArray.length(); i++) {
			Object output = SerializerManager.deseialize(componentType,
					jsonArray.getString(i));
			Array.set(array, i, output);
		}
		return array;
	}

}
