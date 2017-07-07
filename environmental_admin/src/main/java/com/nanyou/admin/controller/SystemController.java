package com.nanyou.admin.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nanyou.admin.service.AdminService;
import com.nanyou.common.model.Admin;
import com.nanyou.framework.security.ClientSession;
import com.nanyou.framework.util.ClientUtils;
import com.nanyou.framework.util.TokenProcessor;


@Controller
@RequestMapping("/system")
public class SystemController {

	@Autowired
	private AdminService adminService;
	
	@RequestMapping(value = "/login" , method = RequestMethod.POST)
	@ResponseBody
	public Map login(String account,String password,HttpServletRequest request){
		Map result = new HashMap();
		try{
			Admin admin = adminService.getAdminByAccountAndPassword(account, password);
			if(admin != null){
				result.put("result", true);
				String token = TokenProcessor.getInstance().saveToken(request);
				result.put("token", token);
				result.put("data", admin);
				HttpSession session = request.getSession();
				ClientSession cs = (ClientSession)session.getAttribute(ClientUtils.CLIENT_SESSION);
				if(cs == null){
					cs = new ClientSession();
				}
                cs.addAttribute(ClientUtils.CLIENT_ADMIN, admin);
	            session.setAttribute(ClientUtils.CLIENT_SESSION, cs);
			}else{
				result.put("result", false);
			}
		}catch(Exception e){
			e.printStackTrace();
			result.put("result", false);
		}
		return result;
	}
	
}
