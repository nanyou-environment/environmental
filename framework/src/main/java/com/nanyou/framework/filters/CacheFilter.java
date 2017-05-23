package com.nanyou.framework.filters;

import java.io.IOException;
import java.util.StringTokenizer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

public class CacheFilter implements Filter{
	private String cachedURIs;
	private int    maxAge=3600;
	

	public void destroy() {
	}

	public void init(FilterConfig config) throws ServletException {
		cachedURIs = jointString(config.getInitParameter("cachedURIs"));
		
		if (StringUtils.isNotEmpty(config.getInitParameter("maxAge"))){
			maxAge = Integer.parseInt(config.getInitParameter("maxAge"));
		}
		
	}

	protected String jointString(String str) {
		StringBuffer buf = new StringBuffer();
		for (StringTokenizer st = new StringTokenizer(str != null ? str : "", "\n", false); st.hasMoreTokens(); buf
				.append(st.nextToken().trim()))
			;
		return buf.toString();
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest hrequest = (HttpServletRequest) request;
		HttpServletResponse hresponse = (HttpServletResponse) response;
		
		String requestURI = hrequest.getRequestURI();
		
		if (requestURI.matches(cachedURIs)) {
			requestURI = StringUtils.lowerCase(requestURI);

			hresponse.setHeader("Cache-Control", "max-age="+maxAge);
			chain.doFilter(request, response);
			return;
		}
	}

}
