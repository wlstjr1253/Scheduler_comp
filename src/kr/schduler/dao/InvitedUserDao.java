package kr.schduler.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import kr.scheduler.domain.InvitedUserDto;

public class InvitedUserDao {
	// �̱��� ����
	private static InvitedUserDao instance = new InvitedUserDao();
	public static InvitedUserDao getInstacne() {
		return instance;
	}
	private InvitedUserDao() {}
	
	// Ŀ�ؼ� Ǯ�κ��� Ŀ�ؼ� ��ȯ
	private Connection getConnection() throws Exception {
		Context initCtx = new InitialContext();
		DataSource ds = (DataSource)initCtx.lookup("java:comp/env/jdbc/xe");
		return ds.getConnection();
	}
	
	// �ڿ� ����
	private void executeClose(ResultSet rs, PreparedStatement pstmt,
			Connection conn) {
		if (rs!=null) try {rs.close();}catch(SQLException e) {};
		if (pstmt!=null) try {pstmt.close();}catch(SQLException e) {};
		if (conn!=null) try {conn.close();}catch(SQLException e) {};
	}
	
	// S: ���� ȸ��  ��� - ���� �߰��� (�Ѳ����� ���)
	public void addInviteMember(List<String> shareds) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;
		String sql = "";
		String sql2 = "";
		int si_idx = 0;

		try {
			conn = getConnection();
			sql = "INSERT INTO schedule_invited_user (si_idx, sc_idx, id) "
				+ "VALUES (?, (SELECT NVL(MAX(sc_idx), 0) FROM schedule), "
				+ "?)";
			sql2 = "(SELECT NVL(MAX(si_idx), 0)+1 FROM schedule_invited_user)";
			pstmt = conn.prepareStatement(sql);
			pstmt2 = conn.prepareStatement(sql2);
			rs = pstmt2.executeQuery();
			if (rs.next()) {
				si_idx = rs.getInt(1);
			}
			// ���� ��û ȸ���� ������ �� �� �ֱ⿡ �ݺ��� ó��
			for (int i = 0; i < shareds.size(); i++) {
				pstmt.setInt(1, si_idx);
				pstmt.setString(2, shareds.get(i));
				si_idx++;
				pstmt.addBatch();
			}
			pstmt.executeBatch();
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			executeClose(null, pstmt, conn);
		}
	}
	// E: ���� ȸ�� ��� - ���� �߰��� (�Ѳ����� ���)
	
	// S: ���� ȸ�� ��� - ���� ������ (�ϳ��� ó��)
	public int moreInviteMember(String memberId, int scIdx) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		String sql = "";
		int si_idx = 0;

		try {
			conn = getConnection();
			conn.setAutoCommit(false);
			sql = "INSERT INTO schedule_invited_user (si_idx, sc_idx, id) "
				+ "VALUES ((SELECT NVL(MAX(si_idx), 0)+1 "
				+ "FROM schedule_invited_user), ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, scIdx);
			pstmt.setString(2, memberId);

			rs = pstmt.executeQuery();
			
			sql = "SELECT * FROM schedule_invited_user "
				+ "WHERE sc_idx = ? "
				+ "AND id = ?";
			pstmt2 = conn.prepareStatement(sql);
			pstmt2.setInt(1, scIdx);
			pstmt2.setString(2, memberId);
			
			rs2 = pstmt2.executeQuery();
			
			if (rs2.next()) {
				si_idx = rs2.getInt("si_idx");
			}
			
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
			throw new Exception(e);
		} finally {
			executeClose(rs2, pstmt2, null);
			executeClose(rs, pstmt, conn);
		}
		return si_idx;
	}
	// E: ���� ȸ�� ��� - ���� ������ (�ϳ��� ó��)
	
	// S: ���� ȸ�� ��ȸ - ��ü ȸ�� ��ȸ
	public List<InvitedUserDto> getInvitedUser(int sc_idx) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<InvitedUserDto> list = null;
		InvitedUserDto invited = null;
		String sql = "";
		
		try {
			conn = getConnection();
			sql = "SELECT invited.*, ifo.name, ifo.email, ifo.reg_date FROM "
				+ "(SELECT * FROM schedule_invited_user WHERE "
				+ "sc_idx = ?)invited, user_info ifo "
				+ "WHERE invited.id = ifo.id "
				+ "ORDER BY ifo.reg_date";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, sc_idx);
			rs = pstmt.executeQuery();
			
			list = new ArrayList<InvitedUserDto>();
			
			while (rs.next()) {
				invited = new InvitedUserDto();
				
				invited.setSi_idx(rs.getInt("si_idx"));
				invited.setSc_idx(rs.getInt("sc_idx"));
				invited.setId(rs.getString("id"));
				invited.setMemberName(rs.getString("name"));
				invited.setMemberMail(rs.getString("email"));
				list.add(invited);
			}
		} catch(Exception e) {
			throw new Exception(e);
		} finally {
			executeClose(rs, pstmt, conn);
		}
		
		return list;
	}
	// E: ���� ȸ�� ��ȸ - ��ü ȸ�� ��ȸ
	
	// S: ���� ȸ�� ����
	public int leaveMember(int si_idx) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "";
		int count = 0;
		System.out.println(si_idx);
		
		try {
			conn = getConnection();
			sql = "DELETE FROM schedule_invited_user WHERE si_idx = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, si_idx);
			pstmt.executeUpdate();

			count = 1;
		} catch(Exception e) {
			// throw new Exception(e);
			e.printStackTrace();
		} finally {
			executeClose(null, pstmt, conn);
		}
		return count;
	}
	// E: ���� ȸ�� ����
}
