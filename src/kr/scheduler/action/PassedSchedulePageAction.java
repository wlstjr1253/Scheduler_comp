package kr.scheduler.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.schduler.dao.SchedulerDao;
import kr.scheduler.domain.SchedulerDto;
import kr.util.AuthUtil;
import kr.util.PagingUtil;

public class PassedSchedulePageAction implements Action {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		//==========�α��� üũ ����=========//
		if(!AuthUtil.isLogin(request)) {
			return "redirect:/main/login.do";
		}
		//=========�α��� üũ ��===========//

		String keyword = request.getParameter("keyword");

		if (keyword == null) keyword = "";

		String pageNum = request.getParameter("pageNum");
		if (pageNum == null) pageNum = "1";

		int rowCount = 3;  // �� �������� �Խù� ��
		int pageCount = 3;  // �� ȭ���� ���������̼� ��
		int currentPage = Integer.parseInt(pageNum);

		// DAO ȣ�� 
		SchedulerDao dao = SchedulerDao.getInstance();
		int count = dao.getPassedScheduleCount(keyword, AuthUtil.getUser_id(request));
		int sharedCount = dao.getSharedPassedScheduleCount(keyword, AuthUtil.getUser_id(request));
		int totalCount = count + sharedCount;

		// ����¡ ó��
		PagingUtil page = new PagingUtil(null, keyword,currentPage,totalCount,
				rowCount,pageCount,"passedSchedule.do");

		List<SchedulerDto> list = null; // ���� ����
		if (totalCount > 0) {
			list = dao.getPassedScheduleListCom(
					page.getStartCount(), 
					page.getEndCount(), 
					keyword, 
					AuthUtil.getUser_id(request));
		}
		
		
		request.setAttribute("count", totalCount);
		request.setAttribute("list", list); // ���� ����
		request.setAttribute("pagingHtml", page.getPagingHtml());
		request.setAttribute("user", AuthUtil.getUser_id(request));

		// JSP ��� ��ȯ
		return "/WEB-INF/views/sc/passedSchedule.jsp";
	}

}