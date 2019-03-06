package kr.favorite.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.favorite.dao.FavoriteDao;

public class AddFavDiaryAjaxAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		int d_idx = Integer.parseInt(request.getParameter("d_idx"));
		
		FavoriteDao dao = FavoriteDao.getInstance();
		Map<String, String> mapAjax = new HashMap<String, String>();
		
		String chkFav = dao.getFavDiary(d_idx);
		if ("Y".equals(chkFav)) {
			mapAjax.put("result", "fail");
		} else if ("N".equals(chkFav)) {
			dao.addFavDiary(d_idx);
			mapAjax.put("result", "success");
		}
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("jsonData", jsonData);
		
		// JSP 경로 반환
		return "/WEB-INF/views/common/ajaxView.jsp";
	}

}
