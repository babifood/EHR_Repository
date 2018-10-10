package com.babifood.control;

import com.babifood.entity.PunchTimeEntity;
import com.babifood.service.PunchTimeService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({ "punchTime" })
public class PunchTimeController {

	@Autowired
	private PunchTimeService punchTimeService;

	@RequestMapping({ "page" })
	@ResponseBody
	public Map<String, Object> findPagePunchTimeInfo(Integer page, Integer rows, String pNumber, String pName) {
		return punchTimeService.findPagePunchTimeInfo(page, rows, pNumber, pName);
	}

	@RequestMapping({ "save" })
	@ResponseBody
	public Map<String, Object> savePagePunchTimeInfo(PunchTimeEntity punchTime) {
		return punchTimeService.savePagePunchTimeInfo(punchTime);
	}

	@RequestMapping({ "remove" })
	@ResponseBody
	public Map<String, Object> removePagePunchTimeInfo(Integer id) {
		return punchTimeService.removePagePunchTimeInfo(id);
	}
	
	@RequestMapping({ "sync" })
	@ResponseBody
	public Map<String, Object> syncPunchTimeInfo() {
		return punchTimeService.syncPunchTimeInfo();
	}
}