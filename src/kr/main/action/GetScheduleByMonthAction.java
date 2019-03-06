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

public class GetScheduleByMonthAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		int year = Integer.parseInt(request.getParameter("year"));
		int month = Integer.parseInt(request.getParameter("month"));
		
		// 등록된 일정 조회
		SchedulerDao dao = SchedulerDao.getInstance();
		List<SchedulerDto> scList = null;
		scList = dao.getScheduleByMonth(AuthUtil.getUser_id(request),
				year, month);
		
		// 공유된 월별 일정 조회
		SchedulerDao dao2 = SchedulerDao.getInstance();
		List<SchedulerDto> sharedList = null;
		sharedList = dao2.getSharedScheduleByMonth(AuthUtil.getUser_id(request), year, month);
		
		List<SchedulerDto> totalList = new ArrayList<SchedulerDto>();
		totalList.addAll(scList);
		totalList.addAll(sharedList);
		
		Collections.sort(totalList);
		
		Map<String, Object>mapAjax = new HashMap<String, Object>();
		mapAjax.put("scList", totalList);
		// mapAjax.put("sharedList", sharedList);
		mapAjax.put("user", AuthUtil.getUser_id(request));
			
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("jsonData", jsonData); // 등록된 일정
		
		// JSP 경로 반환
		return "/WEB-INF/views/common/ajaxView.jsp";
	}

}
