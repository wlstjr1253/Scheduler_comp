package kr.alarm.domain;

public class AlarmDto {
	private int al_timer;
	private String al_re;
	private String sc_start;
	private String sc_startDate;
	private String sc_startTime;
	private String sc_title;
	private int sc_idx;
	private int al_idx;
	
	public int getAl_idx() {
		return al_idx;
	}
	public void setAl_idx(int al_idx) {
		this.al_idx = al_idx;
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
	public String getSc_start() {
		return sc_start;
	}
	public void setSc_start(String sc_start) {
		this.sc_start = sc_start;
	}
	public String getSc_startDate() {
		return sc_startDate;
	}
	public void setSc_startDate(String sc_startDate) {
		this.sc_startDate = sc_startDate;
	}
	public String getSc_startTime() {
		return sc_startTime;
	}
	public void setSc_startTime(String sc_startTime) {
		this.sc_startTime = sc_startTime;
	}
	public String getSc_title() {
		return sc_title;
	}
	public void setSc_title(String sc_title) {
		this.sc_title = sc_title;
	}
	public int getSc_idx() {
		return sc_idx;
	}
	public void setSc_idx(int sc_idx) {
		this.sc_idx = sc_idx;
	}
	
}
