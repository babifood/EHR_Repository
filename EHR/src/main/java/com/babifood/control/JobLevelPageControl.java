package com.babifood.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import com.babifood.entity.PositionEntity;
import com.babifood.entity.PostEntity;
import com.babifood.service.JobLevelPageService;


@Controller
public class JobLevelPageControl{
	@Autowired
	JobLevelPageService JobLevelPageService;
	/**
	 * 查询所有职级
	 * @return 返回职级json
	 */
	@ResponseBody
	@RequestMapping("/loadJobLevelAll")
	public Map<String,Object> loadJobLevelAll(Integer joblevel_id,String joblevel_name,String position_name){
		Map<String,Object> map =new HashMap<String,Object>();
		List<Map<String, Object>> list = JobLevelPageService.loadJobLevelAll(joblevel_id,joblevel_name,position_name);
		map.put("total", list.size());
		map.put("rows", list);
		return map;
	}
	/**
	 * 保存职级
	 * @param JobLevel_id 职务级别ID
	 * @param JobLevel_name 职务级别名称
	 * @param position_name 包含职位
	 * @return map
	 */
	@ResponseBody
	@RequestMapping("/saveJobLevel")
	public Map<String,Object> saveJobLevel(String joblevel_name,String joblevel_desc){
		Map<String,Object> map =new HashMap<String,Object>();
		int rows = JobLevelPageService.saveJobLevel(joblevel_name,joblevel_desc);
		if(rows>0){
			map.put("status", "success");
		}else{
			map.put("status", "error");
		}
		return map;
	}
	/**
	 * 修改职级
	 * @param JobLevel_id 职务级别ID
	 * @param JobLevel_name 职务级别名称
	 * @param position_name 包含职位
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/editJobLevel")
	public Map<String,Object> eidtJobLevel(Integer joblevel_id,String joblevel_name,String joblevel_desc){
		Map<String,Object> map =new HashMap<String,Object>();
		int rows = JobLevelPageService.editJobLevel(joblevel_id, joblevel_name,joblevel_desc);
		if(rows>0){
			map.put("status", "success");
		}else{
			map.put("status", "error");
		}
		return map;
	}
	/**
	 * 删除职级
	 * @param JobLevel_id 职务级别ID
	 * @return map
	 */
	@ResponseBody
	@RequestMapping("/removeJobLevel")
	public Map<String,Object> removeJobLevel(Integer joblevel_id){
		Map<String,Object> map =new HashMap<String,Object>();
		int rows = JobLevelPageService.removeJobLevel(joblevel_id);
		if(rows>0){
			map.put("status", "success");
		}else{
			map.put("status", "error");
		}
		return map;
	}
	/**
	 * 加载职级下拉框数据
	 * @return 返回职级json
	 */
	@ResponseBody
	@RequestMapping("/loadComboboxJobLevelData")
	public List<Map<String, Object>> loadComboboxJobLevelData(){
		List<Map<String, Object>> list = JobLevelPageService.loadComboboxJobLevelData();
		return list;
	}
	/**
	 * 查询所有职位
	 * @return 返回职位json
	 */
	@ResponseBody
	@RequestMapping("/loadPositionAll")
	public Map<String,Object> loadPositionAll(Integer Position_id,String Position_name,Integer JobLevel_id,String JobLevel_name,String post_name){
		Map<String,Object> map =new HashMap<String,Object>();
		List<Map<String, Object>> list = JobLevelPageService.loadPositionAll(Position_id,Position_name,JobLevel_id,JobLevel_name,post_name);
		map.put("total", list.size());
		map.put("rows", list);
		return map;
	}
	/**
	 * 保存职位
	 * @param Position_id 职位ID
	 * @param Position_name 职位名称
	 * @param JobLevel_id 职级ID
	 * @return map
	 */
	@ResponseBody
	@RequestMapping("/savePosition")
	public Map<String,Object> savePosition(String Position_name,Integer joblevel_id){
		Map<String,Object> map =new HashMap<String,Object>();
		int rows = JobLevelPageService.savePosition(Position_name,joblevel_id);
		if(rows>0){
			map.put("status", "success");
		}else{
			map.put("status", "error");
		}
		return map;
	}
	/**
	 * 修改职位
	 * @param Position_id 职位ID
	 * @param Position_name 职位名称
	 * @param JobLevel_id 所属职级
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/editPosition")
	public Map<String,Object> editPosition(PositionEntity positionEntity){
		Map<String,Object> map =new HashMap<String,Object>();
		int rows = JobLevelPageService.editPosition(positionEntity);
		if(rows>0){
			map.put("status", "success");
		}else{
			map.put("status", "error");
		}
		return map;
	}
	/**
	 * 删除职位
	 * @param Position_id 职位ID
	 * @return map
	 */
	@ResponseBody
	@RequestMapping("/removePosition")
	public Map<String,Object> removePosition(Integer Position_id){
		Map<String,Object> map =new HashMap<String,Object>();
		int rows = JobLevelPageService.removePosition(Position_id);
		if(rows>0){
			map.put("status", "success");
		}else if(rows==0){
			map.put("status", "error");
		}
		else{
			map.put("status", "errorforremove");
		}
		return map;
	}
	
