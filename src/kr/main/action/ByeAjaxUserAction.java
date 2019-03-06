package kr.main.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.user.dao.UserDao;
import kr.user.domain.UserDto;
import kr.util.AuthUtil;

public class ByeAjaxUserAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//========로그인 체크 시작==========//
		if(!AuthUtil.isLogin(request)) {
			return "redirect:/main/login.do";
		}
		//=======로그인 체크 끝============//
		String id = request.getParameter("id");
		
		UserDao dao = UserDao.getInstance();
		UserDto user = dao.getUser(id);
		
		String result = "";
		HttpSession session = request.getSession();		
		
		if (user.isCheckedPasswd(request.getParameter("pwd"))) {
			result = "bye";
			dao.removeUser(id);
			session.invalidate();
		} else {
			result = "stay";
		}
		
		Map<String, String> mapAjax = new HashMap<String, String>();
		if (result == "bye") {
			mapAjax.put("result", result);
		} else {
			mapAjax.put("result", result);
		}
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("jsonData", jsonData);
		
				
		// JSP 경로 반환
		return "/WEB-INF/views/common/ajaxView.jsp";
	}

}
