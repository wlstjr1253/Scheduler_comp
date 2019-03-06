package kr.record.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.record.dao.GalleryDao;

public class GalleryDeleteAjaxAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		GalleryDao dao = GalleryDao.getInstance();
		dao.deleteGallery(Integer.parseInt(request.getParameter("g_idx")));
		
		Map<String, String> mapAjax =
				new HashMap<String, String>();
		mapAjax.put("message", "삭제 되었습니다.");
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("jsonData", jsonData);
		
		// JSP 경로 반환
		return "/WEB-INF/views/common/ajaxView.jsp";
	}

}
