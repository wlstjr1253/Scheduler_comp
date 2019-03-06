package kr.record.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import kr.record.domain.DiaryDto;

public class DiaryDao {
	// 싱글턴 패턴
	private static DiaryDao instance = new DiaryDao();
	public static DiaryDao getInstance (){
		return instance;
	}
	private DiaryDao() {}

	// 커넥션 풀로부터 커넥션 할당
	private Connection getConnection() throws Exception {
		Context initCtx = new InitialContext();
		DataSource ds = (DataSource)initCtx.lookup("java:comp/env/jdbc/xe");
		return ds.getConnection();
	}

	// 자원 정리
	private void executeClose(ResultSet rs, PreparedStatement pstmt, 
			Connection conn) throws Exception {
		if ( rs != null ) try { rs.close(); } catch( SQLException e ) {}
		if ( pstmt != null ) try { pstmt.close(); } catch( SQLException e ) {}
		if ( conn != null ) try { conn.close(); } catch( SQLException e ) {}
	}
	
	// S: 일기 등록
	public void insertDiary(DiaryDto diary) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "";
		
		try {
			conn = getConnection();
			sql = "INSERT INTO diary (d_idx, d_title, id, "
				+ "d_content, d_reg_date) VALUES ((SELECT NVL("
				+ "MAX(d_idx), 0)+1 FROM diary), ?, ?, ?, SYSDATE)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, diary.getD_title());
			pstmt.setString(2, diary.getId());
			pstmt.setString(3, diary.getD_content());
			pstmt.executeUpdate();
		} catch(Exception e) {
			throw new Exception(e);
		} finally {
			executeClose(null, pstmt, conn);
		}
	}
	// E: 일기 등록
	
	// S: 월별 일기 조회
	public List<DiaryDto> getDiaryByMonth(String user, int year, int month) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		List<DiaryDto> list = null;
		DiaryDto diary = null;
		
		try {
			conn = getConnection();
			sql = "SELECT * FROM diary "
				+ "WHERE id = ? AND "
				+ "d_reg_date >= TO_DATE(?, 'YYYY-MM-DD HH24:MI:SS') "
				+ "ORDER BY d_reg_date";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user);
			pstmt.setString(2, year+"/"+month+"/"+"01");
			rs = pstmt.executeQuery();
			list = new ArrayList<DiaryDto>();
			while (rs.next()) {
				diary = new DiaryDto();
				diary.setD_idx(rs.getInt("d_idx"));
				diary.setD_title(rs.getString("d_title"));
				diary.setId(rs.getString("id"));
				diary.setD_content(rs.getString("d_content"));
				diary.setD_reg_date(rs.getDate("d_reg_date"));
				
				list.add(diary);
			}
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			executeClose(rs, pstmt, conn);
		}
		return list;
	}
	// E: 월별 일기 조회
	
	// S: 등록전 해당 날짜 일기 갯수 조회
	public int getDiaryCountByDay(String id) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		int count = 0; 
		
		try {
			conn = getConnection();
			sql = "SELECT COUNT(*) FROM diary WHERE "
				+ "TO_DATE(substr(d_reg_date, 1, 9), 'YYYY-MM-DD') = "
				+ "TO_DATE(to_char(sysdate), 'YYYY-MM-DD') AND "
				+ "id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch(Exception e) {
			throw new Exception(e);
		} finally {
			executeClose(rs, pstmt, conn);
		}
		
		return count;
	}
	// E: 등록전 해당 날짜 일기 갯수 조회
	
	// S: 일기 조회
	public DiaryDto getDiaryItem(int idx) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		DiaryDto diary = null;
		String sql = "";
		
		try {
			conn = getConnection();
			sql = "SELECT * FROM diary WHERE d_idx = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idx);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				diary = new DiaryDto();
				
				diary.setD_idx(rs.getInt("d_idx"));
				diary.setD_title(rs.getString("d_title"));
				diary.setId(rs.getString("id"));
				diary.setD_content(rs.getString("d_content"));
				diary.setD_reg_date(rs.getDate("d_reg_date"));
				diary.setD_modify_date(rs.getDate("d_modify_date"));
			}
		} catch(Exception e) {
			throw new Exception(e);
		} finally {
			executeClose(rs, pstmt, conn);
		}
		
		return diary;
	}
	// E: 일기 조회
	
	// S: 일기 수정
	public int updateDiaryItem(DiaryDto diary) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		int count = 0;
		
		try {
			conn = getConnection();
			sql = "UPDATE diary SET d_title=?,d_content=?, "
				+ "d_modify_date=SYSDATE WHERE d_idx=? AND id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, diary.getD_title());
			pstmt.setString(2, diary.getD_content());
			pstmt.setInt(3, diary.getD_idx());
			pstmt.setString(4, diary.getId());
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				count = 1;
			}
		} catch(Exception e) {
			throw new Exception(e);
		} finally {
			executeClose(rs, pstmt, conn);
		}
		return count;
	}
	// E: 일기 수정
	
	
	// S: 일기 삭제
	public int deleteDiary(int idx) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		int count = 0;
		
		try {
			conn = getConnection();
			sql = "DELETE FROM diary WHERE d_idx = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idx);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				count = 1;
			}
		} catch(Exception e) {
			throw new Exception(e);
		} finally {
			executeClose(rs, pstmt, conn);
		}
		
		return count;
	}
	// E: 일기 삭제
}
