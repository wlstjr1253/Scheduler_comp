package kr.main.action;

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

		//========�α��� üũ ����==========//
		if(!AuthUtil.isLogin(request)) {
			return "redirect:/main/login.do";
		}
		//=======�α��� üũ ��============//

		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR); // ��
		int month = cal.get(Calendar.MONTH) + 1; // ��
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
		
		// SimpleDateFormat df = new SimpleDateFormat("dd");
		cal.set(year, prevMonth, 0);
		int prevLastOfDate = cal.getActualMaximum(Calendar.DATE); // ������ ������ ��

		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		// String prevWeekMonday = df.format(cal.getTime()); // ������ ������ ������

		cal.set(year, month, 1);
		int nextMonthWeek = cal.get(Calendar.DAY_OF_WEEK); // ������ 1���� ����

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
		
		// �������� �����Ϻ��� ���ϱ��� ���ϴ� ��
		int firstDayWeek = days.get(1);
		int arrWeek = 0;
		int prevWeekMonday = 0;
		if (firstDayWeek == 2) { // �ش� ���� ù°���� ������ �̶��
			arrWeek = 0;
		} else if (firstDayWeek == 1) { // �ش� ���� ù°���� �Ͽ��� �̶��
			arrWeek = 5;
			prevWeekMonday = prevLastOfDate - arrWeek;
		} else {
			arrWeek = firstDayWeek - 3;
			prevWeekMonday = prevLastOfDate - arrWeek;
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
		return "/WEB-INF/views/main/calendar.jsp";
	}

}
