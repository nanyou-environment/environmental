/*
 *  Copyright 2004 Clinton Begin
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.nanyou.framework.jdbc.sql.beans;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ReflectPermission;
import java.lang.reflect.UndeclaredThrowableException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.nanyou.framework.jdbc.sql.beans.ClassInfo;
import com.nanyou.framework.jdbc.sql.beans.ProbeException;

/**
 * This class represents a cached set of class definition information that
 * allows for easy mapping between property names and getter/setter methods.
 */
public class ClassInfo {

	private static final Log log = LogFactory.getLog(ClassInfo.class);

	private static boolean cacheEnabled = true;

	private static final String[] EMPTY_STRING_ARRAY = new String[0];

	private static final Set SIMPLE_TYPE_SET = new HashSet();

	private static final Map CLASS_INFO_MAP = Collections.synchronizedMap(new HashMap());

	private String className;

	private String[] readablePropertyNames = EMPTY_STRING_ARRAY;

	private String[] writeablePropertyNames = EMPTY_STRING_ARRAY;

	private HashMap setMethods = new HashMap();

	private HashMap getMethods = new HashMap();

	private HashMap setTypes = new HashMap();

	private HashMap getTypes = new HashMap();

	static {
		SIMPLE_TYPE_SET.add(String.class);
		SIMPLE_TYPE_SET.add(Byte.class);
		SIMPLE_TYPE_SET.add(Short.class);
		SIMPLE_TYPE_SET.add(Character.class);
		SIMPLE_TYPE_SET.add(Integer.class);
		SIMPLE_TYPE_SET.add(Long.class);
		SIMPLE_TYPE_SET.add(Float.class);
		SIMPLE_TYPE_SET.add(Double.class);
		SIMPLE_TYPE_SET.add(Boolean.class);
		SIMPLE_TYPE_SET.add(Date.class);
		SIMPLE_TYPE_SET.add(Class.class);
		SIMPLE_TYPE_SET.add(BigInteger.class);
		SIMPLE_TYPE_SET.add(BigDecimal.class);

		SIMPLE_TYPE_SET.add(Collection.class);
		SIMPLE_TYPE_SET.add(Set.class);
		SIMPLE_TYPE_SET.add(Map.class);
		SIMPLE_TYPE_SET.add(List.class);
		SIMPLE_TYPE_SET.add(HashMap.class);
		SIMPLE_TYPE_SET.add(TreeMap.class);
		SIMPLE_TYPE_SET.add(ArrayList.class);
		SIMPLE_TYPE_SET.add(LinkedList.class);
		SIMPLE_TYPE_SET.add(HashSet.class);
		SIMPLE_TYPE_SET.add(TreeSet.class);
		SIMPLE_TYPE_SET.add(Vector.class);
		SIMPLE_TYPE_SET.add(Hashtable.class);
		SIMPLE_TYPE_SET.add(Enumeration.class);
	}

	private ClassInfo(Class clazz) {
		className = clazz.getName();
		addMethods(clazz);
		readablePropertyNames = (String[]) getMethods.keySet().toArray(new String[getMethods.keySet().size()]);
		writeablePropertyNames = (String[]) setMethods.keySet().toArray(new String[setMethods.keySet().size()]);
	}

	private void addMethods(Class cls) {
		Method[] methods = getAllMethodsForClass(cls);
		for (int i = 0; i < methods.length; i++) {
			String name = methods[i].getName();
			if (name.startsWith("set") && name.length() > 3) {
				if (methods[i].getParameterTypes().length == 1) {
					name = dropCase(name);
					if (setMethods.containsKey(name)) {
						// TODO(JGB) - this should probably be a
						// RuntimeException at some point???
						log.error("Illegal overloaded setter method for property " + name + " in class "
								+ cls.getName()
								+ ".  This breaks the JavaBeans specification and can cause unpredicatble results.");
					}
					setMethods.put(name, methods[i]);
					setTypes.put(name, methods[i].getParameterTypes()[0]);
				}
			} else if (name.startsWith("get") && name.length() > 3) {
				if (methods[i].getParameterTypes().length == 0) {
					name = dropCase(name);
					getMethods.put(name, methods[i]);
					getTypes.put(name, methods[i].getReturnType());
				}
			} else if (name.startsWith("is") && name.length() > 2) {
				if (methods[i].getParameterTypes().length == 0) {
					name = dropCase(name);
					getMethods.put(name, methods[i]);
					getTypes.put(name, methods[i].getReturnType());
				}
			}
			name = null;
		}
	}

	private Method[] getAllMethodsForClass(Class cls) {
		Set uniqueMethodNames = new HashSet();
		List allMethods = new ArrayList();
		Class currentClass = cls;
		while (currentClass != null) {
			addMethods(currentClass, uniqueMethodNames, allMethods);
			Class[] interfaces = currentClass.getInterfaces();
			for (int i = 0; i < interfaces.length; i++) {
				addMethods(interfaces[i], uniqueMethodNames, allMethods);
			}
			currentClass = currentClass.getSuperclass();
		}
		return (Method[]) allMethods.toArray(new Method[allMethods.size()]);
	}

	private void addMethods(Class currentClass, Set uniqueMethodNames, List allMethods) {
		Method[] methods = currentClass.getDeclaredMethods();
		for (int i = 0; i < methods.length; i++) {
			Method currentMethod = methods[i];
			String methodName = currentMethod.getName() + currentMethod.getParameterTypes().length;
			if (!uniqueMethodNames.contains(methodName)) {
				uniqueMethodNames.add(methodName);
				if (canAccessPrivateMethods()) {
					try {
						currentMethod.setAccessible(true);
					} catch (Exception e) {
						// Ignored. This is only a final precaution, nothing we
						// can do.
					}
				}
				allMethods.add(currentMethod);
			}
		}
	}

