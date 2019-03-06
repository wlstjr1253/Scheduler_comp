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
		
		//==========�α��� üũ ����=========//
		if(!AuthUtil.isLogin(request)) {
			return "redirect:/member/loginForm.do";
		}
		//=========�α��� üũ ��===========//
		
		// ���� ��Ʈ
		String scKeyword = request.getParameter("scKeyword");
		if (scKeyword == null) scKeyword = "";
		
		int sc_rowCount = 3; // �� �������� �Խù� ��
		int sc_pageCount = 3; // �� ȭ�鿡 ������ ������ ��
		int sc_currentPage = 1; // ���� ������ (���� �˻��� ajax ó����)

		FavoriteDao dao = FavoriteDao.getInstance();
		int scCount = dao.getFavScCount(scKeyword, AuthUtil.getUser_id(request));
		
		// ���� ����¡ ó��
		PagingUtil_paging1 page = new PagingUtil_paging1(null, scKeyword, sc_currentPage, scCount,
				sc_rowCount, sc_pageCount, "favorite.do");
		
		List<SchedulerDto> scList = null;  // ����
		
		if (scCount > 0) {
			scList = dao.getFavScheduleList(page.getStartCount(), 
					page.getEndCount(), 
					scKeyword, 
					AuthUtil.getUser_id(request), scCount);
		}
		

		// ���̾ ��Ʈ
		String dKeyword = request.getParameter("dKeyword");
		if (dKeyword == null) dKeyword = "";
		
		int d_rowCount = 3; // �� �������� �Խù� ��
		int d_pageCount = 3; // �� ȭ�鿡 ������ ������ ��
		int d_currentPage = 1; // ���� ������ (���� �˻��� ajax ó����)
		
		int dCount = dao.getFavDCount(scKeyword, AuthUtil.getUser_id(request));
		
		// ���� ����¡ ó��
		PagingUtil_paging1 page2 = new PagingUtil_paging1(null, dKeyword, d_currentPage, dCount,
				d_rowCount, d_pageCount, "favorite.do");
		
		List<DiaryDto> dList = null;  // ����
		
		if (dCount > 0) {
			dList = dao.getFavDiaryList(page2.getStartCount(), 
					page2.getEndCount(), 
					dKeyword, 
					AuthUtil.getUser_id(request), dCount);
		}
		
		
		// ������ ��Ʈ
		String galKeyword = request.getParameter("galKeyword");
		if (galKeyword == null) galKeyword = "";
		
		int g_rowCount = 4; // �� �������� �Խù� ��
		int g_pageCount = 1; // �� ȭ�鿡 ������ ������ ��
		int g_currentPage = 1; // ���� ������ (���� �˻��� ajax ó����)
		
		// �ʿ�� ���
		// String pageNum = request.getParameter("pageNum");
		// if (pageNum == null) pageNum = "1";
				
		int galleryCount = dao.getFavGalleryCount(galKeyword, AuthUtil.getUser_id(request));
		
		// ������ ����¡ ó��
		PagingPrevNextUtil pagePrevNext = new PagingPrevNextUtil(null, galKeyword, g_currentPage, galleryCount,
				g_rowCount, g_pageCount, "favorite.do");
		
		List<GalleryDto> galleryList = null; // ������ 
		
		if (galleryCount > 0) {
			galleryList = dao.getFavGalleryList(pagePrevNext.getStartCount(), 
					pagePrevNext.getEndCount(), 
					galKeyword, 
					AuthUtil.getUser_id(request));
		}
	
		// ����
		request.setAttribute("scKeyword", scKeyword);
		request.setAttribute("scCount", scCount);
		request.setAttribute("scList", scList);
		request.setAttribute("scPaging", page.getPagingHtml());
		
		// ���̾
		request.setAttribute("dCount", dCount);
		request.setAttribute("dList", dList);
		request.setAttribute("dPaging", page2.getPagingHtml());
		
		// ������
		request.setAttribute("galleryCount", galleryCount);
		request.setAttribute("galleryList", galleryList);
		request.setAttribute("g_pagingHtml", pagePrevNext.getPagingPrevNextHtml());
		
		// JSP ��� ��ȯ
		return "/WEB-INF/views/record/favorite.jsp";
	}

}
