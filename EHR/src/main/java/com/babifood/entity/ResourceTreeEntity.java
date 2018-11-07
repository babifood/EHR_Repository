package com.babifood.entity;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class ResourceTreeEntity {
	private String id;
	private String pid;
	private String text;
	private String state;
	private String iconCls;
	private boolean checked;
	private Map<String, Object> attributes;
	private List<ResourceTreeEntity> children;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public Map<String, Object> getAttributes() {
		return attributes;
	}
	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}
	public List<ResourceTreeEntity> getChildren() {
		return children;
	}
	public void setChildren(List<ResourceTreeEntity> children) {
		this.children = children;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	
}
