<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="easyui-plane" >  
	<form action="allowance/improtExcel" method="post" enctype="multipart/form-data">
		文件上传：<input type="file" id="uploadFile" class="textbox" name="uploadFile" accept=".xls,.xlsx">
		<input  value="提交"  class="easyui-linkbutton" type="submit">
	</form>
</div>
<script type="text/javascript">
	function submitForm() {
		console.log("1111111111111111");
		var uploadFile = document.getElementById("uploadFile").value;
		if(uploadFile){
			document.getElementById("uploadFile").submit;
		}
	}
</script>