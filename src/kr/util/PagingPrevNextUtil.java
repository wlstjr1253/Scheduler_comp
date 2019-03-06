package kr.util;

public class PagingPrevNextUtil {
	private int startCount;	 // 한 페이지에서 보여줄 게시글의 시작 번호
	private int endCount;	 // 한 페이지에서 보여줄 게시글의 끝 번호
	private StringBuffer pagingPrevNextHtml;// 페이지 표시 문자열
	

	/**
	 * currentPage : 현재페이지
	 * totalCount : 전체 게시물 수
	 * rowCount : 한 페이지의  게시물의 수
	 * pageCount : 한 화면에 보여줄 페이지 수
	 * pageUrl : 호출 페이지 url
	 * addKey : 부가적인 key 없을 때는 null 처리 (&num=23형식으로 전달할 것)
	 * */
	public PagingPrevNextUtil(int currentPage, int totalCount, int rowCount,
			int pageCount, String pageUrl) {
		this(null,null,currentPage,totalCount,rowCount,pageCount,pageUrl,null);
	}
	public PagingPrevNextUtil(int currentPage, int totalCount, int rowCount,
			int pageCount, String pageUrl, String addKey) {
		this(null,null,currentPage,totalCount,rowCount,pageCount,pageUrl,addKey);
	}
	public PagingPrevNextUtil(String keyfield, String keyword, int currentPage, int totalCount, int rowCount,
			int pageCount,String pageUrl) {
		this(keyfield,keyword,currentPage,totalCount,rowCount,pageCount,pageUrl,null);
	}
	public PagingPrevNextUtil(String keyfield, String keyword, int currentPage, int totalCount, int rowCount,
			int pageCount,String pageUrl,String addKey) {
		
		if(addKey == null) addKey = ""; //부가키가 null 일때 ""처리
		
		// 전체 페이지 수
		int totalPage = (int) Math.ceil((double) totalCount / rowCount);
		if (totalPage == 0) {
			totalPage = 1;
		}
		// 현재 페이지가 전체 페이지 수보다 크면 전체 페이지 수로 설정
		if (currentPage > totalPage) {
			currentPage = totalPage;
		}
		// 현재 페이지의 처음과 마지막 글의 번호 가져오기.
		startCount = (currentPage - 1) * rowCount + 1;
		endCount = currentPage * rowCount;
		// 시작 페이지와 마지막 페이지 값 구하기.
		int startPage = (int) ((currentPage - 1) ) * 1;
		int endPage = startPage + pageCount - 1;
		int prevPage = currentPage - 1;
		int nextPage = currentPage + 1;
		if (prevPage < 1) {
			prevPage = 1;
		}
		if (nextPage > totalPage) {
			nextPage = totalPage;
		}
		// 마지막 페이지가 전체 페이지 수보다 크면 전체 페이지 수로 설정
		if (endPage > totalPage) {
			endPage = totalPage;
		}
		
		// 이전 block 페이지
		pagingPrevNextHtml = new StringBuffer();
		if (currentPage > pageCount) {
			if(keyword==null){//검색 미사용시
				pagingPrevNextHtml.append("<li class='page-item pagination-prev'>"
						+ "<a href="+pageUrl+"?pageNum="+ (startPage - 1) 
						+ addKey +" class='btn btn-basic' data-link='"+prevPage+"'>"
						+ "<i class='zmdi zmdi-arrow-left'></i> PREV");
			}else{
				pagingPrevNextHtml.append("<li class='page-item pagination-prev'>"
						+ "<a href="+pageUrl+"?keyfield="+keyfield+"&keyword="
						+keyword+"&pageNum="+ (currentPage - 1) + addKey 
						+" class='btn btn-basic' data-link='"+prevPage+"'>"
						+ "<i class='zmdi zmdi-arrow-left'></i> PREV");
			}
			pagingPrevNextHtml.append("</a></li>");
		}
		
		// 다음 block 페이지
		if (totalPage - startPage > pageCount) {
			if(keyword==null){//검색 미사용시
				pagingPrevNextHtml.append("<li class='page-item pagination-next'>"
					+ "<a href="+pageUrl+"?pageNum="+ nextPage
					+ addKey +" class='btn btn-basic' "
					+ "data-link='"+nextPage+"'>"
					+ "NEXT <i class='zmdi zmdi-arrow-right'></i>");
			}else{
				pagingPrevNextHtml.append("<li class='page-item pagination-next'>"
					+ "<a href="+pageUrl+"?keyfield="+keyfield+"&keyword="
					+keyword+"&pageNum="+ (currentPage + 1) + addKey 
					+" class='btn btn-basic' data-link='"+nextPage+"'>"
					+ "NEXT <i class='zmdi zmdi-arrow-right'></i>");
			}
			pagingPrevNextHtml.append("</a></li>");
		}
	}
	public StringBuffer getPagingPrevNextHtml() {
		return pagingPrevNextHtml;
	}
	public int getStartCount() {
		return startCount;
	}
	public int getEndCount() {
		return endCount;
	}

}
