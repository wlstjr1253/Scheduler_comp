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
		
		//==========로그인 체크 시작=========//
		if(!AuthUtil.isLogin(request)) {
			return "redirect:/main/login.do";
		}
		//=========로그인 체크 끝===========//

		String keyword = request.getParameter("keyword");
		if (keyword == null) keyword = "";

		String pageNum = request.getParameter("pageNum");
		if (pageNum == null) pageNum = "1";

		int rowCount = 3; // 한 페이지의 게시물 수
		int pageCount = 3; // 한 화면에 보여질 페이지 수
		int currentPage = Integer.parseInt(pageNum);

		FavoriteDao dao = FavoriteDao.getInstance();
		int dCount = dao.getFavDCount(keyword, AuthUtil.getUser_id(request));

		// 스케쥴 페이징 처릴
		PagingUtil_paging1 page = new PagingUtil_paging1(null, keyword, currentPage, dCount,
				rowCount, pageCount, "favorite.do");

		List<DiaryDto> dList = null;  // 일정

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
		
		// JSP 경로 반환
		return "/WEB-INF/views/common/ajaxView.jsp";
	}

}
