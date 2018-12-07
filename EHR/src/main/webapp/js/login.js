﻿var prefix = window.location.host;
prefix = "http://" + prefix +"/EHR";
$(function(){
	//用户名验证
	$('#login_username').validatebox({
		required : true,
		missingMessage :$('#msg').val()==""?'用户名不能为空!':$('#msg').val(),
	});
	//密码验证
	$('#login_password').validatebox({
		required : true,
		validType :'length[6,18]',
		missingMessage :'密码不能为空!',
		invalidMessage :'密码不能少于6位且不能大于18位',
	});
	//页面加载时光标定位到输入框
	if(!$('#login_username').validatebox('isValid')){
		$('#login_username').focus();
	}else if(!$('#login_password').validatebox('isValid')){
		$('#login_password').focus();
	} 
});
//登录按钮点击事件
function loginButton(){
	if(!$('#login_username').validatebox('isValid')){
		$('#login_username').focus();
	}else if(!$('#login_password').validatebox('isValid')){
		$('#login_password').focus();
	}else{
		$("#loginform").submit();
	}
}