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
import kr.util.PagingUtil_paging1;

public class PassedScheduleAjaxAction implements Action {

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


		int rowCount = 3;   // �� �������� �Խù� ��
		int pageCount = 3;  // �� ȭ���� ���������̼� ��
		int currentPage = Integer.parseInt(pageNum);

		// DAO ȣ�� 
		SchedulerDao dao = SchedulerDao.getInstance();
		int count = dao.getPassedScheduleCount(keyword, AuthUtil.getUser_id(request));
		int sharedCount = dao.getSharedPassedScheduleCount(keyword, AuthUtil.getUser_id(request));
		int totalCount = count + sharedCount;
		
		System.out.println("���� �ϵ� ���� = " + count);

		// ����¡ ó��
		PagingUtil_paging1 page = new PagingUtil_paging1(null, keyword,currentPage,totalCount,
				rowCount,pageCount,"passedSchedule.do");

		List<SchedulerDto> list = null;
		if (count > 0) {
			list = dao.getPassedScheduleListCom(
					page.getStartCount(), 
					page.getEndCount(), 
					keyword, 
					AuthUtil.getUser_id(request));
		}

		Map<String, Object> mapAjax =
				new HashMap<String, Object>();
		mapAjax.put("count", totalCount);
		mapAjax.put("rowCount", rowCount);
		mapAjax.put("list", list);
		mapAjax.put("pagingHtml", page.getPagingHtml());
		mapAjax.put("user", AuthUtil.getUser_id(request));

		// JSON �����ͷ� ��ȯ
		ObjectMapper mapper = new ObjectMapper();
		String jsonData =
				mapper.writeValueAsString(mapAjax);

		request.setAttribute("jsonData", jsonData);

		// JSP ��� ��ȯ
		return "/WEB-INF/views/common/ajaxView.jsp";
	}

}
