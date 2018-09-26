package com.babifood.control;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.babifood.entity.BaseArrangementEntity;
import com.babifood.entity.SpecialArrangementEntity;
import com.babifood.service.BaseArrangementService;
import com.babifood.utils.UtilString;

@Controller
@RequestMapping("arrangement")
public class BaseArrangementController {
	
	@Autowired
	private BaseArrangementService baseArrangementService;
	
	/**
	 * 查询所有排班列表
	 * @return
	 */
	@RequestMapping(value = "base/findAll")
	@ResponseBody
	public List<Map<String, Object>> findBaseArrangements(){
		return baseArrangementService.findBaseArrangements();
	}
	
	/**
	 * 保存排班信息
	 * @param arrangement
	 * @return
	 */
	@RequestMapping("base/save")
	@ResponseBody
	public Map<String, Object> saveBaseArrangement(BaseArrangementEntity arrangement){
		return baseArrangementService.saveBaseArrangement(arrangement);
	}
	
	/**
	 * 删除排班信息
	 * @param id
	 * @return
	 */
	@RequestMapping("base/remove")
	@ResponseBody
	public Map<String, Object> removeBaseArrangement(Integer id){
		return baseArrangementService.removeBaseArrangement(id);
	}
	
	/**
	 * 条件查询特殊排班列表  
	 * @param year
	 * @param month
	 * @param arrangementId
	 * @param deptCode
	 * @param pNumber
	 * @return
	 */
	@RequestMapping("base/specialArrangementList")
	@ResponseBody
	public Map<String, Object> findSpecialArrangementList(String year,String month,String arrangementId,String deptCode,String pNumber) {
		String date = year + "-" + (month.length() == 1? "0"+month : month);
		if(!UtilString.isEmpty(arrangementId)){
			return baseArrangementService.findSpecialArrangementList(date,arrangementId);
		}
		return baseArrangementService.findSpecialArrangementList(date,deptCode,pNumber);
	}
	
	/**
	 * 设置特殊排班
	 * @param arrangement
	 * @return
	 */
	@RequestMapping("base/saveSpecialArrangement")
	@ResponseBody
	public Map<String, Object> saveSpecialArrangement(SpecialArrangementEntity arrangement) {
		return baseArrangementService.saveSpecialArrangement(arrangement);
	}
	
	/**
	 * 删除特殊排班
	 * @param id
	 * @return
	 */
	@RequestMapping("base/removeSpecialArrangement")
	@ResponseBody
	public Map<String, Object> removeSpecialArrangement(String id) {
		return baseArrangementService.removeSpecialArrangement(id);
	}
	
	/**
	 * 根据id查询排班信息
	 * @param id
	 * @return
	 */
	@RequestMapping("base/findSpecialArrangement")
	@ResponseBody
	public Map<String, Object> findSpecialArrangement(String id) {
		return baseArrangementService.findSpecialArrangementById(id);
	}
	
	/**
	 * 获取部门机构、人员的排班
	 * @param deptCode
	 * @param pNumber
	 * @return
	 */
	@RequestMapping("base/specialArrangementId")
	@ResponseBody
	public Map<String, Object> findSpecialArrangementId(String deptCode,String pNumber) {
		return baseArrangementService.findSpecialArrangementId(deptCode,pNumber);
	}
	
	/**
	 * 绑定排班
	 * @param targetId
	 * @param type
	 * @param arrangementId
	 * @return
	 */
	@RequestMapping("base/bindArrangement")
	@ResponseBody
	public Map<String, Object> bindArrangement(String targetId,String type,String arrangementId) {
		try {
			return baseArrangementService.bindArrangement(targetId,type,arrangementId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
