package kr.scheduler.domain;

public class InvitedUserDto {
	private int si_idx;
	private int sc_idx;
	private String id;
	private String memberName;
	private String memberMail;
	
	public int getSi_idx() {
		return si_idx;
	}
	public void setSi_idx(int si_idx) {
		this.si_idx = si_idx;
	}
	public int getSc_idx() {
		return sc_idx;
	}
	public void setSc_idx(int sc_idx) {
		this.sc_idx = sc_idx;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getMemberMail() {
		return memberMail;
	}
	public void setMemberMail(String memberMail) {
		this.memberMail = memberMail;
	}	
	
}
