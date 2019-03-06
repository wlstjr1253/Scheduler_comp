package kr.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import kr.user.domain.UserDto;

public class UserDao {
	// �̱��� ����
	private static UserDao instance = new UserDao();
	public static UserDao getInstance() {
		return instance;
	}
	private UserDao() {}
	
	// Ŀ�ؼ� Ǯ�κ��� Ŀ�ؼ��� �Ҵ�
	private Connection getConnection() throws Exception {
		Context initCtx = new InitialContext();
		DataSource ds = (DataSource)initCtx.lookup("java:comp/env/jdbc/xe");
		return ds.getConnection();
	}
	
	// �ڿ�����
	private void executeClose(ResultSet rs,
			PreparedStatement pstmt,Connection conn){
		if(rs!=null)try{rs.close();}catch(SQLException e){}
		if(pstmt!=null)try{pstmt.close();}catch(SQLException e){}
		if(conn!=null)try{conn.close();}catch(SQLException e){}
	}
	
	// S: ȸ������ insertUser
	public void insertUser(UserDto user) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		String sql = null;
		
		try {
			// Ŀ�ؼ� Ǯ�κ��� Ŀ�ؼ� �Ҵ�
			conn = getConnection();
			// ���� Ŀ�� ����
			conn.setAutoCommit(false);
			// SQL�� �ۼ�
			sql = "INSERT INTO user_auth (id) VALUES (?)";
			// PreparedStatement ��ü ����
			pstmt = conn.prepareStatement(sql);
			// SQL���� ?�� �����͸� �Ҵ�
			pstmt.setString(1, user.getId());
			// SQL�� �ݿ�
			pstmt.executeUpdate();
			
			// SQL�� �ۼ�
			sql = "INSERT INTO user_info (id,pwd,name,phone,email,"
				+ "reg_date) VALUES (?,?,?,?,?,SYSDATE)";
			// PreparedStatement ��ü ����
			pstmt2 = conn.prepareStatement(sql);
			// SQL���� ?�� �����͸� �Ҵ�
			pstmt2.setString(1, user.getId());
			pstmt2.setString(2, user.getPwd());
			pstmt2.setString(3, user.getName());
			pstmt2.setString(4, user.getPhone());
			pstmt2.setString(5, user.getEmail());
			// SQL�� �ݿ�
			pstmt2.executeUpdate();
			
			// ���������� SQL���� �ݿ��Ǹ� commit
			conn.commit();
		} catch(Exception e) {
			// ���ܰ� �߻��ϸ� rollback
			conn.rollback();
			e.printStackTrace();
		} finally {
			executeClose(null, pstmt2, null);
			executeClose(null, pstmt, conn);
		}
	}
	// E: ȸ������ insertUser
	
	// S: ȸ�� �˻� getUser
	public UserDto getUser(String id) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		UserDto user = null;
		
		try {
			// Ŀ�ؼ� Ǯ�κ��� Ŀ�ؼ� �Ҵ�
			conn = getConnection();
			// SQL�� �ۼ�
			sql = "SELECT * FROM user_auth u LEFT OUTER JOIN "
				+ "user_info d ON u.id = d.id "
				+ "WHERE u.id = ?";
			// PreparedStatement ��ü ����
			pstmt = conn.prepareStatement(sql);
			// SQL���� ?�� ������ �Ҵ�
			pstmt.setString(1, id);
			// SQL���� �ݿ��ϰ� ResultSet ��ȯ
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				user = new UserDto();
				user.setId(rs.getString("id"));
				user.setAuth(rs.getInt("auth"));
				user.setPwd(rs.getString("pwd"));
				user.setName(rs.getString("name"));
				user.setPhone(rs.getString("phone"));
				user.setEmail(rs.getString("email"));
				user.setReg_date(rs.getDate("reg_date"));
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		} finally {
			executeClose(rs, pstmt, conn);
		}
		
		return user;
	}
	// E: ȸ�� �˻� getUser
	
	// S: ���� üũ getUserByMail
	public UserDto getUserByMail(String email) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		UserDto user = null;
		
		try {
			// Ŀ�ؼ� Ǯ�κ��� Ŀ�ؼ� �Ҵ�
			conn = getConnection();
			// SQL�� �ۼ�
			sql = "SELECT * FROM user_info "
				+ "WHERE email = ?";
			// PreparedStatement ��ü ����
			pstmt = conn.prepareStatement(sql);
			// SQL���� ?�� ������ �Ҵ�
			pstmt.setString(1, email);
			// SQL���� �ݿ��ϰ� ResultSet ��ȯ
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				user = new UserDto();
				user.setId(rs.getString("id"));
				user.setPwd(rs.getString("pwd"));
				user.setName(rs.getString("name"));
				user.setPhone(rs.getString("phone"));
				user.setEmail(rs.getString("email"));
				user.setReg_date(rs.getDate("reg_date"));
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		} finally {
			executeClose(rs, pstmt, conn);
		}
		
		return user;
	}
	// E: ���� üũ getUserByMail
	
	// S: ȸ�� ���� ���� updateUser
	public boolean updateUser(UserDto user) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		boolean check = false;
		
		try {
			// Ŀ�ؼ� Ǯ�κ��� Ŀ�ؼ��� �Ҵ�
			conn = getConnection();
			// SQL�� �ۼ�
			sql = "UPDATE user_info SET "
				+ "pwd=?,phone=?,email=? "
				+ "WHERE id=? and name=?";
			// PreparedStatement ��ü ����
			pstmt = conn.prepareStatement(sql);
			// SQL���� ?�� ������ �Ҵ�
			pstmt.setString(1, user.getPwd());
			pstmt.setString(2, user.getPhone());
			pstmt.setString(3, user.getEmail());
			pstmt.setString(4, user.getId());
			pstmt.setString(5, user.getName());
			// SQL�� �ݿ� �� resultSet ����
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				check = true;
			}
			
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			executeClose(null, pstmt, conn);
		}
		
		return check;
	}
	// E: ȸ�� ���� ���� updateUser
	
	// S: ȸ��Ż��
	public void removeUser(String id) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		String sql = "";
		
		try {
			// Ŀ�ؼ� Ǯ�κ��� Ŀ�ؼ��� �Ҵ�
			conn = getConnection();
			// ���� Ŀ�� ����
			conn.setAutoCommit(false);
			
			// SQL�� �ۼ� - ���� ����
			sql = "UPDATE user_auth SET auth=0 WHERE id=?";
			// PreparedStatement ��ü ����
			pstmt = conn.prepareStatement(sql);
			// SQL�� ?�� ������ �Ҵ�
			pstmt.setString(1, id);
			// SQL�� �ݿ�
			pstmt.executeUpdate();
			
			// SQL�� �ۼ� - ���� ����
			sql = "UPDATE schedule SET sc_usable='N' WHERE id=?"; 
			// PreparedStatement ��ü ����
			pstmt2 = conn.prepareStatement(sql);
			// SQL���� ?�� �����͸� �Ҵ�
			pstmt2.setString(1, id);
			// SQL�� �ݿ�
			pstmt2.executeUpdate();
			
			// SQL�� �ۼ� - ȸ������ ����
			sql = "DELETE FROM user_info WHERE id=?";
			// PreparedStatement ��ü ����
			pstmt3 = conn.prepareStatement(sql);
			// SQL���� ?�� �����͸� �Ҵ�
			pstmt3.setString(1, id);
			// SQL�� �ݿ�
			pstmt3.executeUpdate();
			
			// ���������� SQL�� �ݿ�
			conn.commit();
		} catch(Exception e) {
			// SQL�� �ݿ� ����
			conn.rollback();
			throw new Exception(e);
		} finally {
			executeClose(null, pstmt3, null);
			executeClose(null, pstmt2, null);
			executeClose(null, pstmt, conn);
		}
	}
	// E: ȸ��Ż��
	
	// S: ���� ��� �˻�
	public List<UserDto> searchUserInfo(String userInfo, String your) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		UserDto user = null;
		List<UserDto> list = new ArrayList<UserDto>();

		try {
			conn = getConnection();
			sql = "SELECT * FROM "
				+ "(SELECT * FROM (SELECT * FROM user_info "
				+ "WHERE id NOT LIKE ?)notMe WHERE "
				+ "notMe.id LIKE ? OR "
				+ "name LIKE ? OR "
				+ "email LIKE ?)srch "
				+ "LEFT OUTER JOIN user_auth au "
				+ "ON srch.id = au.id "
				+ "WHERE au.auth != 0";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, your);
			pstmt.setString(2, "%"+userInfo+"%");
			pstmt.setString(3, "%"+userInfo+"%");
			pstmt.setString(4, "%"+userInfo+"%");
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				user = new UserDto();
				user.setId(rs.getString("id"));
				user.setAuth(rs.getInt("auth"));
				user.setEmail(rs.getString("email"));
				user.setName(rs.getString("name"));
				user.setPhone(rs.getString("phone"));
				user.setReg_date(rs.getDate("reg_date"));
				
				list.add(user);
			}
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			executeClose(null, pstmt, conn);
		}
		return list;
	}
	// E: ���� ��� �˻�
	
	// S: ���� �ۼ��� ��ȸ
	public UserDto getScheduleWriter(int sc_idx) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		UserDto user = null;
		try {
			conn = getConnection();
			sql = "SELECT * FROM schedule s, user_info i "
				+ "WHERE s.id = i.id "
				+ "AND s.sc_idx = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, sc_idx);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				user = new UserDto();
				user.setId(rs.getString("id"));
				user.setEmail(rs.getString("email"));
				user.setName(rs.getString("name"));
			}
		} catch(Exception e) {
			throw new Exception(e);
		} finally {
			executeClose(rs, pstmt, conn);
		}
		return user;
	}
	// E: ���� �ۼ��� ��ȸ
	
	
	// S: ���̵� �˻�
	public String srchId(String userName, String email) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		String result = "";
		
		try {
			conn = getConnection();
			sql = "SELECT * FROM user_info WHERE name LIKE ? AND "
				+ "email = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%"+userName+"%");
			pstmt.setString(2, email);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				result = rs.getString("id");
			}
		} catch(Exception e) {
			throw new Exception(e);
		} finally {
			executeClose(rs, pstmt, conn);
		}
		return result;
	}
	// E: ���̵� �˻�
	

	// S: ��й�ȣ �˻�
	public String srchPw(String id, String email) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		String result = "";
		
		try {
			conn = getConnection();
			sql = "SELECT * FROM user_info WHERE id = ? AND "
				+ "email = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, email);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				result = rs.getString("pwd");
			}
		} catch(Exception e) {
			throw new Exception(e);
		} finally {
			executeClose(rs, pstmt, conn);
		}
		return result;
	}
	// E: ��й�ȣ �˻�
		
}
