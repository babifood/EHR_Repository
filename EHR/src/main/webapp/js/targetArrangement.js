/*
*
*/
var arrangementNode;
var deptcode;
var pNumber;
var lunarInfo = new Array(
		0x04bd8, 0x04ae0, 0x0a570, 0x054d5, 0x0d260, 0x0d950, 0x16554, 0x056a0, 0x09ad0, 0x055d2,
		0x04ae0, 0x0a5b6, 0x0a4d0, 0x0d250, 0x1d255, 0x0b540, 0x0d6a0, 0x0ada2, 0x095b0, 0x14977,
		0x04970, 0x0a4b0, 0x0b4b5, 0x06a50, 0x06d40, 0x1ab54, 0x02b60, 0x09570, 0x052f2, 0x04970,
		0x06566, 0x0d4a0, 0x0ea50, 0x06e95, 0x05ad0, 0x02b60, 0x186e3, 0x092e0, 0x1c8d7, 0x0c950,
		0x0d4a0, 0x1d8a6, 0x0b550, 0x056a0, 0x1a5b4, 0x025d0, 0x092d0, 0x0d2b2, 0x0a950, 0x0b557,
		0x06ca0, 0x0b550, 0x15355, 0x04da0, 0x0a5b0, 0x14573, 0x052b0, 0x0a9a8, 0x0e950, 0x06aa0,
		0x0aea6, 0x0ab50, 0x04b60, 0x0aae4, 0x0a570, 0x05260, 0x0f263, 0x0d950, 0x05b57, 0x056a0,
		0x096d0, 0x04dd5, 0x04ad0, 0x0a4d0, 0x0d4d4, 0x0d250, 0x0d558, 0x0b540, 0x0b6a0, 0x195a6,
		0x095b0, 0x049b0, 0x0a974, 0x0a4b0, 0x0b27a, 0x06a50, 0x06d40, 0x0af46, 0x0ab60, 0x09570,
		0x04af5, 0x04970, 0x064b0, 0x074a3, 0x0ea50, 0x06b58, 0x055c0, 0x0ab60, 0x096d5, 0x092e0,
		0x0c960, 0x0d954, 0x0d4a0, 0x0da50, 0x07552, 0x056a0, 0x0abb7, 0x025d0, 0x092d0, 0x0cab5,
		0x0a950, 0x0b4a0, 0x0baa4, 0x0ad50, 0x055d9, 0x04ba0, 0x0a5b0, 0x15176, 0x052b0, 0x0a930,
		0x07954, 0x06aa0, 0x0ad50, 0x05b52, 0x04b60, 0x0a6e6, 0x0a4e0, 0x0d260, 0x0ea65, 0x0d530,
		0x05aa0, 0x076a3, 0x096d0, 0x04bd7, 0x04ad0, 0x0a4d0, 0x1d0b6, 0x0d250, 0x0d520, 0x0dd45,
		0x0b5a0, 0x056d0, 0x055b2, 0x049b0, 0x0a577, 0x0a4b0, 0x0aa50, 0x1b255, 0x06d20, 0x0ada0,
		0x14b63);

