package kr.record.action;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.schduler.dao.SchedulerDao;
import kr.scheduler.domain.SchedulerDto;
import kr.util.AuthUtil;

public class CalendarPageAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		//========로그인 체크 시작==========//
		if(!AuthUtil.isLogin(request)) {
			return "redirect:/main/login.do";
		}
		//=======로그인 체크 끝============//

		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR); // 년
		int month = cal.get(Calendar.MONTH) + 1; // 월
		if (month < 1) {
			year = year - 1;
			month = 12;
		}
		if (month == 13) {
			year = year + 1;
			month = 1;
		}
		
		int prevMonth = month - 1;
		int nextMonth = month + 1;
		if (prevMonth == 0) {
			prevMonth = 12;
		}
		if (nextMonth == 13) {
			nextMonth = 1;
		}
		
		int date = cal.get(Calendar.DATE); // 일 (오늘)			

		// 희망 연도와 월(0~11)을 Calendar 객체에 셋팅
		cal.set(year, month-1, 1);
		// 1일의 요일을 구함
		int week = cal.get(Calendar.DAY_OF_WEEK); // 1(일요일) ~ 7(토요일)
		// 마지막 날을 구함
		int lastOfDate = cal.getActualMaximum(Calendar.DATE);
		
		HashMap<Integer, Integer> days = new HashMap<Integer, Integer>();
		for (int i = 1; i<=lastOfDate; i++) {
			days.put(i, week);
			week++;
			
			if (week > 7) {
				week = 1;
			}
		}
		
		// SimpleDateFormat df = new SimpleDateFormat("dd");
		cal.set(year, prevMonth, 0);
		int prevLastOfDate = cal.getActualMaximum(Calendar.DATE); // 이전달 마지막 날

		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		// String prevWeekMonday = df.format(cal.getTime()); // 이전달 마지막 월요일

		cal.set(year, month, 1);
		int nextMonthWeek = cal.get(Calendar.DAY_OF_WEEK); // 다음달 1일의 요일

		HashMap<Integer, Integer> nextMonthDays = new HashMap<Integer, Integer>();
		int idx = 0;
		for (int i = nextMonthWeek; i<=8; i++) {
			idx++;
			if (i == 1) {
				nextMonthDays.put(idx, nextMonthWeek);
				break;
			}
			if (nextMonthWeek > 7) {
				nextMonthWeek = 1;
			}
			nextMonthDays.put(idx, nextMonthWeek);
			nextMonthWeek++;

		}
		
		// 지난달의 월요일부터 말일까지 구하는 식
		int firstDayWeek = days.get(1);
		int arrWeek = 0;
		int prevWeekMonday = 0;
		if (firstDayWeek == 2) { // 해당 월의 첫째날이 월요일 이라면
			arrWeek = 0;
		} else if (firstDayWeek == 1) { // 해당 월의 첫째날이 일요일 이라면
			arrWeek = 5;
			prevWeekMonday = prevLastOfDate - arrWeek;
		} else {
			arrWeek = firstDayWeek - 3;
			prevWeekMonday = prevLastOfDate - arrWeek;
		}

		request.setAttribute("year", year);
		request.setAttribute("month", month);		
		request.setAttribute("date", date);
		request.setAttribute("week", week); // 1(일요일) ~ 7(토요일)
		request.setAttribute("lastOfDate", lastOfDate);
		request.setAttribute("days", days);

		request.setAttribute("prevMonth", prevMonth); // 이전 달
		request.setAttribute("prevWeekMonday", prevWeekMonday); // 이전 달 마지막 월요일
		request.setAttribute("prevLastOfDate", prevLastOfDate); // 이전 달 마지막 날

		request.setAttribute("nextMonth", nextMonth); // 다음 달
		request.setAttribute("nextMonthWeek", nextMonthWeek); // 다음 달 첫번째 요일
		request.setAttribute("nextMonthDays", nextMonthDays); // 다음 달 일요일까지 날들
		
		// JSP 경로 반환
		return "/WEB-INF/views/record/diaryCalendar.jsp";
	}

}
