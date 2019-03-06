package kr.scheduler.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.schduler.dao.InvitedUserDao;
import kr.schduler.dao.SchedulerDao;
import kr.scheduler.domain.InvitedUserDto;
import kr.scheduler.domain.SchedulerDto;
import kr.user.dao.UserDao;
import kr.user.domain.UserDto;
import kr.util.AuthUtil;

public class DetailScheduleAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//==========로그인 체크 시작=========//
		if(!AuthUtil.isLogin(request)) {
			return "redirect:/main/login.do";
		}
		//=========로그인 체크 끝===========//
		
		int idx = Integer.parseInt(request.getParameter("sc_idx"));
		
		SchedulerDao dao = SchedulerDao.getInstance();
		SchedulerDto schedule = dao.getScheduleItem(idx);
		
		InvitedUserDao dao2 = InvitedUserDao.getInstacne();
		List<InvitedUserDto> invitedUserList = dao2.getInvitedUser(idx);
		
		UserDao dao3 = UserDao.getInstance();
		UserDto writer = dao3.getScheduleWriter(idx);
		
		request.setAttribute("schedule", schedule);
		request.setAttribute("invitedUserList", invitedUserList);
		request.setAttribute("loginUser", AuthUtil.getUser_id(request));
		request.setAttribute("writer", writer);
		
		// JSP 경로 반환
		return "/WEB-INF/views/sc/addSchedule.jsp";
	}

}
