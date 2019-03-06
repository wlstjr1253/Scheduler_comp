package kr.main.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.user.dao.UserDao;
import kr.user.domain.UserDto;

public class CheckIdAjaxAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");

		UserDao dao = UserDao.getInstance();
		UserDto user = dao.getUser(id);
		

		Map<String, String> mapAjax = new HashMap<String, String>();
		if (user == null) {
			mapAjax.put("result", "idNotFound");
		} else {
			mapAjax.put("result", "idDuplicated");
		}

		ObjectMapper mapper = new ObjectMapper();
		String jsonData = 
				mapper.writeValueAsString(mapAjax);

		request.setAttribute("jsonData", jsonData);

		// JSP 경로 반환
		return "/WEB-INF/views/common/ajaxView.jsp";
	}

}
