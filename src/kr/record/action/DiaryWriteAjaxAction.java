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

public class DiaryWriteAjaxAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		DiaryDto diary = new DiaryDto();
		diary.setD_title(request.getParameter("d_title"));
		diary.setD_content(request.getParameter("d_content"));
		diary.setId(AuthUtil.getUser_id(request));
		
		
		DiaryDao dao = DiaryDao.getInstance();
		Map<String, String> mapAjax =
				new HashMap<String, String>();
		int count = dao.getDiaryCountByDay(AuthUtil.getUser_id(request));
		if (count > 0) {
			mapAjax.put("message", "�̹� ��ϵ� �ϱⰡ �ֽ��ϴ�.");
			
			ObjectMapper mapper = new ObjectMapper();
			String jsonData = mapper.writeValueAsString(mapAjax);
			
			request.setAttribute("jsonData", jsonData);
			
			return "/WEB-INF/views/common/ajaxView.jsp";
		} else {
			dao.insertDiary(diary);
			mapAjax.put("message", "�ϱⰡ ��� �Ǿ����ϴ�.");
				
			ObjectMapper mapper = new ObjectMapper();
			String jsonData = mapper.writeValueAsString(mapAjax);
			
			request.setAttribute("jsonData", jsonData);
			
			// JSP ��� ��ȯ
			return "/WEB-INF/views/common/ajaxView.jsp";
		}
	}

}
