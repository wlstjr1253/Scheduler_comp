package kr.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/*
 * ���ʹ� Servlet 2.3 �̻󿡼� ��� ������.
 * ���� ���ʹ� request, response�� �����̳� JSP ��
 * ���ҵ忡 �����ϱ� ���� �ʿ��� ��ó�� �Ǵ� ����ó�� �۾��� �����ϰ�
 * ��.
 */
public class CharacterEncodingFilter implements Filter{

	private String encoding;
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	@Override
	public void doFilter(ServletRequest request, 
			ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		//���۵� �����Ϳ� ���� ���ڵ� ó��
		if(encoding!=null) {
			request.setCharacterEncoding(encoding);
		}
		
		System.out.println("���ڵ�");
		
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig fConfig) throws ServletException {
		//web.xml�� ����� ���ڵ� ����� �о� ��
		encoding = fConfig.getInitParameter("encoding");
	}

}





