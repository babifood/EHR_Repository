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
	location.href = prefix+'/logout';
};