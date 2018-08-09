package com.babifood.clocked.entrty;

import java.io.Serializable;

/**
 * 移动打卡记录
 * @author BABIFOOD
 *
 */
public class MoveDaKaRecord extends Person implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6201842099623312534L;
	private MobileDaKaLog begin;
	private MobileDaKaLog end;
	public MobileDaKaLog getBegin() {
		return begin;
	}
	public void setBegin(MobileDaKaLog begin) {
		this.begin = begin;
	}
	public MobileDaKaLog getEnd() {
		return end;
	}
	public void setEnd(MobileDaKaLog end) {
		this.end = end;
	}
	
}
