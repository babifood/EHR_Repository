package com.babifood.exception;


public class CustomException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5993032413727338547L;
	//异常信息
	private String message;
	
	public CustomException(String message){
		super(message);
		this.message = message;
		
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}	
}
