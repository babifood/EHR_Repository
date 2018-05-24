var prefix = window.location.host;
prefix = "http://" + prefix +"/EHR";

$(function(){
	
	//登录窗体
	$('#login').dialog({   
		width:300,
		height:200,
		title :'用户登录',
		iconCls:'icon-lock',
		modal: true,
		buttons:'#login-btn'
	});  
	//用户名验证
	$('#userName').validatebox({
		required : true,
		missingMessage :'用户名不能为空!',
	});
	//密码验证
	$('#password').validatebox({
		required : true,
		validType :'length[6,18]',
		missingMessage :'用户名不能为空!',
		invalidMessage :'密码不能少于6位且不能大于30位',
	});
	//页面加载时光标定位到输入框
	if(!$('#userName').validatebox('isValid')){
		$('#userName').focus();
	}else if(!$('#password').validatebox('isValid')){
		$('#password').focus();
	}
	//登录按钮点击事件
	$('#login-btn a').click(function(){
		if(!$('#userName').validatebox('isValid')){
			$('#userName').focus();
		}else if(!$('#password').validatebox('isValid')){
			$('#password').focus();
		}else{
			$.ajax({
				url:prefix+'/login',
				type:'post',
				data:{
					user_name:$('#userName').val(),
					password:$('#password').val(),
				},
				contentType:"application/x-www-form-urlencoded",
				beforeSend:function(){
					$.messager.progress({
						text:'登陆中......',
					});
				},
				success:function(data){
					$.messager.progress('close');
					if(data.status=="error"){
						$.messager.alert("消息提示！","用户名密码错误！","warning",function(){
							$('#password').select();
						});
					}else if(data.status=="success"){
						window.location.href=prefix+"/redirect?pageName=HomePage";
					}else{
						$.messager.alert("消息提示！","请求异常，请检查网络！","warning");
					}
				}
			});
		}
	});
});