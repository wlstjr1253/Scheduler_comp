package kr.main.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.user.dao.UserDao;
import kr.user.domain.UserDto;

public class JoinDataAction implements Action {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		// DTO 생성
		UserDto user = new UserDto();
		// DTO에 전송된 데이터를 저장
		user.setName(request.getParameter("name"));
		user.setId(request.getParameter("id"));
		user.setPwd(request.getParameter("pwd"));
		user.setPhone(request.getParameter("phone"));
		user.setEmail(request.getParameter("email"));
		
		// DAO 호출
		UserDao dao = UserDao.getInstance();
		// 회원가입을 위한 insertMember 호출
		dao.insertUser(user);
				
		
		// JSP 경로 반환
		return "/WEB-INF/views/main/joinComp.jsp";
	}

}
