package kr.record.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.util.AuthUtil;

public class GalleryWriteAction implements Action {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//==========�α��� üũ ����=========//
		if(!AuthUtil.isLogin(request)) {
			return "redirect:/main/login.do";
		}
		//=========�α��� üũ ��===========//

		// JSP ��� ��ȯ
		return "/WEB-INF/views/record/galleryWrite.jsp";
	}

}
