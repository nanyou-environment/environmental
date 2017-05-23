package com.nanyou.framework.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.nanyou.framework.util.RequestUtils;

public class ExceptionHandler implements HandlerExceptionResolver {

	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response,
			Object handler, Exception ex) {
		if(RequestUtils.isAjaxRequest(request)){
			try {
				PrintWriter writer = response.getWriter();
				JSONObject result = new JSONObject();
				response.setContentType("text/html;charset=utf-8");
				response.setStatus(500);
				response.setContentType("text/html;charset=utf8");
				result.put("ERROR_MSG", ex.getMessage());
				writer.write(result.toString());
				writer.flush();
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}else{
			ex.printStackTrace();
			ModelAndView model= new ModelAndView("global/exception"); 
			model.addObject("errorMsg", ex.getMessage());
			return model;
		}
	}

}