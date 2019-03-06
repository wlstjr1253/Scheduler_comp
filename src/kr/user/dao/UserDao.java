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
	// 싱글턴 패턴
	private static UserDao instance = new UserDao();
	public static UserDao getInstance() {
		return instance;
	}
	private UserDao() {}
	
	// 커넥션 풀로부터 커넥션을 할당
	private Connection getConnection() throws Exception {
		Context initCtx = new InitialContext();
		DataSource ds = (DataSource)initCtx.lookup("java:comp/env/jdbc/xe");
		return ds.getConnection();
	}
	
	// 자원정리
	private void executeClose(ResultSet rs,
			PreparedStatement pstmt,Connection conn){
		if(rs!=null)try{rs.close();}catch(SQLException e){}
		if(pstmt!=null)try{pstmt.close();}catch(SQLException e){}
		if(conn!=null)try{conn.close();}catch(SQLException e){}
	}
	
	// S: 회원가입 insertUser
	public void insertUser(UserDto user) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		String sql = null;
		
		try {
			// 커넥션 풀로부터 커넥션 할당
			conn = getConnection();
			// 오토 커밋 해제
			conn.setAutoCommit(false);
			// SQL문 작성
			sql = "INSERT INTO user_auth (id) VALUES (?)";
			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			// SQL문의 ?에 데이터를 할당
			pstmt.setString(1, user.getId());
			// SQL문 반영
			pstmt.executeUpdate();
			
			// SQL문 작성
			sql = "INSERT INTO user_info (id,pwd,name,phone,email,"
				+ "reg_date) VALUES (?,?,?,?,?,SYSDATE)";
			// PreparedStatement 객체 생성
			pstmt2 = conn.prepareStatement(sql);
			// SQL문의 ?에 데이터를 할당
			pstmt2.setString(1, user.getId());
			pstmt2.setString(2, user.getPwd());
			pstmt2.setString(3, user.getName());
			pstmt2.setString(4, user.getPhone());
			pstmt2.setString(5, user.getEmail());
			// SQL문 반영
			pstmt2.executeUpdate();
			
			// 정상적으로 SQL문이 반영되면 commit
			conn.commit();
		} catch(Exception e) {
			// 예외가 발생하면 rollback
			conn.rollback();
			e.printStackTrace();
		} finally {
			executeClose(null, pstmt2, null);
			executeClose(null, pstmt, conn);
		}
	}
	// E: 회원가입 insertUser
	
	// S: 회원 검색 getUser
	public UserDto getUser(String id) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		UserDto user = null;
		
		try {
			// 커넥션 풀로부터 커넥션 할당
			conn = getConnection();
			// SQL문 작성
			sql = "SELECT * FROM user_auth u LEFT OUTER JOIN "
				+ "user_info d ON u.id = d.id "
				+ "WHERE u.id = ?";
			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			// SQL문의 ?에 데이터 할당
			pstmt.setString(1, id);
			// SQL문을 반영하고 ResultSet 반환
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
	// E: 회원 검색 getUser
	
	// S: 메일 체크 getUserByMail
	public UserDto getUserByMail(String email) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		UserDto user = null;
		
		try {
			// 커넥션 풀로부터 커넥션 할당
			conn = getConnection();
			// SQL문 작성
			sql = "SELECT * FROM user_info "
				+ "WHERE email = ?";
			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			// SQL문의 ?에 데이터 할당
			pstmt.setString(1, email);
			// SQL문을 반영하고 ResultSet 반환
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
	// E: 메일 체크 getUserByMail
	
	// S: 회원 정보 수정 updateUser
	public boolean updateUser(UserDto user) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		boolean check = false;
		
		try {
			// 커넥션 풀로부터 커넥션을 할당
			conn = getConnection();
			// SQL문 작성
			sql = "UPDATE user_info SET "
				+ "pwd=?,phone=?,email=? "
				+ "WHERE id=? and name=?";
			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			// SQL문의 ?에 데이터 할당
			pstmt.setString(1, user.getPwd());
			pstmt.setString(2, user.getPhone());
			pstmt.setString(3, user.getEmail());
			pstmt.setString(4, user.getId());
			pstmt.setString(5, user.getName());
			// SQL문 반영 및 resultSet 저장
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
	// E: 회원 정보 수정 updateUser
	
	// S: 회원탈퇴
	public void removeUser(String id) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		String sql = "";
		
		try {
			// 커넥션 풀로부터 커넥션을 할당
			conn = getConnection();
			// 오토 커밋 해제
			conn.setAutoCommit(false);
			
			// SQL문 작성 - 권한 변경
			sql = "UPDATE user_auth SET auth=0 WHERE id=?";
			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			// SQL문 ?에 데이터 할당
			pstmt.setString(1, id);
			// SQL문 반영
			pstmt.executeUpdate();
			
			// SQL문 작성 - 일정 감춤
			sql = "UPDATE schedule SET sc_usable='N' WHERE id=?"; 
			// PreparedStatement 객체 생성
			pstmt2 = conn.prepareStatement(sql);
			// SQL문의 ?에 데이터를 할당
			pstmt2.setString(1, id);
			// SQL문 반영
			pstmt2.executeUpdate();
			
			// SQL문 작성 - 회원정보 삭제
			sql = "DELETE FROM user_info WHERE id=?";
			// PreparedStatement 객체 생성
			pstmt3 = conn.prepareStatement(sql);
			// SQL문의 ?에 데이터를 할당
			pstmt3.setString(1, id);
			// SQL문 반영
			pstmt3.executeUpdate();
			
			// 성공적으로 SQL문 반영
			conn.commit();
		} catch(Exception e) {
			// SQL문 반영 실패
			conn.rollback();
			throw new Exception(e);
		} finally {
			executeClose(null, pstmt3, null);
			executeClose(null, pstmt2, null);
			executeClose(null, pstmt, conn);
		}
	}
	// E: 회원탈퇴
	
	// S: 공유 멤버 검색
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
	// E: 공유 멤버 검색
	
	// S: 일정 작성자 조회
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
	// E: 일정 작성자 조회
	
	
	// S: 아이디 검색
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
	// E: 아이디 검색
	

	// S: 비밀번호 검색
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
	// E: 비밀번호 검색
		
}
