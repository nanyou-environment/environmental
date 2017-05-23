package com.nanyou.framework.json;

import java.io.Writer;
import java.util.Collection;
import java.util.Iterator;

public class CollectionSerializer implements JSONSerializer {

	public void serialize(Object data, Writer os) {
		if (!(data instanceof Collection)) {
			throw new IllegalArgumentException(data.getClass().getName()
					+ " is not a Collection"); //$NON-NLS-1$
		}

		try {
			os.write('[');
			Collection collection = (Collection) data;
			for (Iterator iter = collection.iterator(); iter.hasNext();) {
				Object element = (Object) iter.next();
				SerializerManager.serialize(element, os);
				// os.write(nested);
				if (iter.hasNext()) {
					os.write(ConversionConstants.INBOUND_ARRAY_SEPARATOR);
				}
			}
			os.write("]"); //$NON-NLS-1$
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Object deseialize(Class paramType, String value) {
		Collection collection = null;
		try {
			collection = (Collection) paramType.newInstance();
			JSONArray jsonArray = null;
			jsonArray = new JSONArray(value);

			for (int i = 0; i < jsonArray.length(); i++) {
				Object output = SerializerManager.deseialize(String.class,
						jsonArray.getString(i));
				collection.add(output);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return collection;
	}

}
