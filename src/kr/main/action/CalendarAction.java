package kr.main.action;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;

public class CalendarAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Calendar cal = Calendar.getInstance();
		int year = 0;
		int month = 0;
		int prevMonth = 0;
		int nextMonth = 0;
		
		try {
			year = Integer.parseInt(request.getParameter("year"));
			month = Integer.parseInt(request.getParameter("month"));
		} catch (NumberFormatException e) {
			year = cal.get(Calendar.YEAR); // 년
			month = cal.get(Calendar.MONTH) + 1; // 월
		}
		
		prevMonth = month - 1;
		nextMonth = month + 1;
		
		
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
		
		SimpleDateFormat df = new SimpleDateFormat("dd");
		cal.set(year, prevMonth, 0);
		int prevLastOfDate = cal.getActualMaximum(Calendar.DATE); // 이전달 마지막 날
		System.out.println(prevLastOfDate);
		
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		String prevWeekMonday = df.format(cal.getTime()); // 이전달 마지막 월요일
		
		cal.set(year, month, 1);
		int nextMonthWeek = cal.get(Calendar.DAY_OF_WEEK); // 다음달 1일의 요일
		
		HashMap<Integer, Integer> nextMonthDays = new HashMap<Integer, Integer>();
		int idx = 0;
		for (int i = nextMonthWeek; i<=8; i++) {
			idx++;
			if (nextMonthWeek > 7) {
				nextMonthWeek = 1;
			}
			nextMonthDays.put(idx, nextMonthWeek);
			nextMonthWeek++;
			
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
		return "/WEB-INF/views/main/main.jsp";
	}

}
