package kr.record.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.record.dao.DiaryDao;
import kr.record.domain.DiaryDto;
import kr.util.AuthUtil;

public class ReadDiaryAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//==========�α��� üũ ����=========//
		if(!AuthUtil.isLogin(request)) {
			return "redirect:/main/login.do";
		}
		//=========�α��� üũ ��===========//
		
		int idx = Integer.parseInt(request.getParameter("d_idx"));
		
		DiaryDao dao = DiaryDao.getInstance();
		DiaryDto diary = dao.getDiaryItem(idx);
		
		request.setAttribute("diary", diary);
		
		// JSP ��� ��ȯ
		return "/WEB-INF/views/record/diary.jsp";
	}

}
