package kr.alarm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import kr.alarm.domain.AlarmDto;

public class AlarmDao {
	// 싱글턴 패턴
	private static AlarmDao instance = new AlarmDao();
	public static AlarmDao getInstance() {
		return instance;
	}
	private AlarmDao() {}
	
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
	
	// S: 알람 조회
	public List<AlarmDto> getAlarm(String user) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		List<AlarmDto> list = null;
		
		try {
			conn = getConnection();
			sql = "SELECT * FROM schedule s, alarm a "
				+ "WHERE s.sc_idx = a.sc_idx AND " 
				+ "s.id = ? AND "
				+ "a.al_re = 'Y' AND "
				+ "TO_DATE(s.sc_start, 'YYYY-MM-DD HH24:MI:SS') >= "
				+ "sysdate AND "
				+ "TO_DATE(s.sc_start, 'YYYY-MM-DD HH24:MI:SS') <= "
				+ "sysdate+1 "
				+ "ORDER BY s.sc_start";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user);
			rs = pstmt.executeQuery();
			
			list = new ArrayList<AlarmDto>();
			
			while (rs.next()) {
				AlarmDto alarm = new AlarmDto();
				alarm.setAl_timer(rs.getInt("al_timer"));
				alarm.setAl_re(rs.getString("al_re"));
				alarm.setSc_title(rs.getString("sc_title"));
				alarm.setSc_start(rs.getString("sc_start"));
				alarm.setSc_startDate(rs.getString("sc_start"));
				alarm.setSc_startTime(rs.getString("sc_start"));
				alarm.setSc_idx(rs.getInt("sc_idx"));
				alarm.setAl_idx(rs.getInt("al_idx"));
				
				list.add(alarm);
			}
		} catch(Exception e) {
			throw new Exception(e);
		} finally {
			executeClose(rs, pstmt, conn);
		}
		
		return list;
	}
	// E: 알람 조회
	
	// S: 알람 수정
	public void modifyAlarm(int al_timer, int sc_idx) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "";
		
		try {
			conn = getConnection();
			sql = "UPDATE alarm SET al_timer=? WHERE sc_idx=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, al_timer);
			pstmt.setInt(2, sc_idx);
			pstmt.executeUpdate();
		} catch(Exception e) {
			throw new Exception(e);
		} finally {
			executeClose(null, pstmt, conn);
		}
	}
	// E: 알람 수정	
	
	// S: 알람 끄기
	public int offAlarm(int al_idx) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		int count = 0;
		
		try {
			conn = getConnection();
			sql = "UPDATE alarm SET al_re = 'N' WHERE al_idx = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, al_idx);
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
	// E: 알람 끄기	
}
