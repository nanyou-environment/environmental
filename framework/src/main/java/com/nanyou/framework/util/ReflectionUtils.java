package com.nanyou.framework.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.nanyou.framework.spring.SpringContextUtil;

public class ReflectionUtils {
	public static Object invoke(Object invokeTarget, String methodName,
			Object[] params) throws Exception {
		Object invokerResult = null;

		if (invokeTarget == null) {
			throw new IllegalArgumentException("InvokeTarget is required");
		}
		if (params == null) {
			invokerResult = invokeTarget.getClass().getMethod(methodName,
					new Class[] {}).invoke(invokeTarget, new Object[] {});
		} else {
			Method[] allMethods = invokeTarget.getClass().getMethods();
			List candidateMethods = filterMethodsByName(allMethods, methodName);
			Object invokeResult = null;
			List javaParams = null;

			Iterator iter = candidateMethods.iterator();
			while (iter.hasNext()) {
				Method method = (Method) iter.next();
				Class[] paramTypes = method.getParameterTypes();
				javaParams = getMatchedParams(paramTypes, params);
				if (javaParams != null && javaParams.size() == params.length) {
					invokeResult = method.invoke(invokeTarget, javaParams
							.toArray());
					return invokeResult;
				} else {
					continue;
				}
			}
			throw new IllegalArgumentException();
		}

		return invokerResult;
	}
	
	
	public static Object invoke(String serviceName, String methodName,
			Object[] params) throws Exception {
		
		Object invokerResult = null;
		
		Object invokeTarget = SpringContextUtil.getService(serviceName);
		
		if (invokeTarget == null) {
			throw new IllegalArgumentException("InvokeTarget is required");
		}
		
		if (params == null) {
			invokerResult = invokeTarget.getClass().getMethod(methodName,
					new Class[] {}).invoke(invokeTarget, new Object[] {});
		} else {
			Method[] allMethods = invokeTarget.getClass().getMethods();
			List candidateMethods = filterMethodsByName(allMethods, methodName);
			Object invokeResult = null;
			List javaParams = null;

			Iterator iter = candidateMethods.iterator();
			while (iter.hasNext()) {
				Method method = (Method) iter.next();
				Class[] paramTypes = method.getParameterTypes();
				javaParams = getMatchedParams(paramTypes, params);
				if (javaParams != null && javaParams.size() == params.length) {
					invokeResult = method.invoke(invokeTarget, javaParams
							.toArray());
					return invokeResult;
				} else {
					continue;
				}
			}
			throw new IllegalArgumentException();
		}

		return invokerResult;
	}

	private static List getMatchedParams(Class[] paramTypes, Object[] params)
			throws Exception {
		List javaParams = new ArrayList();
		if (paramTypes.length != params.length) {
			return null;
		} else {
			for (int i = 0; i < paramTypes.length; i++) {
				Class clazz = paramTypes[i];
				if (clazz.isAssignableFrom(params[i].getClass())) {
					javaParams.add(params[i]);
				}
			}
		}
		return javaParams;
	}

	private static List filterMethodsByName(Method[] methods, String methodName)
			throws NoSuchMethodException {
		List methodsWithTheSameName = new ArrayList();
		for (int i = 0; i < methods.length; i++) {
			Method method = methods[i];
			if (method.getName().equals(methodName)) {
				methodsWithTheSameName.add(method);
			}
		}
		if (methodsWithTheSameName.size() == 0) {
			throw new NoSuchMethodException(methodName);
		}
		return methodsWithTheSameName;
	}
}
