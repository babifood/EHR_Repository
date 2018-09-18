package com.babifood.entity;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class PersonBasrcEntity {
	private String p_id;//人员ID
	private String p_number;//编号
	private String p_name;//名称
	private String p_sex;//性别
	private Integer p_age;//年龄
	private String p_title;//职称
	private String p_post_id;//岗位编号
	private String p_post;//岗位名称
	private String p_level_id;//职级编号
	private String p_level_name;//职级名称
	private String p_company_id;//公司编码
	private String p_company_name;//公司名称
	private String p_department_id;//所属部门编号
	private String p_department;//所属部门
	private String p_organization_id;//单位机构编码
	private String p_organization;//单位机构
	private String p_section_office_id;//科室编码
	private String p_section_office;//科室
	
	private String p_group_id;//班组编码
	private String p_group;//班组
	
	private String p_state;//员工状态
	private String p_property;//员工性质
	private String p_post_property;//岗位性质
	private String p_in_date;//入职日期
	private String p_turn_date;//转正日期
	private String p_out_date;//离职日期
	private String p_out_describe;//离职原因
	private String p_checking_in;//考勤方式
	private String p_contract_begin_date;//劳动合同开始日期
	private String p_contract_end_date;//劳动合同结束日期
	private String p_shebao_begin_month;//社保购买起始月
	private String p_shebao_end_month;//社保购买终止月
	private String p_gjj_begin_month;//公积金购买起始月
	private String p_gjj_end_month;//公积金购买终止月
	private String p_nationality;//国籍
	private String p_nation;//民族
//	private String p_huji;//户籍
	private String p_huko_state;//户口性质
	private String p_marriage;//婚否
	private String p_politics;//政治面貌
//	private String p_identity_nub;//身份证号
//	private String p_identity_end_date;//身份证有效日期
	private String p_phone;//联系电话
	private String p_bank_nub;//银行卡号
	private String p_bank_name;//开户行
	private String p_huji_add;//户籍地址
	private String p_changzhu_add;//常住联系地址
	private String p_urgency_name;//紧急联系人名称
	private String p_urgency_relation;//紧急联系人关系
	private String p_urgency_phone;//紧急联系人电话
	private String p_kinsfolk_y_n;//是否有亲属同在公司
	private String p_kinsfolk_relation;//亲属关系
	private String p_kinsfolk_name;//亲属姓名
	private String p_kinsfolk_id_nub;//亲属身份证号码
	private String p_kinsfolk_xueli;//亲属最高学历
  //private String p_health_end_date;//健康证有效期
	private Integer p_company_age;//司龄
//	private Double p_base_salary;//基本工资
//	private Double p_overtime_salary;//固定加班工资
//	private Double p_subsidy;//岗位津贴
//	private Double p_length_salary;//工龄工资
//	private Double p_month_performance;//月度绩效基数
	private String p_c_yingpin_table;//应聘申请表
	private String p_c_interview_tab;//面谈记录表
	private String p_c_id_copies;//身份证复印件
	private String p_c_xueli;//学历证书
	private String p_c_xuewei;//学位证书
	private String p_c_bank_nub;//银行卡号
	private String p_c_tijian_tab;//体检表
	private String p_c_health;//健康证
	private String p_c_img;//照片
	private String p_c_welcome;//欢迎词
	private String p_c_staff;//员工手册回执
	private String p_c_admin;//管理者回执
	private String p_c_shebao;//社保同意书
	private String p_c_shangbao;//商保申请书
	private String p_c_secrecy;//保密协议
	private String p_c_prohibida;//禁业限制协议
	private String p_c_contract;//劳动合同
	private String p_c_post;//岗位说明书
	private String p_c_corruption;//反贪腐承诺书
	private String p_c_probation;//试用期考核表
	private String p_create_date;//创建时间
	private String p_this_dept_code;//当前末及部门
	private String p_id_num;//身份证号码
	private String p_birthday;//出生年月日
	
	private String p_use_work_form;//用工形式
	private String p_contract_count;//劳动合同签订次数
	
	private String p_oa_and_ehr;//区分那个系统创建的员工号段
	
	private List<PersonEducationEntity> education;//教育背景
	private List<PersonCultivateFront> cultivateFront;//培训经历_入职前
	private List<PersonCultivateLater> cultivateLater;//培训经历_入职后
	private List<PersonWorkFront> workFront;//工作经历_入职前
	private List<PersonWorkLater> workLater;//工作经历_入职后
	private List<PersonCertificate> certificate;//获得证书
	private List<PersonFamily> family;//家庭背景
	
	
	public PersonBasrcEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getP_id() {
		return p_id;
	}
	public void setP_id(String p_id) {
		this.p_id = p_id;
	}
	public String getP_number() {
		return p_number;
	}
	public void setP_number(String p_number) {
		this.p_number = p_number;
	}
	public String getP_name() {
		return p_name;
	}
	public void setP_name(String p_name) {
		this.p_name = p_name;
	}
	public String getP_sex() {
		return p_sex;
	}
	public void setP_sex(String p_sex) {
		this.p_sex = p_sex;
	}
	public Integer getP_age() {
		return p_age;
	}
	public void setP_age(Integer p_age) {
		this.p_age = p_age;
	}
	public String getP_title() {
		return p_title;
	}
	public void setP_title(String p_title) {
		this.p_title = p_title;
	}
	public String getP_post_id() {
		return p_post_id;
	}
	public void setP_post_id(String p_post_id) {
		this.p_post_id = p_post_id;
	}
	public String getP_post() {
		return p_post;
	}
	public void setP_post(String p_post) {
		this.p_post = p_post;
	}
	public String getP_level_id() {
		return p_level_id;
	}
	public void setP_level_id(String p_level_id) {
		this.p_level_id = p_level_id;
	}
	public String getP_level_name() {
		return p_level_name;
	}
	public void setP_level_name(String p_level_name) {
		this.p_level_name = p_level_name;
	}
	public String getP_company_id() {
		return p_company_id;
	}
	public void setP_company_id(String p_company_id) {
		this.p_company_id = p_company_id;
	}
	public String getP_company_name() {
		return p_company_name;
	}
	public void setP_company_name(String p_company_name) {
		this.p_company_name = p_company_name;
	}
	public String getP_department_id() {
		return p_department_id;
	}
	public void setP_department_id(String p_department_id) {
		this.p_department_id = p_department_id;
	}
	public String getP_department() {
		return p_department;
	}
	public void setP_department(String p_department) {
		this.p_department = p_department;
	}
	
	public String getP_organization_id() {
		return p_organization_id;
	}

	public void setP_organization_id(String p_organization_id) {
		this.p_organization_id = p_organization_id;
	}

	public String getP_organization() {
		return p_organization;
	}

	public void setP_organization(String p_organization) {
		this.p_organization = p_organization;
	}

	public String getP_section_office_id() {
		return p_section_office_id;
	}

	public void setP_section_office_id(String p_section_office_id) {
		this.p_section_office_id = p_section_office_id;
	}

	public String getP_section_office() {
		return p_section_office;
	}

	public void setP_section_office(String p_section_office) {
		this.p_section_office = p_section_office;
	}

	public String getP_state() {
		return p_state;
	}
	public void setP_state(String p_state) {
		this.p_state = p_state;
	}
	public String getP_property() {
		return p_property;
	}
	public void setP_property(String p_property) {
		this.p_property = p_property;
	}
	public String getP_post_property() {
		return p_post_property;
	}
	public void setP_post_property(String p_post_property) {
		this.p_post_property = p_post_property;
	}
	public String getP_in_date() {
		return p_in_date;
	}
	public void setP_in_date(String p_in_date) {
		this.p_in_date = p_in_date;
	}
	public String getP_turn_date() {
		return p_turn_date;
	}
	public void setP_turn_date(String p_turn_date) {
		this.p_turn_date = p_turn_date;
	}
	public String getP_out_date() {
		return p_out_date;
	}
	public void setP_out_date(String p_out_date) {
		this.p_out_date = p_out_date;
	}
	public String getP_out_describe() {
		return p_out_describe;
	}
	public void setP_out_describe(String p_out_describe) {
		this.p_out_describe = p_out_describe;
	}
	public String getP_checking_in() {
		return p_checking_in;
	}
	public void setP_checking_in(String p_checking_in) {
		this.p_checking_in = p_checking_in;
	}
	public String getP_contract_begin_date() {
		return p_contract_begin_date;
	}
	public void setP_contract_begin_date(String p_contract_begin_date) {
		this.p_contract_begin_date = p_contract_begin_date;
	}
	public String getP_contract_end_date() {
		return p_contract_end_date;
	}
	public void setP_contract_end_date(String p_contract_end_date) {
		this.p_contract_end_date = p_contract_end_date;
	}
	public String getP_shebao_begin_month() {
		return p_shebao_begin_month;
	}
	public void setP_shebao_begin_month(String p_shebao_begin_month) {
		this.p_shebao_begin_month = p_shebao_begin_month;
	}
	public String getP_shebao_end_month() {
		return p_shebao_end_month;
	}
	public void setP_shebao_end_month(String p_shebao_end_month) {
		this.p_shebao_end_month = p_shebao_end_month;
	}
	public String getP_gjj_begin_month() {
		return p_gjj_begin_month;
	}
	public void setP_gjj_begin_month(String p_gjj_begin_month) {
		this.p_gjj_begin_month = p_gjj_begin_month;
	}
	public String getP_gjj_end_month() {
		return p_gjj_end_month;
	}
	public void setP_gjj_end_month(String p_gjj_end_month) {
		this.p_gjj_end_month = p_gjj_end_month;
	}
	public String getP_nationality() {
		return p_nationality;
	}
	public void setP_nationality(String p_nationality) {
		this.p_nationality = p_nationality;
	}
	public String getP_nation() {
		return p_nation;
	}
	public void setP_nation(String p_nation) {
		this.p_nation = p_nation;
	}
//	public String getP_huji() {
//		return p_huji;
//	}
//	public void setP_huji(String p_huji) {
//		this.p_huji = p_huji;
//	}
	public String getP_huko_state() {
		return p_huko_state;
	}
	public void setP_huko_state(String p_huko_state) {
		this.p_huko_state = p_huko_state;
	}
	public String getP_marriage() {
		return p_marriage;
	}
	public void setP_marriage(String p_marriage) {
		this.p_marriage = p_marriage;
	}
	public String getP_politics() {
		return p_politics;
	}
	public void setP_politics(String p_politics) {
		this.p_politics = p_politics;
	}
//	public String getP_identity_nub() {
//		return p_identity_nub;
//	}
//	public void setP_identity_nub(String p_identity_nub) {
//		this.p_identity_nub = p_identity_nub;
//	}
//	public String getP_identity_end_date() {
//		return p_identity_end_date;
//	}
//	public void setP_identity_end_date(String p_identity_end_date) {
//		this.p_identity_end_date = p_identity_end_date;
//	}
	public String getP_phone() {
		return p_phone;
	}
	public void setP_phone(String p_phone) {
		this.p_phone = p_phone;
	}
	public String getP_bank_nub() {
		return p_bank_nub;
	}
	public void setP_bank_nub(String p_bank_nub) {
		this.p_bank_nub = p_bank_nub;
	}
	public String getP_bank_name() {
		return p_bank_name;
	}
	public void setP_bank_name(String p_bank_name) {
		this.p_bank_name = p_bank_name;
	}
	public String getP_huji_add() {
		return p_huji_add;
	}
	public void setP_huji_add(String p_huji_add) {
		this.p_huji_add = p_huji_add;
	}
	public String getP_changzhu_add() {
		return p_changzhu_add;
	}
	public void setP_changzhu_add(String p_changzhu_add) {
		this.p_changzhu_add = p_changzhu_add;
	}
	public String getP_urgency_name() {
		return p_urgency_name;
	}
	public void setP_urgency_name(String p_urgency_name) {
		this.p_urgency_name = p_urgency_name;
	}
	public String getP_urgency_relation() {
		return p_urgency_relation;
	}
	public void setP_urgency_relation(String p_urgency_relation) {
		this.p_urgency_relation = p_urgency_relation;
	}
	public String getP_urgency_phone() {
		return p_urgency_phone;
	}
	public void setP_urgency_phone(String p_urgency_phone) {
		this.p_urgency_phone = p_urgency_phone;
	}
	public String getP_kinsfolk_y_n() {
		return p_kinsfolk_y_n;
	}
	public void setP_kinsfolk_y_n(String p_kinsfolk_y_n) {
		this.p_kinsfolk_y_n = p_kinsfolk_y_n;
	}
	public String getP_kinsfolk_relation() {
		return p_kinsfolk_relation;
	}
	public void setP_kinsfolk_relation(String p_kinsfolk_relation) {
		this.p_kinsfolk_relation = p_kinsfolk_relation;
	}
	public String getP_kinsfolk_name() {
		return p_kinsfolk_name;
	}
	public void setP_kinsfolk_name(String p_kinsfolk_name) {
		this.p_kinsfolk_name = p_kinsfolk_name;
	}
	public String getP_kinsfolk_id_nub() {
		return p_kinsfolk_id_nub;
	}
	public void setP_kinsfolk_id_nub(String p_kinsfolk_id_nub) {
		this.p_kinsfolk_id_nub = p_kinsfolk_id_nub;
	}
	public String getP_kinsfolk_xueli() {
		return p_kinsfolk_xueli;
	}
	public void setP_kinsfolk_xueli(String p_kinsfolk_xueli) {
		this.p_kinsfolk_xueli = p_kinsfolk_xueli;
	}
//	public String getP_health_end_date() {
//		return p_health_end_date;
//	}
//	public void setP_health_end_date(String p_health_end_date) {
//		this.p_health_end_date = p_health_end_date;
//	}
	public Integer getP_company_age() {
		return p_company_age;
	}
	public void setP_company_age(Integer p_company_age) {
		this.p_company_age = p_company_age;
	}
//	public Double getP_base_salary() {
//		return p_base_salary;
//	}
//	public void setP_base_salary(Double p_base_salary) {
//		this.p_base_salary = p_base_salary;
//	}
//	public Double getP_overtime_salary() {
//		return p_overtime_salary;
//	}
//	public void setP_overtime_salary(Double p_overtime_salary) {
//		this.p_overtime_salary = p_overtime_salary;
//	}
//	public Double getP_subsidy() {
//		return p_subsidy;
//	}
//	public void setP_subsidy(Double p_subsidy) {
//		this.p_subsidy = p_subsidy;
//	}
//	public Double getP_length_salary() {
//		return p_length_salary;
//	}
//	public void setP_length_salary(Double p_length_salary) {
//		this.p_length_salary = p_length_salary;
//	}
//	public Double getP_month_performance() {
//		return p_month_performance;
//	}
//	public void setP_month_performance(Double p_month_performance) {
//		this.p_month_performance = p_month_performance;
//	}
	public String getP_c_yingpin_table() {
		return p_c_yingpin_table;
	}
	public void setP_c_yingpin_table(String p_c_yingpin_table) {
		this.p_c_yingpin_table = p_c_yingpin_table;
	}
	public String getP_c_interview_tab() {
		return p_c_interview_tab;
	}
	public void setP_c_interview_tab(String p_c_interview_tab) {
		this.p_c_interview_tab = p_c_interview_tab;
	}
	public String getP_c_id_copies() {
		return p_c_id_copies;
	}
	public void setP_c_id_copies(String p_c_id_copies) {
		this.p_c_id_copies = p_c_id_copies;
	}
	public String getP_c_xueli() {
		return p_c_xueli;
	}
	public void setP_c_xueli(String p_c_xueli) {
		this.p_c_xueli = p_c_xueli;
	}
	public String getP_c_xuewei() {
		return p_c_xuewei;
	}
	public void setP_c_xuewei(String p_c_xuewei) {
		this.p_c_xuewei = p_c_xuewei;
	}
	public String getP_c_bank_nub() {
		return p_c_bank_nub;
	}
	public void setP_c_bank_nub(String p_c_bank_nub) {
		this.p_c_bank_nub = p_c_bank_nub;
	}
	public String getP_c_tijian_tab() {
		return p_c_tijian_tab;
	}
	public void setP_c_tijian_tab(String p_c_tijian_tab) {
		this.p_c_tijian_tab = p_c_tijian_tab;
	}
	public String getP_c_health() {
		return p_c_health;
	}
	public void setP_c_health(String p_c_health) {
		this.p_c_health = p_c_health;
	}
	public String getP_c_img() {
		return p_c_img;
	}
	public void setP_c_img(String p_c_img) {
		this.p_c_img = p_c_img;
	}
	public String getP_c_welcome() {
		return p_c_welcome;
	}
	public void setP_c_welcome(String p_c_welcome) {
		this.p_c_welcome = p_c_welcome;
	}
	public String getP_c_staff() {
		return p_c_staff;
	}
	public void setP_c_staff(String p_c_staff) {
		this.p_c_staff = p_c_staff;
	}
	public String getP_c_admin() {
		return p_c_admin;
	}
	public void setP_c_admin(String p_c_admin) {
		this.p_c_admin = p_c_admin;
	}
	public String getP_c_shebao() {
		return p_c_shebao;
	}
	public void setP_c_shebao(String p_c_shebao) {
		this.p_c_shebao = p_c_shebao;
	}
	public String getP_c_shangbao() {
		return p_c_shangbao;
	}
	public void setP_c_shangbao(String p_c_shangbao) {
		this.p_c_shangbao = p_c_shangbao;
	}
	public String getP_c_secrecy() {
		return p_c_secrecy;
	}
	public void setP_c_secrecy(String p_c_secrecy) {
		this.p_c_secrecy = p_c_secrecy;
	}
	public String getP_c_prohibida() {
		return p_c_prohibida;
	}
	public void setP_c_prohibida(String p_c_prohibida) {
		this.p_c_prohibida = p_c_prohibida;
	}
	public String getP_c_contract() {
		return p_c_contract;
	}
	public void setP_c_contract(String p_c_contract) {
		this.p_c_contract = p_c_contract;
	}
	public String getP_c_post() {
		return p_c_post;
	}
	public void setP_c_post(String p_c_post) {
		this.p_c_post = p_c_post;
	}
	public String getP_c_corruption() {
		return p_c_corruption;
	}
	public void setP_c_corruption(String p_c_corruption) {
		this.p_c_corruption = p_c_corruption;
	}
	public String getP_c_probation() {
		return p_c_probation;
	}
	public void setP_c_probation(String p_c_probation) {
		this.p_c_probation = p_c_probation;
	}
	public String getP_create_date() {
		return p_create_date;
	}
	public void setP_create_date(String p_create_date) {
		this.p_create_date = p_create_date;
	}

	public List<PersonEducationEntity> getEducation() {
		return education;
	}

	public void setEducation(List<PersonEducationEntity> education) {
		this.education = education;
	}

	public List<PersonCultivateFront> getCultivateFront() {
		return cultivateFront;
	}

	public void setCultivateFront(List<PersonCultivateFront> cultivateFront) {
		this.cultivateFront = cultivateFront;
	}

	public List<PersonCultivateLater> getCultivateLater() {
		return cultivateLater;
	}

	public void setCultivateLater(List<PersonCultivateLater> cultivateLater) {
		this.cultivateLater = cultivateLater;
	}

	public List<PersonWorkFront> getWorkFront() {
		return workFront;
	}

	public void setWorkFront(List<PersonWorkFront> workFront) {
		this.workFront = workFront;
	}

	public List<PersonWorkLater> getWorkLater() {
		return workLater;
	}

	public void setWorkLater(List<PersonWorkLater> workLater) {
		this.workLater = workLater;
	}

	public List<PersonCertificate> getCertificate() {
		return certificate;
	}

	public void setCertificate(List<PersonCertificate> certificate) {
		this.certificate = certificate;
	}

	public List<PersonFamily> getFamily() {
		return family;
	}

	public void setFamily(List<PersonFamily> family) {
		this.family = family;
	}

	public String getP_group_id() {
		return p_group_id;
	}

	public void setP_group_id(String p_group_id) {
		this.p_group_id = p_group_id;
	}

	public String getP_group() {
		return p_group;
	}

	public void setP_group(String p_group) {
		this.p_group = p_group;
	}

	public String getP_this_dept_code() {
		return p_this_dept_code;
	}

	public void setP_this_dept_code(String p_this_dept_code) {
		this.p_this_dept_code = p_this_dept_code;
	}

	public String getP_id_num() {
		return p_id_num;
	}

	public void setP_id_num(String p_id_num) {
		this.p_id_num = p_id_num;
	}

	public String getP_birthday() {
		return p_birthday;
	}

	public void setP_birthday(String p_birthday) {
		this.p_birthday = p_birthday;
	}

	public String getP_use_work_form() {
		return p_use_work_form;
	}

	public void setP_use_work_form(String p_use_work_form) {
		this.p_use_work_form = p_use_work_form;
	}

	public String getP_contract_count() {
		return p_contract_count;
	}

	public void setP_contract_count(String p_contract_count) {
		this.p_contract_count = p_contract_count;
	}

	public String getP_oa_and_ehr() {
		return p_oa_and_ehr;
	}

	public void setP_oa_and_ehr(String p_oa_and_ehr) {
		this.p_oa_and_ehr = p_oa_and_ehr;
	}
	
	

}