var solarMonth = new Array(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
var solarTerm = new Array("小寒", "大寒", "立春", "雨水", "惊蛰", "春分", "清明", "谷雨", "立夏", "小满", "芒种", "夏至", "小暑", "大暑", "立秋", "处暑", "白露", "秋分", "寒露", "霜降", "立冬", "小雪", "大雪", "冬至")
var sTermInfo = new Array(0, 21208, 42467, 63836, 85337, 107014, 128867, 150921, 173149, 195551, 218072, 240693, 263343, 285989, 308563, 331033, 353350, 375494, 397447, 419210, 440795, 462224, 483532, 504758)
var nStr1 = new Array('日', '一', '二', '三', '四', '五', '六', '七', '八', '九', '十')
var nStr2 = new Array('初', '十', '廿', '卅', ' ')
var monthName = new Array("正月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "冬月", "腊月")

/*****************************************************************************
 日期计算
 *****************************************************************************/

//====================================== 返回农历 y年的总天数
function lYearDays(y) {
	var i,
	sum = 348;
	for (i = 0x8000; i > 0x8; i >>= 1)
		sum += (lunarInfo[y - 1900] & i) ? 1 : 0;
	return (sum + leapDays(y));
}

//====================================== 返回农历 y年闰月的天数
function leapDays(y) {
	if (leapMonth(y))
		return ((lunarInfo[y - 1900] & 0x10000) ? 30 : 29);
	else
		return (0);
}

//====================================== 返回农历 y年闰哪个月 1-12 , 没闰返回 0
function leapMonth(y) {
    return(lunarInfo[y - 1900] & 0xf);
}

//====================================== 返回农历 y年m月的总天数
function monthDays(y, m) {
	return ((lunarInfo[y - 1900] & (0x10000 >> m)) ? 30 : 29);
}

//====================================== 算出农历, 传入日期控件, 返回农历日期控件
//                                       该控件属性有 .year .month .day .isLeap
function Lunar(objDate) {
	
	var i,
	leap = 0,
	temp = 0;
	var offset = (Date.UTC(objDate.getFullYear(), objDate.getMonth(), objDate.getDate()) - Date.UTC(1900, 0, 31)) / 86400000;
	
	for (i = 1900; i < 2050 && offset > 0; i++) {
		temp = lYearDays(i);
		offset -= temp;
	}
	
	if (offset < 0) {
		offset += temp;
		i--;
	}
	
	this.year = i;
	
	leap = leapMonth(i); //闰哪个月
	this.isLeap = false;
	
	for (i = 1; i < 13 && offset > 0; i++) {
		//闰月
		if (leap > 0 && i == (leap + 1) && this.isLeap == false) {
			--i;
			this.isLeap = true;
			temp = leapDays(this.year);
		} else {
			temp = monthDays(this.year, i);
		}
		
		//解除闰月
		if (this.isLeap == true && i == (leap + 1))
			this.isLeap = false;
		
		offset -= temp;
	}
	
	if (offset == 0 && leap > 0 && i == leap + 1)
		if (this.isLeap) {
			this.isLeap = false;
		} else {
			this.isLeap = true;
			--i;
		}
	
	if (offset < 0) {
		offset += temp;
		--i;
	}
	
	this.month = i;
	this.day = offset + 1;
}

//==============================返回公历 y年某m+1月的天数
function solarDays(y, m) {
	if (m == 1)
		return (((y % 4 == 0) && (y % 100 != 0) || (y % 400 == 0)) ? 29 : 28);
	else
		return (solarMonth[m]);
}
//============================== 阴历属性
//============================== 阴历属性
function calElement(sYear, sMonth, sDay, week, lYear, lMonth, lDay, isLeap, cYear, cMonth, cDay) {
	
	this.isToday = false;
	//瓣句
	this.sYear = sYear; //公元年4位数字
	this.sMonth = sMonth; //公元月数字
	this.sDay = sDay; //公元日数字
	this.week = week; //星期, 1个中文
	//农历
	this.lYear = lYear; //公元年4位数字
	this.lMonth = lMonth; //农历月数字
	this.lDay = lDay; //农历日数字
	this.isLeap = isLeap; //是否为农历闰月?
	//八字
	this.cYear = cYear; //年柱, 2个中文
	this.cMonth = cMonth; //月柱, 2个中文
	this.cDay = cDay; //日柱, 2个中文
	
	this.color = '';
	
	this.lunarFestival = ''; //农历节日
	this.solarFestival = ''; //公历节日
	this.solarTerms = ''; //节气
}

//===== 某年的第n个节气为几日(从0小寒起算)
function sTerm(y, n) {
	if (y == 2009 && n == 2) {
		sTermInfo[n] = 43467
	}
	var offDate = new Date((31556925974.7 * (y - 1900) + sTermInfo[n] * 60000) + Date.UTC(1900, 0, 6, 2, 5));
	return (offDate.getUTCDate());
}


//============================== 返回阴历 (y年,m+1月)
function CalConv2(yy, mm, dd, y, d, m, dt, nm, nd) {
    var dy = d + '' + dd
    if ((yy == 0 && dd == 6) || (yy == 6 && dd == 0) || (yy == 1 && dd == 7) || (yy == 7 && dd == 1) || (yy == 2 && dd == 8) || (yy == 8 && dd == 2) || (yy == 3 && dd == 9) || (yy == 9 && dd == 3) || (yy == 4 && dd == 10) || (yy == 10 && dd == 4) || (yy == 5 && dd == 11) || (yy == 11 && dd == 5)) {
        return '<FONT color=#0000A0>日值岁破 大事不宜</font>';
    }
    else if ((mm == 0 && dd == 6) || (mm == 6 && dd == 0) || (mm == 1 && dd == 7) || (mm == 7 && dd == 1) || (mm == 2 && dd == 8) || (mm == 8 && dd == 2) || (mm == 3 && dd == 9) || (mm == 9 && dd == 3) || (mm == 4 && dd == 10) || (mm == 10 && dd == 4) || (mm == 5 && dd == 11) || (mm == 11 && dd == 5)) {
        return '<FONT color=#0000A0>日值月破 大事不宜</font>';
    }
    else if ((y == 0 && dy == '911') || (y == 1 && dy == '55') || (y == 2 && dy == '111') || (y == 3 && dy == '75') || (y == 4 && dy == '311') || (y == 5 && dy == '95') || (y == 6 && dy == '511') || (y == 7 && dy == '15') || (y == 8 && dy == '711') || (y == 9 && dy == '35')) {
        return '<FONT color=#0000A0>日值上朔 大事不宜</font>';
    }
    else if ((m == 1 && dt == 13) || (m == 2 && dt == 11) || (m == 3 && dt == 9) || (m == 4 && dt == 7) || (m == 5 && dt == 5) || (m == 6 && dt == 3) || (m == 7 && dt == 1) || (m == 7 && dt == 29) || (m == 8 && dt == 27) || (m == 9 && dt == 25) || (m == 10 && dt == 23) || (m == 11 && dt == 21) || (m == 12 && dt == 19)) {
        return '<FONT color=#0000A0>日值杨公十三忌 大事不宜</font>';
    }
    else {
        return 0;
    }
}


function calendar(y, m) {

	var sDObj,
	lDObj,
	lY,
	lM,
	lD = 1,
	lL,
	lX = 0,
	tmp1,
	tmp2,
	tmp3;
	var cY,
	cM,
	cD; //年柱,月柱,日柱
	var lDPOS = new Array(3);
	var n = 0;
	var firstLM = 0;
	
	sDObj = new Date(y, m, 1, 0, 0, 0, 0); //当月一日日期
	this.length = solarDays(y, m); //公历当月天数
	this.firstWeek = sDObj.getDay(); //公历当月1日星期几
   
    lM2 = (y - 1900) * 12 + m + 12;
    //当月一日与 1900/1/1 相差天数
    //1900/1/1与 1970/1/1 相差25567日, 1900/1/1 日柱为甲戌日(60进制10)
    var dayCyclical = Date.UTC(y, m, 1, 0, 0, 0, 0) / 86400000 + 25567 + 10;

    for (var i = 0; i < this.length; i++) {

        if (lD > lX) {
            sDObj = new Date(y, m, i + 1);    //当月一日日期
            lDObj = new Lunar(sDObj);     //农历
            lY = lDObj.year;           //农历年
            lM = lDObj.month;          //农历月
            lD = lDObj.day;            //农历日
            lL = lDObj.isLeap;         //农历是否闰月
            lX = lL ? leapDays(lY) : monthDays(lY, lM); //农历当月最后一天

            if (n == 0) firstLM = lM;
            lDPOS[n++] = i - lD + 1;
        }

        lD2 = (dayCyclical + i);

        this[i] = new calElement(y, m + 1, i + 1, nStr1[(i + this.firstWeek) % 7],
                lY, lM, lD++, lL,
                cY, cM, cD);

    }
	//今日
	if (y == tY && m == tM)
		this[tD - 1].isToday = true;
}

//======================================= 返回该年的复活节(春分后第一次满月周后的第一主日)
//======================================= 返回该年的复活节(春分后第一次满月周后的第一主日)
function easter(y) {
	
	var term2 = sTerm(y, 5); //取得春分日期
	var dayTerm2 = new Date(Date.UTC(y, 2, term2, 0, 0, 0, 0)); //取得春分的公历日期控件(春分一定出现在3月)
	var lDayTerm2 = new Lunar(dayTerm2); //取得取得春分农历
	
	if (lDayTerm2.day < 15) //取得下个月圆的相差天数
		var lMlen = 15 - lDayTerm2.day;
	else
		var lMlen = (lDayTerm2.isLeap ? leapDays(y) : monthDays(y, lDayTerm2.month)) - lDayTerm2.day + 15;
	
	//一天等于 1000*60*60*24 = 86400000 毫秒
	var l15 = new Date(dayTerm2.getTime() + 86400000 * lMlen); //求出第一次月圆为公历几日
	var dayEaster = new Date(l15.getTime() + 86400000 * (7 - l15.getUTCDay())); //求出下个周日
	
	this.m = dayEaster.getUTCMonth();
	this.d = dayEaster.getUTCDate();

}
//====================== 中文日期
function cDay(d, m,dt) {
	var s;
	switch (d) {
	case 1:
		s = monthName[m - 1];
		if(dt){
		s = '初一';
		}
		break;
	case 10:
		s = '初十';
		break;
	case 20:
		s = '二十';
		break;
	case 30:
		s = '三十';
		break;
	default:
		s = nStr2[Math.floor(d / 10)];
		s += nStr1[d % 10];
	}
	return (s);
}
var cld;

//---没有问题

//存放节假日
var arrangementDays = [];
var arrangementData;
function targetDrawCld(SY, SM) {
	if(pNumber){
		arrangementData = {"year":SY,'month':SM+1,"pNumber":pNumber};
	} else if(deptcode){
		arrangementData = {"year":SY,'month':SM+1,"deptCode":deptcode};
	} else {
		arrangementData = {"year":SY,'month':SM+1};
	}
    var i,sD,s,size;
    cld = new calendar(SY, SM);
    var rows = null;
    $.ajax({
        url:"arrangement/base/specialArrangementList",
        type:'POST',
        data:arrangementData,
        cache:false,
        async:false,
        dataType:'json',
        success:function(data) {
            if (data.code === "1") {
            	rows = data.arrangementList;
            	if(data.arrangementType){
//            		var type;
//            		if(data.arrangementType == '10'){//1-公司 2-中心 3-部门 4-科室 5-组 10-人员
//            			type = "按人员";
//            		} else if (data.arrangementType == '1') {
//            			type = "按公司";
//					} else if (data.arrangementType == '2') {
//						type = "按中心";
//					} else if (data.arrangementType == '3') {
//						type = "按部门";
//					} else if (data.arrangementType == '4') {
//						type = "按科室";
//					} else if (data.arrangementType == '5') {
//						type = "按班组";
//					}
            		$("#target_arrangement_type").text(data.arrangementType);
            		$("#target_arrangement_name").text(data.arrangementName + ":");
            		$("#target_arrangement_time").text(data.standardTime);
            	} else {
            		$("#target_arrangement_type").text("");
            		$("#target_arrangement_name").text("");
            		$("#target_arrangement_time").text("");
            	}
            } else {
            	$.messager.show({
            		title:'提示消息消息',
            		msg:data.msg,
            		timeout:3000,
            		showType:'slide'
            	});

            }
        },
    });
  
    for (i = 0; i < 42; i++) {
        sObj = $("#target_SD" + i)[0];
        lObj = $("#target_LD" + i)[0];
        
        sObj.className = '';
        
        //在这里回显回来的数值，如果是工作日为淡粉红，假日为青色
        sD = i - cld.firstWeek;
        var type =  $("#target_GD" + i).attr("class") //每次进来 都清除所有样式
		$("#target_GD" + i).removeClass(type);
        if (sD > -1 && sD < cld.length) {
        	$("#target_GD" + i).addClass("calendar_day");
            sObj.innerHTML = sD + 1;
			
			var nowDays = SY+''+addZ((SM+1))+addZ((sD+1));
			var hstr = arrangementDays.join();
			if(hstr.indexOf(nowDays)>-1){
				 $("#target_GD" + i).addClass("selday");
			}
			sObj.style.color = cld[sD].color;  //国定假日颜色
			
            if (cld[sD].lDay == 1){  //显示农历月
                lObj.innerHTML = '<b>' + (cld[sD].isLeap ? '闰' : '') + cld[sD].lMonth + '月' + (monthDays(cld[sD].lYear, cld[sD].lMonth) == 29 ? '小' : '大') + '</b>';
            }else{  //显示农历日
                lObj.innerHTML = cDay(cld[sD].lDay);
            }
            
            s = cld[sD].lunarFestival;
           
            if (s.length > 0) {  //农历节日
                if (s.length > 8) s = s.substr(0, 7) + '...';
                	s = s.fontcolor('red');
                
            } 
            
            if (rows && Object.keys(rows).length > 0) {   //将从数据库查询到的数据回显。
            	$.each(rows,function(index,ob){
            		if((sD+1) == ob.day){
            			$("#target_GD" + i).attr("data_id",ob.id);
            			if(ob.isAttend == "1"){
            				var time = ob.startTime + " ~ " + ob.endTime
            				$("#target_GD" + i).addClass("worksday");
            				lObj.innerHTML = "<span style='color:red'>"+time+"</span>";
            			} else {
            				$("#target_GD" + i).addClass("offday");
            				lObj.innerHTML = "<span style='color:red'>"+ob.remark+"</span>";
						}
            		}
            	});
            }
        } else {  //非日期
            $("#target_GD" + i).addClass("unover");
        }
    }
    $("table.biao .calendar_day").click(function() {
    	$("table.biao td").removeClass("selday");
    	this.classList.add("selday");
	})
}

/*清除数据*/
function targetClear() {
    for (i = 0; i < 42; i++) {
        sObj = $("#target_SD" + i)[0];
        if(sObj){
        	sObj.innerHTML = '';
        	lObj = $("#target_LD" + i)[0];
        	lObj.innerHTML = '';
        	$("#target_GD" + i).removeClass("unover");
        	$("#target_GD" + i).removeClass("jinri");
        	$("#target_GD" + i).removeClass("selday");
        }

    }

}


var Today = new Date();
var tY = Today.getFullYear();
var tM = Today.getMonth();

var tD = Today.getDate();
//////////////////////////////////////////////////////////////////////////////

var width = "130";
var offsetX = 2;
var offsetY = 18;

var x = 0;
var y = 0;
var snow = 0;
var sw = 0;
var cnt = 0;
var dStyle;


function addZ(obj){
	 return obj<10?'0'+obj:obj;
	}
var targetGlobal = {
	    currYear : -1, // 当前年
	    currMonth : -1, // 当前月，0-11
	    currDate : null, // 当前点选的日期
	    uid : null,
	    username : null,
	    email : null,
	    single : false
	    // 是否为独立页调用，如果是值为日历id，使用时请注意对0的判断，使用 single !== false 或者 single === false
	};

var targetDateSelection = {
	    currYear : -1,
	    currMonth : -1,

	    minYear : 1901,
	    maxYear : 2200,

	    beginYear : 0,
	    endYear : 0,

	    tmpYear : -1,
	    tmpMonth : -1,

	    init : function(year, month) {
	        if (typeof year == 'undefined' || typeof month == 'undefined') {
	            year = targetGlobal.currYear;
	            month = targetGlobal.currMonth;
	        }
	        this.setYear(year);
	        this.setMonth(month);
	        this.showYearContent();
	        this.showMonthContent();
	    },
	    show : function() {
	        document.getElementById('target_dateSelectionDiv').style.display = 'block';
	    },
	    hide : function() {
	        this.rollback();
	        document.getElementById('target_dateSelectionDiv').style.display = 'none';
	    },
	    today : function() {
	        var today = new Date();
	        var year = today.getFullYear();
	        var month = today.getMonth();

	        if (this.currYear != year || this.currMonth != month) {
	            if (this.tmpYear == year && this.tmpMonth == month) {
	                this.rollback();
	            } else {
	                this.init(year, month);
	                this.commit();
	            }
	        }
	    },
	    go : function() {
	        if (this.currYear == this.tmpYear && this.currMonth == this.tmpMonth) {
	            this.rollback();
	        } else {
	            this.commit();
	        }
	        this.hide();
	    },
	    goToday : function() {
	        this.today();
	        this.hide();
	    },
	    goPrevMonth : function() {
	        this.prevMonth();
	        this.commit();
	    },
	    goNextMonth : function() {
	        this.nextMonth();
	        this.commit();
	    },
	    goPrevYear : function() {
	        this.prevYear();
	        this.commit();
	    },
	    goNextYear : function() {
	        this.nextYear();
	        this.commit();
	    },
	    changeView : function() {
	        targetGlobal.currYear = this.currYear;
	        targetGlobal.currMonth = this.currMonth;
	        targetClear();
	        $("#target_nian").html(targetGlobal.currYear);
	        $("#target_yue").html(parseInt(targetGlobal.currMonth) + 1);
	        targetDrawCld(targetGlobal.currYear, targetGlobal.currMonth);
	    },
	    commit : function() {
	        if (this.tmpYear != -1 || this.tmpMonth != -1) {
	            // 如果发生了变化
	            if (this.currYear != this.tmpYear
	                    || this.currMonth != this.tmpMonth) {
	                // 执行某操作
	                this.showYearContent();
	                this.showMonthContent();
	                this.changeView();
	            }

	            this.tmpYear = -1;
	            this.tmpMonth = -1;
	        }
	    },
	    rollback : function() {
	        if (this.tmpYear != -1) {
	            this.setYear(this.tmpYear);
	        }
	        if (this.tmpMonth != -1) {
	            this.setMonth(this.tmpMonth);
	        }
	        this.tmpYear = -1;
	        this.tmpMonth = -1;
	        this.showYearContent();
	        this.showMonthContent();
	    },
	    prevMonth : function() {
	        var month = this.currMonth - 1;
	        if (month == -1) {
	            var year = this.currYear - 1;
	            if (year >= this.minYear) {
	                month = 11;
	                this.setYear(year);
	            } else {
	                month = 0;
	            }
	        }
	        this.setMonth(month);
	    },
	    nextMonth : function() {
	        var month = this.currMonth + 1;
	        if (month == 12) {
	            var year = this.currYear + 1;
	            if (year <= this.maxYear) {
	                month = 0;
	                this.setYear(year);
	            } else {
	                month = 11;
	            }
	        }
	        this.setMonth(month);
	    },
	    prevYear : function() {
	        var year = this.currYear - 1;
	        if (year >= this.minYear) {
	            this.setYear(year);
	        }
	    },
	    nextYear : function() {
	        var year = this.currYear + 1;
	        if (year <= this.maxYear) {
	            this.setYear(year);
	        }
	    },
	    prevYearPage : function() {
	        this.endYear = this.beginYear - 1;
	        this.showYearContent(null, this.endYear);
	    },
	    nextYearPage : function() {
	        this.beginYear = this.endYear + 1;
	        this.showYearContent(this.beginYear, null);
	    },
	    selectYear : function() {//杨：select
	        var selectY = $('select[@name="SY"] option[@selected]').text();
	        this.setYear(selectY);
	        this.commit();
	    },
	    selectMonth : function() {
	        var selectM = $('select[@name="SM"] option[@selected]').text();
	        this.setMonth(selectM - 1);
	        this.commit();
	    },
	    setYear : function(value) {
	        if (this.tmpYear == -1 && this.currYear != -1) {
	            this.tmpYear = this.currYear;
	        }
	        $('#target_SY' + this.currYear).removeClass('curr');
	        this.currYear = value;
	        $('#target_SY' + this.currYear).addClass('curr');
	    },
	    setMonth : function(value) {
	        if (this.tmpMonth == -1 && this.currMonth != -1) {
	            this.tmpMonth = this.currMonth;
	        }
	        $('#target_SM' + this.currMonth).removeClass('curr');
	        this.currMonth = value;
	        $('#target_SM' + this.currMonth).addClass('curr');
	    },
	    showYearContent : function(beginYear, endYear) {
	        if (!beginYear) {
	            if (!endYear) {
	                endYear = this.currYear + 1;
	            }
	            this.endYear = endYear;
	            if (this.endYear > this.maxYear) {
	                this.endYear = this.maxYear;
	            }
	            this.beginYear = this.endYear - 3;
	            if (this.beginYear < this.minYear) {
	                this.beginYear = this.minYear;
	                this.endYear = this.beginYear + 3;
	            }
	        }
	        if (!endYear) {
	            if (!beginYear) {
	                beginYear = this.currYear - 2;
	            }
	            this.beginYear = beginYear;
	            if (this.beginYear < this.minYear) {
	                this.beginYear = this.minYear;
	            }
	            this.endYear = this.beginYear + 3;
	            if (this.endYear > this.maxYear) {
	                this.endYear = this.maxYear;
	                this.beginYear = this.endYear - 3;
	            }
	        }

	        var s = '';
	        for (var i = this.beginYear; i <= this.endYear; i++) {
	            s += '<span id="target_SY' + i
	                    + '" class="year" onclick="targetDateSelection.setYear(' + i
	                    + ')">' + i + '</span>';
	        }
	        document.getElementById('target_yearListContent').innerHTML = s;
	        $('#target_SY' + this.currYear).addClass('curr');
	    },
	    showMonthContent : function() {
	        var s = '';
	        for (var i = 0; i < 12; i++) {
	            s += '<span id="target_SM' + i
	                    + '" class="month" onclick="targetDateSelection.setMonth(' + i
	                    + ')">' + (i + 1).toString() + '</span>';
	        }
	        document.getElementById('target_monthListContent').innerHTML = s;
	        $('#target_SM' + this.currMonth).addClass('curr');
	    },
	    //根据节假日去相关的月份
		 goHoliday : function(N){
			this.setMonth(N);
			this.commit();
		}
	};

/*初始化日期*/
function targetArrangementInit() {
    targetInitRiliIndex();
    targetClear();
    $("#target_nian").html(tY);
    $("#target_yue").html(tM + 1);

    /*年份递减*/
    $("#target_nianjian").unbind("click").click(function() {
        targetDateSelection.goPrevYear();
    });
    /*年份递加*/
    $("#target_nianjia").unbind("click").click(function() {
        targetDateSelection.goNextYear();
    });

    /*月份递减*/
    $("#target_yuejian").unbind("click").click(function() {
        targetDateSelection.goPrevMonth();
//        $('.holiday').show();
    });
    
    /*月份递加*/
    $("#target_yuejia").unbind("click").click(function() {
        targetDateSelection.goNextMonth();
    });
    targetDrawCld(tY, tM);
}

//初始当前日期
function targetInitRiliIndex() {
    var dateObj = new Date();
    targetGlobal.currYear = dateObj.getFullYear();
    targetGlobal.currMonth = dateObj.getMonth();
    targetDateSelection.init();
}

//当前对应部门或者员工
function setArrangementData(number,code){
	pNumber = number;
	deptcode = code;
	targetArrangementInit();
}


