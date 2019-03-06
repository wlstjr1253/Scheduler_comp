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
		
		// DTO ����
		UserDto user = new UserDto();
		// DTO�� ���۵� �����͸� ����
		user.setName(request.getParameter("name"));
		user.setId(request.getParameter("id"));
		user.setPwd(request.getParameter("pwd"));
		user.setPhone(request.getParameter("phone"));
		user.setEmail(request.getParameter("email"));
		
		// DAO ȣ��
		UserDao dao = UserDao.getInstance();
		// ȸ�������� ���� insertMember ȣ��
		dao.insertUser(user);
				
		
		// JSP ��� ��ȯ
		return "/WEB-INF/views/main/joinComp.jsp";
	}

}
