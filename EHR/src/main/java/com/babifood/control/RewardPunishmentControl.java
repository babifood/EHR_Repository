package com.babifood.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.babifood.entity.LoginEntity;
import com.babifood.entity.RewardPunishmentEntity;
import com.babifood.service.RewardPunishmentService;


@Controller
public class RewardPunishmentControl{
	@Autowired
	RewardPunishmentService RewardPunishmentService;
	/**
	 * 查询所有奖惩项目
	 * @return 返回奖惩项目json
	 */
	@ResponseBody
	@RequestMapping("/loadRAPItemAll")
	public Map<String,Object> loadRAPItemAll(String category_id,String item_name){
		Map<String,Object> map =new HashMap<String,Object>();
		List<Map<String, Object>> list = RewardPunishmentService.loadRAPItemAll(category_id,item_name);
		map.put("total", list.size());
		map.put("rows", list);
		return map;
	}
	/**
	 * 保存奖惩项目
	 * @param item_id 奖惩项目ID
	 * @param item_name 奖惩项目名称
	 * @param category_id 奖惩类别ID
	 * @return map
	 */
	@ResponseBody
	@RequestMapping("/saveRAPItem")
	public Map<String,Object> saveRAPItem(String item_id,String item_name,String category_id){
		Map<String,Object> map =new HashMap<String,Object>();
		int rows = RewardPunishmentService.saveRAPItem(item_id, item_name,category_id);
		if(rows>0){
			map.put("status", "success");
		}else{
			map.put("status", "error");
		}
		return map;
	}
	/**
	 * 修改奖惩项目
	 * @param item_id 奖惩项目ID
	 * @param item_name 奖惩项目名称
	 * @param category_id 奖惩类别ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/editRAPItem")
	public Map<String,Object> eidtRAPItem(String item_id,String item_name,String category_id){
		Map<String,Object> map =new HashMap<String,Object>();
		int rows = RewardPunishmentService.eidtRAPItem(item_id, item_name,category_id);
		if(rows>0){
			map.put("status", "success");
		}else{
			map.put("status", "error");
		}
		return map;
	}
	/**
	 * 删除奖惩项目
	 * @param item_id 奖惩项目ID
	 * @return map
	 */
	@ResponseBody
	@RequestMapping("/removeRAPItem")
	public Map<String,Object> removeRAPItem(String item_id){
		Map<String,Object> map =new HashMap<String,Object>();
		int rows = RewardPunishmentService.removeRAPItem(item_id);
		if(rows>0){
			map.put("status", "success");
		}else{
			map.put("status", "error");
		}
		return map;
	}
	/**
	 * 加载奖惩项目下拉框数据
	 * @return 返回奖惩项目json
	 */
	@ResponseBody
	@RequestMapping("/loadComboboxRAPItemData")
	public List<Map<String, Object>> loadComboboxRAPItemData(){
		List<Map<String, Object>> list = RewardPunishmentService.loadComboboxRAPItemData();
		return list;
	}
	/**
	 * 查询所有奖惩记录
	 * @return 返回奖惩记录json
	 */
	@ResponseBody
	@RequestMapping("/loadRewardPunishment")
	public Map<String,Object> loadRewardPunishment(String rap_category, String rap_item){
		Map<String,Object> map =new HashMap<String,Object>();
		List<Map<String, Object>> list = RewardPunishmentService.loadRewardPunishment(rap_category, rap_item);
		map.put("total", list.size());
		map.put("rows", list);
		return map;
	}
	/**
	 * 保存奖惩记录
	 * @param RewardPunishmentEntity 奖惩记录对象
	 * @return map
	 */
	@ResponseBody
	@RequestMapping("/saveRewardPunishment")
	public Map<String,Object> saveUser(RewardPunishmentEntity rewardpunishmentEntity){
		Map<String,Object> map =new HashMap<String,Object>();
		int rows = RewardPunishmentService.saveRewardPunishment(rewardpunishmentEntity);
		if(rows>0){
			map.put("status", "success");
		}else{
			map.put("status", "error");
		}
		return map;
	}
	/**
	 * 修改奖惩记录
	 * @param RewardPunishmentEntity 奖惩记录对象
	 * @return map
	 */
	@ResponseBody
	@RequestMapping("/editRewardPunishment")
	public Map<String,Object> editRewardPunishment(RewardPunishmentEntity rewardpunishmentEntity){
		Map<String,Object> map =new HashMap<String,Object>();
		int rows = RewardPunishmentService.editRewardPunishment(rewardpunishmentEntity);
		if(rows>0){
			map.put("status", "success");
		}else{
			map.put("status", "error");
		}
		return map;
	}
	/**
	 * 删除奖惩记录
	 * @param Position_id 奖惩记录id
	 * @return map
	 */
	@ResponseBody
	@RequestMapping("/removeRewardPunishment")
	public Map<String,Object> removeRewardPunishment(String rap_id){
		Map<String,Object> map =new HashMap<String,Object>();
		int rows = RewardPunishmentService.removeRewardPunishment(rap_id);
		if(rows>0){
			map.put("status", "success");
		}else{
			map.put("status", "error");
		}
		return map;
	}
	
}
