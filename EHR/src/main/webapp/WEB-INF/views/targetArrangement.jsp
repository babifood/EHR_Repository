<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/targetArrangement.css" />
<div class="main" style="height: 98%;">
		<div style="padding-left: 60px;background-color: #99BBE8;height: 30px;text-align: inherit;">
			<div style="padding: 7px;">
				<span style="font-size: 16px;" id="target_arrangement_name"></span>
				<span style="font-size: 16px;margin-left: 10px" id="target_arrangement_time"></span>
				<span style="font-size: 16px;margin-left: 20px" id="target_arrangement_type"></span>
			</div>
		</div>
		<div id="myrl"
			style="width: 100%; margin-left: auto; margin-right: auto; height: 100%; overflow: hidden;">
<!-- 			<form name=CLD> -->
				<table class="biao" style="table-layout: fixed; width:100%">
					<tbody id="target_calendar_table" style="width: 100%;">
						<tr>
							<td class="calTit" colSpan=7
								style="height: 30px; padding-top: 3px; text-align: center;">

								<a href="#" title="上一年" id="target_nianjian" class="ymNaviBtn lsArrow"></a>
								<a href="#" title="上一月" id="target_yuejian" class="ymNaviBtn lArrow"></a>

								<div style="width: 250px; float: left; padding-left: 30%;font-size: 20px">
									<span id="target_dateSelectionRili" class="dateSelectionRili"
										style="cursor: hand; color: white; border-bottom: 1px solid white;"
										onclick="targetDateSelection.show()"> <span id="target_nian"
										class="topDateFont"></span><span class="topDateFont">年</span><span
										id="target_yue" class="topDateFont"></span><span class="topDateFont">月</span>
										<span class="dateSelectionBtn cal_next" style="font-size: 12px;"
										onclick="targetDateSelection.show()">▼</span></span> &nbsp;&nbsp;<font
										id=target_GZ class="topDateFont"></font>
								</div> <!--新加导航功能-->
								<div style="left: 40%; display: none;" id="target_dateSelectionDiv">
									<div id="target_dateSelectionHeader"></div>
									<div id="target_dateSelectionBody">
										<div id="target_yearList">
											<div id="target_yearListPrev" onclick="targetDateSelection.prevYearPage()">&lt;</div>
											<div id="target_yearListContent"></div>
											<div id="target_yearListNext" onclick="targetDateSelection.nextYearPage()">&gt;</div>
										</div>
										<div id="target_dateSeparator"></div>
										<div id="target_monthList">
											<div id="target_monthListContent">
												<span id="target_SM0" class="month" onclick="targetDateSelection.setMonth(0)">1</span>
												<span id="target_SM1" class="month" onclick="targetDateSelection.setMonth(1)">2</span><span
													id="target_SM2" class="month" onclick="targetDateSelection.setMonth(2)">3</span><span
													id="target_SM3" class="month" onclick="targetDateSelection.setMonth(3)">4</span><span
													id="target_SM4" class="month" onclick="targetDateSelection.setMonth(4)">5</span><span
													id="target_SM5" class="month" onclick="targetDateSelection.setMonth(5)">6</span><span
													id="target_SM6" class="month" onclick="targetDateSelection.setMonth(6)">7</span><span
													id="target_SM7" class="month" onclick="targetDateSelection.setMonth(7)">8</span><span
													id="target_SM8" class="month" onclick="targetDateSelection.setMonth(8)">9</span><span
													id="target_SM9" class="month" onclick="targetDateSelection.setMonth(9)">10</span><span
													id="target_SM10" class="month" onclick="targetDateSelection.setMonth(10)">11</span><span
													id="target_SM11" class="month curr" onclick="targetDateSelection.setMonth(11)">12</span>
											</div>
											<div style="clear: both;"></div>
										</div>
										<div id="target_dateSelectionBtn">
											<div id="target_dateSelectionTodayBtn"
												onclick="targetDateSelection.goToday()">今天</div>
											<div id="target_dateSelectionOkBtn" onclick="targetDateSelection.go()">确定</div>
											<div id="target_dateSelectionCancelBtn"
												onclick="targetDateSelection.hide()">取消</div>
										</div>
									</div>
									<div id="target_dateSelectionFooter"></div>
								</div> <a href="#" id="target_nianjia" title="下一年" class="ymNaviBtn rsArrow"
								style="float: right;"></a> <a href="#" id="target_yuejia" title="下一月"
								class="ymNaviBtn rArrow" style="float: right;"></a> <!--	<a id="jintian" href="#" title="今天" class="btn" style="float:right; margin-top:-2px; font-size:12px; text-align:center;">今天</a>-->

							</td>
						</tr>
						<tr class="calWeekTit"
							style="font-size: 16px; height: 30px; text-align: center;width: 100%;">
							<TD width="15%" class="red">星期日</TD>
							<TD width="14%">星期一</TD>
							<TD width="14%">星期二</TD>
							<TD width="14%">星期三</TD>
							<TD width="14%">星期四</TD>
							<TD width="14%">星期五</TD>
							<TD width="15%" class="red">星期六</TD>
						</tr>

					</tbody>
				</table>
<!-- 			</form> -->
		</div>
	</div>
<SCRIPT language="JavaScript">
var gNum;
var table = document.getElementById("target_calendar_table");
for (var i = 0; i < 6; i++) {
	var tr = document.createElement("tr");
	tr.style = "table-layout:fixed;height:50px";
	tr.align = "center";
	for (var j = 0; j < 7; j++) {
		gNum = i * 7 + j;
		var td = document.createElement("td");
		td.id = "target_GD" + gNum;
		td.on = "0";
		var font1 = document.createElement("font");
		font1.id = "target_SD" + gNum;
		font1.style = "font-size :16px";
		font1.face = "Arial";
		if (j == 0 || j == 6) {
			font1.color = "red";
		}
		font1.TITLE = "";
		td.appendChild(font1);
		td.appendChild(document.createElement("br"));
		var font2 = document.createElement("font");
		font2.id = "target_LD" + gNum;
		font2.size = "2";
		font2.style = "white-space:nowrap;overflow:hidden;cursor:default;";
		td.appendChild(font2);
		tr.appendChild(td);
	}
	table.appendChild(tr);
}
</SCRIPT>
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/js/targetArrangement.js"></script>