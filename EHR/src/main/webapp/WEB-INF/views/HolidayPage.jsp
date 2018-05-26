<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/calendar/css/calendarAll.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/calendar/css/skin.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/calendar/css/fontSize12.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/calendar/css/calendar.css" />
<div class="easyui-panel" data-options="fit:true" id="holiday_panel">
	<div class="main" style="height: 98%">
		<div id="myrl"
			style="width: 100%"; margin-left: auto; margin-right: auto; height: 100%; overflow: hidden;">
			<form name=CLD>
				<TABLE class="biao" style="table-layout: fixed" width="100%">
					<TBODY id="calendar-table">
						<TR>
							<TD class="calTit" colSpan=7
								style="height: 35px; padding-top: 3px; text-align: center;">

								<a href="#" title="上一年" id="nianjian" class="ymNaviBtn lsArrow"></a>
								<a href="#" title="上一月" id="yuejian" class="ymNaviBtn lArrow"></a>

								<div style="width: 250px; float: left; padding-left: 230px;">
									<span id="dateSelectionRili" class="dateSelectionRili"
										style="cursor: hand; color: white; border-bottom: 1px solid white;"
										onclick="dateSelection.show()"> <span id="nian"
										class="topDateFont"></span><span class="topDateFont">年</span><span
										id="yue" class="topDateFont"></span><span class="topDateFont">月</span>
										<span class="dateSelectionBtn cal_next"
										onclick="dateSelection.show()">▼</span></span> &nbsp;&nbsp;<font
										id=GZ class="topDateFont"></font>
								</div> <!--新加导航功能-->
								<div style="left: 310px; display: none;" id="dateSelectionDiv">
									<div id="dateSelectionHeader"></div>
									<div id="dateSelectionBody">
										<div id="yearList">
											<div id="yearListPrev" onclick="dateSelection.prevYearPage()">&lt;</div>
											<div id="yearListContent"></div>
											<div id="yearListNext" onclick="dateSelection.nextYearPage()">&gt;</div>
										</div>
										<div id="dateSeparator"></div>
										<div id="monthList">
											<div id="monthListContent">
												<span id="SM0" class="month"
													onclick="dateSelection.setMonth(0)">1</span><span id="SM1"
													class="month" onclick="dateSelection.setMonth(1)">2</span><span
													id="SM2" class="month" onclick="dateSelection.setMonth(2)">3</span><span
													id="SM3" class="month" onclick="dateSelection.setMonth(3)">4</span><span
													id="SM4" class="month" onclick="dateSelection.setMonth(4)">5</span><span
													id="SM5" class="month" onclick="dateSelection.setMonth(5)">6</span><span
													id="SM6" class="month" onclick="dateSelection.setMonth(6)">7</span><span
													id="SM7" class="month" onclick="dateSelection.setMonth(7)">8</span><span
													id="SM8" class="month" onclick="dateSelection.setMonth(8)">9</span><span
													id="SM9" class="month" onclick="dateSelection.setMonth(9)">10</span><span
													id="SM10" class="month"
													onclick="dateSelection.setMonth(10)">11</span><span
													id="SM11" class="month curr"
													onclick="dateSelection.setMonth(11)">12</span>
											</div>
											<div style="clear: both;"></div>
										</div>
										<div id="dateSelectionBtn">
											<div id="dateSelectionTodayBtn"
												onclick="dateSelection.goToday()">今天</div>
											<div id="dateSelectionOkBtn" onclick="dateSelection.go()">确定</div>
											<div id="dateSelectionCancelBtn"
												onclick="dateSelection.hide()">取消</div>
										</div>
									</div>
									<div id="dateSelectionFooter"></div>
								</div> <a href="#" id="nianjia" title="下一年" class="ymNaviBtn rsArrow"
								style="float: right;"></a> <a href="#" id="yuejia" title="下一月"
								class="ymNaviBtn rArrow" style="float: right;"></a> <!--	<a id="jintian" href="#" title="今天" class="btn" style="float:right; margin-top:-2px; font-size:12px; text-align:center;">今天</a>-->

							</TD>
						</TR>
						<TR class="calWeekTit"
							style="font-size: 12px; height: 35px; text-align: center;">
							<TD width="100" class="red">星期日</TD>
							<TD width="100">星期一</TD>
							<TD width="100">星期二</TD>
							<TD width="100">星期三</TD>
							<TD width="100">星期四</TD>
							<TD width="100">星期五</TD>
							<TD width="100" class="red">星期六</TD>
						</TR>

						<SCRIPT language="JavaScript">
							var gNum;
							var table = document
									.getElementById("calendar-table");
							for (var i = 0; i < 6; i++) {
								var tr = document.createElement("tr");
								tr.style = "table-layout:fixed;height:60px";
								tr.align = "center";
								// 						tr.height="50";
								// 										tr.id = "tt";
								for (var j = 0; j < 7; j++) {
									gNum = i * 7 + j;
									var td = document.createElement("td");
									td.id = "GD" + gNum;
									td.on = "0";
									var font1 = document.createElement("font");
									font1.id = "SD" + gNum;
									font1.style = "font-size :22px";
									font1.face = "Arial";
									if (j == 0 || j == 6) {
										font1.color = "red";
									}
									font1.TITLE = "";
									td.appendChild(font1);
									td
											.appendChild(document
													.createElement("br"));
									var font2 = document.createElement("font");
									font2.id = "LD" + gNum;
									font2.size = "2";
									font2.style = "white-space:nowrap;overflow:hidden;cursor:default;";
									td.appendChild(font2);
									tr.appendChild(td);
								}
								table.appendChild(tr);
							}
						</SCRIPT>
					</tbody>
				</TABLE>
			</form>
		</div>
		<div id="holiday_dog" class="easyui-dialog" closed="true"
			data-options="modal: true" buttons="#holiday_dlg_buttons"
			style="width: 450px;">
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
			<div id="holiday_dlg_buttons" style="text-align: center;">
				<a href="#" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="saveHoliday()" style="width: 90px;">保存</a> 
				<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#holiday_dog').dialog('close')" style="width: 90px;">取消</a>
			</div>
		</div>
	</div>
</div>
<script src="http://www.jq22.com/jquery/jquery-1.10.2.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/calendar/js/calendar.js"></script>
