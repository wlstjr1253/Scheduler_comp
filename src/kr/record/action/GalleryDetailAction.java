package kr.record.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.record.dao.GalleryDao;
import kr.record.domain.GalleryDto;

public class GalleryDetailAction implements Action {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		GalleryDao dao = GalleryDao.getInstance();
		GalleryDto galleryItem = dao.getGalleryItem(
				Integer.parseInt(request.getParameter("g_idx")));
		
		request.setAttribute("galleryItem", galleryItem);
		
		// JSP 경로 반환
		return "/WEB-INF/views/record/galleryWrite.jsp";
	}

}
