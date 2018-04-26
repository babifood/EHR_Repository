package com.babifood.control;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.babifood.entity.LoginEntity;
import com.babifood.service.LoginService;


@Controller
public class LoginControl {
	@Autowired
	LoginService loginService;
	@ResponseBody
	@RequestMapping("/login")
	public Map<String,Object> loginMethod(String user_name,String password,HttpServletRequest request){
		HttpSession session =  request.getSession();
		Map<String,Object> map = new HashMap<String,Object>();
		LoginEntity logininfo = (LoginEntity) loginService.loginServiceMethod(user_name, password);
		if(logininfo!=null){
			map.put("status", "success");
			session.setAttribute("userinfo", logininfo);
		}else{
			map.put("status", "error");
			session.setAttribute("userinfo", null);
		}
		return map;	
	}
	@RequestMapping("/redirect")
	public String Redirect(HttpServletRequest request){
		HttpSession session =  request.getSession();
		String pageName = request.getParameter("pageName");
		if(session.getAttribute("userinfo")!=null){
			return pageName;
		}else{
			return "";
		}
		
	}
}
