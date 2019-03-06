package kr.scheduler.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.schduler.dao.InvitedUserDao;
import kr.schduler.dao.SchedulerDao;
import kr.scheduler.domain.SchedulerDto;
import kr.util.AuthUtil;

public class AddScheduleDataAction implements Action {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		//==========로그인 체크 시작=========//
		if(!AuthUtil.isLogin(request)) {
			return "redirect:/main/login.do";
		}
		//=========로그인 체크 끝===========//

		String startDate = request.getParameter("startDate").replace("-", "");
		String startTime = request.getParameter("startTime").replace(" ", "");
		String startSecond = request.getParameter("startSecond");
		if (Integer.parseInt(startSecond) < 10 && Integer.parseInt(startSecond) > 0) {
			startSecond = "0"+startSecond;
		}
		String scStart = startDate + " " +startTime + ":" + startSecond;
		
		String endDate = request.getParameter("endDate").replace("-", "");
		String endTime = request.getParameter("endTime").replace(" ", "");
		String endSecond = request.getParameter("endSecond");
		if (Integer.parseInt(endSecond) < 10 && Integer.parseInt(startSecond) > 0) {
			endSecond = "0"+endSecond;
		}
		String scEnd = endDate + " " +endTime + ":" + endSecond;
		System.out.println(scEnd);
		
		SchedulerDto schedule = new SchedulerDto();
		schedule.setSc_title(request.getParameter("sc_title"));
		schedule.setId(AuthUtil.getUser_id(request));
		schedule.setSc_place(request.getParameter("sc_place"));
		schedule.setSc_start(scStart);
		schedule.setSc_end(scEnd);
		schedule.setSc_all_day(request.getParameter("sc_all_day"));
		schedule.setSc_content(request.getParameter("sc_content"));
		schedule.setAl_timer(Integer.parseInt(request.getParameter("alarmTime")));
			
		SchedulerDao dao = SchedulerDao.getInstance();
		InvitedUserDao dao2 = InvitedUserDao.getInstacne();
		dao.addSchedule(schedule);
		
		String[] sharedList = request.getParameterValues("sharedId");
		System.out.println(sharedList);
		if (sharedList != null) {
			List<String> shareds = new ArrayList<String>();
			
			String sharedUsers = "";
			for (int i = 0 ; i < sharedList.length; i++) {
				if (i == sharedList.length-1) {
					sharedUsers += sharedList[i];
					break;
				} else {
					sharedUsers += sharedList[i]+",";
				}
			}
			
			for (int i = 0; i < sharedList.length; i++) {
				shareds.add(sharedList[i]);
			}
			dao2.addInviteMember(shareds);
		}
		

		// JSP 경로 반환
		return "redirect:/main/main.do";
	}

}
