package kr.main.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;

public class LogoutAction implements Action {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		// �α׾ƿ�
		HttpSession session = request.getSession();
		session.invalidate();

		// JSP ��� ��ȯ
		return "redirect:/main/login.do";
	}

}
