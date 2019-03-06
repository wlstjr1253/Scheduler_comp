package kr.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class AuthUtil {
	//�α��� ���� üũ
	public static boolean isLogin(
			HttpServletRequest request) {
		HttpSession session = request.getSession();
		String user_id = 
				(String)session.getAttribute("user_id");
		//�α��� üũ
		if(user_id==null) {
			return false;
		}
		return true;
	}


	//�α����� ���̵� ��ȯ
	public static String getUser_id(
			HttpServletRequest request) {
		HttpSession session = request.getSession();
		return (String)session.getAttribute("user_id");
	}
	//�α��� �� ID�� �ۼ��� ID ��ġ ���� üũ
	public static boolean isAuthWriter(
			HttpServletRequest request, String writer) {
		HttpSession session = request.getSession();
		String user_id = 
				(String)session.getAttribute("user_id");
		if(user_id!=null && user_id.equals(writer)) {
			return true;
		}
		return false;
	}
	
	public static boolean isAuthWriter(
			               HttpServletRequest request, 
			               String writer,
			               String filename) {
		HttpSession session = request.getSession();
		String user_id = 
				(String)session.getAttribute("user_id");
		if(user_id!=null && user_id.equals(writer)) {
			return true;
		}
		//���� ����
		FileUtil.removeFile(filename);
		return false;
	}
}










