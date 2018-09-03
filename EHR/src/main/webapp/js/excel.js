$(function() {
	
	var setting = {
		url:"",
		datagrid_id:"",
		success : function(){
			
		},
		error : function() {
			
		}
	} ;
	
	var id ;
	$.fn.importExcel1 = function(options){
		id = this.attr("id");
		console.log(id);
		this.html("");
		var formdiv= getImportExcelForm(id);
		this.append(formdiv);
		var buttiondiv= getImportExcelButton(id);
		this.append(buttiondiv);
		this.attr("buttons","#dialog_buttons_"+id)
        //模拟上传excel  
        $("#uploadEventBtn_"+id).unbind("click").bind("click", function() {
            $("#uploadEventFile_"+id).click();
        });
        $("#uploadEventFile_"+id).unbind("change").bind("change", function() {
            $("#uploadEventPath_"+id).attr("value",$("#uploadEventFile_"+id).val());
        });
		$.extend(setting,options);
		
	}
	
	$.fn.importExcel1.dialog = function(){
		$("#uploadEventFile_"+id).val("");
		$("#uploadEventPath_"+id).attr("value","");
		$("#"+id).dialog("open");
	}
	
	//点击上传钮  
    function uploadBtn(id) {
        var uploadEventFile = $("#uploadEventFile_"+id).val();
        if (uploadEventFile == '') {
            alert("请择excel,再上传");
        } else if (uploadEventFile.lastIndexOf(".xls") < 0) {//可判断以.xls和.xlsx结尾的excel  
            alert("只能上传Excel文件");
        } else {
            var formData = new FormData($("#uploadExcel_"+id)[0]);
            $.ajax({
                url : setting.url,
                type : "POST",
                data : formData,
                dataType : "json",
                success : function(result) {
                	setting.success(result);
                },
                error : function(result) {
                	setting.error(result);
                },
                cache : false,
                contentType : false,
                processData : false
            });
        }
    }
	
	function getImportExcelForm(id){
		var div1 = document.createElement("div");
		div1.style = "text-align: center;";
		div1.setAttribute("style","text-align: center;");
		var form = document.createElement("form");
		form.id="uploadExcel_"+id;
		form.method="post";
		form.enctype="multipart/form-data";
//		form.style="margin-top: 5px";
		div1.appendChild(form);
		var button = document.createElement("button");
		button.className = "easyui-linkbutton";
		button.id="uploadEventBtn_"+id;
		button.type="button";
		form.appendChild(button);
		button.appendChild(document.createTextNode("选择文件"));
		var input1 = document.createElement("input");
		input1.type="file";
		input1.name="file";
		input1.style="width:0px;height:0px;";
		input1.setAttribute("style","width:0px;height:0px;");
		input1.id="uploadEventFile_"+id;
		form.appendChild(input1);
		var input2 = document.createElement("input");
		input2.id="uploadEventPath_"+id;
		input2.className="textbox";
		input2.disabled="disabled";
		input2.type="text";
		input2.placeholder="请择excel表";
		form.appendChild(input2);
		return div1;
	}
	
	function getImportExcelButton(id){
		var div2 = document.createElement("div");
		div2.style="text-align: center; padding: 5px 0;";
		div2.setAttribute("style","text-align: center; padding: 5px 0;");
		div2.id="dialog_buttons_"+id;
		var a1 = document.createElement("a");
		a1.href="#";
		a1.className="easyui-linkbutton";
		a1.setAttribute("iconCls","icon-ok");
		a1.onclick = function(){
			uploadBtn(id);
		};
		a1.style="width: 90px;";
		a1.setAttribute("style","width: 90px;");
		a1.id="booten_"+id;
		a1.appendChild(document.createTextNode("导入"));
		div2.appendChild(a1);
		var a2 = document.createElement("a");
		a2.href="#";
		a2.className="easyui-linkbutton";
		a2.setAttribute("iconCls","icon-cancel");
		a2.onclick = function(){
			$("#"+id).dialog('close');
		};
		a2.style="width: 90px;";
		a2.setAttribute("style","width: 90px;");
		a2.appendChild(document.createTextNode("取消"));
		div2.appendChild(a2);
		return div2
	}
})

