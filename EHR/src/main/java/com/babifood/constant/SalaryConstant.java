package com.babifood.constant;

public class SalaryConstant {

	// 生产、物流、仓储一线人员 平时加班工资
	public static final Double USUALLY_OVERTIME_PAY = 20.9;

	// 生产、物流、仓储一线人员 平时加班工资
	public static final Double HOLIDAY_OVERTIME_PAY = 41.8;

	// 技术专员 餐补
	public static final Double TECHNICAL_SPECIALIST_MEAL_SUPPLEMENT = 8.5;

	// 其他 餐补
	public static final Double OTHER_MEAL_SUPPLEMENT = 17.0;
	
	//个税基数
	public static final Double MAINLAND_PERSONAL_INCOME_BASE_TAX = 3500.0;

	//中国国际  个税第一档
	public static final Double MAINLAND_PERSONAL_INCOME_TAX_FIRST = 1500.0;
	
	//中国国际  个税第二档
	public static final Double MAINLAND_PERSONAL_INCOME_TAX_SECOND = 4500.0;
	
	/**----------------------------------各地区最低薪资标准----------------------------------------------*/
	//上海
	public static final Double SHANGHAI_LOWEST_SALARY = 2300.0;
	
	//江苏
	public static final Double JIANGSU_LOWEST_SALARY = 2300.0;
	
	//浙江
	public static final Double ZHEJIANG_LOWEST_SALARY = 2300.0;
	
	//广东
	public static final Double GUANGDONG_LOWEST_SALARY = 2300.0;
	
	//深圳
	public static final Double SHENZHEN_LOWEST_SALARY = 2300.0;
	
	//北京
	public static final Double BEIJING_LOWEST_SALARY = 2300.0;
	
	/**-----------------------------------病假计算系数-------------------------------------------------*/
	//6个月小时数
	public static final Double SIX_MONTH_HOURS = 180 * 8.0;
	
	//年累计病假时间 < 6个月        &&   司龄 < 2年的系数
	public static final Double COMPANYAGE_LESS_SIX_MONTH_HOURS_TOW = 0.4;
	
	//年累计病假时间 < 6个月        &&   司龄 < 4年的系数
	public static final Double COMPANYAGE_LESS_SIX_MONTH_HOURS_FOUR = 0.3;
	
	//年累计病假时间 < 6个月        &&   司龄 < 6年的系数
	public static final Double COMPANYAGE_LESS_SIX_MONTH_HOURS_SIX = 0.2;
	
	//年累计病假时间 < 6个月        &&   司龄 < 8年的系数
	public static final Double COMPANYAGE_LESS_SIX_MONTH_HOURS_EIGHT = 0.1;
	
	//年累计病假时间 < 6个月        &&   司龄 > 8年的系数
	public static final Double COMPANYAGE_LESS_SIX_MONTH_HOURS = 0.0;
	
	//年累计病假时间 > 6个月        &&   司龄 < 1年的系数
	public static final Double COMPANYAGE_MORE_SIX_MONTH_HOURS_ONE = 0.6;
	
	//年累计病假时间 > 6个月        &&   司龄 < 3年的系数
	public static final Double COMPANYAGE_MORE_SIX_MONTH_HOURS_THREE = 0.5;
	
	//年累计病假时间>6个月        &&   司龄 > 3年的系数
	public static final Double COMPANYAGE_MORE_SIX_MONTH_HOURS = 0.5;
	
	/**------------------------------------个税相关系数-----------------------------------------------*/
	public static final Double FULL_MAINLAND_FREE_SALARY = 3500.0;//全职 大陆  免税基数
	public static final Double FULL_UNMAINLAND_FREE_SALARY = 4800.0;//全职 非大陆  免税基数
	public static final Double FULL_TAX_FIRST_AMOUNT = 1455.0;//全职 收税 第1档薪资基数
	public static final Double FULL_TAX_FIRST_MODULUS = 0.03;//全职 收税 第1档薪资系数
	public static final Double FULL_TAX_FIRST_DEDUCT =  0.0;//全职 收税 第1档薪资速算扣除
	public static final Double FULL_TAX_SECOND_AMOUNT = 4155.0;//全职 收税 第2档薪资基数
	public static final Double FULL_TAX_SECOND_MODULUS = 0.1;//全职 收税 第2档个税系数
	public static final Double FULL_TAX_SECOND_DEDUCT =  105.0;//全职 收税 第2档速算扣除
	public static final Double FULL_TAX_THREAD_AMOUNT = 7755.0;//全职 收税 第3档薪资基数
	public static final Double FULL_TAX_THREAD_MODULUS = 0.2;//全职 收税 第3档个税系数
	public static final Double FULL_TAX_THREAD_DEDUCT =  555.0;//全职 收税 第3档速算扣除
	public static final Double FULL_TAX_FOURTH_AMOUNT = 27255.0;//全职 收税 第4档薪资基数
	public static final Double FULL_TAX_FOURTH_MODULUS = 0.25;//全职 收税 第4档个税系数
	public static final Double FULL_TAX_FOURTH_DEDUCT =  1005.0;//全职 收税 第4档速算扣除
	public static final Double FULL_TAX_FIFTH_AMOUNT = 41255.0;//全职 收税 第5档薪资基数
	public static final Double FULL_TAX_FIFTH_MODULUS = 0.3;//全职 收税 第5档个税系数
	public static final Double FULL_TAX_FIFTH_DEDUCT =  2755.0;//全职 收税 第5档速算扣除
	public static final Double FULL_TAX_SIXTH_AMOUNT = 57505.0;//全职 收税 第6档薪资基数
	public static final Double FULL_TAX_SIXTH_MODULUS = 0.35;//全职 收税 第6档个税系数
	public static final Double FULL_TAX_SIXTH_DEDUCT =  5505.0;//全职 收税 第6档速算扣除
	public static final Double FULL_TAX_SEVENTH_MODULUS = 0.35;//全职 收税 第7档个税系数
	public static final Double FULL_TAX_SEVENTH_DEDUCT =  13505.0;//全职 收税 第7档速算扣除
	
