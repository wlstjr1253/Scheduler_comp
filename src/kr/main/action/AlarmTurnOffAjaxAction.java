package kr.main.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import kr.alarm.dao.AlarmDao;
import kr.controller.Action;

public class AlarmTurnOffAjaxAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		AlarmDao dao = AlarmDao.getInstance();
		int count = dao.offAlarm(Integer.parseInt(request.getParameter("al_idx")));
		
		Map<String, Object> mapAjax = new HashMap<String, Object>();
		if (count > 0) {
			mapAjax.put("result", "success");
		} else {
			mapAjax.put("result", "fail");
		}
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(mapAjax);
	
		request.setAttribute("jsonData", jsonData);
		
		// JSP 경로 반환
		return "/WEB-INF/views/common/ajaxView.jsp";
	}

}
