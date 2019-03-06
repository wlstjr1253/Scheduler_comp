package kr.scheduler.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.schduler.dao.InvitedUserDao;

public class InviteMemberAjaxAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String memberId = request.getParameter("memberId");
		int scIdx = Integer.parseInt(request.getParameter("scIdx"));

		InvitedUserDao dao = InvitedUserDao.getInstacne();
		int si_idx = dao.moreInviteMember(memberId, scIdx);
		
		Map<String, Integer> mapAjax = new HashMap<String, Integer>();
		mapAjax.put("si_idx", si_idx);

		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(mapAjax); 
				
		request.setAttribute("jsonData", jsonData);
		
		// JSP 경로 반환
		return "/WEB-INF/views/common/ajaxView.jsp";
	}

}
