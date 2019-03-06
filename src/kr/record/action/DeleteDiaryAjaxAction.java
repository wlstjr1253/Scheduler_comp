package kr.record.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.record.dao.DiaryDao;

public class DeleteDiaryAjaxAction implements Action {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		DiaryDao dao = DiaryDao.getInstance();
		int result = dao.deleteDiary(Integer.parseInt(request.getParameter("d_idx")));
		
		Map<String, String> mapAjax = new HashMap<String, String>();
		if (result > 0) {
			mapAjax.put("result", "success");
		} else {
			mapAjax.put("result", "fail");
		}
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("jsonData", jsonData);
		
		// JSP 경로 반환
		return "/WEB-INF/views/common/ajaxView.jsp";
	}

}
