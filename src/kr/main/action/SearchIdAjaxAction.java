package kr.main.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.user.dao.UserDao;
import kr.util.AuthUtil;

public class SearchIdAjaxAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String userName = request.getParameter("userName");
		String email = request.getParameter("email");

		UserDao dao = UserDao.getInstance();
		String userId = dao.srchId(userName, email);

		Map<String, String> mapAjax = new HashMap<String, String>();
		if (userId == "") {
			mapAjax.put("result", "fail");
		} else {
			mapAjax.put("result", userId);
		}

		// JSON 데이터로 변환
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(mapAjax);

		request.setAttribute("jsonData", jsonData);

		// JSP 경로 반환
		return "/WEB-INF/views/common/ajaxView.jsp";
	}

}
