var prefix = window.location.host;
prefix = "http://" + prefix +"/EHR";
$(function(){
	$('#tabs').tabs({
		fit:true,
		border:false,
	});
	//导航菜单
	$('#menu').tree({
		url:prefix+"/loadTerr",
		lines:true,
		onClick:function(node){
			if(node.url){
				if($('#tabs').tabs('exists',node.text)){
					$('#tabs').tabs('select',node.text);
				}else{
					$('#tabs').tabs('add',{
						title:node.text,
						closable:true,
						href:prefix+"/redirect?pageName="+node.url,
					});
				}
			}
		}
	});
	
});
//退出登录
function logoout(){
	$.ajax({
		url:prefix+'/clearSession',
		type:'post',
		beforeSend:function(){
			$.messager.progress({
				text:'正在安全退出......',
			});
		},
		success:function(data){
			$.messager.progress('close');
			if(data.status=="success"){
				window.location.href=prefix+"/Login.jsp";
			}else{
				$.messager.alert("消息提示！","请求异常，请检查网络！","warning");
			}
		}
	});
	
};