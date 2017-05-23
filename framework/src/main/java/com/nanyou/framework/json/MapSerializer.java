package com.nanyou.framework.json;

import java.io.Writer;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class MapSerializer implements JSONSerializer {

	public void serialize(Object obj, Writer os) {
		try {
			Map map = (Map) obj;

			os.write(ConversionConstants.INBOUND_MAP_START);

			for (Iterator iter = map.entrySet().iterator(); iter.hasNext();) {
				Entry entry = (Entry) iter.next();
				Object key = entry.getKey();
				Object value = entry.getValue();
				if (key instanceof String) {
					if ("class".equals(key)) {
						continue;
					}
					os.write(String.valueOf(key));
					os.write(ConversionConstants.INBOUND_MAP_ENTRY);
				}
				if (value != null) {
					SerializerManager.serialize(value,os);
					//os.write(output);
				} else {
					os.write(ConversionConstants.INBOUND_NULL);
				}
				if (iter.hasNext()){
					os.write(ConversionConstants.INBOUND_MAP_SEPARATOR);
				}
			}

			os.write(ConversionConstants.INBOUND_MAP_END);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

	}

	public Object deseialize(Class paramType, String json) {

		// If the text is null then the whole bean is null
		if (json.trim().equals(ConversionConstants.INBOUND_NULL)) {
			return null;
		}

		if (!json.startsWith(ConversionConstants.INBOUND_MAP_START)) {
			throw new IllegalArgumentException("MapConverter Missing Opener"); //$NON-NLS-1$
		}

		if (!json.endsWith(ConversionConstants.INBOUND_MAP_END)) {
			throw new IllegalArgumentException("MapConverter Missing Closer"); //$NON-NLS-1$
		}

		// Maybe we ought to check that the paramType isn't expecting a more
		// distinct type of Map and attempt to create that?
		Map map = null;

		// If paramType is concrete then just use whatever we've got.
		if (!paramType.isInterface()
				&& !Modifier.isAbstract(paramType.getModifiers())) {
			// If there is a problem creating the type then we have no way
			// of completing this - they asked for a specific type and we
			// can't create that type. I don't know of a way of finding
			// subclasses that might be instaniable so we accept failure.
			try {
				map = (Map) paramType.newInstance();
			} catch (Exception e) {
				return null;
			}
		} else {
			map = new HashMap();
		}

		try {
			JSONObject mapJSONObject = new JSONObject(json);
			Iterator keys = mapJSONObject.keys();
			while (keys.hasNext()) {
				String key = (String) keys.next();
				((Map) map).put(key, mapJSONObject.get(key));
			}
		} catch (Exception e) {
		}

		return map;

	}

}
