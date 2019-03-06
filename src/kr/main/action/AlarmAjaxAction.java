package kr.main.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import kr.alarm.dao.AlarmDao;
import kr.alarm.domain.AlarmDto;
import kr.controller.Action;
import kr.util.AuthUtil;

public class AlarmAjaxAction implements Action {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		AlarmDao dao = AlarmDao.getInstance();
		List<AlarmDto> al_list = dao.getAlarm(AuthUtil.getUser_id(request));
				
		Map<String, Object> mapAjax = new HashMap<String, Object>();
		mapAjax.put("list", al_list);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(mapAjax);
	
		request.setAttribute("jsonData", jsonData);
		
		// JSP 경로 반환
		return "/WEB-INF/views/common/ajaxView.jsp";
	}

}
