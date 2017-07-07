package com.nanyou.framework.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class TokenProcessor {
	private static String TOKEN_SESSION_ATTR = "SESSION_TOKEN";
	private static String TOKEN_HEAD_ATTR = "access-token";
	private static TokenProcessor instance = new TokenProcessor();

	private long previous;

	protected TokenProcessor() {
	}

	public static TokenProcessor getInstance() {
		return instance;
	}

	public synchronized boolean isTokenValid(HttpServletRequest request) {
		return isTokenValid(request, false);
	}

	public synchronized boolean isTokenValid(HttpServletRequest request, boolean reset) {

		HttpSession session = request.getSession(false);
		if (session == null)
			return false;
		String saved = (String) session.getAttribute(TOKEN_SESSION_ATTR);
		if (saved == null)
			return false;

		if (reset) {
			resetToken(request);
		}
		String token = request.getHeader(TOKEN_HEAD_ATTR);
		if (token == null)
			return false;
		else
			return saved.equals(token);
	}

	public synchronized void resetToken(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return;
		} else {
			session.removeAttribute(TOKEN_SESSION_ATTR);
			return;
		}
	}

	public synchronized String saveToken(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String token = generateToken(request);
		if (token != null)
			session.setAttribute(TOKEN_SESSION_ATTR, token);
		return token;
	}

	public synchronized String generateToken(HttpServletRequest request) {
		HttpSession session = request.getSession();
		return generateToken(session.getId());
	}

	public synchronized String generateToken(String id) {
		try {
			long current = System.currentTimeMillis();
			if (current == previous)
				current++;
			previous = current;
			// byte now[] = (current+"").toString().getBytes();
			byte now[] = (new Long(current)).toString().getBytes();
			MessageDigest md = MessageDigest.getInstance("MD5");

			md.update(id.getBytes());
			md.update(now);
			return toHex(md.digest());
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}

	private String toHex(byte buffer[]) {
		StringBuffer sb = new StringBuffer(buffer.length * 2);
		for (int i = 0; i < buffer.length; i++) {
			sb.append(Character.forDigit((buffer[i] & 240) >> 4, 16));
			sb.append(Character.forDigit(buffer[i] & 15, 16));
		}

		return sb.toString();
	}
}
