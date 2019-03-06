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

import kr.scheduler.domain.SchedulerDto;

public class SchedulerDao {
	// �̱��� ����
	private static SchedulerDao instance = new SchedulerDao();
	public static SchedulerDao getInstance() {
		return instance;
	}
	private SchedulerDao() {}
	
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
	
	// S: ���� ���
	public void addSchedule(SchedulerDto schedule) throws Exception {
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
			sql = "INSERT INTO schedule (sc_idx, sc_title, id, "
				+ "sc_place,sc_start,sc_end,sc_all_day,sc_content,"
				+ "sc_reg_date) VALUES ((select nvl(max(sc_idx), "
				+ "0)+1 from schedule),?,?,?,?,?,?,?,SYSDATE)";
			// PreparedStatement ��ü ����
			pstmt = conn.prepareStatement(sql);
			// SQL���� ?�� �����͸� �Ҵ�
			pstmt.setString(1, schedule.getSc_title());
			pstmt.setString(2, schedule.getId());
			pstmt.setString(3, schedule.getSc_place());
			pstmt.setString(4, schedule.getSc_start());
			pstmt.setString(5, schedule.getSc_end());
			pstmt.setString(6, schedule.getSc_all_day());
			pstmt.setString(7, schedule.getSc_content());
			// SQL�� �ݿ�
			pstmt.executeUpdate();
			
			// SQL�� �ۼ�
			sql = "INSERT INTO alarm (al_idx,sc_idx,al_timer) VALUES ("
				+ "(SELECT nvl(max(al_idx), 0)+1 from alarm), (SELECT NVL(max(sc_idx), "
				+ "0) from schedule),?)";
			// PreparedStatement ��ü ����
			pstmt2 = conn.prepareStatement(sql);
			// SQL���� ?�� ������ �Ҵ�
			pstmt2.setInt(1, schedule.getAl_timer());
			// SQL�� �ݿ�
			pstmt2.executeUpdate();

			conn.commit();
		} catch(Exception e) {
			conn.rollback();
			throw new Exception(e);
		} finally {
			executeClose(null, pstmt2, null);
			executeClose(null, pstmt, conn);
		}
	}
	// E: ���� ���
	
	
	// S: ��ü ���� ����, �˻� ���� ����
	public int getScheduleCount(String keyword, String id) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		int count = 0;
		
		try {
			// Ŀ�ؼ� Ǯ�κ��� Ŀ�ؼ� �Ҵ�
			conn = getConnection();
			
			if (keyword == null || "".equals(keyword)) {
				// ��ü�� ����
				sql = "SELECT COUNT(*) FROM schedule WHERE id = ? AND "
					+ "sc_usable = 'Y' AND "
					+ "TO_DATE(sc_end, 'YYYY-MM-DD HH24:MI:SS') >= "
					+ "sysdate+2";
				// preparedStatement ��ü ����
				pstmt = conn.prepareStatement(sql);
				// sql���� ?�� ������ �Ҵ�
				pstmt.setString(1, id);
			} else {
				// �˻��� ����
				sql = "SELECT COUNT(*) FROM schedule WHERE id = ? AND "
					+ "sc_usable = 'Y' AND "
					+ "TO_DATE(sc_end, 'YYYY-MM-DD HH24:MI:SS') >= "
					+ "sysdate+2 AND "
					+ "(sc_title LIKE ? OR "
					+ "sc_place LIKE ? OR "
					+ "sc_content LIKE ?)";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, id);
				pstmt.setString(2, "%"+keyword+"%");
				pstmt.setString(3, "%"+keyword+"%");
				pstmt.setString(4, "%"+keyword+"%");
			}
			
			rs = pstmt.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
				
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			executeClose(rs, pstmt, conn);
		}
		
		return count;
	}
	// E: ��ü ���� ����, �˻� ���� ����
	
	
	// S: ������ ��ü ���� ����, �˻� ���� ����
	public int getSharedScheduleCount(String keyword, String id) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		int count = 0;
		
		try {
			// Ŀ�ؼ� Ǯ�κ��� Ŀ�ؼ� �Ҵ�
			conn = getConnection();
			
			if (keyword == null || "".equals(keyword)) {
				// ��ü�� ����
				sql = "SELECT COUNT(*) FROM schedule s, schedule_invited_user i WHERE "
					+ "s.sc_idx = i.sc_idx AND "
					+ "i.id = ? AND " 
					+ "sc_usable = 'Y' AND "
					+ "TO_DATE(sc_end, 'YYYY-MM-DD HH24:MI:SS') >= "
					+ "sysdate+2";
				// preparedStatement ��ü ����
				pstmt = conn.prepareStatement(sql);
				// sql���� ?�� ������ �Ҵ�
				pstmt.setString(1, id);
			} else {
				// �˻��� ����
				sql = "SELECT COUNT(*) FROM schedule s, schedule_invited_user i WHERE "
					+ "s.sc_idx = i.sc_idx AND "
					+ "i.id = ? AND " 
					+ "sc_usable = 'Y' AND "
					+ "TO_DATE(sc_end, 'YYYY-MM-DD HH24:MI:SS') >= "
					+ "sysdate AND "
					+ "(s.sc_title LIKE ? OR "
					+ "s.sc_place LIKE ? OR "
					+ "s.sc_content LIKE ?)";				
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, id);
				pstmt.setString(2, "%"+keyword+"%");
				pstmt.setString(3, "%"+keyword+"%");
				pstmt.setString(4, "%"+keyword+"%");
			}
			
			rs = pstmt.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
				
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			executeClose(rs, pstmt, conn);
		}
		
		return count;
	}
	// E: ������ ��ü ���� ����, �˻� ���� ����
	
	
	// S: �ٰ��� �ϵ� �˻�
	public List<SchedulerDto> getScheduleListCom(int start, int end, 
			String keyword, String id, String day) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<SchedulerDto> list = null;
		String sql = "";
		
		try {
			// Ŀ�ؼ� Ǯ�κ��� Ŀ�ؼ� �Ҵ�
			conn = getConnection();
			
				if (keyword == null || "".equals(keyword)) {
					if (day == "today") { // ���� ���� �˻�
//						sql = "SELECT * FROM schedule WHERE id = ? AND "
//							+ "sc_usable = 'Y' AND "
//							+ "TO_DATE(substr(sc_start,1,9), 'YYYY-MM-DD') <= "
//							+ "TO_DATE(to_char(sysdate,'YYYY-MM-DD' )) AND "
//							+ "TO_DATE(substr(sc_end,1,9), 'YYYY-MM-DD') >= "
//							+ "TO_DATE(to_char(sysdate,'YYYY-MM-DD' )) "
//							+ "ORDER BY sc_start ASC";
						
						sql = "SELECT sc_idx, sc_title, id, sc_place, sc_start, sc_end, "
							+ "sc_content, sc_all_day, sc_favorite FROM schedule WHERE "
							+ "id = ? AND sc_usable = 'Y' AND "
							+ "TO_DATE(substr(sc_start,1,9), 'YYYY-MM-DD') <="
							+ "TO_DATE(to_char(sysdate,'YYYY-MM-DD' )) AND "
							+ "TO_DATE(substr(sc_end,1,9), 'YYYY-MM-DD') >= "
							+ "TO_DATE(to_char(sysdate,'YYYY-MM-DD' )) "
							+ "UNION ALL "
							+ "SELECT sc_idx, sc_title, sc_id, sc_place, sc_start, "
							+ "sc_end, sc_content, sc_all_day, sc_favorite "
							+ "FROM invited_user_vw "
							+ "WHERE id = ? AND sc_usable = 'Y' AND "
							+ "TO_DATE(substr(sc_start,1,9), 'YYYY-MM-DD') <= "
							+ "TO_DATE(to_char(sysdate,'YYYY-MM-DD' )) AND "
							+ "TO_DATE(substr(sc_end,1,9), 'YYYY-MM-DD') >= "
							+ "TO_DATE(to_char(sysdate,'YYYY-MM-DD' )) "
							+ "ORDER BY sc_start";
						
						// PreparedStatement ��ü ����
						pstmt = conn.prepareStatement(sql);
						// SQL���� ?�� ������ �Ҵ�
						pstmt.setString(1, id);
						pstmt.setString(2, id);
						
					} else if (day == "tomorrow") { 
//						sql = "SELECT * FROM schedule WHERE id = ? AND "
//							+ "sc_usable = 'Y' AND "
//							+ "TO_DATE(substr(sc_start,1,9), 'YYYY-MM-DD') <= "
//							+ "TO_DATE(to_char(sysdate+1,'YYYY-MM-DD' )) AND "
//							+ "TO_DATE(substr(sc_end,1,9), 'YYYY-MM-DD') >= "
//							+ "TO_DATE(to_char(sysdate+1,'YYYY-MM-DD' )) "
//							+ "ORDER BY sc_start ASC";	
						
						sql = "SELECT sc_idx, sc_title, id, sc_place, sc_start, sc_end, "
							+ "sc_content, sc_all_day, sc_favorite FROM schedule WHERE "
							+ "id = ? AND sc_usable = 'Y' AND "
							+ "TO_DATE(substr(sc_start,1,9), 'YYYY-MM-DD') <= "
							+ "TO_DATE(to_char(sysdate+1,'YYYY-MM-DD' )) AND "
							+ "TO_DATE(substr(sc_end,1,9), 'YYYY-MM-DD') >= "
							+ "TO_DATE(to_char(sysdate+1,'YYYY-MM-DD' )) "
							+ "UNION ALL "
							+ "SELECT sc_idx, sc_title, sc_id, sc_place, sc_start, "
							+ "sc_end, sc_content, sc_all_day, sc_favorite "
							+ "FROM invited_user_vw "
							+ "WHERE id = ? AND sc_usable = 'Y' AND "
							+ "TO_DATE(substr(sc_start,1,9), 'YYYY-MM-DD') <= "
							+ "TO_DATE(to_char(sysdate+1,'YYYY-MM-DD' )) AND "
							+ "TO_DATE(substr(sc_end,1,9), 'YYYY-MM-DD') >= "
							+ "TO_DATE(to_char(sysdate+1,'YYYY-MM-DD' )) "
							+ "ORDER BY sc_start";
						
						// PreparedStatement ��ü ����
						pstmt = conn.prepareStatement(sql);
						
						// SQL���� ?�� ������ �Ҵ�
						pstmt.setString(1, id);
						pstmt.setString(2, id);

					} else {
//						// 2�� �� ���� ����
//						sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM "
//							+ "(SELECT * FROM schedule s WHERE s.id = ? AND "
//							+ "sc_usable = 'Y' AND "
//							+ "TO_DATE(s.sc_end, 'YYYY-MM-DD HH24:MI:SS') >= "
//							+ "sysdate+2 "
//							+ "ORDER BY s.sc_start ASC)a)"
//							+ "WHERE rnum >= ? AND rnum <= ?";
						
						// 2�� �� ���� ���� (�ڽ��� �� �� �� �������� ��)
						sql = "SELECT * FROM(SELECT c.*, rownum rnum FROM (SELECT * FROM "
							+ "(SELECT sc_idx, sc_title, id, sc_place, sc_start, sc_end, "
							+ "sc_content, sc_all_day, sc_favorite FROM schedule WHERE "
							+ "id = ? AND sc_usable = 'Y' AND "
							+ "TO_DATE(sc_end, 'YYYY-MM-DD HH24:MI:SS') >= "
							+ "sysdate+2 "
							+ "UNION ALL "
							+ "SELECT sc_idx, sc_title, sc_id, sc_place, sc_start, "
							+ "sc_end, sc_content, sc_all_day, sc_favorite "
							+ "FROM invited_user_vw "
							+ "WHERE id = ? AND sc_usable = 'Y' AND "
							+ "TO_DATE(sc_end, 'YYYY-MM-DD HH24:MI:SS') >= "
							+ "sysdate+2 "
							+ "ORDER BY sc_start))c) "
							+ "WHERE rnum between ? and ?";
						
						// PreparedStatement ��ü ����
						pstmt = conn.prepareStatement(sql);
						
						// SQL���� ?�� ������ �Ҵ�
						pstmt.setString(1, id);
						pstmt.setString(2, id);
						pstmt.setInt(3, start);
						pstmt.setInt(4, end);
					}	

			} else {
//				// �˻��� ����
//				sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM "
//					+ "(SELECT * FROM schedule s WHERE s.id = ? AND "
//					+ "sc_usable = 'Y' AND "
//					+ "TO_DATE(s.sc_end, 'YYYY-MM-DD HH24:MI:SS') >= "
//					+ "TO_DATE(to_char(sysdate+2,'YYYY-MM-DD' )) AND "
//					+ "(s.sc_title LIKE ? OR "
//					+ "s.sc_place LIKE ? OR "
//					+ "s.sc_content LIKE ?) "
//					+ "ORDER BY s.sc_start ASC)a) "
//					+ "WHERE rnum >= ? AND rnum <= ?";
				
				// 2�� �� ���� �˻��� ���� (�ڽ��� �� �� �� �������� ��)
				sql = "SELECT * FROM(SELECT c.*, rownum rnum FROM (SELECT * FROM "
					+ "(SELECT sc_idx, sc_title, id, sc_place, sc_start, sc_end, "
					+ "sc_content, sc_all_day, sc_favorite FROM schedule WHERE "
					+ "id = ? AND sc_usable = 'Y' AND "
					+ "TO_DATE(sc_end, 'YYYY-MM-DD HH24:MI:SS') >= "
					+ "sysdate+2 AND "
					+ "(sc_title LIKE ? OR "
					+ "sc_place LIKE ? OR "
					+ "sc_content LIKE ?)"
					+ "UNION ALL "
					+ "SELECT sc_idx, sc_title, sc_id, sc_place, sc_start, "
					+ "sc_end, sc_content, sc_all_day, sc_favorite "
					+ "FROM invited_user_vw "
					+ "WHERE id = ? AND sc_usable = 'Y' AND "
					+ "TO_DATE(sc_end, 'YYYY-MM-DD HH24:MI:SS') >= "
					+ "sysdate+2 AND "
					+ "(sc_title LIKE ? OR "
					+ "sc_place LIKE ? OR "
					+ "sc_content LIKE ?)"
					+ "ORDER BY sc_start))c) "
					+ "WHERE rnum between ? and ?";

				// PreparedStatement ��ü ����
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, id);
				pstmt.setString(2, "%"+keyword+"%"); // sc_title
				pstmt.setString(3, "%"+keyword+"%"); // sc_content
				pstmt.setString(4, "%"+keyword+"%"); // sc_place
				pstmt.setString(5, id);
				pstmt.setString(6, "%"+keyword+"%"); // sc_title
				pstmt.setString(7, "%"+keyword+"%"); // sc_content
				pstmt.setString(8, "%"+keyword+"%"); // sc_place
				pstmt.setInt(9, start);
				pstmt.setInt(10, end);
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
				// schedule.setA_id(rs.getString("a.id"));
				
				list.add(schedule);
			}
			
		} catch(Exception e) {
			// throw new Exception(e);
			e.printStackTrace();
		} finally {
			executeClose(rs, pstmt, conn);
		}
		
		return list;
	}
	// E: �ٰ��� �ϵ�
	
	
	// S: ������ �ٰ��� �ϵ� �˻�
	public List<SchedulerDto> getSharedScheduleListCom(int start, int end, 
			String keyword, String id, String day) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<SchedulerDto> list = null;
		String sql = "";
		
		try {
			// Ŀ�ؼ� Ǯ�κ��� Ŀ�ؼ� �Ҵ�
			conn = getConnection();
			
				if (keyword == null || "".equals(keyword)) {
					if (day == "today") { // ���� ���� �˻�
						sql = "SELECT * FROM schedule s, schedule_invited_user i "
							+ "WHERE s.sc_idx = i.sc_idx AND "
							+ "i.id = ? AND "
							+ "s.sc_usable = 'Y' AND "
							+ "fn_to_date(s.sc_start) <= "
							+ "fn_substr_sysdate(sysdate) AND "
							+ "fn_to_date(s.sc_end) >= "
							+ "fn_substr_sysdate(sysdate) "
							+ "ORDER BY sc_start ASC";
						
						// PreparedStatement ��ü ����
						pstmt = conn.prepareStatement(sql);
						// SQL���� ?�� ������ �Ҵ�
						pstmt.setString(1, id);
						
					} else if (day == "tomorrow") { 
						sql = "SELECT * FROM schedule s, schedule_invited_user i "
							+ "WHERE s.sc_idx = i.sc_idx AND "
							+ "i.id = ? AND "
							+ "sc_usable = 'Y' AND "
							+ "TO_DATE(substr(sc_start,1,9), 'YYYY-MM-DD') <= "
							+ "TO_DATE(to_char(sysdate+1,'YYYY-MM-DD' )) AND "
							+ "TO_DATE(substr(sc_end,1,9), 'YYYY-MM-DD') >= "
							+ "TO_DATE(to_char(sysdate+1,'YYYY-MM-DD' )) "
							+ "ORDER BY sc_start ASC";						
						
						// PreparedStatement ��ü ����
						pstmt = conn.prepareStatement(sql);
						
						// SQL���� ?�� ������ �Ҵ�
						pstmt.setString(1, id);

					} else {
						// 2�� �� ���� ����
						sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM "
							+ "(SELECT * FROM invited_user_vw s WHERE s.id = "
							+ "? AND sc_usable = 'Y' AND "
							+ "TO_DATE(s.sc_end, 'YYYY-MM-DD HH24:MI:SS') >= "
							+ "sysdate+2 "
							+ "ORDER BY s.sc_start ASC)a) "
							+ "WHERE rnum between ? and ?";
						
						// PreparedStatement ��ü ����
						pstmt = conn.prepareStatement(sql);
						
						// SQL���� ?�� ������ �Ҵ�
						pstmt.setString(1, id);
						pstmt.setInt(2, start);
						pstmt.setInt(3, end);
					}	
				
			

			} else {
				// �˻��� ����
				sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM "
					+ "(SELECT * FROM invited_user_vw s WHERE s.id = "
					+ "? AND sc_usable = 'Y' AND "
					+ "TO_DATE(substr(s.sc_end,1,9), 'YYYY-MM-DD') >= "
					+ "TO_DATE(to_char(sysdate+2,'YYYY-MM-DD' )) AND "
					+ "(sc_title LIKE ? OR " 
					+ "sc_place LIKE ? OR " 
					+ "sc_content LIKE ?) " 
					+ "ORDER BY s.sc_start ASC)a) "
					+ "WHERE rnum between ? and ?";

				// PreparedStatement ��ü ����
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, id);
				pstmt.setString(2, "%"+keyword+"%"); // sc_title
				pstmt.setString(3, "%"+keyword+"%"); // sc_place
				pstmt.setString(4, "%"+keyword+"%"); // sc_content
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
	// E: ������ �ٰ��� �ϵ� �˻�
	
	
	// S: ������ �ϵ� ����
	public int getPassedScheduleCount(String keyword, String id) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		int count = 0;
		
		
		try {
			// Ŀ�ؼ� Ǯ�κ��� Ŀ�ؼ� �Ҵ�
			conn = getConnection();
			
			if (keyword == null || "".equals(keyword)) {
				// ��ü�� ����
				sql = "SELECT count(*) FROM schedule WHERE id = ? AND "
					+ "sc_usable = 'Y' AND "
					+ "TO_DATE(sc_end, 'YYYY-MM-DD HH24:MI:SS') < sysdate";
				// preparedStatement ��ü ����
				pstmt = conn.prepareStatement(sql);
				// sql���� ?�� ������ �Ҵ�
				pstmt.setString(1, id);
			} else {
				// �˻��� ����
				sql = "SELECT COUNT(*) FROM schedule WHERE id = ? AND "
					+ "sc_usable = 'Y' AND "
					+ "TO_DATE(sc_end, 'YYYY-MM-DD HH24:MI:SS') < sysdate AND "
					+ "(sc_title LIKE ? OR "
					+ "sc_place LIKE ? OR "
					+ "sc_content LIKE ?)";

				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, id);
				pstmt.setString(2, "%"+keyword+"%");
				pstmt.setString(3, "%"+keyword+"%");
				pstmt.setString(4, "%"+keyword+"%");
			}
			
			rs = pstmt.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
				
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			executeClose(rs, pstmt, conn);
		}
		
		return count;
	}
	// E: ������ �ϵ� ����
	
	// S: ������ ������ �ϵ� ����
	public int getSharedPassedScheduleCount(String keyword, String id) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		int count = 0;
		
		try {
			// Ŀ�ؼ� Ǯ�κ��� Ŀ�ؼ� �Ҵ�
			conn = getConnection();
			
			if (keyword == null || "".equals(keyword)) {
				// ��ü�� ����
				sql = "SELECT COUNT(*) FROM "
					+ "(SELECT * FROM invited_user_vw s WHERE s.id = ? AND "
					+ "sc_usable = 'Y' AND "
					+ "TO_DATE(s.sc_end, 'YYYY-MM-DD HH24:MI:SS') < " 
					+ "sysdate)";
				
				// preparedStatement ��ü ����
				pstmt = conn.prepareStatement(sql);
				// sql���� ?�� ������ �Ҵ�
				pstmt.setString(1, id);
			} else {
				// �˻��� ����
//				sql = "SELECT COUNT(*) FROM (SELECT a.*, rownum rnum FROM "
//					+ "(SELECT * FROM invited_user_vw s WHERE s.id = ? AND "
//					+ "sc_usable = 'Y' AND "
//					+ "TO_DATE(s.sc_end, 'YYYY-MM-DD HH24:MI:SS') < " 
//					+ "sysdate AND "
//					+ "(sc_title LIKE ? OR "
//					+ "sc_place LIKE ? OR "
//					+ "sc_content LIKE ?)"
//					+ "ORDER BY s.sc_start ASC)a) "
//					+ "WHERE rnum between ? and ?";
				sql = "SELECT COUNT(*) FROM "
					+ "(SELECT * FROM invited_user_vw s WHERE s.id = ? AND "
					+ "sc_usable = 'Y' AND "
					+ "TO_DATE(s.sc_end, 'YYYY-MM-DD HH24:MI:SS') < " 
					+ "sysdate AND "
					+ "(sc_title LIKE ? OR "
					+ "sc_place LIKE ? OR "
					+ "sc_content LIKE ?))";

				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, id);
				pstmt.setString(2, "%"+keyword+"%");
				pstmt.setString(3, "%"+keyword+"%");
				pstmt.setString(4, "%"+keyword+"%");
			}
			
			rs = pstmt.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
				
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			executeClose(rs, pstmt, conn);
		}
		
		return count;
	}
	// E: ������ ������ �ϵ� ����
	
	// S: ������ �ϵ� 
	public List<SchedulerDto> getPassedScheduleListCom(int start, int end, 
			String keyword, String id) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<SchedulerDto> list = null;
		String sql = "";
		
		try {
			// Ŀ�ؼ� Ǯ�κ��� Ŀ�ؼ� �Ҵ�
			conn = getConnection();
			
			if (keyword == null || "".equals(keyword)) {	
//				sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM "
//					+ "(SELECT * FROM schedule s WHERE s.id = ? AND "
//					+ "sc_usable = 'Y' AND "
//					+ "TO_DATE(sc_end, 'YYYY-MM-DD HH24:MI:SS') < sysdate "
//					+ "ORDER BY s.sc_end DESC)a)"
//					+ "WHERE rnum >= ? AND rnum <= ?";
				
				sql = "SELECT * FROM (SELECT c.*, rownum rnum FROM "
					+ "(SELECT * FROM(SELECT sc_idx, sc_title, id, sc_place, "
					+ "sc_start, sc_end, sc_content, sc_all_day, sc_favorite "
					+ "FROM schedule WHERE id = ? AND sc_usable = 'Y' "
					+ "AND TO_DATE(sc_end, 'YYYY-MM-DD HH24:MI:SS') < "
					+ "sysdate "
					+ "UNION ALL "
					+ "SELECT sc_idx, sc_title, sc_id, sc_place, sc_start, "
					+ "sc_end, sc_content, sc_all_day, sc_favorite "
					+ "FROM invited_user_vw "
					+ "WHERE id = ? AND "
					+ "TO_DATE(sc_end, 'YYYY-MM-DD HH24:MI:SS') < "
					+ "sysdate "
					+ "ORDER BY sc_start DESC))c) "
					+ "WHERE rnum between ? and ?";

				// PreparedStatement ��ü ����
				pstmt = conn.prepareStatement(sql);
				
				// SQL���� ?�� ������ �Ҵ�
				pstmt.setString(1, id);
				pstmt.setString(2, id);
				pstmt.setInt(3, start);
				pstmt.setInt(4, end);
			} else {
				// �˻��� ����
//				sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM "
//					+ "(SELECT * FROM schedule s WHERE s.id = ? AND "
//					+ "sc_usable = 'Y' AND "
//					+ "TO_DATE(sc_end, 'YYYY-MM-DD HH24:MI:SS') < sysdate AND "
//					+ "(sc_title LIKE ? OR "
//					+ "sc_place LIKE ? OR "
//					+ "sc_content LIKE ?) "
//					+ "ORDER BY s.sc_start DESC)a)"
//					+ "WHERE rnum >= ? AND rnum <= ?";
				
				sql = "SELECT * FROM (SELECT c.*, rownum rnum FROM "
					+ "(SELECT * FROM(SELECT sc_idx, sc_title, id, sc_place, "
					+ "sc_start, sc_end, sc_content, sc_all_day, sc_favorite "
					+ "FROM schedule WHERE id = ? AND sc_usable = 'Y' "
					+ "AND TO_DATE(sc_end, 'YYYY-MM-DD HH24:MI:SS') < "
					+ "sysdate "
					+ "(sc_title LIKE ? OR "
					+ "sc_place LIKE ? OR "
					+ "sc_content LIKE ?) "
					+ "UNION ALL "
					+ "SELECT sc_idx, sc_title, sc_id, sc_place, sc_start, "
					+ "sc_end, sc_content, sc_all_day, sc_favorite "
					+ "FROM invited_user_vw "
					+ "WHERE id = ? AND "
					+ "TO_DATE(sc_end, 'YYYY-MM-DD HH24:MI:SS') < "
					+ "sysdate "
					+ "(sc_title LIKE ? OR "
					+ "sc_place LIKE ? OR "
					+ "sc_content LIKE ?) "
					+ "ORDER BY sc_start DESC))c) "
					+ "WHERE rnum between ? and ?";

				// PreparedStatemnet ��ü ����
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, id);
				pstmt.setString(2, "%"+keyword+"%"); // sc_title
				pstmt.setString(3, "%"+keyword+"%"); // sc_place
				pstmt.setString(4, "%"+keyword+"%"); // sc_content
				pstmt.setString(5, id);
				pstmt.setString(6, "%"+keyword+"%"); // sc_title
				pstmt.setString(7, "%"+keyword+"%"); // sc_place
				pstmt.setString(8, "%"+keyword+"%"); // sc_content
				pstmt.setInt(9, start);
				pstmt.setInt(10, end);
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
	// E: ������ �ϵ� 
	
	
	// S: ������ ������ �ϵ�
	public List<SchedulerDto> getSharedPassedSchedule(int start, int end, 
			String keyword, String id) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<SchedulerDto> list = null;
		String sql = "";
		
		try {
			conn = getConnection();
			if (keyword == null || "".equals(keyword)) {
				sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM "
					+ "(SELECT * FROM invited_user_vw s WHERE s.id = ? AND "
					+ "sc_usable = 'Y' AND "
					+ "TO_DATE(s.sc_end, 'YYYY-MM-DD HH24:MI:SS') < " 
					+ "sysdate "
					+ "ORDER BY s.sc_start ASC)a) "
					+ "WHERE rnum between ? and ?";
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, id);
				pstmt.setInt(2, start);
				pstmt.setInt(3, end);
				
			} else {
				sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM "
					+ "(SELECT * FROM invited_user_vw s WHERE s.id = ? AND "
					+ "sc_usable = 'Y' AND "
					+ "TO_DATE(s.sc_end, 'YYYY-MM-DD HH24:MI:SS') < " 
					+ "sysdate AND "
					+ "(sc_title LIKE ? OR "
					+ "sc_place LIKE ? OR "
					+ "sc_content LIKE ?)"
					+ "ORDER BY s.sc_start ASC)a) "
					+ "WHERE rnum between ? and ?";
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, id);
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
	// E: ������ ������ �ϵ�
	
	
	// S: ���� ��ȸ
	public SchedulerDto getScheduleItem(int idx) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		SchedulerDto schedule = null;
		ResultSet rs = null;
		String sql = "";
				
		try {
			conn = getConnection();
			sql = "SELECT * FROM schedule s, alarm a WHERE "
				+ "s.sc_idx = a.sc_idx AND "
				+ "s.sc_idx = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idx);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				schedule = new SchedulerDto();
				schedule.setId(rs.getString("id"));
				schedule.setSc_idx(rs.getInt("sc_idx"));
				schedule.setSc_title(rs.getString("sc_title"));
				schedule.setSc_place(rs.getString("sc_place"));
				schedule.setSc_start(rs.getString("sc_start"));
				schedule.setSc_end(rs.getString("sc_end"));
				schedule.setSc_all_day(rs.getString("sc_all_day"));
				schedule.setSc_content(rs.getString("sc_content"));
				schedule.setSc_favorite(rs.getString("sc_favorite"));
				schedule.setAl_timer(rs.getInt("al_timer"));
				schedule.setAl_re(rs.getString("al_re"));
			}
		} catch(Exception e) {
			throw new Exception(e);
		} finally {
			executeClose(rs, pstmt, conn);			
		}
		
		return schedule;
	}
	// E: ���� ��ȸ
	
	// S: ���� ���� ��ȸ (�Ѱ�)
	public SchedulerDto getSharedSchedule(int sc_idx, String id) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		SchedulerDto schedule = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			conn = getConnection();
			sql = "SELECT * FROM schedule s, schedule_invited_user i "
				+ "WHERE s.sc_idx = i.sc_idx "
				+ "AND i.sc_idx = ? "
				+ "AND i.id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, sc_idx);
			pstmt.setString(2, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				schedule = new SchedulerDto();
				
				schedule.setSc_idx(rs.getInt("sc_idx"));
				schedule.setSc_title(rs.getString("sc_title"));
				schedule.setId(rs.getString("id"));
				schedule.setSc_place(rs.getString("sc_place"));
				schedule.setSc_start(rs.getString("sc_start"));
				schedule.setSc_startDate(rs.getString("sc_start"));
				schedule.setSc_startTime(rs.getString("sc_start"));
				schedule.setSc_end(rs.getString("sc_end"));
				schedule.setSc_endDate(rs.getString("sc_end"));
				schedule.setSc_endTime(rs.getString("sc_end"));
				schedule.setSc_all_day(rs.getString("sc_all_day"));
				schedule.setSc_content(rs.getString("sc_content"));
				schedule.setSc_favorite(rs.getString("sc_favorite"));
			}
		} catch(Exception e) {
			throw new Exception(e);
		} finally {
			executeClose(rs, pstmt, conn);
		}
		
		return schedule;
	}
	// E: ���� ���� ��ȸ (�Ѱ�)
	
	// S: ���� ����
	public void modifySchedule(SchedulerDto schedule) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "";
		
		try {
			conn = getConnection();
			
			sql = "UPDATE schedule SET sc_title=?,sc_place=?,sc_start=?,sc_end=?,"
				+ "sc_all_day=?,sc_content=?,sc_modify_date="
				+ "sysdate WHERE sc_idx=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, schedule.getSc_title());
			pstmt.setString(2, schedule.getSc_place());
			pstmt.setString(3, schedule.getSc_start());
			pstmt.setString(4, schedule.getSc_end());
			pstmt.setString(5, schedule.getSc_all_day());
			pstmt.setString(6, schedule.getSc_content());
			pstmt.setInt(7, schedule.getSc_idx());
			pstmt.executeUpdate();
		
		} catch(Exception e) {
			throw new Exception(e);
		} finally {
			executeClose(null, pstmt, conn);
		}
	}
	// E: ���� ����	
	
	// S: ���� �ε��� ��������
	public int getScIdx() throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		int count = 0;
		
		try {
			conn = getConnection();
			sql = "SELECT (SELECT NVL(MAX(sc_idx), 0)+1 FROM "
				+ "schedule ) FROM dual";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch(Exception e) {
			throw new Exception(e);
		} finally {
			executeClose(null, pstmt, conn);
		}
		return count;
	}
	// E: ���� �ε��� ��������
		
	// S: ������ ���� ��ȸ
	public List<SchedulerDto> getScheduleByMonth(String user, int year, int month) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		SchedulerDto schedule = null;
		List<SchedulerDto> list = null;
		
		int nextMonth = month;
		int nextYear = year;
		if (month == 12) {
			nextMonth = 1;
			nextYear = year + 1;
		} else {
			nextMonth = month + 1;
		}

		try {
			conn = getConnection();
			sql = "SELECT * FROM schedule "
				+ "WHERE id = ? AND sc_usable = 'Y' AND "
				+ "TO_DATE(substr(sc_start, 1, 9), 'YYYY-MM-DD') < "
				+ "TO_DATE(?, 'YYYY-MM-DD') AND "
				+ "TO_DATE(substr(sc_end, 1, 9), 'YYYY-MM-DD') >= "
				+ "TO_DATE(?, 'YYYY-MM-DD') "
				+ "ORDER BY sc_start ASC";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user);
			pstmt.setString(2, nextYear+"/"+nextMonth+"/01");
			pstmt.setString(3, year+"/"+month+"/01");
			
			rs = pstmt.executeQuery();
			
			list = new ArrayList<SchedulerDto>();
			while (rs.next()) {
				schedule = new SchedulerDto();
				
				schedule.setId(rs.getString("id"));
				schedule.setSc_idx(rs.getInt("sc_idx"));
				schedule.setSc_title(rs.getString("sc_title"));
				schedule.setSc_place(rs.getString("sc_place"));
				schedule.setSc_start(rs.getString("sc_start"));
				schedule.setSc_startDate(rs.getString("sc_start"));
				schedule.setSc_startTime(rs.getString("sc_start"));
				schedule.setSc_end(rs.getString("sc_end"));
				schedule.setSc_endDate(rs.getString("sc_end"));
				schedule.setSc_endTime(rs.getString("sc_end"));
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
	// E: ������ ���� ��ȸ
	
	
	// S: ���� ������ ���� ��ȸ
	public List<SchedulerDto> getSharedScheduleByMonth(String id, int year, int month) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		List<SchedulerDto> list = null;
		
		int nextMonth = month;
		int nextYear = year;
		if (month == 12) {
			nextMonth = 1;
			nextYear = year + 1;
		} else {
			nextMonth = month + 1;
		}
		
		try {
			conn = getConnection();
			sql = "SELECT * FROM schedule s, schedule_invited_user i "
				+ "WHERE s.sc_idx = i.sc_idx AND "
				+ "i.id = ? AND "
				+ "TO_DATE(substr(sc_start, 1, 9), 'YYYY-MM-DD') <= "
				+ "TO_DATE(?, 'YYYY-MM-DD') AND "
				+ "TO_DATE(substr(sc_end, 1, 9), 'YYYY-MM-DD') >= "
				+ "TO_DATE(?, 'YYYY-MM-DD') "
				+ "ORDER BY sc_start ASC";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, nextYear+"/"+nextMonth+"/01");
			pstmt.setString(3, year+"/"+month+"/01");
			rs = pstmt.executeQuery();
			
			list = new ArrayList<SchedulerDto>();
			while (rs.next()) {
				SchedulerDto schedule = new SchedulerDto();
				
				schedule.setSc_idx(rs.getInt("sc_idx"));
				schedule.setSc_title(rs.getString("sc_title"));
				schedule.setId(rs.getString("id"));
				schedule.setSc_place(rs.getString("sc_place"));
				schedule.setSc_start(rs.getString("sc_start"));
				schedule.setSc_startDate(rs.getString("sc_start"));
				schedule.setSc_startTime(rs.getString("sc_start"));
				schedule.setSc_end(rs.getString("sc_end"));
				schedule.setSc_endDate(rs.getString("sc_end"));
				schedule.setSc_endTime(rs.getString("sc_end"));
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
	// E: ���� ������ ���� ��ȸ
		
	// S: �Ϻ� ���� ��ȸ
	public List<SchedulerDto> getScheduleByDay(String id, int year, 
			int month, int day) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		List<SchedulerDto> list = null;
		SchedulerDto schedule = null;
		
		try {
			conn = getConnection();
			sql = "SELECT * FROM schedule WHERE id = ? AND "
				+ "sc_usable = 'Y' AND "
				+ "TO_DATE(substr(sc_start, 1, 9), 'YYYY-MM-DD') < "
				+ "TO_DATE(?, 'YYYY-MM-DD')+1 AND "
				+ "TO_DATE(substr(sc_end, 1, 9), 'YYYY-MM-DD') >= "
				+ "TO_DATE(?, 'YYYY-MM-DD') "
				+ "ORDER BY sc_start ASC";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, year+"-"+month+"-"+day);
			pstmt.setString(3, year+"-"+month+"-"+day);
			
			rs = pstmt.executeQuery();
			
			list = new ArrayList<SchedulerDto>();
			while (rs.next()) {
				schedule = new SchedulerDto();
				schedule.setSc_idx(rs.getInt("sc_idx"));
				schedule.setId(rs.getString("id"));
				schedule.setSc_title(rs.getString("sc_title"));
				schedule.setSc_start(rs.getString("sc_start"));
				schedule.setSc_startDate(rs.getString("sc_start"));
				schedule.setSc_startTime(rs.getString("sc_start"));
				schedule.setSc_end(rs.getString("sc_end"));
				schedule.setSc_endDate(rs.getString("sc_end"));
				schedule.setSc_endTime(rs.getString("sc_end"));
				schedule.setSc_all_day(rs.getString("sc_all_day"));
				schedule.setSc_place(rs.getString("sc_place"));
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
	// E: �Ϻ� ���� ��ȸ
	
	// S: �Ϻ� ������ ���� ��ȸ
	public List<SchedulerDto> getSharedScheduleByDay(String id, int year, int month, 
			int day) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		List<SchedulerDto> list = null;
		
		try {
			conn = getConnection();
			sql = "SELECT * FROM schedule s, schedule_invited_user i "
				+ "WHERE s.sc_idx = i.sc_idx "
				+ "AND i.id = ? "
				+ "AND TO_DATE(substr(sc_start, 1, 9), 'YYYY-MM-DD') < "
				+ "TO_DATE(?, 'YYYY-MM-DD')+1 "
				+ "AND TO_DATE(substr(sc_end, 1, 9), 'YYYY-MM-DD') >= "
				+ "TO_DATE(?, 'YYYY-MM-DD') "
				+ "ORDER BY sc_start ASC";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, year+"-"+month+"-"+day);
			pstmt.setString(3, year+"-"+month+"-"+day);
			rs = pstmt.executeQuery();
			
			list = new ArrayList<SchedulerDto>();
			while (rs.next()) {
				SchedulerDto schedule = new SchedulerDto();
				
				schedule.setSc_idx(rs.getInt("sc_idx"));
				schedule.setSc_title(rs.getString("sc_title"));
				schedule.setId(rs.getString("id"));
				schedule.setSc_place(rs.getString("sc_place"));
				schedule.setSc_start(rs.getString("sc_start"));
				schedule.setSc_startDate(rs.getString("sc_start"));
				schedule.setSc_startTime(rs.getString("sc_start"));
				schedule.setSc_end(rs.getString("sc_end"));
				schedule.setSc_endDate(rs.getString("sc_end"));
				schedule.setSc_endTime(rs.getString("sc_end"));
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
	// E: �Ϻ� ������ ���� ��ȸ
	
	// S: ���� ����
	public void deleteSchedule(int sc_idx) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;  // �˸� ����
		PreparedStatement pstmt2 = null; // ���� �ʴ� ȸ�� ����
		PreparedStatement pstmt3 = null; // ���� ����
		String sql = "";
		
		try {
			conn = getConnection();
			conn.setAutoCommit(false);
			sql = "DELETE FROM alarm WHERE sc_idx = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, sc_idx);
			pstmt.executeUpdate();
			
			sql = "DELETE FROM schedule_invited_user WHERE sc_idx = ?";
			pstmt2 = conn.prepareStatement(sql);
			pstmt2.setInt(1, sc_idx);
			pstmt2.executeUpdate();
			
			sql = "DELETE FROM schedule WHERE sc_idx = ?";
			pstmt3 = conn.prepareStatement(sql);
			pstmt3.setInt(1, sc_idx);
			pstmt3.executeUpdate();
			
			conn.commit();
		} catch(Exception e) {
			conn.rollback();
			throw new Exception(e);
		} finally {
			executeClose(null, pstmt3, null);
			executeClose(null, pstmt2, null);
			executeClose(null, pstmt, conn);
		}
	}
	// E: ���� ����
	
	// S: �ٰ��� ���� 3�� ��ȸ (snb��)
	public List<SchedulerDto> getSchedule3(String userId) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		List<SchedulerDto> list = null;
		SchedulerDto schedule = null;
		
		try {
			conn = getConnection();
			sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM "
				+ "(SELECT * FROM schedule s WHERE s.id = ? AND "
				+ "sc_usable = 'Y' AND "
				+ "TO_DATE(substr(sc_end, 1, 9), 'YYYY-MM-DD') >= "
				+ "TO_DATE(to_char(sysdate, 'YYYY-MM-DD')) "
				+ "ORDER BY s.sc_start ASC)a) WHERE rnum <= 3";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery();

			list = new ArrayList<SchedulerDto>();
			while (rs.next()) {
				schedule = new SchedulerDto();
				
				schedule.setId(rs.getString("id"));
				schedule.setSc_idx(rs.getInt("sc_idx"));
				schedule.setSc_title(rs.getString("sc_title"));
				schedule.setSc_content(rs.getString("sc_content"));
				schedule.setSc_start(rs.getString("sc_start"));
				schedule.setSc_startDate(rs.getString("sc_start"));
				schedule.setSc_startTime(rs.getString("sc_start"));
				schedule.setSc_end(rs.getString("sc_end"));
				schedule.setSc_endDate(rs.getString("sc_end"));
				schedule.setSc_endTime(rs.getString("sc_end"));
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
	// E: �ٰ��� ���� 3�� ��ȸ (snb��)
	
}
