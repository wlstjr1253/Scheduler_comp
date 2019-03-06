package kr.scheduler.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.user.dao.UserDao;
import kr.user.domain.UserDto;
import kr.util.AuthUtil;

public class SearchUserAjaxAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String userInfo = request.getParameter("user");
		String yourSelf = AuthUtil.getUser_id(request);
		
		UserDao dao = UserDao.getInstance();
		List<UserDto> users = dao.searchUserInfo(userInfo, yourSelf);
		
		Map<String, Object> mapAjax = new HashMap<String, Object>();
		mapAjax.put("users", users);
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("jsonData", jsonData);
				
		// JSP 경로 반환
		return "/WEB-INF/views/common/ajaxView.jsp";
	}

}
