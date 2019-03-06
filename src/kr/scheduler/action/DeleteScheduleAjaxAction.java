package kr.scheduler.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.schduler.dao.SchedulerDao;

public class DeleteScheduleAjaxAction implements Action {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		int sc_idx = Integer.parseInt(request.getParameter("sc_idx"));
		
		SchedulerDao schedule = SchedulerDao.getInstance();
		schedule.deleteSchedule(sc_idx);
		
		Map<String, Object> mapAjax =
				new HashMap<String, Object>();
		mapAjax.put("result", "success");

		// JSON 데이터로 변환
		ObjectMapper mapper = new ObjectMapper();
		String jsonData =
				mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("jsonData", jsonData);
		
		// JSP 경로 반환
		return "/WEB-INF/views/common/ajaxView.jsp";
	}

}
