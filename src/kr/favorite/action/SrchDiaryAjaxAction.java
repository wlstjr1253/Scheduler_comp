package kr.favorite.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.favorite.dao.FavoriteDao;
import kr.record.domain.DiaryDto;
import kr.util.AuthUtil;
import kr.util.PagingUtil_paging1;

public class SrchDiaryAjaxAction implements Action {

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
		int dCount = dao.getFavDCount(keyword, AuthUtil.getUser_id(request));

		// ������ ����¡ ó��
		PagingUtil_paging1 page = new PagingUtil_paging1(null, keyword, currentPage, dCount,
				rowCount, pageCount, "favorite.do");

		List<DiaryDto> dList = null;  // ����

		if (dCount > 0) {
			dList = dao.getFavDiaryList(page.getStartCount(), 
					page.getEndCount(), 
					keyword, 
					AuthUtil.getUser_id(request), dCount);
		}
		
		Map<String, Object> mapAjax = new HashMap<String, Object>();
		mapAjax.put("dCount", dCount);
		mapAjax.put("dList", dList);
		mapAjax.put("d_pagingHtml", page);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("jsonData", jsonData);
		
		// JSP ��� ��ȯ
		return "/WEB-INF/views/common/ajaxView.jsp";
	}

}
