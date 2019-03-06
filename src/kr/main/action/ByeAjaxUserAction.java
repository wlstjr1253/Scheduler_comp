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
		//========�α��� üũ ����==========//
		if(!AuthUtil.isLogin(request)) {
			return "redirect:/main/login.do";
		}
		//=======�α��� üũ ��============//
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
		
				
		// JSP ��� ��ȯ
		return "/WEB-INF/views/common/ajaxView.jsp";
	}

}
