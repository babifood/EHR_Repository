package com.babifood.entity;

import java.sql.Date;

import org.springframework.stereotype.Component;

@Component
public class RewardPunishmentEntity {
	private String rap_id;
	private String rap_category;
	private Integer rap_item;
	private Date rap_date;
	private String rap_reason;
	private Integer rap_money;
	private String rap_proposer_id;
	private String rap_proposer;
	private String rap_p_id;
	private String rap_p;
	private String rap_desc;
	
	public RewardPunishmentEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getrap_id() {
		return rap_id;
	}
	public void setrap_id(String rap_id) {
		this.rap_id = rap_id;
	}
	public String getrap_category() {
		return rap_category;
	}
	public void setrap_category(String rap_category) {
		this.rap_category = rap_category;
	}
	public Integer getrap_item() {
		return rap_item;
	}
	public void setrap_item(Integer rap_item) {
		this.rap_item = rap_item;
	}
	public Date getrap_date() {
		return rap_date;
	}
	public void setrap_date(Date rap_date) {
		this.rap_date = rap_date;
	}
	public String getrap_reason() {
		return rap_reason;
	}
	public void setrap_reason(String rap_reason) {
		this.rap_reason = rap_reason;
	}
	public Integer getrap_money() {
		return rap_money;
	}
	public void setrap_money(Integer rap_money) {
		this.rap_money = rap_money;
	}
	public String getrap_proposer_id() {
		return rap_proposer_id;
	}
	public void setrap_proposer_id(String rap_proposer_id) {
		this.rap_proposer_id = rap_proposer_id;
	}
	public String getrap_proposer() {
		return rap_proposer;
	}
	public void setrap_proposer(String rap_proposer) {
		this.rap_proposer = rap_proposer;
	}
	public String getrap_p_id() {
		return rap_p_id;
	}
	public void setrap_p_id(String rap_p_id) {
		this.rap_p_id = rap_p_id;
	}
	public String getrap_p() {
		return rap_p;
	}
	public void setrap_p(String rap_p) {
		this.rap_p = rap_p;
	}
	public String getrap_desc() {
		return rap_desc;
	}
	public void setrap_desc(String rap_desc) {
		this.rap_desc = rap_desc;
	}
	
}
