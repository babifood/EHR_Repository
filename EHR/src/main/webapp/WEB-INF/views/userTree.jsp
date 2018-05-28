<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
 li:hover {
 	cursor: pointer;
 }
 #unSelectP .datagrid-header{
 	position:absolute;
 	visibility:hidden;
 }
</style>
    <div style="margin: 0;padding: 20px 50px;">
		<div style="float:left; width:470px; height:30px;  margin-left:5px;">	
			<div title="查询" style="float:left; width:195px; margin-left:5px;">
				<input id="serch_unselect" name="serch_unselect" class="easyui-textbox" data-options="
			        		iconWidth: 22
			        		,icons:[
			        			{
			        				iconCls:'icon-search'
			        				,handler:searchunselect
			        			}
				]"/>
			</div>
			<div title="空的" style="float:left; width:49px; height:20px; margin-left:5px;"></div>
			<div title="查询已选" style="float:left; width:195px; margin-left:5px;">
				<input id="serch_inselect" name="serch_inselect" class="easyui-textbox" data-options="
			        		iconWidth: 22
			        		,icons:[
			        			{
			        				iconCls:'icon-search'
			        				,handler:searchinselect
			        			}
				]"/>
			</div>
		</div>
	</div>
	<div style="margin: 0;padding: 20px 50px;">
		<div style="float:left; width:470px; height:350px;  margin-left:5px;">	
			<div id="unSelectT" style="float:left; width:200px; height:350px;  margin-left:5px;">
				<div style="float:left; width:195px; height:162px;  margin-left:5px; border: 1px solid #ccc; overflow:auto">
					<ul id="unSelectD"></ul>
				</div>
				<div style="float:left; width:195px; height:10px;  margin-left:5px;"></div>
				<div id="unSelectP" style="float:left; width:195px; height:162px;  margin-left:5px; border: 1px solid #ccc; overflow:auto">
				</div>				
			</div>
			<div style="float:left; width:49px; height:340px;  margin-left:5px;">
				<button type="button" class="btn-xs btn-primary" onclick="$add()">>></button>
			</div>
			<div id="inSelectP" class="easyui-datagrid" style="float:left; width:195px; height:340px;  margin-left:5px; border: 1px solid #ccc;"> 
			</div>
		</div>
	</div>

<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/js/RewardAndPunishmentSelectPerson.js"></script>
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/js/userTree.js"></script>