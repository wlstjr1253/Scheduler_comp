package kr.record.action;

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
			System.out.println("���� ��");
			month = Integer.parseInt(request.getParameter("month")) - 1;
		}
		if (changes == 2) {
			System.out.println("���� ��");
			month = Integer.parseInt(request.getParameter("month")) + 1;
		}
		if (changes == 3) {
			System.out.println("����");
			year = cal.get(Calendar.YEAR); // ��
			month = cal.get(Calendar.MONTH) + 1; // ��
		}
		if (changes == 4) {
			System.out.println("����");
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
		cal.set(year, prevMonth-1, 1);
		int prevLastOfDate = cal.getActualMaximum(Calendar.DATE); // ������ ������ ��
		
		cal.set(year, prevMonth, prevLastOfDate);
		cal.set(Calendar.DAY_OF_WEEK, prevLastOfDate);
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
		
		Map<String, Object> mapAjax = new HashMap<String,Object>();
		mapAjax.put("year", year);
		mapAjax.put("month", month);
		mapAjax.put("date", date);
		mapAjax.put("week", week); // 1(�Ͽ���) ~ 7(�����)
		mapAjax.put("lastOfDate", lastOfDate);
		mapAjax.put("days", days);
		
		mapAjax.put("prevMonth", prevMonth); // ���� ��
		mapAjax.put("prevWeekMonday", prevWeekMonday); // ���� �� ������ ������
		mapAjax.put("prevLastOfDate", prevLastOfDate); // ���� �� ������ ��
		
		mapAjax.put("nextMonth", nextMonth); // ���� ��
		mapAjax.put("nextMonthWeek", nextMonthWeek); // ���� �� ù��° ����
		mapAjax.put("nextMonthDays", nextMonthDays); // ���� �� �Ͽ��ϱ��� ����
		
		
		// JSON �����ͷ� ��ȯ
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("jsonData", jsonData);
		
		// JSP ��� ��ȯ
		return "/WEB-INF/views/common/ajaxView.jsp";
	}

}
