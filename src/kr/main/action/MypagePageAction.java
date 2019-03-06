package kr.main.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.user.dao.UserDao;
import kr.user.domain.UserDto;
import kr.util.AuthUtil;

public class MypagePageAction implements Action {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		//========�α��� üũ ����==========//
		if(!AuthUtil.isLogin(request)) {
			return "redirect:/main/login.do";
		}
		//=======�α��� üũ ��============//
		
		UserDao dao = UserDao.getInstance();
		HttpSession session = request.getSession();
		UserDto user = dao.getUser((String)session.getAttribute("user_id"));
		
		request.setAttribute("user", user);

		// JSP ��� ��ȯ
		return "/WEB-INF/views/main/mypage.jsp";
	}

}
