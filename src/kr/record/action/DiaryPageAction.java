package kr.record.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.util.AuthUtil;

public class DiaryPageAction implements Action {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//==========로그인 체크 시작=========//
		if(!AuthUtil.isLogin(request)) {
			return "redirect:/main/login.do";
		}
		//=========로그인 체크 끝===========//

		// JSP 경로 반환
		return "/WEB-INF/views/record/diary.jsp";
	}

}
