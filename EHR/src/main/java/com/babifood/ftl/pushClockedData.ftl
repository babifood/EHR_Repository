<forms version="2.1">
	<#list checkDataList as ck>
	<formExport>
		<summary id="8547562650925286759" name="formmain_0382"/>
		<definitions>
			<column id="field0001" type="0" name="姓名" length="255"/>
			<column id="field0002" type="0" name="工号" length="255"/>
			<column id="field0003" type="0" name="部门" length="255"/>
			<column id="field0004" type="0" name="单位机构" length="255"/>
			<column id="field0005" type="3" name="日期" length="255"/>
			<column id="field0006" type="0" name="星期" length="10"/>
			<column id="field0008" type="4" name="年假" length="20"/>
			<column id="field0009" type="4" name="缺勤小时数" length="20"/>
			<column id="field0010" type="4" name="婚假" length="20"/>
			<column id="field0011" type="4" name="请假小时数" length="20"/>
			<column id="field0013" type="4" name="迟到" length="2"/>
			<column id="field0014" type="4" name="早退" length="2"/>
			<column id="field0015" type="4" name="旷工" length="2"/>
			<column id="field0016" type="4" name="丧假" length="20"/>
			<column id="field0017" type="4" name="餐补个数" length="20"/>
			<column id="field0019" type="4" name="病假" length="20"/>
			<column id="field0020" type="4" name="异动小时数" length="20"/>
			<column id="field0021" type="2" name="打卡起始时间" length="255"/>
			<column id="field0022" type="2" name="打卡结束时间" length="255"/>
			<column id="field0023" type="4" name="其它请假" length="20"/>
			<column id="field0024" type="4" name="加班小时数" length="20"/>
			<column id="field0025" type="0" name="标准下班时间" length="255"/>
			<column id="field0026" type="0" name="标准上班时间" length="255"/>
			<column id="field0027" type="4" name="出差小时数" length="20"/>
			<column id="field0028" type="4" name="事假" length="20"/>
			<column id="field0029" type="4" name="年度" length="4"/>
			<column id="field0030" type="4" name="月份" length="2"/>
			<column id="field0007" type="4" name="考勤标志" length="2"/>
			<column id="field0018" type="4" name="打卡原始时长" length="20"/>
			<column id="field0031" type="2" name="业务开始时间" length="255"/>
			<column id="field0012" type="4" name="工作时长" length="20"/>
			<column id="field0032" type="2" name="业务结束时间" length="255"/>
			<column id="field0033" type="4" name="培训假" length="20"/>
			<column id="field0034" type="4" name="产假" length="20"/>
			<column id="field0035" type="4" name="陪产假" length="20"/>
			<column id="field0036" type="4" name="调休" length="20"/>
			<column id="field0037" type="0" name="打卡地点" length="200"/>
			<column id="field0038" type="0" name="考勤方式" length="50"/>
			<column id="field0039" type="4" name="标准工作时长" length="20"/>
		</definitions>
		<values>
			<column name="姓名"><value>${ck.userName}</value></column>
			<column name="工号"><value>${ck.workNum}</value></column>
			<column name="部门"><value>${ck.deptCode}</value></column>
			<column name="单位机构"><value>${ck.organCode}</value></column>
			<column name="日期"><value>${ck.checkingDate}</value></column>
			<column name="星期"><value>${ck.week}</value></column>
			<column name="年假"><value>${ck.nianJia}</value></column>
			<column name="缺勤小时数"><value>${ck.queQin}</value></column>
			<column name="婚假"><value>${ck.hunJia}</value></column>
			<column name="请假小时数"><value>${ck.qingJia}</value></column>
			<column name="迟到"><value>${ck.chiDao}</value></column>
			<column name="早退"><value>${ck.zaoTui}</value></column>
			<column name="旷工"><value>${ck.kuangGongCiShu}</value></column>
			<column name="丧假"><value>${ck.sangJia}</value></column>
			<column name="餐补个数"><value>${ck.canBu}</value></column>
			<column name="病假"><value>${ck.bingJia}</value></column>
			<column name="异动小时数"><value>${ck.yiDong}</value></column>
			<column name="打卡起始时间"><value>${ck.checkingBeginTime}</value></column>
			<column name="打卡结束时间"><value>${ck.checkingEndTime}</value></column>
			<column name="其它请假"><value>${ck.otherQingJia}</value></column>
			<column name="加班小时数"><value>${ck.jiaBan}</value></column>
			<column name="标准下班时间"><value>${ck.beginTime}</value></column>
			<column name="标准上班时间"><value>${ck.endTime}</value></column>
			<column name="出差小时数"><value>${ck.chuCha}</value></column>
			<column name="事假"><value>${ck.shiJia}</value></column>
			<column name="年度"><value>${ck.year}</value></column>
			<column name="月份"><value>${ck.month}</value></column>
			<column name="考勤标志"><value>${ck.clockFlag}</value></column>
			<column name="打卡原始时长"><value>${ck.originalCheckingLength}</value></column>
			<column name="业务开始时间"><value>${ck.eventBeginTime}</value></column>
			<column name="工作时长"><value>${ck.actualWorkLength}</value></column>
			<column name="业务结束时间"><value>${ck.eventEndTime}</value></column>
			<column name="培训假"><value>${ck.peiXunJia}</value></column>
			<column name="产假"><value>${ck.chanJia}</value></column>
			<column name="陪产假"><value>${ck.peiChanJia}</value></column>
			<column name="调休"><value>${ck.tiaoXiu}</value></column>
			<column name="打卡地点"><value></value></column>
			<column name="考勤方式"><value>${ck.checkingType}</value></column>
			<column name="标准工作时长"><value>${ck.standWorkLength}</value></column>
		</values>
		<subForms/>	
	</formExport>
	</#list>
</forms>