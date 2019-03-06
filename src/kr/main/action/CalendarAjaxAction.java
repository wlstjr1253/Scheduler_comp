package kr.main.action;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;

public class CalendarAjaxAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Calendar cal = Calendar.getInstance();
		
		int changes = Integer.parseInt(request.getParameter("ch"));
		int year = Integer.parseInt(request.getParameter("year"));
		int month = 0;
		if (changes == 1) {
			System.out.println("이전 달");
			month = Integer.parseInt(request.getParameter("month")) - 1;
		}
		if (changes == 2) {
			System.out.println("다음 달");
			month = Integer.parseInt(request.getParameter("month")) + 1;
		}
		if (changes == 3) {
			System.out.println("오늘");
			year = cal.get(Calendar.YEAR); // 년
			month = cal.get(Calendar.MONTH) + 1; // 월
		}
		if (changes == 4) {
			System.out.println("변경");
			year = Integer.parseInt(request.getParameter("year"));
			month = Integer.parseInt(request.getParameter("month"));
		}		
		
		if (month == 0) {
			year = year -1;
			month = 12;
		}
		if (month == 13) {
			year = year + 1;
			month = 1;
		}
		
		int prevMonth = 0;
		int nextMonth = 0;
		
		prevMonth = month - 1;
		if (prevMonth == 0) {
			prevMonth = 12;
		}
		nextMonth = month + 1;
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
		cal.set(year, prevMonth-1, 1);
		int prevLastOfDate = cal.getActualMaximum(Calendar.DATE); // 이전달 마지막 날
		
		cal.set(year, prevMonth, prevLastOfDate);
		cal.set(Calendar.DAY_OF_WEEK, prevLastOfDate);
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
		
		Map<String, Object> mapAjax = new HashMap<String,Object>();
		mapAjax.put("year", year);
		mapAjax.put("month", month);
		mapAjax.put("date", date);
		mapAjax.put("week", week); // 1(일요일) ~ 7(토요일)
		mapAjax.put("lastOfDate", lastOfDate);
		mapAjax.put("days", days);
		
		mapAjax.put("prevMonth", prevMonth); // 이전 달
		mapAjax.put("prevWeekMonday", prevWeekMonday); // 이전 달 마지막 월요일
		mapAjax.put("prevLastOfDate", prevLastOfDate); // 이전 달 마지막 날
		
		mapAjax.put("nextMonth", nextMonth); // 다음 달
		mapAjax.put("nextMonthWeek", nextMonthWeek); // 다음 달 첫번째 요일
		mapAjax.put("nextMonthDays", nextMonthDays); // 다음 달 일요일까지 날들
		
		
		// JSON 데이터로 변환
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("jsonData", jsonData);
		
		// JSP 경로 반환
		return "/WEB-INF/views/common/ajaxView.jsp";
	}

}
