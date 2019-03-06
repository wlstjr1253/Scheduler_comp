package kr.util;

public class PagingUtil {
	private int startCount;	 // �� ���������� ������ �Խñ��� ���� ��ȣ
	private int endCount;	 // �� ���������� ������ �Խñ��� �� ��ȣ
	private StringBuffer pagingHtml;// ������ ǥ�� ���ڿ�
	

	/**
	 * currentPage : ����������
	 * totalCount : ��ü �Խù� ��
	 * rowCount : �� ��������  �Խù��� ��
	 * pageCount : �� ȭ�鿡 ������ ������ ��
	 * pageUrl : ȣ�� ������ url
	 * addKey : �ΰ����� key ���� ���� null ó�� (&num=23�������� ������ ��)
	 * */
	public PagingUtil(int currentPage, int totalCount, int rowCount,
			int pageCount, String pageUrl) {
		this(null,null,currentPage,totalCount,rowCount,pageCount,pageUrl,null);
	}
	public PagingUtil(int currentPage, int totalCount, int rowCount,
			int pageCount, String pageUrl, String addKey) {
		this(null,null,currentPage,totalCount,rowCount,pageCount,pageUrl,addKey);
	}
	public PagingUtil(String keyfield, String keyword, int currentPage, int totalCount, int rowCount,
			int pageCount,String pageUrl) {
		this(keyfield,keyword,currentPage,totalCount,rowCount,pageCount,pageUrl,null);
	}
	public PagingUtil(String keyfield, String keyword, int currentPage, int totalCount, int rowCount,
			int pageCount,String pageUrl,String addKey) {
		
				if(addKey == null) addKey = ""; //�ΰ�Ű�� null �϶� ""ó��
				
				System.out.println("startCount" + startCount);
		
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
		int startPage = (int) ((currentPage - 1) / pageCount) * pageCount + 1;
		int endPage = startPage + pageCount - 1;
		int prevTenPage = currentPage - 10;
		int prevPage = currentPage - 1;
		int nextTenPage = currentPage + 10;
		int nextPage = currentPage + 1;
		if (prevTenPage < 1) {
			prevTenPage = 1;
		}
		if (prevPage < 1) {
			prevPage = 1;
		}
		if (nextTenPage > totalPage) {
			nextTenPage = totalPage;
		}
		if (nextPage > totalPage) {
			nextPage = totalPage;
		}
		// ������ �������� ��ü ������ ������ ũ�� ��ü ������ ���� ����
		if (endPage > totalPage) {
			endPage = totalPage;
		}
		// ���� block ������
		pagingHtml = new StringBuffer();
		if (currentPage > pageCount) {
			pagingHtml.append("<li class='page-item pagination-first'>");
			pagingHtml.append("<a href='#' class='page-link' data-link='"+(prevTenPage)+"'>");
			pagingHtml.append("</a>");
			pagingHtml.append("</li>");
			if(keyword==null){//�˻� �̻���
				pagingHtml.append("<li class='page-item pagination-prev'><a href="+pageUrl+"?pageNum="+ (startPage - 1) + addKey +" class='page-link' data-link='"+prevPage+"'>");
			}else{
				pagingHtml.append("<li class='page-item pagination-prev'><a href="+pageUrl+"?keyfield="+keyfield+"&keyword="+keyword+"&pageNum="+ (currentPage - 1) + addKey +" class='page-link' data-link='"+prevPage+"'>");
			}
			pagingHtml.append("</a></li>");
		}
		//������ ��ȣ.���� �������� ���������� �����ϰ� ��ũ�� ����.
		for (int i = startPage; i <= endPage; i++) {
			if (i > totalPage) {
				break;
			}
			if (i == currentPage) {
				pagingHtml.append("<li class='page-item active'><a href='"+pageUrl+"?pageNum="+i+"' class='page-link' data-link='"+i+"'>");
				pagingHtml.append(i);
				pagingHtml.append("</a></li>");
			} else {
				if(keyword==null){//�˻� �̻���
					pagingHtml.append("<li class='page-item'><a href='"+pageUrl+"?pageNum=");
				}else{
					pagingHtml.append("<li class='page-item'><a href='"+pageUrl+"?keyfield="+keyfield+"&keyword="+keyword+"&pageNum=");
				}
				pagingHtml.append(i);
				pagingHtml.append(addKey+"' class='page-link' data-link='"+i+"'>");
				pagingHtml.append(i);
				pagingHtml.append("</a></li>");
			}
		}
		
		// ���� block ������
		if (totalPage - startPage >= pageCount) {
			if(keyword==null){//�˻� �̻���
				pagingHtml.append("<li class='page-item pagination-next'>"
					+ "<a href="+pageUrl+"?pageNum="+ nextPage
					+ addKey +" class='page-link' "
					+ "data-link='"+nextPage+"'>");
			}else{
				pagingHtml.append("<li class='page-item pagination-next'>"
					+ "<a href="+pageUrl+"?keyfield="+keyfield+"&keyword="
					+keyword+"&pageNum="+ (currentPage + 1) + addKey 
					+" class='page-link' data-link='"+nextPage+"'>");
			}
			pagingHtml.append("</a></li>");
			pagingHtml.append("<li class='page-item pagination-last'>");
			pagingHtml.append("<a href='#' class='page-link' "
					+ "data-link='"+nextTenPage+"'>");
			pagingHtml.append("</a>");
			pagingHtml.append("</li>");
		}
	}
	public StringBuffer getPagingHtml() {
		return pagingHtml;
	}
	public int getStartCount() {
		return startCount;
	}
	public int getEndCount() {
		return endCount;
	}

}
