package kr.main.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.schduler.dao.SchedulerDao;
import kr.scheduler.domain.SchedulerDto;
import kr.util.AuthUtil;

public class GetScheduleByDayAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		int year = Integer.parseInt(request.getParameter("year"));
		int month = Integer.parseInt(request.getParameter("month"));
		int day = Integer.parseInt(request.getParameter("day"));
		
		SchedulerDao dao = SchedulerDao.getInstance();
		List<SchedulerDto> scList = dao.getScheduleByDay(AuthUtil.getUser_id(request), 
				year, month, day);
		
		SchedulerDao dao2 = SchedulerDao.getInstance();
		List<SchedulerDto> sharedList = dao2.getSharedScheduleByDay(AuthUtil.getUser_id(request), 
				year, month, day);
		
		List<SchedulerDto> totalList = new ArrayList<SchedulerDto>();
		totalList.addAll(scList);
		totalList.addAll(sharedList);

		
		Collections.sort(totalList);
		
		Map<String, Object> mapAjax =
				new HashMap<String, Object>();
		mapAjax.put("schedules", totalList);
		// mapAjax.put("sharedList", list2);
		mapAjax.put("user", AuthUtil.getUser_id(request));

		
		// JSON 데이터로 변환
		ObjectMapper mapper = new ObjectMapper();
		String jsonData =
				mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("jsonData", jsonData);
		
		// JSP 경로 반환
		return "/WEB-INF/views/common/ajaxView.jsp";
	}

}
