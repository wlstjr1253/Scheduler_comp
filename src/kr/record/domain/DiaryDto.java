package kr.record.domain;

import java.sql.Date;

public class DiaryDto {
	private int d_idx;
	private String d_title;
	private String id;
	private String d_content;
	private Date d_reg_date;
	private Date d_modify_date;
	private char d_favorite;
	
	public int getD_idx() {
		return d_idx;
	}
	public void setD_idx(int d_idx) {
		this.d_idx = d_idx;
	}
	public String getD_title() {
		return d_title;
	}
	public void setD_title(String d_title) {
		this.d_title = d_title;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getD_content() {
		return d_content;
	}
	public void setD_content(String d_content) {
		this.d_content = d_content;
	}
	public Date getD_reg_date() {
		return d_reg_date;
	}
	public void setD_reg_date(Date d_reg_date) {
		this.d_reg_date = d_reg_date;
	}
	public Date getD_modify_date() {
		return d_modify_date;
	}
	public void setD_modify_date(Date d_modify_date) {
		this.d_modify_date = d_modify_date;
	}
	public char getD_favorite() {
		return d_favorite;
	}
	public void setD_favorite(char d_favorite) {
		this.d_favorite = d_favorite;
	}
	
	
	
}
