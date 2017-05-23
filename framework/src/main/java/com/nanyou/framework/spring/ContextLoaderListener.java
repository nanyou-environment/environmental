package com.nanyou.framework.spring;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class ContextLoaderListener extends
		org.springframework.web.context.ContextLoaderListener {

	public void contextInitialized(ServletContextEvent event) {
		super.contextInitialized(event);
		ServletContext servletContext = event.getServletContext();
		ApplicationContext context = WebApplicationContextUtils
				.getWebApplicationContext(servletContext);
		SpringContextHolder.setApplicationContext(context);
	}

}
