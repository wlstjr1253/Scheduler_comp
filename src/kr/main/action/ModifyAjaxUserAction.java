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

public class ModifyAjaxUserAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//========�α��� üũ ����==========//
		if(!AuthUtil.isLogin(request)) {
			return "redirect:/main/login.do";
		}
		//=======�α��� üũ ��============//
		
		HttpSession session = request.getSession();
		UserDto user = new UserDto();
		user.setId((String)session.getAttribute("user_id"));
		user.setName(request.getParameter("name"));
		user.setPwd(request.getParameter("pwd"));
		user.setPhone(request.getParameter("phone"));
		user.setEmail(request.getParameter("email"));
		
		UserDao dao = UserDao.getInstance();
		boolean result = dao.updateUser(user);
		
		Map<String, String> mapAjax = new HashMap<String, String>();
		if (result == false) {
			mapAjax.put("result", "modifyFail");
		} else {
			mapAjax.put("result", "modifySuccess");
		}

		
		// JSON �����ͷ� ��ȯ
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("jsonData", jsonData);

		// JSP ��� ��ȯ
		return "/WEB-INF/views/common/ajaxView.jsp";
	}

}
