package kr.scheduler.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.schduler.dao.InvitedUserDao;

public class LeaveMemberAjaxAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		int si_idx = Integer.parseInt(request.getParameter("si_idx"));
		
		InvitedUserDao dao = InvitedUserDao.getInstacne();
		int count = dao.leaveMember(si_idx);
		
		Map<String, String> mapAjax = new HashMap<String, String>();
		if (count > 0) {
			mapAjax.put("result", "success");
		} else {
			mapAjax.put("result", "false");
		}
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(mapAjax); 
				
		request.setAttribute("jsonData", jsonData);
		
		// JSP 경로 반환
		return "/WEB-INF/views/common/ajaxView.jsp";
	}

}
