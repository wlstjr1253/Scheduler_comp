package kr.scheduler.domain;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class SchedulerDto implements Comparable<SchedulerDto> {
	private int sc_idx; 
	private String sc_title;
	private String id;
	private String sc_place;
	private String sc_start;
	private String sc_startDate;
	private String sc_startTime;
	private String sc_end;
	private String sc_endDate;
	private String sc_endTime;
	private String sc_all_day;
	private String sc_content;
	private String sc_favorite;
	private String sc_usable;
	private String a_id;
	private int al_timer;
	private String al_re;
	
	public int getSc_idx() {
		return sc_idx;
	}
	public String getA_id() {
		return a_id;
	}
	public void setA_id(String a_id) {
		this.a_id = a_id;
	}
	public void setSc_idx(int sc_idx) {
		this.sc_idx = sc_idx;
	}
	public String getSc_title() {
		return sc_title;
	}
	public void setSc_title(String sc_title) {
		this.sc_title = sc_title;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSc_place() {
		return sc_place;
	}
	public void setSc_place(String sc_place) {
		this.sc_place = sc_place;
	}
	public String getSc_start() {
		return sc_start;
	}
	public void setSc_start(String sc_start) {
		this.sc_start = sc_start;
	}
	public String getSc_startDate() {
		String sc_startDate = sc_start.substring(0, 4)+"-"
	            + sc_start.substring(4, 6)+"-"
	            + sc_start.substring(6, 8)+""; 
		return sc_startDate;
	}
	public void setSc_startDate(String sc_startDate) {
		  String startDate = sc_startDate.substring(0, 4)+"-"
		            + sc_startDate.substring(4, 6)+"-"
		            + sc_startDate.substring(6, 8)+""; 
		  this.sc_startDate = startDate;
		}
	public String getSc_startTime() {
		String sc_startTime = sc_start.substring(9,11) + ":" 
				 + sc_start.substring(12,14);
		return sc_startTime;
	}
	public void setSc_startTime(String sc_startTime) {
		String startTime = sc_startTime.substring(0,2) + ":" 
						 + sc_startTime.substring(3,5);
		this.sc_startTime = startTime;
	}
	public String getSc_end() {
		return sc_end;
	}
	public void setSc_end(String sc_end) {
		this.sc_end = sc_end;
	}
	public String getSc_endDate() {
		String sc_endDate = sc_end.substring(0, 4)+"-"
	            + sc_end.substring(4, 6)+"-"
	            + sc_end.substring(6, 8)+""; 
		return sc_endDate;
	}
	public void setSc_endDate(String sc_endDate) {
		String endDate = sc_endDate.substring(0, 4)+"-"
					   + sc_endDate.substring(4, 6)+"-"
					   + sc_endDate.substring(6, 8)+"";
		this.sc_endDate = endDate;
	}
	public String getSc_endTime() {
		String sc_endTime = sc_end.substring(9,11) + ":"
				   + sc_end.substring(12,14);
		return sc_endTime;
	}
	public void setSc_endTime(String sc_endTime) {
		String endTime = sc_endTime.substring(0,2) + ":"
					   + sc_endTime.substring(3,5);
		this.sc_endTime = endTime;
	}
	public String getSc_all_day() {
		return sc_all_day;
	}
	public void setSc_all_day(String sc_all_day) {
		this.sc_all_day = sc_all_day;
	}
	public String getSc_content() {
		return sc_content;
	}
	public void setSc_content(String sc_content) {
		this.sc_content = sc_content;
	}
	public String getSc_favorite() {
		return sc_favorite;
	}
	public void setSc_favorite(String sc_favorite) {
		this.sc_favorite = sc_favorite;
	}
	public String getSc_usable() {
		return sc_usable;
	}
	public void setSc_usable(String sc_usable) {
		this.sc_usable = sc_usable;
	}
	public int getAl_timer() {
		return al_timer;
	}
	public void setAl_timer(int al_timer) {
		this.al_timer = al_timer;
	}
	public String getAl_re() {
		return al_re;
	}
	public void setAl_re(String al_re) {
		this.al_re = al_re;
	}
	
	@Override
	public int compareTo(SchedulerDto schedule) {
		String sc_start = this.sc_start;
		String sc_startYear = sc_start.substring(0, 4);
		String sc_startMonth = sc_start.substring(4,6);
		String sc_startDay = sc_start.substring(6,8);
		String sc_startTime = sc_start.substring(9, 17);
		
		String getSc_Start = schedule.getSc_start();

		SimpleDateFormat dt = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		
		Date startDate;
		Date getStartDate;
		
		try {
			startDate = dt.parse(sc_startYear+sc_startMonth+sc_startDay+" "+sc_startTime);
			getStartDate = dt.parse(getSc_Start);
			
			if (startDate.getTime() > getStartDate.getTime()) {
				return 1;
			} else if (startDate.getTime() < getStartDate.getTime()) {
				return -1;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	
}
