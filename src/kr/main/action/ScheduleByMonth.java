package kr.main.action;

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

public class ScheduleByMonth implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		int year = Integer.parseInt(request.getParameter("year"));
		int month = Integer.parseInt(request.getParameter("month"));

		// 등록된 일정 조회
		SchedulerDao dao = SchedulerDao.getInstance();
		List<SchedulerDto> scList = null;
		scList = dao.getScheduleByMonth(AuthUtil.getUser_id(request),
				year, month);
		
		Map<String, Object>mapAjax = new HashMap<String, Object>();
		mapAjax.put("scList", scList);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("jsonData", jsonData); // 등록된 일정
		
		// JSP 경로 반환
		return "/WEB-INF/views/common/ajaxView.jsp";
	}

}
