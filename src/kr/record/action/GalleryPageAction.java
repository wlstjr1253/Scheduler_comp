package kr.record.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.record.dao.GalleryDao;
import kr.record.domain.GalleryDto;
import kr.scheduler.domain.SchedulerDto;
import kr.util.AuthUtil;
import kr.util.PagingUtil_paging1;

public class GalleryPageAction implements Action {

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
		
		int rowCount = 12;  // 한 페이지의 게시물 수
		int pageCount = 3;  // 한 화면의 페이지네이션 수
		int currentPage = Integer.parseInt(pageNum);
		
		// DAO 호출
		GalleryDao dao = GalleryDao.getInstance();
		int count = dao.getGalleryCount(keyword, AuthUtil.getUser_id(request));
		
		
		// 페이징 처리
		PagingUtil_paging1 page = new PagingUtil_paging1(null, keyword,currentPage,count,
				rowCount,pageCount,"gallery.do");
		
		List<GalleryDto> galleryList = null;
		
		if (count > 0) {
			galleryList = dao.getGalleryList(
					page.getStartCount(),
					page.getEndCount(),
					keyword,
					AuthUtil.getUser_id(request));
		}
		
		request.setAttribute("count", count);
		request.setAttribute("galleryList", galleryList);
		request.setAttribute("pagingHtml", page.getPagingHtml());

		// JSP 경로 반환
		return "/WEB-INF/views/record/gallery.jsp";
	}

}
