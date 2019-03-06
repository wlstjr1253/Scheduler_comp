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
		//==========�α��� üũ ����=========//
		if(!AuthUtil.isLogin(request)) {
			return "redirect:/main/login.do";
		}
		//=========�α��� üũ ��===========//
		
		String keyword = request.getParameter("keyword");
		
		if (keyword == null) keyword = "";
		
		String pageNum = request.getParameter("pageNum");
		if (pageNum == null) pageNum = "1";
		
		int rowCount = 12;  // �� �������� �Խù� ��
		int pageCount = 3;  // �� ȭ���� ���������̼� ��
		int currentPage = Integer.parseInt(pageNum);
		
		// DAO ȣ��
		GalleryDao dao = GalleryDao.getInstance();
		int count = dao.getGalleryCount(keyword, AuthUtil.getUser_id(request));
		
		
		// ����¡ ó��
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

		// JSP ��� ��ȯ
		return "/WEB-INF/views/record/gallery.jsp";
	}

}
