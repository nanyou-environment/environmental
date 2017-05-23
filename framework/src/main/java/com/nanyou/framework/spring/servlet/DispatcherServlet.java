package com.nanyou.framework.spring.servlet;

import org.springframework.web.context.WebApplicationContext;

import com.nanyou.framework.spring.SpringContextHolder;

@SuppressWarnings("serial")
public class DispatcherServlet extends
		org.springframework.web.servlet.DispatcherServlet {
	@Override
	protected WebApplicationContext initWebApplicationContext() {
		WebApplicationContext ctx = super.initWebApplicationContext();
		SpringContextHolder.setApplicationContext(ctx);
		return ctx;
	}
}
