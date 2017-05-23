package com.nanyou.framework.json;

import java.io.Writer;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

public class SerializerManager {
	static Map serializerTypes = new LinkedHashMap();
	static {

		serializerTypes.put(String.class, new StringSerializer());
		serializerTypes.put(Boolean.class, new BooleanSerializer());
		serializerTypes.put(Long.class, new PrimitiveSerializer());
		serializerTypes.put(Integer.class, new PrimitiveSerializer());
		serializerTypes.put(Float.class, new PrimitiveSerializer());
		serializerTypes.put(Double.class, new PrimitiveSerializer());
		serializerTypes.put(Character.class, new PrimitiveSerializer());
		serializerTypes.put(BigDecimal.class, new BigNumberSerializer());
		serializerTypes.put(Date.class, new DateSerializer());
		serializerTypes.put(Iterator.class, new IteratorSerializer());
		serializerTypes.put(Array.class, new ArraySerializer());
		serializerTypes.put(Collection.class, new CollectionSerializer());
		serializerTypes.put(Map.class, new MapSerializer());
		serializerTypes.put(Object.class, new BeanSerializer());
	}
	

	private static JSONSerializer getSerializer(Class clz) {
		if (clz.isArray()) {
			return (JSONSerializer) serializerTypes.get(Array.class);
		}

		if (clz.isPrimitive()) {
			return (JSONSerializer) serializerTypes.get(Long.class);
		}

		for (Iterator iter = serializerTypes.entrySet().iterator(); iter.hasNext();) {
			Entry entry = (Entry) iter.next();
			Class clazz = (Class) entry.getKey();
			if (clazz.isAssignableFrom(clz)) {
				return (JSONSerializer) entry.getValue();
			}
		}

		return (JSONSerializer) serializerTypes.get(Object.class);
	}
	
	public static void serialize(Object objectSource,Writer os) {
		
		serialize(objectSource, os, false);
	}
	
	public static void serialize(Object objectSource,Writer os,boolean force) {
		
		if (objectSource != null) {
			
			if (objectSource instanceof JSONSerializable && !force){
				((JSONSerializable)objectSource).serialize(os);
				return ;
			}
			
			Class clazz = objectSource.getClass();
			
			JSONSerializer serializer = getSerializer(clazz);
			if (serializer != null) {
				serializer.serialize(objectSource,os);
			}
		}
	}

	public static Object deseialize(Class paramType, String json) {
		if (StringUtils.isEmpty(json)) {
			return null;
		}
		JSONSerializer serializer = getSerializer(paramType);
		if (serializer != null) {
			return serializer.deseialize(paramType, json);
		}
		return null;
	}
}
