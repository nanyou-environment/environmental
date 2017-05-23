package com.nanyou.framework.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nanyou.framework.security.ClientSession;
import com.nanyou.framework.security.ClientSessionHolder;

public class ClientUtils {
	public static String CLIENT_SESSION = "CLIENT_SESSION";
	public static String CLIENT_ADMIN = "CLIENT_ADMIN";

	public static ClientSession getCS() {
		return ClientSessionHolder.getClientSession();
	}

	public static Object get(String key) {
		if (getCS() == null) {
			return null;
		}
		return getCS().getAttribute(key);
	}

	public static void set(String key, Object value) {
		getCS().addAttribute(key, value);
	}

	public static void remove(String key) {
		getCS().removeAttribute(key);
	}



	/**
	 * 抹去客户端登录信�?
	 * 
	 * @param username
	 * @param password
	 * @param request
	 * @param resp
	 */
	public static void forgetme(HttpServletRequest request, HttpServletResponse resp) {
		request.getSession().invalidate();
	}

}