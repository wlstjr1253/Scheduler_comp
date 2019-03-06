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
	
	//Servlet ��ü�� ������ �� �� �� ���� ����
	@Override
	public void init(ServletConfig config)
	                            throws ServletException{
		//�о� �� ���Ͽ��� key,value ����
		Properties pr = new Properties();
		//"/WEB-INF/ActionMap.properties" ��ȯ
		String props = 
			config.getInitParameter("propertiesPath");
		//ActionMap.properties ������ ���� ��� ���ϱ�
		String path = config.getServletContext()
				            .getRealPath(props);
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(path);
			//���� ��Ʈ���� Properties ��ü�� �Ѱ��ָ�
			//Properties ��ü�� key�� value�� ������
			//��������
			pr.load(fis);
		}catch(IOException e) {
			throw new ServletException(e);
		}finally{
			if(fis!=null)try {fis.close();}catch(IOException e) {}
		}
		
		//Properties ��ü���� key ���ϱ�
		Iterator<?> keyIter = pr.keySet().iterator();
		while(keyIter.hasNext()) {
			String command = (String)keyIter.next();//key
			String className = pr.getProperty(command);//value
			
			try {
				//���ڿ��� �̿��ؼ� Ŭ������ ã�Ƴ��� Class Ÿ������ ��ȯ
				Class<?> commandClass = Class.forName(className);
				//��ü ����
				Object commandInstance = commandClass.newInstance();
				//HashMap�� key�� value�� ���
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
		
		//Ŭ���̾�Ʈ�� ��û�� URI�� ��ȯ
		String command = request.getRequestURI();
		System.out.println("URI : " + command);
		
		if(command.indexOf(request.getContextPath())==0) {
			command = command.substring(
					   request.getContextPath().length());
			System.out.println("���� �ĺ� �ּ� : " + command);
		}
		
		//HashMap�� key�� �־ value(�� ��ü)�� ����
		com = commandMap.get(command);
		
		try {
			//��Ŭ������ �޼��带 �����ؼ� �����͸� 
			//request�� �����ϰ� JSP ��θ� ��ȯ
			view = com.execute(request, response);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		if(view.startsWith("redirect:")) {
			//�����̷�Ʈ
			view = view.substring("redirect:".length());
			response.sendRedirect(request.getContextPath()+view);
		}else {		
			if (view.endsWith("ajaxView.jsp")) {
				// ajax ����� ��� ������ include ���� ����
			} else {
				//============���ø� ������ ���============//
				request.setAttribute("viewURI", view);
				view = "/WEB-INF/views/template/layout.jsp";
			}
			
			//forward ������� view(jsp) ȣ��
			RequestDispatcher dispatcher =
					request.getRequestDispatcher(view);
			dispatcher.forward(request, response);
		}
	}
}



