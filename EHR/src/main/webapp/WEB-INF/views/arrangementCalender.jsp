<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="main" style="height: 98%;">
	<div
		style="padding-left: 50px; background-color: #99BBE8; height: 36px; text-align: inherit;">
		<div style="padding: 7px; width: 40%; float: left">
			<span style="font-size: 16px;" id="arrangement_name">排班名称</span>：<span
				style="font-size: 16px;margin-left: 10px" id="arrangement_time">09:00 ~ 18:00</span>
		</div>
		<div id="arrangement_calender_tools"
			style="padding: 5px; width: 40%; float: right; text-align: right;">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addSpecialArrangement()">添加</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeSpecialArrangement()">删除</a> 
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="updateSpecialArrangement()">修改</a>
		</div>
	</div>
	<div id="myrl" style="width: 100%; margin-left: auto; margin-right:auto; height: 100%; overflow:hidden;">
		<!-- 			<form name=CLD> -->
		<TABLE class="biao" style="table-layout: fixed width=100%">
			<TBODY id="calendar-table">
				<TR>
					<TD class="calTit" colSpan=7
						style="height: 40px; padding-top: 3px; text-align: center;">

						<a href="#" title="上一年" id="nianjian" class="ymNaviBtn lsArrow"></a>
						<a href="#" title="上一月" id="yuejian" class="ymNaviBtn lArrow"></a>

						<div
							style="width: 250px; float: left; padding-left: 35%; font-size: 20px">
							<span id="dateSelectionRili" class="dateSelectionRili"
								style="cursor: hand; color: white; border-bottom: 1px solid white;"
								onclick="dateSelection.show()"> <span id="nian"
								class="topDateFont"></span><span class="topDateFont">年</span><span
								id="yue" class="topDateFont"></span><span class="topDateFont">月</span>
								<span class="dateSelectionBtn cal_next" style="font-size: 12px;"
								onclick="dateSelection.show()">▼</span></span> &nbsp;&nbsp;<font id=GZ
								class="topDateFont"></font>
						</div> <!--新加导航功能-->
						<div style="left: 41%; top: 30px; display: none;"
							id="dateSelectionDiv">
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
											id="SM10" class="month" onclick="dateSelection.setMonth(10)">11</span><span
											id="SM11" class="month curr"
											onclick="dateSelection.setMonth(11)">12</span>
									</div>
									<div style="clear: both;"></div>
								</div>
								<div id="dateSelectionBtn">
									<div id="dateSelectionTodayBtn"
										onclick="dateSelection.goToday()">今天</div>
									<div id="dateSelectionOkBtn" onclick="dateSelection.go()">确定</div>
									<div id="dateSelectionCancelBtn" onclick="dateSelection.hide()">取消</div>
								</div>
							</div>
							<div id="dateSelectionFooter"></div>
						</div> <a href="#" id="nianjia" title="下一年" class="ymNaviBtn rsArrow"
						style="float: right;"></a> <a href="#" id="yuejia" title="下一月"
						class="ymNaviBtn rArrow" style="float: right;"></a> <!--	<a id="jintian" href="#" title="今天" class="btn" style="float:right; margin-top:-2px; font-size:12px; text-align:center;">今天</a>-->

					</TD>
				</TR>
				<TR class="calWeekTit"
					style="font-size: 16px; height: 45px; text-align: center;">
					<TD width="100" class="red">星期日</TD>
					<TD width="100">星期一</TD>
					<TD width="100">星期二</TD>
					<TD width="100">星期三</TD>
					<TD width="100">星期四</TD>
					<TD width="100">星期五</TD>
					<TD width="100" class="red">星期六</TD>
				</TR>

			</tbody>
		</TABLE>
		<!-- 			</form> -->
	</div>
</div>
<SCRIPT language="JavaScript">
	var gNum;
	var table = document.getElementById("calendar-table");
	for (var i = 0; i < 6; i++) {
		var tr = document.createElement("tr");
		tr.style = "table-layout:fixed;height:71px";
		tr.align = "center";
		for (var j = 0; j < 7; j++) {
			gNum = i * 7 + j;
			var td = document.createElement("td");
			td.id = "GD" + gNum;
			td.on = "0";
			var font1 = document.createElement("font");
			font1.id = "SD" + gNum;
			font1.style = "font-size :26px";
			font1.face = "Arial";
			if (j == 0 || j == 6) {
				font1.color = "red";
			}
			font1.TITLE = "";
			td.appendChild(font1);
			td.appendChild(document.createElement("br"));
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
<script type="text/javascript" charset="utf-8"
	src="${pageContext.request.contextPath}/js/arrangementCalender.js"></script>