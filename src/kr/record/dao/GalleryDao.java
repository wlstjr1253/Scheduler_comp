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

import kr.record.domain.GalleryDto;
import kr.util.FileUtil;

public class GalleryDao {
	// 싱글턴 패턴
	private static GalleryDao instance = new GalleryDao();
	public static GalleryDao getInstance (){
		return instance;
	}
	private GalleryDao() {}
	
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
	
	// S: 갤러리 등록
	public void insertGallery(GalleryDto gallery) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "";
		
		try {
			// 커넥션 풀로부터 커넥션 반환
			conn = getConnection();
			// SQL문 작성
			sql = "INSERT INTO gallery (g_idx, g_title, id, g_content, "
				+ "g_photo1, g_photo2, g_photo3 "
				+ ") VALUES ((SELECT NVL(MAX(g_idx), 0)+1 FROM "
				+ "gallery),?,?,?,?,?,?)";
			// preparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			// SQL문의 ?에 데이터 할당
			pstmt.setString(1, gallery.getG_title());
			pstmt.setString(2, gallery.getId());
			pstmt.setString(3, gallery.getG_content());
			pstmt.setString(4, gallery.getG_photo1());
			pstmt.setString(5, gallery.getG_photo2());
			pstmt.setString(6, gallery.getG_photo3());
			// SQL문 반영
			pstmt.executeUpdate();
			
		} catch(Exception e) {
			throw new Exception(e);
		} finally {
			executeClose(null, pstmt, conn);
		}
	}
	// E: 갤러리 등록
	
	
	// S: 갤러리 목록 조회
	public List<GalleryDto> getGalleryList(int start, int end, 
			String keyword, String id) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		List<GalleryDto> list = null;
		
		try {
			conn = getConnection();
			
			if (keyword == null || "".equals(keyword)) {
				sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM "
					+ "(SELECT * FROM gallery g WHERE g.id = ? AND "
					+ "g_usable = 'Y' ORDER BY g_reg_date DESC)a) WHERE "
					+ "rnum >= ? AND rnum <= ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, id);
				pstmt.setInt(2, start);
				pstmt.setInt(3, end);
			} else {
				sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM "
					+ "(SELECT * FROM gallery g WHERE g.id = ? AND "
					+ "g_usable = 'Y' g_title LIKE ? OR g_content LIKE ? "
					+ "ORDER BY g_reg_date DESC)a) WHERE "
					+ "rnum >= ? AND rnum <= ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, id);
				pstmt.setString(2, keyword);
				pstmt.setString(3, keyword);				
				pstmt.setInt(4, start);
				pstmt.setInt(5, end);
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
			executeClose(null, pstmt ,conn);
		}
		
		return list;
	}
	// E: 갤러리 목록 조회
	
	
	// S: 갤러리 갯수 조회
	public int getGalleryCount(String keyword, String id) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		int count = 0;
		
		try {
			// 커넥션 풀로부터 커넥션 할당
			conn = getConnection();
			
			if (keyword == null || "".equals(keyword)) {
				sql = "SELECT COUNT(*) FROM gallery WHERE id = ? AND "
						+ "g_usable = 'Y' ";
					
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, id);
			} else {
				sql = "SELECT COUNT(*) FROM gallery WHERE id = ? AND "
					+ "g_usable = 'Y' AND"
					+ "g_title LIKE ? OR "
					+ "g_content LIKE ?";
					
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, id);
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
	// E: 갤러리 갯수 조회
	
	
	// S: 갤러리 항목 (하나) 조회
	public GalleryDto getGalleryItem(int idx) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		GalleryDto gallery = null;
		String sql = "";
		
		try {
			conn = getConnection();
			sql = "SELECT * FROM gallery WHERE g_idx = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idx);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				gallery = new GalleryDto();
				
				gallery.setG_idx(rs.getInt("g_idx"));
				gallery.setG_title(rs.getString("g_title"));
				gallery.setId(rs.getString("id"));
				gallery.setG_content(rs.getString("g_content"));
				gallery.setG_photo1(rs.getString("g_photo1"));
				gallery.setG_photo2(rs.getString("g_photo2"));
				gallery.setG_photo3(rs.getString("g_photo3"));
				gallery.setG_reg_date(rs.getDate("g_reg_date"));
				gallery.setG_modify_date(rs.getDate("g_modify_date"));
				gallery.setG_favorite(rs.getString("g_favorite"));
				
			}
			
		} catch(Exception e) {
			throw new Exception(e);
		} finally {
			executeClose(rs, pstmt, conn);
		}
		
		return gallery;
	}
	// E: 갤러리 항목 (하나) 조회
	
	
	// S: 갤러리 수정 
	public int updateGalleryItem(GalleryDto gallery) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		int count = 0;
		
		try {
			conn = getConnection();
			sql = "UPDATE gallery SET g_title=?,g_content=?,g_photo1=?,"
				+ "g_photo2=?,g_modify_date=SYSDATE WHERE g_idx = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, gallery.getG_title());
			pstmt.setString(2, gallery.getG_content());
			pstmt.setString(3, gallery.getG_photo1());
			pstmt.setString(4, gallery.getG_photo2());
			pstmt.setInt(5, gallery.getG_idx());
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				count = 1;
			}
		} catch(Exception e) {
			if (gallery.getG_photo1() != null) {
				FileUtil.removeFile(gallery.getG_photo1());
				FileUtil.removeFile(gallery.getG_photo2());
				FileUtil.removeFile(gallery.getG_photo3());
			}
			throw new Exception(e);
		} finally {
			executeClose(null, pstmt, conn);
		}
		
		return count;
	}
	// E: 갤러리 수정
	
	// S: 갤러리 삭제
	public void deleteGallery(int idx) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "";
		
		try {
			conn = getConnection();
			sql = "DELETE FROM gallery WHERE g_idx = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idx);
			pstmt.executeUpdate();
		} catch(Exception e) {
			throw new Exception(e);
		} finally {
			executeClose(null, pstmt, conn);
		}
	}
	// E: 갤러리 삭제
}
