package kr.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
          
public class DispatcherServlet extends HttpServlet{

	private Map<String,Action> commandMap = 
			new HashMap<String,Action>();
	
	//Servlet 객체가 생성된 후 단 한 번만 수행
	@Override
	public void init(ServletConfig config)
	                            throws ServletException{
		//읽어 온 파일에서 key,value 구분
		Properties pr = new Properties();
		//"/WEB-INF/ActionMap.properties" 반환
		String props = 
			config.getInitParameter("propertiesPath");
		//ActionMap.properties 파일의 절대 경로 구하기
		String path = config.getServletContext()
				            .getRealPath(props);
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(path);
			//파일 스트림을 Properties 객체에 넘겨주면
			//Properties 객체가 key와 value의 쌍으로
			//구분해줌
			pr.load(fis);
		}catch(IOException e) {
			throw new ServletException(e);
		}finally{
			if(fis!=null)try {fis.close();}catch(IOException e) {}
		}
		
		//Properties 객체에서 key 구하기
		Iterator<?> keyIter = pr.keySet().iterator();
		while(keyIter.hasNext()) {
			String command = (String)keyIter.next();//key
			String className = pr.getProperty(command);//value
			
			try {
				//문자열을 이용해서 클래스를 찾아내고 Class 타입으로 반환
				Class<?> commandClass = Class.forName(className);
				//객체 생성
				Object commandInstance = commandClass.newInstance();
				//HashMap에 key와 value로 등록
				commandMap.put(command, (Action)commandInstance);
				System.out.println(command+","+commandInstance);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	@Override
	protected void doGet(HttpServletRequest request,
			             HttpServletResponse response) 
	               throws ServletException,IOException{
		requestPro(request,response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			             HttpServletResponse response) 
	               throws ServletException,IOException{
		requestPro(request,response);
	}
	
	private void requestPro(HttpServletRequest request, 
			HttpServletResponse response) 
	             throws ServletException,IOException{
		Action com = null;
		String view = null;
		
		//클라이언트가 요청한 URI를 반환
		String command = request.getRequestURI();
		System.out.println("URI : " + command);
		
		if(command.indexOf(request.getContextPath())==0) {
			command = command.substring(
					   request.getContextPath().length());
			System.out.println("최종 식별 주소 : " + command);
		}
		
		//HashMap에 key를 넣어서 value(모델 객체)을 얻음
		com = commandMap.get(command);
		
		try {
			//모델클래스의 메서드를 실행해서 데이터를 
			//request에 보관하고 JSP 경로를 반환
			view = com.execute(request, response);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		if(view.startsWith("redirect:")) {
			//리다이렉트
			view = view.substring("redirect:".length());
			response.sendRedirect(request.getContextPath()+view);
		}else {		
			if (view.endsWith("ajaxView.jsp")) {
				// ajax 통신의 경우 페이지 include 하지 않음
			} else {
				//============템플릿 페이지 사용============//
				request.setAttribute("viewURI", view);
				view = "/WEB-INF/views/template/layout.jsp";
			}
			
			//forward 방식으로 view(jsp) 호출
			RequestDispatcher dispatcher =
					request.getRequestDispatcher(view);
			dispatcher.forward(request, response);
		}
	}
}



