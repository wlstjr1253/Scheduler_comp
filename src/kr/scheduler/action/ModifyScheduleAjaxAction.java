package kr.scheduler.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import kr.alarm.dao.AlarmDao;
import kr.controller.Action;
import kr.schduler.dao.SchedulerDao;
import kr.scheduler.domain.SchedulerDto;

public class ModifyScheduleAjaxAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		int sc_idx = Integer.parseInt(request.getParameter("sc_idx"));
		String startDate = request.getParameter("startDate").replace("-", "");
		String startTime = request.getParameter("startTime").replace(" ", "")+":00";
		String scStart = startDate + " " +startTime;
		
		String endDate = request.getParameter("endDate").replace("-", "");
		String endTime = request.getParameter("endTime").replace(" ", "")+":00";
		String scEnd = endDate + " " +endTime;
		
		int al_timer = Integer.parseInt(request.getParameter("alarmTime"));
				
		SchedulerDao dao = SchedulerDao.getInstance();
		SchedulerDto schedule = new SchedulerDto();
		schedule.setSc_idx(sc_idx);
		schedule.setSc_title(request.getParameter("sc_title"));
		schedule.setSc_place(request.getParameter("sc_place"));
		schedule.setSc_start(scStart);
		schedule.setSc_end(scEnd);
		schedule.setSc_all_day(request.getParameter("sc_all_day"));
		schedule.setSc_content(request.getParameter("sc_content"));
		
		dao.modifySchedule(schedule);
		
		AlarmDao dao2 = AlarmDao.getInstance();
		dao2.modifyAlarm(al_timer, sc_idx);
		
		Map<String, String> mapAjax = new HashMap<String, String>();
		mapAjax.put("message", "일정이 수정 되었습니다.");
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("jsonData", jsonData);
		
		// JSP 경로 반환
		return "/WEB-INF/views/common/ajaxView.jsp";
	}

}
