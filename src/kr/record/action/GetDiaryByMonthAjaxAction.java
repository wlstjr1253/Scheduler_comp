package kr.record.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.record.dao.DiaryDao;
import kr.record.domain.DiaryDto;
import kr.util.AuthUtil;

public class GetDiaryByMonthAjaxAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		int year = Integer.parseInt(request.getParameter("year"));
		int month = Integer.parseInt(request.getParameter("month"));
		
		DiaryDao dao = DiaryDao.getInstance();
		List<DiaryDto> dList = null;
		dList = dao.getDiaryByMonth(AuthUtil.getUser_id(request), 
				year, month);
		
		Map<String, Object> mapAjax = 
				new HashMap<String, Object>();
		
		mapAjax.put("dList", dList);
		
		// JSON 데이터로 변환
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = 
				mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("jsonData", jsonData);		
		
		// JSP 경로 반환
		return "/WEB-INF/views/common/ajaxView.jsp";
	}

}