	private boolean canAccessPrivateMethods() {
		try {
			System.getSecurityManager().checkPermission(new ReflectPermission("suppressAccessChecks"));
			return true;
		} catch (SecurityException e) {
			return false;
		} catch (NullPointerException e) {
			return true;
		}
	}

	private static String dropCase(String name) {
		if (name.startsWith("is")) {
			name = name.substring(2);
		} else if (name.startsWith("get") || name.startsWith("set")) {
			name = name.substring(3);
		} else {
			throw new ProbeException("Error parsing property name '" + name
					+ "'.  Didn't start with 'is', 'get' or 'set'.");
		}

		if (name.length() == 1 || (name.length() > 1 && !Character.isUpperCase(name.charAt(1)))) {
			name = name.substring(0, 1).toLowerCase(Locale.US) + name.substring(1);
		}

		return name;
	}

	/**
	 * Gets the name of the class the instance provides information for
	 * 
	 * @return The class name
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * Gets the setter for a property as a Method object
	 * 
	 * @param propertyName -
	 *            the property
	 * @return The Method
	 */
	public Method getSetter(String propertyName) {
		Method method = (Method) setMethods.get(propertyName);
		if (method == null) {
			throw new ProbeException("There is no WRITEABLE property named '" + propertyName + "' in class '"
					+ className + "'");
		}
		return method;
	}

	/**
	 * Gets the getter for a property as a Method object
	 * 
	 * @param propertyName -
	 *            the property
	 * @return The Method
	 */
	public Method getGetter(String propertyName) {
		Method method = (Method) getMethods.get(propertyName);
		if (method == null) {
			throw new ProbeException("There is no READABLE property named '" + propertyName + "' in class '"
					+ className + "'");
		}
		return method;
	}

	/**
	 * Gets the type for a property setter
	 * 
	 * @param propertyName -
	 *            the name of the property
	 * @return The Class of the propery setter
	 */
	public Class getSetterType(String propertyName) {
		Class clazz = (Class) setTypes.get(propertyName);
		if (clazz == null) {
			throw new ProbeException("There is no WRITEABLE property named '" + propertyName + "' in class '"
					+ className + "'");
		}
		return clazz;
	}

	/**
	 * Gets the type for a property getter
	 * 
	 * @param propertyName -
	 *            the name of the property
	 * @return The Class of the propery getter
	 */
	public Class getGetterType(String propertyName) {
		Class clazz = (Class) getTypes.get(propertyName);
		if (clazz == null) {
			throw new ProbeException("There is no READABLE property named '" + propertyName + "' in class '"
					+ className + "'");
		}
		return clazz;
	}

	/**
	 * Gets an array of the readable properties for an object
	 * 
	 * @return The array
	 */
	public String[] getReadablePropertyNames() {
		return readablePropertyNames;
	}

	/**
	 * Gets an array of the writeable properties for an object
	 * 
	 * @return The array
	 */
	public String[] getWriteablePropertyNames() {
		return writeablePropertyNames;
	}

	/**
	 * Check to see if a class has a writeable property by name
	 * 
	 * @param propertyName -
	 *            the name of the property to check
	 * @return True if the object has a writeable property by the name
	 */
	public boolean hasWritableProperty(String propertyName) {
		return setMethods.keySet().contains(propertyName);
	}

	/**
	 * Check to see if a class has a readable property by name
	 * 
	 * @param propertyName -
	 *            the name of the property to check
	 * @return True if the object has a readable property by the name
	 */
	public boolean hasReadableProperty(String propertyName) {
		return getMethods.keySet().contains(propertyName);
	}

	/**
	 * Tells us if the class passed in is a knwon common type
	 * 
	 * @param clazz
	 *            The class to check
	 * @return True if the class is known
	 */
	public static boolean isKnownType(Class clazz) {
		if (SIMPLE_TYPE_SET.contains(clazz)) {
			return true;
		} else if (Collection.class.isAssignableFrom(clazz)) {
			return true;
		} else if (Map.class.isAssignableFrom(clazz)) {
			return true;
		} else if (List.class.isAssignableFrom(clazz)) {
			return true;
		} else if (Set.class.isAssignableFrom(clazz)) {
			return true;
		} else if (Iterator.class.isAssignableFrom(clazz)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Gets an instance of ClassInfo for the specified class.
	 * 
	 * @param clazz
	 *            The class for which to lookup the method cache.
	 * @return The method cache for the class
	 */
	public static ClassInfo getInstance(Class clazz) {
		if (cacheEnabled) {
			synchronized (clazz) {
				ClassInfo cache = (ClassInfo) CLASS_INFO_MAP.get(clazz);
				if (cache == null) {
					cache = new ClassInfo(clazz);
					CLASS_INFO_MAP.put(clazz, cache);
				}
				return cache;
			}
		} else {
			return new ClassInfo(clazz);
		}
	}

	public static void setCacheEnabled(boolean cacheEnabled) {
		ClassInfo.cacheEnabled = cacheEnabled;
	}

	/**
	 * Examines a Throwable object and gets it's root cause
	 * 
	 * @param t -
	 *            the exception to examine
	 * @return The root cause
	 */
	public static Throwable unwrapThrowable(Throwable t) {
		Throwable t2 = t;
		while (true) {
			if (t2 instanceof InvocationTargetException) {
				t2 = ((InvocationTargetException) t).getTargetException();
			} else if (t instanceof UndeclaredThrowableException) {
				t2 = ((UndeclaredThrowableException) t).getUndeclaredThrowable();
			} else {
				return t2;
			}
		}
	}

}
