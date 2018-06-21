package com.babifood.utils;

public class CustomerContextHolder {
	public static final String DATA_SOURCE_EHR = "EHR";
	public static final String DATA_SOURCE_OA = "OA";
	private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();
	
	public static void setCustomerType(String customerType){
		contextHolder.set(customerType);
	}
	public static String getCustomerType(){
		return (String) contextHolder.get();
	}
	public static void clearCustomerType(){
		contextHolder.remove();
	}
}
