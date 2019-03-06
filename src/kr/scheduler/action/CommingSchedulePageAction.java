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
		
		//==========로그인 체크 시작=========//
		if(!AuthUtil.isLogin(request)) {
			return "redirect:/member/loginForm.do";
		}
		//=========로그인 체크 끝===========//
		
		String keyword = request.getParameter("keyword");
		
		if (keyword == null) keyword = "";
		
		String pageNum = request.getParameter("pageNum");
		if (pageNum == null) pageNum = "1";
		
		int rowCount = 3;  // 한 페이지의 게시물 수
		int pageCount = 3;  // 한 화면의 페이지네이션 수
		int currentPage = Integer.parseInt(pageNum);
		
		// DAO 호출 
		SchedulerDao dao = SchedulerDao.getInstance();
		int count = dao.getScheduleCount(keyword, AuthUtil.getUser_id(request));
		int sharedCount = dao.getSharedScheduleCount(keyword, AuthUtil.getUser_id(request));
		int totalCount = count + sharedCount;
		
		// 페이징 처리
		PagingUtil_paging1 page = new PagingUtil_paging1(null, keyword,
				currentPage,
				totalCount,
				rowCount,pageCount,"commingSchedule.do");
		
		List<SchedulerDto> list = null; // 2일 후 부터 일정 리스트
		List<SchedulerDto> list2 = null; // 오늘
		List<SchedulerDto> list3 = null; // 내일
		
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
		request.setAttribute("list", list);   // 다가올 일정 (2일후)
		request.setAttribute("list2", list2); // 오늘
		request.setAttribute("list3", list3); // 내일
		request.setAttribute("user", AuthUtil.getUser_id(request));

		request.setAttribute("pagingHtml", page.getPagingHtml()); // 페이징
		
		// JSP 경로 반환
		return "/WEB-INF/views/sc/commingSchedule.jsp";
	}

}
