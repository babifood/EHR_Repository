<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/calendar/css/calendarAll.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/calendar/css/skin.css" />
<%-- <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/calendar/css/fontSize12.css" /> --%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/holiday.css" />
<div class="easyui-panel" data-options="fit:true,tools:'#hoilday_tools'" id="holiday_panel" title="节假日">
	<div id="hoilday_tools">
<!-- 		<a href="#" class="icon-add" style="width: 70px;text-align: right;" onclick="addHoliday()"><div>添加</div></a> -->
<!-- 		<a href="#" class="icon-edit" style="width: 70px;text-align: right;" onclick="editHoliday()">修改</a> -->
<!-- 		<a href="#" class="icon-remove" style="width: 70px;text-align: right;" onclick="removeHoliday()">删除</a> -->
		<a href="#" class="icon-add" style="width: 180px;text-align: right;" onclick="syncHoliday()"><div>同步OA节假日</div></a>
	</div>
	<div class="main" style="height: 98%">
		<div id="myrl"
			style="width: 100%; margin-left: auto; margin-right: auto; height: 100%; overflow: hidden;">
<!-- 			<form name=CLD> -->
				<table class="biao" style="table-layout: fixed; width=100%">
					<tbody id="holiday_calendar-table">
						<tr>
							<td class="calTit" colSpan=7
								style="height: 35px; padding-top: 3px; text-align: center;">

								<a href="#" title="上一年" id="holiday_nianjian" class="ymNaviBtn lsArrow"></a>
								<a href="#" title="上一月" id="holiday_yuejian" class="ymNaviBtn lArrow"></a>

								<div style="width: 250px; float: left; padding-left: 330px;">
									<span id="holiday_dateSelectionRili" class="dateSelectionRili"
										style="cursor: hand; color: white; border-bottom: 1px solid white;"
										onclick="holidayDateSelection.show()"> <span id="holiday_nian"
										class="topDateFont"></span><span class="topDateFont">年</span><span
										id="holiday_yue" class="topDateFont"></span><span class="topDateFont">月</span>
										<span class="dateSelectionBtn cal_next"
										onclick="holidayDateSelection.show()">▼</span></span> &nbsp;&nbsp;<font
										id="holiday_GZ" class="topDateFont"></font>
								</div> <!--新加导航功能-->
								<div style="left: 410px; display: none;" id="holiday_dateSelectionDiv">
									<div id="holiday_dateSelectionHeader"></div>
									<div id="holiday_dateSelectionBody">
										<div id="holiday_yearList">
											<div id="holiday_yearListPrev" onclick="holidayDateSelection.prevYearPage()">&lt;</div>
											<div id="holiday_yearListContent"></div>
											<div id="holiday_yearListNext" onclick="holidayDateSelection.nextYearPage()">&gt;</div>
										</div>
										<div id="holiday_dateSeparator"></div>
										<div id="holiday_monthList">
											<div id="holiday_monthListContent">
												<span id="holiday_SM0" class="month" onclick="holidayDateSelection.setMonth(0)">1</span>
												<span id="holiday_SM1" class="month" onclick="holidayDateSelection.setMonth(1)">2</span><span
													id="holiday_SM2" class="month" onclick="holidayDateSelection.setMonth(2)">3</span><span
													id="holiday_SM3" class="month" onclick="holidayDateSelection.setMonth(3)">4</span><span
													id="holiday_SM4" class="month" onclick="holidayDateSelection.setMonth(4)">5</span><span
													id="holiday_SM5" class="month" onclick="holidayDateSelection.setMonth(5)">6</span><span
													id="holiday_SM6" class="month" onclick="holidayDateSelection.setMonth(6)">7</span><span
													id="holiday_SM7" class="month" onclick="holidayDateSelection.setMonth(7)">8</span><span
													id="holiday_SM8" class="month" onclick="holidayDateSelection.setMonth(8)">9</span><span
													id="holiday_SM9" class="month" onclick="holidayDateSelection.setMonth(9)">10</span><span
													id="holiday_SM10" class="month" onclick="holidayDateSelection.setMonth(10)">11</span><span
													id="holiday_SM11" class="month curr" onclick="holidayDateSelection.setMonth(11)">12</span>
											</div>
											<div style="clear: both;"></div>
										</div>
										<div id="holiday_dateSelectionBtn">
											<div id="holiday_dateSelectionTodayBtn" onclick="holidayDateSelection.goToday()">今天</div>
											<div id="holiday_dateSelectionOkBtn" onclick="holidayDateSelection.go()">确定</div>
											<div id="holiday_dateSelectionCancelBtn" onclick="holidayDateSelection.hide()">取消</div>
										</div>
									</div>
									<div id="holiday_dateSelectionFooter"></div>
								</div> 
								<a href="#" id="holiday_nianjia" title="下一年" class="ymNaviBtn rsArrow" style="float: right;"></a> 
								<a href="#" id="holiday_yuejia" title="下一月" class="ymNaviBtn rArrow" style="float: right;"></a> <!--	<a id="jintian" href="#" title="今天" class="btn" style="float:right; margin-top:-2px; font-size:12px; text-align:center;">今天</a>-->
							</td>
						</tr>
						<tr class="calWeekTit"
							style="font-size: 12px; height: 35px; text-align: center;">
							<TD width="100" class="red">星期日</TD>
							<TD width="100">星期一</TD>
							<TD width="100">星期二</TD>
							<TD width="100">星期三</TD>
							<TD width="100">星期四</TD>
							<TD width="100">星期五</TD>
							<TD width="100" class="red">星期六</TD>
						</tr>

					</tbody>
				</table>
