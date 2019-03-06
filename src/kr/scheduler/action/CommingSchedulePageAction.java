package kr.scheduler.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.schduler.dao.SchedulerDao;
import kr.scheduler.domain.SchedulerDto;
import kr.util.AuthUtil;
import kr.util.PagingUtil_paging1;

public class CommingSchedulePageAction implements Action {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		//==========�α��� üũ ����=========//
		if(!AuthUtil.isLogin(request)) {
			return "redirect:/member/loginForm.do";
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
		int count = dao.getScheduleCount(keyword, AuthUtil.getUser_id(request));
		int sharedCount = dao.getSharedScheduleCount(keyword, AuthUtil.getUser_id(request));
		int totalCount = count + sharedCount;
		
		// ����¡ ó��
		PagingUtil_paging1 page = new PagingUtil_paging1(null, keyword,
				currentPage,
				totalCount,
				rowCount,pageCount,"commingSchedule.do");
		
		List<SchedulerDto> list = null; // 2�� �� ���� ���� ����Ʈ
		List<SchedulerDto> list2 = null; // ����
		List<SchedulerDto> list3 = null; // ����
		
		if (count > 0) {
			list = dao.getScheduleListCom(
					page.getStartCount(), 
					page.getEndCount(), 
					keyword, 
					AuthUtil.getUser_id(request), null);
			
			list2 = dao.getScheduleListCom(
					page.getStartCount(), 
					page.getEndCount(), 
					keyword, 
					AuthUtil.getUser_id(request), "today");
			
			list3 = dao.getScheduleListCom(
					page.getStartCount(), 
					page.getEndCount(), 
					keyword, 
					AuthUtil.getUser_id(request), "tomorrow");
		}
		
		
		request.setAttribute("count", totalCount);
		request.setAttribute("list", list);   // �ٰ��� ���� (2����)
		request.setAttribute("list2", list2); // ����
		request.setAttribute("list3", list3); // ����
		request.setAttribute("user", AuthUtil.getUser_id(request));

		request.setAttribute("pagingHtml", page.getPagingHtml()); // ����¡
		
		// JSP ��� ��ȯ
		return "/WEB-INF/views/sc/commingSchedule.jsp";
	}

}
