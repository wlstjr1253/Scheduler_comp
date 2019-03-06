package kr.util;

public class PagingPrevNextUtil {
	private int startCount;	 // �� ���������� ������ �Խñ��� ���� ��ȣ
	private int endCount;	 // �� ���������� ������ �Խñ��� �� ��ȣ
	private StringBuffer pagingPrevNextHtml;// ������ ǥ�� ���ڿ�
	

	/**
	 * currentPage : ����������
	 * totalCount : ��ü �Խù� ��
	 * rowCount : �� ��������  �Խù��� ��
	 * pageCount : �� ȭ�鿡 ������ ������ ��
	 * pageUrl : ȣ�� ������ url
	 * addKey : �ΰ����� key ���� ���� null ó�� (&num=23�������� ������ ��)
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
		
		if(addKey == null) addKey = ""; //�ΰ�Ű�� null �϶� ""ó��
		
		// ��ü ������ ��
		int totalPage = (int) Math.ceil((double) totalCount / rowCount);
		if (totalPage == 0) {
			totalPage = 1;
		}
		// ���� �������� ��ü ������ ������ ũ�� ��ü ������ ���� ����
		if (currentPage > totalPage) {
			currentPage = totalPage;
		}
		// ���� �������� ó���� ������ ���� ��ȣ ��������.
		startCount = (currentPage - 1) * rowCount + 1;
		endCount = currentPage * rowCount;
		// ���� �������� ������ ������ �� ���ϱ�.
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
		// ������ �������� ��ü ������ ������ ũ�� ��ü ������ ���� ����
		if (endPage > totalPage) {
			endPage = totalPage;
		}
		
		// ���� block ������
		pagingPrevNextHtml = new StringBuffer();
		if (currentPage > pageCount) {
			if(keyword==null){//�˻� �̻���
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
		
		// ���� block ������
		if (totalPage - startPage > pageCount) {
			if(keyword==null){//�˻� �̻���
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
