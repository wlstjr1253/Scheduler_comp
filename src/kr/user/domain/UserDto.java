package kr.user.domain;

import java.sql.Date;

public class UserDto {
	private String id;
	private int auth;
	private String pwd;
	private String name;
	private String phone;
	private String email;
	private Date reg_date;
	
	// 비번 체크
	public boolean isCheckedPasswd(String userPwd) {
		if (auth == 0) {
			return true;
		}
		
		// auth -> 0:탈퇴회원, 1:일반회원
		if (auth > 0 && pwd.equals(userPwd)) {
			return true;
		}
		return false;
	}
	
	/* getters and setters */
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getAuth() {
		return auth;
	}
	public void setAuth(int auth) {
		this.auth = auth;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getReg_date() {
		return reg_date;
	}
	public void setReg_date(Date reg_date) {
		this.reg_date = reg_date;
	}

}
