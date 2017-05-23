package com.nanyou.framework.json;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Writer;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;

public class BeanSerializer implements JSONSerializer {

	public void serialize(Object objectSource,Writer os ) {
		String[] include = null;
		String[] exclude = null;


		if (objectSource instanceof PartialJSONSerializable) {
			include = ((PartialJSONSerializable) objectSource).getInclude();
			exclude = ((PartialJSONSerializable) objectSource).getExclude();
		}
		try {
			//os.write(ConversionConstants.INBOUND_MAP_START);
			BeanInfo beanInfo = Introspector.getBeanInfo(getOriginalClass(objectSource.getClass()));
			PropertyDescriptor[] properties = beanInfo.getPropertyDescriptors();
			ArrayList list = new ArrayList();
			for (int i = 0; i < properties.length; i++) {
				String propertyName = properties[i].getName();

				if (propertyName.equals("class")) {
					continue;
				} else {
					if (isIncluded(propertyName, include, exclude)) {
						list.add(properties[i]);
					}
				}
			}
			properties = (PropertyDescriptor[])list.toArray(new PropertyDescriptor[list.size()]);
			
			Map nameValueMap = new LinkedHashMap();
			for (int i = 0; i < properties.length; i++) {
				String propertyName = properties[i].getName();

				if (propertyName.equals("class")) {
					continue;
				} else {
					if (isIncluded(propertyName, include, exclude)) {
						Method readMethod = properties[i].getReadMethod();
						if (readMethod != null) {
							Object value = readMethod.invoke(objectSource, new Object[] {});
							if (value != null) {
								nameValueMap.put(propertyName,value);
								
//								os.write(propertyName);
//								os.write(ConversionConstants.INBOUND_MAP_ENTRY);
//								SerializerManager.serialize(value,os);
//								if (i<properties.length-1){
//									os.write(ConversionConstants.INBOUND_MAP_SEPARATOR);
//								}
							}
						}
					}
				}
			}
			
			SerializerManager.serialize(nameValueMap, os);
			
			//os.write(ConversionConstants.INBOUND_MAP_END);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private boolean isIncluded(String propName, String[] includes, String[] excludes) {
		if ((includes != null) && (ArrayUtils.indexOf(includes, propName) < 0)){
			return false;
		}
		if ((excludes != null) && (ArrayUtils.indexOf(excludes, propName) >= 0))
			return false;
		return true;
	}

	public Object deseialize(Class paramType, String json) {

		// If the text is null then the whole bean is null
		if (json.trim().equals(ConversionConstants.INBOUND_NULL)) {
			return null;
		}

		if (!json.startsWith(ConversionConstants.INBOUND_MAP_START)) {
			throw new IllegalArgumentException(" Missing Opener " + ConversionConstants.INBOUND_MAP_START); //$NON-NLS-1$
		}

		if (!json.endsWith(ConversionConstants.INBOUND_MAP_END)) {
			throw new IllegalArgumentException("Missing Closer " + ConversionConstants.INBOUND_MAP_START); //$NON-NLS-1$
		}

		try {
			Object bean = null;
			bean = paramType.newInstance();

			JSONObject beanJSONObject = new JSONObject(json);
			BeanInfo beanInfo = Introspector.getBeanInfo(paramType);
			PropertyDescriptor[] properties = beanInfo.getPropertyDescriptors();
			for (int i = 0; i < properties.length; i++) {
				String propertyName = properties[i].getName();
				Object targetPropertyValue = properties[i].getReadMethod().invoke(bean, new Object[] {});
				if (targetPropertyValue == null) {
					targetPropertyValue = properties[i].getPropertyType().newInstance();
				}
				if (beanJSONObject.has(propertyName)) {
					Object value = beanJSONObject.get(propertyName);
					Object unmarshalledProp = SerializerManager.deseialize(properties[i].getPropertyType(), value
							.toString());
					if (properties[i].getWriteMethod() != null) {
						properties[i].getWriteMethod().invoke(bean, new Object[] { unmarshalledProp });
					}
				}
			}
			return bean;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	private Class getOriginalClass(Class clazz) throws ClassNotFoundException {
		String className = clazz.getName();
		if (className.indexOf("$$") == -1) {
			return clazz;
		} else {
			Class originalClass = Class.forName(className.substring(0, className.indexOf("$$")));
			return originalClass;
		}
	}

}
