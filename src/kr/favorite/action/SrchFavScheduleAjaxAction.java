package kr.favorite.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.favorite.dao.FavoriteDao;
import kr.scheduler.domain.SchedulerDto;
import kr.util.AuthUtil;
import kr.util.PagingUtil_paging1;

public class SrchFavScheduleAjaxAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//==========�α��� üũ ����=========//
		if(!AuthUtil.isLogin(request)) {
			return "redirect:/main/login.do";
		}
		//=========�α��� üũ ��===========//
		
		String keyword = request.getParameter("keyword");
		if (keyword == null) keyword = "";
		
		String pageNum = request.getParameter("pageNum");
		if (pageNum == null) pageNum = "1";
		
		int rowCount = 3; // �� �������� �Խù� ��
		int pageCount = 3; // �� ȭ�鿡 ������ ������ ��
		int currentPage = Integer.parseInt(pageNum);
		
		FavoriteDao dao = FavoriteDao.getInstance();
		int scCount = dao.getFavScCount(keyword, AuthUtil.getUser_id(request));
		
		// ������ ����¡ ó��
		PagingUtil_paging1 page = new PagingUtil_paging1(null, keyword, currentPage, scCount,
				rowCount, pageCount, "favorite.do");
		
		List<SchedulerDto> scList = null;  // ����
		
		if (scCount > 0) {
			scList = dao.getFavScheduleList(page.getStartCount(), 
					page.getEndCount(), 
					keyword, 
					AuthUtil.getUser_id(request), scCount);
		}
		
		Map<String, Object> mapAjax = new HashMap<String, Object>();
		
		mapAjax.put("scCount", scCount); 
		mapAjax.put("scList", scList);
		mapAjax.put("sc_pagingHtml", page);
		 
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("jsonData", jsonData);
		
		// JSP ��� ��ȯ
		return "/WEB-INF/views/common/ajaxView.jsp";
	}

}
