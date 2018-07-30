package com.babifood.clocked.service;

import org.springframework.stereotype.Service;

/***
 * 考勤数据归集
 * @author BABIFOOD
 *
 */
@Service
public interface CollectionClockedDataService {
	//1加载数据
	//2根据打卡记录计算相关数据
	//3根据考勤业务计算相关数据
	//4保存数据
	//5推送OA考勤结果		
	public void loadData() throws Exception;
	public void attachWithDaKa() throws Exception ;
	public void attachWithBizData() throws Exception;
	public int[] saveDate() throws Exception;
	public void pushOA();
	public int[] execute(int year,int month) throws Exception;
}
