package kr.record.domain;

import java.sql.Date;

public class GalleryDto {
	private int g_idx;
	private String g_title;
	private String id;
	private String g_content;
	private String g_photo1;
	private String g_photo2;
	private String g_photo3;
	private Date g_reg_date;
	private Date g_modify_date;
	private String g_favorite;
	
	public int getG_idx() {
		return g_idx;
	}
	public void setG_idx(int g_idx) {
		this.g_idx = g_idx;
	}
	public String getG_title() {
		return g_title;
	}
	public void setG_title(String g_title) {
		this.g_title = g_title;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getG_content() {
		return g_content;
	}
	public void setG_content(String g_content) {
		this.g_content = g_content;
	}
	public String getG_photo1() {
		return g_photo1;
	}
	public void setG_photo1(String g_photo1) {
		this.g_photo1 = g_photo1;
	}
	public String getG_photo2() {
		return g_photo2;
	}
	public void setG_photo2(String g_photo2) {
		this.g_photo2 = g_photo2;
	}
	public String getG_photo3() {
		return g_photo3;
	}
	public void setG_photo3(String g_photo3) {
		this.g_photo3 = g_photo3;
	}
	public Date getG_reg_date() {
		return g_reg_date;
	}
	public void setG_reg_date(Date g_reg_date) {
		this.g_reg_date = g_reg_date;
	}
	public Date getG_modify_date() {
		return g_modify_date;
	}
	public void setG_modify_date(Date g_modify_date) {
		this.g_modify_date = g_modify_date;
	}
	public String getG_favorite() {
		return g_favorite;
	}
	public void setG_favorite(String g_favorite) {
		this.g_favorite = g_favorite;
	}		
	
}
