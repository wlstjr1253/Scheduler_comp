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
 * 필터는 Servlet 2.3 이상에서 사용 가능함.
 * 서블릿 필터는 request, response가 서블릿이나 JSP 등
 * 리소드에 도달하기 전에 필요한 전처리 또는 사후처리 작업을 가능하게
 * 함.
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
		
		//전송된 데이터에 대한 인코딩 처리
		if(encoding!=null) {
			request.setCharacterEncoding(encoding);
		}
		
		System.out.println("인코딩");
		
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig fConfig) throws ServletException {
		//web.xml에 명시한 인코딩 방식을 읽어 옴
		encoding = fConfig.getInitParameter("encoding");
	}

}





