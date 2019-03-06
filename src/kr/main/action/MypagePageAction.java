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

		//========로그인 체크 시작==========//
		if(!AuthUtil.isLogin(request)) {
			return "redirect:/main/login.do";
		}
		//=======로그인 체크 끝============//
		
		UserDao dao = UserDao.getInstance();
		HttpSession session = request.getSession();
		UserDto user = dao.getUser((String)session.getAttribute("user_id"));
		
		request.setAttribute("user", user);

		// JSP 경로 반환
		return "/WEB-INF/views/main/mypage.jsp";
	}

}
