package kr.scheduler.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.schduler.dao.SchedulerDao;
import kr.scheduler.domain.SchedulerDto;
import kr.util.AuthUtil;
import kr.util.PagingUtil;

public class AddScheduleAjaxAction implements Action {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String keyword = request.getParameter("keyword");
		
		if (keyword == null) keyword = "";
		
		String pageNum = request.getParameter("pageNum");
		if (pageNum == null) pageNum = "1";
		
		int rowCount = 3;  // �� �������� �Խù� ��
		int pageCount = 5;  // �� ȭ���� ���������̼� ��
		int currentPage = Integer.parseInt(pageNum);
		
		// DAO ȣ�� 
		SchedulerDao dao = SchedulerDao.getInstance();
		int count = dao.getScheduleCount(keyword, AuthUtil.getUser_id(request));

		// ����¡ ó��
		PagingUtil page = new PagingUtil(null, keyword,currentPage,count,
				rowCount,pageCount,"commingSchedule.do");

		List<SchedulerDto> list = null;
		if (count > 0) {
			list = dao.getScheduleListCom(
					page.getStartCount(), 
					page.getEndCount(), 
					keyword, 
					AuthUtil.getUser_id(request), null);
		}
		
		Map<String, Object> mapAjax =
				new HashMap<String, Object>();
		mapAjax.put("count", count);
		mapAjax.put("rowCount", rowCount);
		mapAjax.put("list", list);
		
		// JSON �����ͷ� ��ȯ
		ObjectMapper mapper = new ObjectMapper();
		String jsonData =
				mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("jsonData", jsonData);
		
		// JSP ��� ��ȯ
		return "/WEB-INF/views/common/ajaxView.jsp";
	}

}
