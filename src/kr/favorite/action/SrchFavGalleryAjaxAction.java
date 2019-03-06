package kr.favorite.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.favorite.dao.FavoriteDao;
import kr.record.domain.GalleryDto;
import kr.util.AuthUtil;
import kr.util.PagingPrevNextUtil;

public class SrchFavGalleryAjaxAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//==========로그인 체크 시작=========//
		if(!AuthUtil.isLogin(request)) {
			return "redirect:/main/login.do";
		}
		//=========로그인 체크 끝===========//
		
		String galKeyword = request.getParameter("keyword");
		if (galKeyword == null) galKeyword = "";
		
		String pageNum = request.getParameter("pageNum");
		if (pageNum == null) pageNum = "1";
		
		int g_rowCount = 4; // 한 페이지의 게시물 수
		int g_pageCount = 1; // 한 화면에 보여질 페이지 수
		int g_currentPage = Integer.parseInt(pageNum);
		
		FavoriteDao dao = FavoriteDao.getInstance();
		int galleryCount = dao.getFavGalleryCount(galKeyword, AuthUtil.getUser_id(request));
		
		// 갤러리 페이징 처리
		PagingPrevNextUtil pagePrevNext = new PagingPrevNextUtil(null, galKeyword, g_currentPage, galleryCount,
				g_rowCount, g_pageCount, "favorite.do");
		
		List<GalleryDto> galleryList = null; // 갤러리 
		
		if (galleryCount > 0) {
			galleryList = dao.getFavGalleryList(pagePrevNext.getStartCount(), 
					pagePrevNext.getEndCount(), 
					galKeyword, 
					AuthUtil.getUser_id(request));
		}
		
		Map<String, Object> mapAjax = new HashMap<String, Object>();
		mapAjax.put("galleryCount", galleryCount);
		mapAjax.put("galleryList", galleryList);
		mapAjax.put("g_pagingHtml", pagePrevNext);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("jsonData", jsonData);
		
		// JSP 경로 반환
		return "/WEB-INF/views/common/ajaxView.jsp";
	}

}
