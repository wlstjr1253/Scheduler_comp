package kr.favorite.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.favorite.dao.FavoriteDao;

public class AddFavScheduleAjaxAction implements Action {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		int sc_idx = Integer.parseInt(request.getParameter("sc_idx"));
		
		FavoriteDao dao = FavoriteDao.getInstance();
		dao.addFavSchedule(sc_idx);
				
		Map<String, String> mapAjax = new HashMap<String, String>();
		mapAjax.put("result", "success");
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("jsonData", jsonData);
		
		// JSP 경로 반환
		return "/WEB-INF/views/common/ajaxView.jsp";
	}

}
