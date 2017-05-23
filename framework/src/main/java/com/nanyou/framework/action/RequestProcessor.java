package com.nanyou.framework.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.nanyou.framework.exception.ServiceException;
import com.nanyou.framework.util.ExceptionUtils;
import com.nanyou.framework.util.JavaScriptUtil;
import com.nanyou.framework.util.ReflectionUtils;

public class RequestProcessor {
	static Log log = LogFactory.getLog(RequestProcessor.class);

	private ServletContext servletContext;

	public ServletContext getServletContext() {
		return servletContext;
	}

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	public void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			doProcess(request, response);
		} catch (Throwable t) {
			error(request, response, t);
		}
	}

	private void doProcess(HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		response.setContentType("text/html");

		String requestPath = StringUtils.removeStart(request.getRequestURI(),
				request.getContextPath());
		requestPath = StringUtils.removeStart(requestPath, request
				.getServletPath());

		if (requestPath.startsWith("/")) {
			requestPath = requestPath.substring(1);
		}
		if (requestPath.endsWith("/")) {
			requestPath = requestPath.substring(0, requestPath.length() - 1);
		}

		String actionName = requestPath;
		if (StringUtils.isEmpty(actionName)) {
			throw new RuntimeException("Can't execute action because "
					+ actionName + " is found.");
		}

		if (isMultipart(request)) {
			request = new CommonsMultipartResolver().resolveMultipart(request);
		}

		String method = getDispatchMethod(request);

		try {
			invokeAction(request, response, actionName, method);
		} catch (InvocationTargetException e) {
			Throwable ex = e.getTargetException();
			if (request.getRemoteHost() != null
					&& (request.getRemoteHost().equals("127.0.0.1") || request
							.getRemoteHost().equals("localhost"))) {
				throw ex;
			} else {
				// 为解决异常报错问题修改（2012-07-05�?
				if (!(ex instanceof ServiceException)) {
					ex.printStackTrace();
				} else if (ex instanceof ServiceException
						&& ex.getMessage() != null
						&& ex.getMessage().startsWith("分布式改造")) {
						ex.printStackTrace();
					
				} else {
					throw ex;
				}
			}
		} catch (Exception e) {
			throw e;
		}

	}

	private void writeResponse(HttpServletResponse response, String text)
			throws IOException {
		if (text == null) {
			text = "";
		}
		PrintWriter writer = response.getWriter();
		writer.print(text);
		writer.flush();
		writer.close();
	}

	private void error(HttpServletRequest request,
			HttpServletResponse response, Throwable t) throws IOException {
		ServiceException se = null;

		se = ExceptionUtils.translateException(t);

		if (log.isErrorEnabled()) {
			// log.error(t);
			if (se.getCause() != null) {
				log.error(t.getMessage(), t);
			}
		}

		if (isMultipart(request)) {
			response.setStatus(HttpServletResponse.SC_OK);

		} else {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
		// response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		if (isMultipart(request)) {
			sb.append("success:false,");
		}
		sb.append("ERROR_MSG:").append(
				"\"" + JavaScriptUtil.escapeJavaScript(se.getMessage()) + "\"");

		if (se.getCause() != null) {
			sb.append(",");
			String stacktrace = org.apache.commons.lang.exception.ExceptionUtils
					.getFullStackTrace(t);
			stacktrace = "\"" + JavaScriptUtil.escapeJavaScript(stacktrace)
					+ "\"";
			stacktrace = JavaScriptUtil.replace(stacktrace, "\\r\\n", "<BR>");
			sb.append("ERROR_STACK:").append(stacktrace);
		}
		sb.append("}");
		writeResponse(response, sb.toString());
	}

	public void invokeAction(HttpServletRequest request,
			HttpServletResponse response, String actionName, String methodName)
			throws Exception {
		if (log.isDebugEnabled()) {
			log.debug(request.getRequestURI());
		}

		Object invokeTarget = Class.forName(actionName).newInstance();

		if (invokeTarget == null) {
			throw new IllegalArgumentException(actionName + " is required");
		}

		if (invokeTarget instanceof Action) {
			Action action = (Action) invokeTarget;
			action.setServletContext(getServletContext());
			if (StringUtils.isEmpty(methodName)) {
				ReflectionUtils.invoke(action, "execute", new Object[] {
						request, response });
			} else {
				ReflectionUtils.invoke(action, methodName, new Object[] {
						request, response });
			}
		} else {
			throw new IllegalArgumentException(actionName
					+ " is not a JsonAction");
		}
	}

	protected boolean isMultipart(HttpServletRequest request) {

		if (!"POST".equalsIgnoreCase(request.getMethod())) {
			return false;
		}

		String contentType = request.getContentType();
		if ((contentType != null)
				&& contentType.startsWith("multipart/form-data")) {
			return true;
		} else {
			return false;
		}
	}

	protected String getDispatchMethod(HttpServletRequest request) {
		return request.getParameter("method");
	}

}
