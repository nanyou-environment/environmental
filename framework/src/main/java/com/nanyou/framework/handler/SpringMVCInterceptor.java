package com.nanyou.framework.handler;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.nanyou.framework.util.TokenProcessor;


public class SpringMVCInterceptor implements HandlerInterceptor {

	private String unfilteredURIs;
	private Pattern regex = null;

	public String getUnfilteredURIs() {
		return unfilteredURIs;
	}

	public void setUnfilteredURIs(String unfilteredURIs) {
		this.unfilteredURIs = unfilteredURIs;
	}

	private void init() {
		regex = Pattern.compile(unfilteredURIs);
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String requestURI = request.getRequestURI().substring(request.getContextPath().length());
		
		if (regex.matcher(requestURI).matches()) {
			return true;
		}
		boolean auth = TokenProcessor.getInstance().isTokenValid(request);
		if (!auth) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			return false;
		}
//		HttpSession session = request.getSession(true);
//		ClientSession cs = (ClientSession) session.getAttribute("CLIENT_SESSION");
//		ClientSessionHolder.setClientSession(cs);
		return true;
	}
	
	/**
	 * 这个方法只会在当前这个Interceptor的preHandle方法返回值为true的时候才会执行。postHandle是进行处理器拦截用的，它的执行时间是在处理器进行处理之
	 * 后，也就是在Controller的方法调用之后执行，但是它会在DispatcherServlet进行视图的渲染之前执行，也就是说在这个方法中你可以对ModelAndView进行操
	 * 作。这个方法的链式结构跟正常访问的方向是相反的，也就是说先声明的Interceptor拦截器该方法反而会后调用，这跟Struts2里面的拦截器的执行过程有点像，
	 * 只是Struts2里面的intercept方法中要手动的调用ActionInvocation的invoke方法，Struts2中调用ActionInvocation的invoke方法就是调用下一个Interceptor
	 * 或者是调用action，然后要在Interceptor之前调用的内容都写在调用invoke之前，要在Interceptor之后调用的内容都写在调用invoke方法之后。
	 */
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 该方法也是需要当前对应的Interceptor的preHandle方法的返回值为true时才会执行。该方法将在整个请求完成之后，也就是DispatcherServlet渲染了视图执行，
	 * 这个方法的主要作用是用于清理资源的，当然这个方法也只能在当前这个Interceptor的preHandle方法的返回值为true时才会执行。
	 */
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
	throws Exception {
		// TODO Auto-generated method stub
		
	}
	
}