	/**
	 * 下面是岗位的
	*/
	/**
	 * 加载职位下拉框数据
	 * @return 返回职位json
	 */
	@ResponseBody
	@RequestMapping("/loadComboboxPositionData")
	public List<Map<String, Object>> loadComboboxPositionData(){
		List<Map<String, Object>> list = JobLevelPageService.loadComboboxPositionData();
		return list;
	}
	/**
	 * 查询所有岗位
	 * @return 返回岗位json
	 */
	@ResponseBody
	@RequestMapping("/loadPostAll")
	public Map<String,Object> loadPostAll(Integer post_id, String post_name, Integer position_id,String position_name){
		Map<String,Object> map =new HashMap<String,Object>();
		List<Map<String, Object>> list = JobLevelPageService.loadPostAll(post_id,post_name,position_id,position_name);
		map.put("total", list.size());
		map.put("rows", list);
		return map;
	}
	/**
	 * 保存岗位
	 * @param post_id 岗位ID
	 * @param post_name 岗位名称
	 * @param position_id 职位ID
	 * @return map
	 */
	@ResponseBody
	@RequestMapping("/savePost")
	public Map<String,Object> savePost(String post_name, Integer position_id){
		Map<String,Object> map =new HashMap<String,Object>();
		int rows = JobLevelPageService.savePost(post_name,position_id);
		if(rows>0){
			map.put("status", "success");
		}else{
			map.put("status", "error");
		}
		return map;
	}
	/**
	 * 修改岗位
	 * @param 
	 * @param 
	 * @param 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/editPost")
	public Map<String,Object> editPost(PostEntity postEntity){
		Map<String,Object> map =new HashMap<String,Object>();
		int rows = JobLevelPageService.editPost(postEntity);
		if(rows>0){
			map.put("status", "success");
		}else{
			map.put("status", "error");
		}
		return map;
	}
	/**
	 * 删除岗位
	 * @param post_id 岗位ID
	 * @return map
	 */
	@ResponseBody
	@RequestMapping("/removePost")
	public Map<String,Object> removePost(Integer post_id){
		Map<String,Object> map =new HashMap<String,Object>();
		int rows = JobLevelPageService.removePost(post_id);
		if(rows>0){
			map.put("status", "success");
		}else{
			map.put("status", "error");
		}
		return map;
	}
	/**
	 * 加载岗位信息
	 * @return 返回职位json
	 */
	@ResponseBody
	@RequestMapping("/loadComboboxPostData")
	public List<Map<String, Object>> loadComboboxPostData(){
		return JobLevelPageService.loadPostAll(null,"",null,"");
	}
}
