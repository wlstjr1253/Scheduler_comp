package kr.favorite.dao;

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
import kr.record.domain.GalleryDto;
import kr.scheduler.domain.SchedulerDto;

public class FavoriteDao {
	// 싱글톤 패턴
	private static FavoriteDao instance = new FavoriteDao();
	public static FavoriteDao getInstance() {
		return instance;
	}
	private FavoriteDao() {}
	
	// 커넥션 풀로부터 커넥션 반환
	private Connection getConnection() throws Exception {
		Context initCtx = new InitialContext();
		DataSource ds = (DataSource)initCtx.lookup("java:comp/env/jdbc/xe");
		return ds.getConnection();
	}
	
	// 자원 정리
	private void executeClose(ResultSet rs, PreparedStatement pstmt,
			Connection conn) {
		if (rs!=null) try {rs.close();}catch(SQLException e) {};
		if (pstmt!=null) try {pstmt.close();}catch(SQLException e) {};
		if (conn!=null) try {conn.close();}catch(SQLException e) {};
	}
	
	// S: 일정 즐겨찾기 추가
	public void addFavSchedule(int sc_idx) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "";
		
		try {
			conn = getConnection();
			sql = "UPDATE schedule SET sc_favorite = 'Y' WHERE "
				+ "sc_idx = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, sc_idx);
			pstmt.executeUpdate();
		} catch(Exception e) {
			throw new Exception(e);
		} finally {
			executeClose(null, pstmt, conn);
		}
	}
	// E: 일정 즐겨찾기 추가	
	
	// S: 일정 즐겨찾기 개수 검색
	public int getFavScCount(String keyword, String user) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		int count = 0;
		
		try {
			conn = getConnection();
			if (keyword == null || "".equals(keyword)) {
				sql = "SELECT COUNT(*) FROM schedule "
					+ "WHERE id = ? AND sc_favorite = 'Y' AND "
					+ "sc_usable = 'Y' ORDER BY sc_reg_date";
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, user);
				
			} else {
				sql = "SELECT COUNT(*) FROM schedule "
					+ "WHERE id = ? AND sc_favorite = 'Y' AND "
					+ "sc_usable = 'Y' AND "
					+ "(sc_content LIKE ? OR "
					+ "sc_title LIKE ? OR "
					+ "sc_place LIKE ?) "
					+ "ORDER BY sc_reg_date";
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, user);
				pstmt.setString(2, "%"+keyword+"%");
				pstmt.setString(3, "%"+keyword+"%");
				pstmt.setString(4, "%"+keyword+"%");
			}
			
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
	// E: 일정 즐겨찾기 개수 검색
	
	// S: 일정 즐겨찾기 조회
	public List<SchedulerDto> getFavScheduleList(int start, int end, 
			String keyword, String user, int sc_idx) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		List<SchedulerDto> list = null;
		
		try {
			conn = getConnection();
			if (keyword == null || "".equals(keyword)) {
				sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM("
					+ "SELECT * FROM schedule s "
					+ "WHERE s.id = ? AND s.sc_usable = 'Y' "
					+ "AND s.sc_favorite = 'Y' "
					+ "ORDER BY s.sc_start DESC)a)"
					+ "WHERE rnum >= ? AND rnum <= ?";
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, user);
				pstmt.setInt(2, start);
				pstmt.setInt(3, end);
			} else {
				sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM("
					+ "SELECT * FROM schedule s "
					+ "WHERE s.id = ? AND s.sc_usable = 'Y' "
					+ "AND s.sc_favorite = 'Y' AND "
					+ "(s.sc_title LIKE ? OR "
					+ "s.sc_place LIKE ? OR "
					+ "s.sc_content LIKE ?) "
					+ "ORDER BY s.sc_start DESC)a)"
					+ "WHERE rnum >= ? AND rnum <= ?";
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, user);
				pstmt.setString(2, "%"+keyword+"%");
				pstmt.setString(3, "%"+keyword+"%");
				pstmt.setString(4, "%"+keyword+"%");
				pstmt.setInt(5, start);
				pstmt.setInt(6, end);
			}
			
			rs = pstmt.executeQuery();
			
			list = new ArrayList<SchedulerDto>();
			
			while (rs.next()) {
				SchedulerDto schedule = new SchedulerDto();
				schedule.setSc_idx(rs.getInt("sc_idx"));
				schedule.setSc_title(rs.getString("sc_title"));
				schedule.setId(rs.getString("id"));
				schedule.setSc_place(rs.getString("sc_place"));
				schedule.setSc_start(rs.getString("sc_start"));
				schedule.setSc_startDate(rs.getString("sc_start").substring(0, 8));
				schedule.setSc_startTime(rs.getString("sc_start").substring(9, 14));
				schedule.setSc_end(rs.getString("sc_end"));
				schedule.setSc_endDate(rs.getString("sc_end").substring(0, 8));
				schedule.setSc_endTime(rs.getString("sc_end").substring(9, 14));
				schedule.setSc_all_day(rs.getString("sc_all_day"));
				schedule.setSc_content(rs.getString("sc_content"));
				schedule.setSc_favorite(rs.getString("sc_favorite"));
				
				list.add(schedule);
			}
			
		} catch(Exception e) {
			throw new Exception(e);
		} finally {
			executeClose(rs, pstmt, conn);
		}
		
		return list;
	}
	// E: 일정 즐겨찾기 조회
	
	// S: 다이어리 등록 조회
	public String getFavDiary(int d_idx) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		String chkDiary = "";
		
		try {
			conn = getConnection();
			sql = "SELECT d_favorite FROM diary WHERE d_idx = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, d_idx);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				chkDiary = rs.getString(1);
			}
		} catch(Exception e) {
			throw new Exception(e);
		} finally {
			executeClose(rs, pstmt, conn);
		}
		
		return chkDiary;
	}
	// E: 다이어리 등록 조회	
	
	// S: 다이어리 즐겨찾기 추가
	public void addFavDiary(int d_idx) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "";
		try {
			conn = getConnection();
			sql = "UPDATE diary SET d_favorite = 'Y' WHERE "
				+ "d_idx = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, d_idx);
			pstmt.executeUpdate();
		} catch(Exception e) {
			throw new Exception(e);
		} finally {
			executeClose(null, pstmt, conn);
		}
	}
	// E: 다이어리 즐겨찾기 추가
	
	// S: 다이어리 즐겨찾기 개수 검색
	public int getFavDCount(String keyword, String user) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		int count = 0;
		
		try {
			conn = getConnection();
			if (keyword == null || "".equals(keyword)) {
				sql = "SELECT COUNT(*) FROM diary "
					+ "WHERE id = ? AND d_favorite = 'Y' AND "
					+ "d_usable = 'Y' ORDER BY d_reg_date";
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, user);
				
			} else {
				sql = "SELECT COUNT(*) FROM diary "
					+ "WHERE id = ? AND d_favorite = 'Y' AND "
					+ "d_usable = 'Y' AND "
					+ "(d_content LIKE ? OR "
					+ "d_title LIKE ?) "
					+ "ORDER BY d_reg_date";
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, user);
				pstmt.setString(2, "%"+keyword+"%");
				pstmt.setString(3, "%"+keyword+"%");
			}
			
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
	// E: 다이어리 즐겨찾기 개수 검색
	
	// S: 다이어리 즐겨찾기 조회
	public List<DiaryDto> getFavDiaryList(int start, int end, 
			String keyword, String user, int d_idx) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		List<DiaryDto> list = null;
		
		try {
			conn = getConnection();
			if (keyword == null || "".equals(keyword)) {
				sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM("
					+ "SELECT * FROM diary d "
					+ "WHERE d.id = ? AND d.d_usable = 'Y' "
					+ "AND d.d_favorite = 'Y' "
					+ "ORDER BY d.d_reg_date DESC)a)"
					+ "WHERE rnum >= ? AND rnum <= ?";
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, user);
				pstmt.setInt(2, start);
				pstmt.setInt(3, end);
			} else {
				sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM("
					+ "SELECT * FROM diary d "
					+ "WHERE d.id = ? AND d.d_usable = 'Y' "
					+ "AND d.d_favorite = 'Y' AND "
					+ "(d.d_title LIKE ? OR "
					+ "d.d_content LIKE ?) "
					+ "ORDER BY d.d_reg_date DESC)a)"
					+ "WHERE rnum >= ? AND rnum <= ?";
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, user);
				pstmt.setString(2, "%"+keyword+"%");
				pstmt.setString(3, "%"+keyword+"%");
				pstmt.setInt(4, start);
				pstmt.setInt(5, end);
			}
			
			rs = pstmt.executeQuery();
			
			list = new ArrayList<DiaryDto>();
			
			while (rs.next()) {
				DiaryDto diary = new DiaryDto();
				diary.setD_idx(rs.getInt("d_idx"));
				diary.setD_title(rs.getString("d_title"));
				diary.setId(rs.getString("id"));
				diary.setD_content(rs.getString("d_content"));
				diary.setD_reg_date(rs.getDate("d_reg_date"));
				
				list.add(diary);
			}
			
		} catch(Exception e) {
			throw new Exception(e);
		} finally {
			executeClose(rs, pstmt, conn);
		}
		
		return list;
	}
	// E: 다이어리 즐겨찾기 조회
	
	
	// S: 갤러리 등록 조회
	public String getFavGallery(int g_idx) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		String g_fav = "";
		
		try {
			conn = getConnection();
			sql = "SELECT g_favorite FROM gallery WHERE g_idx = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, g_idx);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				g_fav = rs.getString(1);
			}
		} catch(Exception e) {
			throw new Exception(e);
		} finally {
			executeClose(rs, pstmt, conn);
		}
		
		return g_fav;
	}
	// E: 갤러리 등록 조회
	
	// S: 갤러리 즐겨찾기 추가
	public void addFavGallery(int g_idx) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "";
		try {
			conn = getConnection();
			sql = "UPDATE gallery SET g_favorite = 'Y' WHERE "
				+ "g_idx = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, g_idx);
			pstmt.executeUpdate();
		} catch(Exception e) {
			throw new Exception(e);
		} finally {
			executeClose(null, pstmt, conn);
		}
	}
	// E: 갤러리 즐겨찾기 추가
	
	
	// S: 갤러리 총 갯수
	public int getFavGalleryCount(String keyword, String user) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		int count = 0;
		
		try {
			conn = getConnection();
			if (keyword == null || "".equals(keyword)) {
				sql = "SELECT COUNT(*) FROM gallery WHERE id = ? AND "
					+ "g_favorite = 'Y' AND "
					+ "g_usable = 'Y' "
					+ "ORDER BY g_reg_date";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, user);
			} else {
				sql = "SELECT COUNT(*) FROM gallery WHERE id = ? AND "
					+ "g_favorite = 'Y' AND "
					+ "g_usable = 'Y' AND "
					+ "(g_content LIKE ? OR "
					+ "g_title LIKE ? OR "
					+ "g_photo1 LIKE ?) "
					+ "ORDER BY g_reg_date";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, user);
				pstmt.setString(2, "%"+keyword+"%");
				pstmt.setString(3, "%"+keyword+"%");
				pstmt.setString(4, "%"+keyword+"%");
			}
			
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
	// E: 갤러리 총 갯수
	
	
	// S: 갤러리 즐겨찾기 조회
	public List<GalleryDto> getFavGalleryList(int start, int end, 
			String keyword, String user) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		List<GalleryDto> list = null;
		
		try {
			conn = getConnection();
			if (keyword == null || "".equals(keyword)) {
				sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM("
					+ "SELECT * FROM gallery g "
					+ "WHERE g.id = ? AND g.g_usable = 'Y' "
					+ "AND g.g_favorite = 'Y' "
					+ "ORDER BY g.g_reg_date DESC)a)"
					+ "WHERE rnum >= ? AND rnum <= ?";
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, user);
				pstmt.setInt(2, start);
				pstmt.setInt(3, end);
			} else {
				sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM("
					+ "SELECT * FROM gallery g "
					+ "WHERE g.id = ? AND g.g_usable = 'Y' "
					+ "AND g.g_favorite = 'Y' AND "
					+ "(g.g_title LIKE ? OR "
					+ "g.g_photo1 LIKE ? OR "
					+ "g.g_photo2 LIKE ? OR "
					+ "g.g_content LIKE ?) "
					+ "ORDER BY g.g_reg_date DESC)a)"
					+ "WHERE rnum >= ? AND rnum <= ?";
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, user);
				pstmt.setString(2, "%"+keyword+"%");
				pstmt.setString(3, "%"+keyword+"%");
				pstmt.setString(4, "%"+keyword+"%");
				pstmt.setString(5, "%"+keyword+"%");
				pstmt.setInt(6, start);
				pstmt.setInt(7, end);
			}
			
			rs = pstmt.executeQuery();
			
			list = new ArrayList<GalleryDto>();
			
			while (rs.next()) {
				GalleryDto gallery = new GalleryDto();
				gallery.setG_idx(rs.getInt("g_idx"));
				gallery.setG_title(rs.getString("g_title"));
				gallery.setId(rs.getString("id"));
				gallery.setG_content(rs.getString("g_content"));
				gallery.setG_reg_date(rs.getDate("g_reg_date"));
				gallery.setG_photo1(rs.getString("g_photo1"));
				gallery.setG_photo2(rs.getString("g_photo2"));
				gallery.setG_photo3(rs.getString("g_photo3"));
				gallery.setG_favorite(rs.getString("g_favorite"));
				
				list.add(gallery);
			}
			
		} catch(Exception e) {
			throw new Exception(e);
		} finally {
			executeClose(rs, pstmt, conn);
		}
		return list;
	}
	// E: 갤러리 즐겨찾기 조회
	
	
	// S: 즐겨찾기 삭제
	public void removeFavorite(int idx, String part) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "";

		try {
			conn = getConnection();
			if (part.equals("schedule")) {
				sql = "UPDATE schedule SET sc_favorite='N' WHERE sc_idx = ?";
			} else if (part.equals("diary")) {
				sql = "UPDATE diary SET d_favorite='N' WHERE d_idx = ?";
			} else if (part.equals("gallery")) {
				sql = "UPDATE gallery SET g_favorite='N' WHERE g_idx = ?";
			}
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idx);
			pstmt.executeUpdate();
		} catch(Exception e) {
			throw new Exception(e);
		} finally {
			executeClose(null, pstmt, conn);
		}
	}
	// E: 즐겨찾기 삭제
	
}