	public static final Double PART_TAX_FREE_SALARY = 800.0;//兼职  免税薪资基数
	public static final Double PART_TAX_BASE_MODULUS = 0.8;//兼职  基础系数
	public static final Double PART_TAX_FIRST_AMOUNT = 4000.0;//兼职  第1档薪资基数
	public static final Double PART_TAX_FIRST_MODULUS = 0.2;//兼职  第1档个税系数
	public static final Double PART_TAX_FIRST_DEDUCT =  0.0;//兼职  第1档速算扣除
	public static final Double PART_TAX_SECOND_AMOUNT = 25000.0;//兼职  第2档薪资基数
	public static final Double PART_TAX_SECOND_MODULUS = 0.2;//兼职  第2档个税系数
	public static final Double PART_TAX_SECOND_DEDUCT =  0.0;//兼职  第2档速算扣除
	public static final Double PART_TAX_THREAD_AMOUNT = 65000.0;//兼职  第3档薪资基数
	public static final Double PART_TAX_THREAD_MODULUS = 0.3;//兼职  第3档个税系数
	public static final Double PART_TAX_THREAD_DEDUCT =  2000.0;//兼职  第3档速算扣除
	public static final Double PART_TAX_FOURTH_MODULUS = 0.4;//兼职  第4档个税系数
	public static final Double PART_TAX_FOURTH_DEDUCT =  7000.0;//兼职  第4档速算扣除
	
	/**-------------------------------------------默认公式------------------------------------------------------------*/
	public static final String LATER_AND_LEAVE_FORMULA = "laterAndLeaveTime<=3?0:laterAndLeaveTime<=5?(laterAndLeaveTime - 3) * baseDeduction / 5:baseDeduction * (0.4 + (laterAndLeaveTime - 5)/3)";//迟到早退扣款公式
	public static final String COMPLETION_DEDUCTION_FORMULA = "(workType==piece?companySalary/attendanceHours:(baseSalary + fixedOverTimeSalary + companySalary + postSalary)/attendanceHours) * completionHours * 3";//旷工扣款公式
	public static final String THING_DEDUCTION_FORMULA = "(workType==piece?companySalary/attendanceHours:(baseSalary + fixedOverTimeSalary + companySalary + postSalary)/attendanceHours) * thingHours";//事假扣款公式
	public static final String SICK_DEDUCTION_FORMULA = "sickGrandHours<=180 * 8?(workType==piece?companySalary/attendanceHours:(baseSalary + fixedOverTimeSalary + companySalary + postSalary)/attendanceHours) * sickHours * (companyAge<=2?0.4:companyAge<=4?0.3:companyAge<=6?0.2:companyAge<=8?0.1:0.0):(sickGrandHours - sickHours)<=180 * 8?(workType==piece?companySalary/attendanceHours:(baseSalary + fixedOverTimeSalary + companySalary + postSalary)/attendanceHours) * ((sickGrandHours-180*8) * (companyAge<=1?0.6:companyAge<=3?0.5:0.4) + (180 * 8 - (sickGrandHours - sickHours)) * (companyAge<=2?0.4:companyAge<=4?0.3:companyAge<=6?0.2:companyAge<=8?0.1:0.0)):(workType==piece?companySalary/attendanceHours:(baseSalary + fixedOverTimeSalary + companySalary + postSalary)/attendanceHours) * sickHours * (companyAge<=1?0.6:companyAge<=3?0.5:0.4)";//病假扣款公式
	public static final String PARENTAL_DEDUCTION_FORMULA = "(workType==piece?companySalary/attendanceHours:(baseSalary + fixedOverTimeSalary + companySalary + postSalary)/attendanceHours) * parentalHours";//产假扣款公式
	public static final String ONBOARDING_DEDUCTION_FORMULA = "(workType==piece?companySalary/attendanceHours:(baseSalary + fixedOverTimeSalary + companySalary + postSalary)/attendanceHours) * onboardingHours";//月中入职、离职扣款公式
	public static final String TOTAL_DEDUCTION_FORMULA = "laterAndLeaveFormula + thingFormula + completionFormula + parentalFormula + onboardingFormula + sickFormula";//应扣合计公式
	public static final String RICESTICK_ALLOWANCE_FORMULA = "singelMeal * mealnum";//餐补公式
	public static final String CALLSUNSALARY_ALLOWANCE_FORMULA = "absenceHours<=24?callSubsidies:(attendanceHours - absenceHours)/attendanceHours * callSubsidies";//话费补贴公式
	public static final String HIGHTEM_ALLOWANCE_FORMULA = "(attendanceHours - absenceHours)/attendanceHours * highTem";//高温补贴公式
	public static final String LOWTEM_ALLOWANCE_FORMULA = "(attendanceHours - absenceHours)/attendanceHours * lowTem";//低温补贴公式
	public static final String WAGE_PAYABLE_FORMULA = "fixSalary + highTemFormula + lowTemFormula + morningShift + nightShift + stay + otherAllowance + performanceBonus + security + compensatory + addOther - totalFormula - beforeDeduction";//应发工资公式
	public static final String PERSONAL_TAX_FORMULA = "";//个税公式
	public static final String REAL_WAGES_FORMULA = "";//实发工资公式
	public static final String PERFORMANCE_FORMULA = "performanceSalary * performanceScore";//绩效工资公式
}
