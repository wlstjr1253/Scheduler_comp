package kr.main.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.user.dao.UserDao;
import kr.user.domain.UserDto;

public class CheckEmailAjaxAction implements Action {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String email = request.getParameter("email");
		
		UserDao dao = UserDao.getInstance();
		UserDto user = dao.getUserByMail(email);
		
		Map<String, String> mapAjax = new HashMap<String, String>();
		if (user == null) {
			mapAjax.put("result", "emailNotFound");
		} else {
			mapAjax.put("result", "emailDuplicated");
		}
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = 
				mapper.writeValueAsString(mapAjax);

		request.setAttribute("jsonData", jsonData);
		
		
		// JSP 경로 반환
		return "/WEB-INF/views/common/ajaxView.jsp";
	}

}
