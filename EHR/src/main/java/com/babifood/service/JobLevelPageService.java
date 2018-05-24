package com.babifood.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.babifood.entity.PositionEntity;
import com.babifood.entity.PostEntity;

@Service
public interface JobLevelPageService {
	
	//职级
	public List<Map<String, Object>> loadJobLevelAll(Integer JobLevel_id,String JobLeverl_name,String position_name);
	
	public Integer saveJobLevel(Integer joblevel_id,String joblevel_name);
	
	public Integer editJobLevel(Integer joblevel_id,String joblevel_name);
	
	public Integer removeJobLevel(Integer joblevel_id);
	

	
	//职位
	public List<Map<String,Object>> loadComboboxJobLevelData();
	
	public List<Map<String,Object>> loadPositionAll(Integer Position_id,String Position_name,Integer JobLevel_id,String JobLevel_name,String post_name);
	
	public Integer savePosition(String Position_name,Integer JobLevel_id);
	
	public Integer editPosition(PositionEntity positionEntity);
	
	public Integer removePosition(Integer Position_id);
	
	//岗位
	public List<Map<String,Object>> loadComboboxPositionData();
	
	public List<Map<String,Object>> loadPostAll(Integer post_id,String post_name,Integer position_id,String position_name);
	
	public Integer savePost(String post_name,Integer position_id);
	
	public Integer editPost(PostEntity postEntity);
	
	public Integer removePost(Integer post_id);
}
