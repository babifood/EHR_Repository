package com.babifood.entity;

public class PunchTimeEntity
{
  private Integer id;
  private String workNum;
  private String userName;
  private String clockedDate;
  private String beginTime;
  private String endTime;
  private String desc;

  public Integer getId()
  {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getWorkNum() {
    return this.workNum;
  }

  public void setWorkNum(String workNum) {
    this.workNum = workNum;
  }

  public String getUserName() {
    return this.userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getClockedDate() {
    return this.clockedDate;
  }

  public void setClockedDate(String clockedDate) {
    this.clockedDate = clockedDate;
  }

  public String getBeginTime() {
    return this.beginTime;
  }

  public void setBeginTime(String beginTime) {
    this.beginTime = beginTime;
  }

  public String getEndTime() {
    return this.endTime;
  }

  public void setEndTime(String endTime) {
    this.endTime = endTime;
  }

  public String getDesc() {
    return this.desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }
}