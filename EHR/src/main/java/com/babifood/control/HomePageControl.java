package com.babifood.control;



import java.util.HashMap;
import java.util.List;
import java.util.Map;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.babifood.entity.LoginEntity;
import com.babifood.service.HomePageService;

@Controller
public class HomePageControl {
	@Autowired
	HomePageService homePageService;
	/**
	 * 获取导航菜单列表
	 * @param id父及菜单id
	 * @return 返回菜单对象集合
	 */
	@ResponseBody
	@RequestMapping("/loadTerr")
	public List<Map<String,Object>> loadTreeMenu(String id,HttpServletRequest request){
		HttpSession session =  request.getSession();
		LoginEntity login = (LoginEntity) session.getAttribute("userinfo");
		return homePageService.LoadTerrMenu(id,login.getRole_id());
	}
	/**
	 * 安全退出时清空缓存
	 */
	@ResponseBody
	@RequestMapping("/clearSession")
	public Map<String,Object> clearSession(HttpServletRequest request){
		Map<String,Object> map = new HashMap<String,Object>();
		HttpSession session =  request.getSession();
		session.removeAttribute("userinfo");
		map.put("status", "success");
		return map;
	}
}
