package kr.main.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.user.dao.UserDao;
import kr.user.domain.UserDto;

public class LoginDataAction implements Action {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String id = request.getParameter("id");
		String pwd = request.getParameter("pwd");
		
		UserDao dao = UserDao.getInstance();
		UserDto user = dao.getUser(id);
		boolean check = false;
		
		if (user != null) {
			check = user.isCheckedPasswd(pwd);
		}
		
		if (check) { // 인증 성공
			// 로그인 처리
			HttpSession session = request.getSession();
			session.setAttribute("user_id", id);
			session.setAttribute("user_auth", user.getAuth());
			session.setAttribute("user_name", user.getName());
			session.setAttribute("user_email", user.getEmail());
		}
		
		request.setAttribute("check", check);
		
		// JSP 경로 반환
		return "/WEB-INF/views/main/loginComp.jsp";
	}

}
