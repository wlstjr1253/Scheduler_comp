package kr.record.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.favorite.dao.FavoriteDao;
import kr.record.domain.DiaryDto;
import kr.record.domain.GalleryDto;
import kr.scheduler.domain.SchedulerDto;
import kr.util.AuthUtil;
import kr.util.PagingPrevNextUtil;
import kr.util.PagingUtil_paging1;

public class FavoritePageAction implements Action {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		//==========로그인 체크 시작=========//
		if(!AuthUtil.isLogin(request)) {
			return "redirect:/member/loginForm.do";
		}
		//=========로그인 체크 끝===========//
		
		// 일정 파트
		String scKeyword = request.getParameter("scKeyword");
		if (scKeyword == null) scKeyword = "";
		
		int sc_rowCount = 3; // 한 페이지의 게시물 수
		int sc_pageCount = 3; // 한 화면에 보여질 페이지 수
		int sc_currentPage = 1; // 현재 페이지 (이후 검색은 ajax 처리함)

		FavoriteDao dao = FavoriteDao.getInstance();
		int scCount = dao.getFavScCount(scKeyword, AuthUtil.getUser_id(request));
		
		// 일정 페이징 처릴
		PagingUtil_paging1 page = new PagingUtil_paging1(null, scKeyword, sc_currentPage, scCount,
				sc_rowCount, sc_pageCount, "favorite.do");
		
		List<SchedulerDto> scList = null;  // 일정
		
		if (scCount > 0) {
			scList = dao.getFavScheduleList(page.getStartCount(), 
					page.getEndCount(), 
					scKeyword, 
					AuthUtil.getUser_id(request), scCount);
		}
		

		// 다이어리 파트
		String dKeyword = request.getParameter("dKeyword");
		if (dKeyword == null) dKeyword = "";
		
		int d_rowCount = 3; // 한 페이지의 게시물 수
		int d_pageCount = 3; // 한 화면에 보여질 페이지 수
		int d_currentPage = 1; // 현재 페이지 (이후 검색은 ajax 처리함)
		
		int dCount = dao.getFavDCount(scKeyword, AuthUtil.getUser_id(request));
		
		// 일정 페이징 처릴
		PagingUtil_paging1 page2 = new PagingUtil_paging1(null, dKeyword, d_currentPage, dCount,
				d_rowCount, d_pageCount, "favorite.do");
		
		List<DiaryDto> dList = null;  // 일정
		
		if (dCount > 0) {
			dList = dao.getFavDiaryList(page2.getStartCount(), 
					page2.getEndCount(), 
					dKeyword, 
					AuthUtil.getUser_id(request), dCount);
		}
		
		
		// 갤러리 파트
		String galKeyword = request.getParameter("galKeyword");
		if (galKeyword == null) galKeyword = "";
		
		int g_rowCount = 4; // 한 페이지의 게시물 수
		int g_pageCount = 1; // 한 화면에 보여질 페이지 수
		int g_currentPage = 1; // 현재 페이지 (이후 검색은 ajax 처리함)
		
		// 필요시 사용
		// String pageNum = request.getParameter("pageNum");
		// if (pageNum == null) pageNum = "1";
				
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
	
		// 일정
		request.setAttribute("scKeyword", scKeyword);
		request.setAttribute("scCount", scCount);
		request.setAttribute("scList", scList);
		request.setAttribute("scPaging", page.getPagingHtml());
		
		// 다이어리
		request.setAttribute("dCount", dCount);
		request.setAttribute("dList", dList);
		request.setAttribute("dPaging", page2.getPagingHtml());
		
		// 갤러리
		request.setAttribute("galleryCount", galleryCount);
		request.setAttribute("galleryList", galleryList);
		request.setAttribute("g_pagingHtml", pagePrevNext.getPagingPrevNextHtml());
		
		// JSP 경로 반환
		return "/WEB-INF/views/record/favorite.jsp";
	}

}
