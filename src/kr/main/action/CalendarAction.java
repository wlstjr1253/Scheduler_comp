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
			year = cal.get(Calendar.YEAR); // ��
			month = cal.get(Calendar.MONTH) + 1; // ��
		}
		
		prevMonth = month - 1;
		nextMonth = month + 1;
		
		
		int date = cal.get(Calendar.DATE); // �� (����)			
	
		// ��� ������ ��(0~11)�� Calendar ��ü�� ����
		cal.set(year, month-1, 1);
		// 1���� ������ ����
		int week = cal.get(Calendar.DAY_OF_WEEK); // 1(�Ͽ���) ~ 7(�����)
		// ������ ���� ����
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
		int prevLastOfDate = cal.getActualMaximum(Calendar.DATE); // ������ ������ ��
		System.out.println(prevLastOfDate);
		
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		String prevWeekMonday = df.format(cal.getTime()); // ������ ������ ������
		
		cal.set(year, month, 1);
		int nextMonthWeek = cal.get(Calendar.DAY_OF_WEEK); // ������ 1���� ����
		
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
		request.setAttribute("week", week); // 1(�Ͽ���) ~ 7(�����)
		request.setAttribute("lastOfDate", lastOfDate);
		request.setAttribute("days", days);
		
		request.setAttribute("prevMonth", prevMonth); // ���� ��
		request.setAttribute("prevWeekMonday", prevWeekMonday); // ���� �� ������ ������
		request.setAttribute("prevLastOfDate", prevLastOfDate); // ���� �� ������ ��
		
		request.setAttribute("nextMonth", nextMonth); // ���� ��
		request.setAttribute("nextMonthWeek", nextMonthWeek); // ���� �� ù��° ����
		request.setAttribute("nextMonthDays", nextMonthDays); // ���� �� �Ͽ��ϱ��� ����
		
		// JSP ��� ��ȯ
		return "/WEB-INF/views/main/main.jsp";
	}

}
