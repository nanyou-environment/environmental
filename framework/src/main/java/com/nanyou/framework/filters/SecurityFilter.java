package com.nanyou.framework.filters;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.StringTokenizer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;

import com.nanyou.framework.security.ClientSession;
import com.nanyou.framework.security.ClientSessionHolder;
import com.nanyou.framework.util.RequestUtils;

public class SecurityFilter implements Filter {

	private String unfilteredURIs;

	private String loginURL;

	private String welcomeURL;

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest hrequest = (HttpServletRequest) request;
		HttpServletResponse hresponse = (HttpServletResponse) response;
		
		String requestURI = hrequest.getRequestURI();

		HttpSession session = hrequest.getSession(true);
		ClientSession cs = (ClientSession) session.getAttribute("CLIENT_SESSION");

		if(requestURI.equals("/")||requestURI.equals(hrequest.getContextPath())){
			showPage(welcomeURL,hrequest, hresponse);
			return;
		}
		
		
		if (requestURI.matches(unfilteredURIs)) {
			chain.doFilter(request, response);
			return;
		}

		// 执行过滤  
        // 从session中获取登录者实体
        if (null == cs)  
        {  
            boolean isAjaxRequest = RequestUtils.isAjaxRequest(hrequest);  
            if (isAjaxRequest)  
            {  
                hresponse.setCharacterEncoding("UTF-8");  
                hresponse.sendError(HttpStatus.UNAUTHORIZED.value(), "您已经太长时间没有操作,请刷新页面");  
                return ;  
            }  
            showPage(loginURL,hrequest, hresponse);
            return;  
        }  
        else  
        {  
            // 如果session中存在登录�?实体，则继续  
        	ClientSessionHolder.setClientSession(cs);
            chain.doFilter(request, response);  
        }  
	}

	public void destroy() {
	}

	public void init(FilterConfig config) throws ServletException {
		loginURL = config.getInitParameter("loginURL").trim();
		welcomeURL = config.getInitParameter("welcomeURL").trim();
		unfilteredURIs = jointString(config.getInitParameter("unfilteredURIs"));
	}

	protected String jointString(String str) {
		StringBuffer buf = new StringBuffer();
		for (StringTokenizer st = new StringTokenizer(str != null ? str : "",
				"\n", false); st.hasMoreTokens(); buf.append(st.nextToken()
				.trim()))
			;
		return buf.toString();
	}

	private void showPage(String pageUrl, HttpServletRequest hrequest,
			HttpServletResponse response) throws UnsupportedEncodingException,
			ServletException, IOException {
		response.sendRedirect(hrequest.getContextPath() + pageUrl);
	}

}