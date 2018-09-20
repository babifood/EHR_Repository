package com.babifood.control;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.babifood.entity.LoginEntity;
import com.babifood.entity.UserRoleEntity;
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
		//从shiro的session中取activeUser
		Subject subject = SecurityUtils.getSubject();
		//取身份信息
		LoginEntity activeUser = (LoginEntity) subject.getPrincipal();
		StringBuffer strRole = new StringBuffer();
		List<UserRoleEntity> roleList = activeUser.getRoleList();
		for (int i = 0;i<roleList.size();i++) {
			strRole.append("'");
			strRole.append(roleList.get(i).getRole_id());
			if(i==roleList.size()-1){
				strRole.append("'");
			}else{
				strRole.append("',");
			}
		}
		
		return homePageService.LoadTerrMenu(id,strRole.toString());
	}
	//系统首页
	@RequestMapping("/HomePage")
	public String first(Model model)throws Exception{
		
		//从shiro的session中取activeUser
		Subject subject = SecurityUtils.getSubject();
		//取身份信息
		LoginEntity activeUser = (LoginEntity) subject.getPrincipal();
		//通过model传到页面
		model.addAttribute("activeUser", activeUser);
		
		return "/HomePage";
	}
	//
	//生日提醒
	@ResponseBody
	@RequestMapping("/loadBirthday")
	public Map<String,Object> loadBirthday(){
		Map<String,Object> map =new HashMap<String,Object>();
		List<Map<String, Object>> list = homePageService.loadBirthday();
		map.put("total", list.size());
		map.put("rows", list);
		return map;
	}
	//转正提醒
	@ResponseBody
	@RequestMapping("/loadZhuanZheng")
	public Map<String,Object> loadZhuanZheng(){
		Map<String,Object> map =new HashMap<String,Object>();
		List<Map<String, Object>> list = homePageService.loadZhuanZheng();
		map.put("total", list.size());
		map.put("rows", list);
		return map;
	}
	//证件到期提醒
	@ResponseBody
	@RequestMapping("/loadCertificateExpire")
	public Map<String,Object> loadCertificateExpire(){
		Map<String,Object> map =new HashMap<String,Object>();
		List<Map<String, Object>> list = homePageService.loadCertificateExpire();
		map.put("total", list.size());
		map.put("rows", list);
		return map;
	}
	//劳动合同到期提醒
	@ResponseBody
	@RequestMapping("/loadContractExpire")
	public Map<String,Object> loadContractExpire(){
		Map<String,Object> map =new HashMap<String,Object>();
		List<Map<String, Object>> list = homePageService.loadContractExpire();
		map.put("total", list.size());
		map.put("rows", list);
		return map;
	}	
	//员工入离职报表统计
	@ResponseBody
	@RequestMapping("/loadWorkInOutFormsData")
	public List<Map<String, Object>> loadWorkInOutForms(){
		//模拟数据
//		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
//		for(int i=1;i<=12;i++){
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("work_month", i+"月");
//			map.put("inWork", i+5);
//			map.put("outWork", i+3);
//			list.add(map);
//		}
//		return list;
		//真实数据
		List<Map<String, Object>> list = null;
		list = homePageService.loadWorkInOutForms();
		return list;
	}		
}
