package kr.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class AuthUtil {
	//로그인 여부 체크
	public static boolean isLogin(
			HttpServletRequest request) {
		HttpSession session = request.getSession();
		String user_id = 
				(String)session.getAttribute("user_id");
		//로그인 체크
		if(user_id==null) {
			return false;
		}
		return true;
	}


	//로그인한 아이디 반환
	public static String getUser_id(
			HttpServletRequest request) {
		HttpSession session = request.getSession();
		return (String)session.getAttribute("user_id");
	}
	//로그인 한 ID와 작성자 ID 일치 여부 체크
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
		//파일 삭제
		FileUtil.removeFile(filename);
		return false;
	}
}










