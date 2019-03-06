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

		//==========로그인 체크 시작=========//
		if(!AuthUtil.isLogin(request)) {
			return "redirect:/main/login.do";
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
		int count = dao.getPassedScheduleCount(keyword, AuthUtil.getUser_id(request));
		int sharedCount = dao.getSharedPassedScheduleCount(keyword, AuthUtil.getUser_id(request));
		int totalCount = count + sharedCount;

		// 페이징 처리
		PagingUtil page = new PagingUtil(null, keyword,currentPage,totalCount,
				rowCount,pageCount,"passedSchedule.do");

		List<SchedulerDto> list = null; // 지난 일정
		if (totalCount > 0) {
			list = dao.getPassedScheduleListCom(
					page.getStartCount(), 
					page.getEndCount(), 
					keyword, 
					AuthUtil.getUser_id(request));
		}
		
		
		request.setAttribute("count", totalCount);
		request.setAttribute("list", list); // 지난 일정
		request.setAttribute("pagingHtml", page.getPagingHtml());
		request.setAttribute("user", AuthUtil.getUser_id(request));

		// JSP 경로 반환
		return "/WEB-INF/views/sc/passedSchedule.jsp";
	}

}
