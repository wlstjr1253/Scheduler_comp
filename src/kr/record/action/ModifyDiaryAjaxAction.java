package kr.record.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.record.dao.DiaryDao;
import kr.record.domain.DiaryDto;
import kr.util.AuthUtil;

public class ModifyDiaryAjaxAction implements Action {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		DiaryDao dao = DiaryDao.getInstance();
		DiaryDto diary = new DiaryDto();
		diary.setD_idx(Integer.parseInt(request.getParameter("d_idx")));
		diary.setD_title(request.getParameter("d_title"));
		diary.setD_content(request.getParameter("d_content"));
		diary.setId(AuthUtil.getUser_id(request));
		int count = dao.updateDiaryItem(diary);
		
		Map<String, String> mapAjax = new HashMap<String, String>();
		if (count > 0) {
			mapAjax.put("update", "success");
		} else {
			mapAjax.put("update", "fail");
		}
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("jsonData", jsonData);
		
		// JSP 경로 반환
		return "/WEB-INF/views/common/ajaxView.jsp";
	}

}