<!-- 			</form> -->
		</div>
	</div>
		<div id="holiday_dog" class="easyui-dialog" closed="true" data-options="modal: true" buttons="#holiday_operate_buttons" style="width: 450px;">
			<div style="margin: 0; padding: 20px 50px;">
				<div style="margin-bottom: 20px; font-size: 18px; border-bottom: 1px solid #ccc;">
					<span style="color: blue;" id="holiday_type">添加节假日</span>
				</div>
				<input type="hidden" id="holiday_id" name="id" value="">
				<div style="margin-bottom: 10px;">
					节假日名称：<input type="text" id="holiday_name" name="holiday_name"
						class="textbox" data-options="required:true" style="width: 180px;" />
					<span id="holiday_name_span" style="color: red"></span>
				</div>
				<div style="margin-bottom: 10px;">
					开&nbsp;始&nbsp;时&nbsp;间：<input class="easyui-datebox" type="text" id="start_date" name="start_date" class="textbox" data-options="required:true" style="width: 180px;" /> 
						<span id="holiday_date_span" style="color: red"></span>
				</div>
				<div style="margin-bottom: 10px;">
					结&nbsp;束&nbsp;时&nbsp;间：<input class="easyui-datebox" type="text" id="end_date" name="end_date" class="textbox" data-options="required:true" style="width: 180px;" /> 
						<span id="holiday_date_span" style="color: red"></span>
				</div>
				<div style="margin-bottom: 10px;"> 
					备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：<textarea name="remark" rows="3" class="textbox" cols="25" id="holiday_remark" />
				</div>
			</div>
		</div>
		<div id="holiday_operate_buttons" style="text-align: center;">
			<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveHoliday()" style="width: 90px;">保存</a> 
			<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#holiday_dog').dialog('close')" style="width: 90px;">取消</a>
		</div>
</div>

<SCRIPT language="JavaScript">
var gNum;
var table = document.getElementById("holiday_calendar-table");
for (var i = 0; i < 6; i++) {
	var tr = document.createElement("tr");
	tr.style = "table-layout:fixed;height:60px";
	tr.align = "center";
	for (var j = 0; j < 7; j++) {
		gNum = i * 7 + j;
		var td = document.createElement("td");
		td.id = "holiday_GD" + gNum;
		td.on = "0";
		var font1 = document.createElement("font");
		font1.id = "holiday_SD" + gNum;
		font1.style = "font-size :22px";
		font1.face = "Arial";
		if (j == 0 || j == 6) {
			font1.color = "red";
		}
		font1.TITLE = "";
		td.appendChild(font1);
		td.appendChild(document.createElement("br"));
		var font2 = document.createElement("font");
		font2.id = "holiday_LD" + gNum;
		font2.size = "2";
		font2.style = "white-space:nowrap;overflow:hidden;cursor:default;";
		td.appendChild(font2);
		tr.appendChild(td);
	}
	table.appendChild(tr);
}
</SCRIPT>
<script type="text/javascript" src="${pageContext.request.contextPath}/calendar/js/calendar.js"></script>
