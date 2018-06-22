package com.babifood.control;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.babifood.entity.ArrangementEntity;
import com.babifood.entity.LoginEntity;
import com.babifood.service.ArrangementService;

@Controller
@RequestMapping("arrangement")
public class ArrangementController {

	@Autowired
	private ArrangementService arrangementService;
	
	@RequestMapping(value = "findList")
	@ResponseBody
	public Map<String, Object> findListArrangements(Integer page,Integer rows){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageNum", page == null? 1 : page <= 0 ? 1 : page);
		map.put("pageSize", rows == null? 10 : rows <= 0 ? 10 : rows);
		return arrangementService.findListArrangements(map);
	}
	
	
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveArrangement(ArrangementEntity arrangement,String deptCodes,String pIds,HttpServletRequest request){
		HttpSession session =  request.getSession();
		LoginEntity logininfo = (LoginEntity) session.getAttribute("userinfo");
		arrangement.setCreatorId(logininfo.getUser_id());
		arrangement.setCreatorName(logininfo.getUser_name());
		return arrangementService.saveArrangement(arrangement,deptCodes,pIds);
	}
	
	@RequestMapping("remove")
	@ResponseBody
	public Map<String, Object> removeArrangement(Integer id){
		return arrangementService.removeArrangement(id);
	}
	
	
}
