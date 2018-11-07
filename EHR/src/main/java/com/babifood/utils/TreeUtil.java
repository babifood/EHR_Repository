package com.babifood.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.babifood.entity.ResourceTreeEntity;

public class TreeUtil {
	public static List<ResourceTreeEntity> getTreeChildNode(String pid,List<Map<String, Object>> treeList,List<Map<String, Object>> listRoleResource){
		List<ResourceTreeEntity> tree = new ArrayList<ResourceTreeEntity>();
		for (Map<String, Object> map : treeList) {
			if(pid.equals(map.get("nid"))){
				ResourceTreeEntity t = new ResourceTreeEntity();
				t.setId(map.get("id").toString());
				t.setText(map.get("text").toString());
				for (Map<String, Object> resource : listRoleResource) {
					if(map.get("id").equals(resource.get("resource_code"))){
						t.setChecked(true);
					}
				}
				tree.add(t);
			}
		}
		return tree;
	}
}
