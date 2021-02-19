package co.mydiary;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DiaryOracleDAO implements DAO {
	Connection conn;
	Statement stmt;
	PreparedStatement psmt;
	ResultSet rs;

	@Override
	public int insert(DiaryVO vo) {
		int r = 0;
		conn = JdbcUtil.connect();
		String sql = "INSERT INTO DIARY VALUES(?,?)";
		try {
			psmt = conn.prepareStatement(sql);

			psmt.setString(1, vo.getWdate());
			psmt.setString(2, vo.getContents());
			r = psmt.executeUpdate();
			System.out.println(r + "건이 등록됨.");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.disconnect(conn);
		}
		return r;
	}

	@Override
	public void update(DiaryVO vo) {
		int r = 0;
		conn = JdbcUtil.connect();
		String sql = "UPDATE DIARY SET CONTENTS = ?"//
				+ "WHERE WDATE = ?";
		try {
			psmt = conn.prepareStatement(sql);

			psmt.setString(1, vo.getContents());
			psmt.setString(2, vo.getWdate());
			r = psmt.executeUpdate();
			System.out.println(r + "건이 수정됨.");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.disconnect(conn);
		}
	}

	@Override
	public int delete(String date) {
		int r = 0;
		conn = JdbcUtil.connect();
		String sql = "DELETE FROM DIARY WHERE WDATE = ?";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, date);
			r = psmt.executeUpdate();
			System.out.println(r + "건이 삭제됨.");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.disconnect(conn);
		}
		return r;
	}

	@Override
	public DiaryVO selectDate(String date) {
		DiaryVO vo = new DiaryVO();
		conn = JdbcUtil.connect();
		String sql = "SELECT * FROM DIARY WHERE WDATE = ?";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, date);
			rs = psmt.executeQuery();
			if (rs.next()) {
				vo.setWdate(rs.getString(1));
				vo.setContents(rs.getString(2));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.disconnect(conn);
		}
		return vo;
	}

	@Override
	public List<DiaryVO> selectContent(String content) {
		List<DiaryVO> list = new ArrayList<DiaryVO>();
		DiaryVO vo;
		String sql = "SELECT "//
				+ "WDATE,CONTENTS "//
				+ "FROM DIARY WHERE CONTENTS LIKE '%' || ? || '%' ";
		try {
			conn = JdbcUtil.connect();
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, content);
			rs = psmt.executeQuery();
			while (rs.next()) {
				vo = new DiaryVO();
				vo.setWdate(rs.getString("wdate"));
				vo.setContents(rs.getString("contents"));
				list.add(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.disconnect(conn);
		}
		return list;
	}

	@Override
	public List<DiaryVO> selectAll() {
		List<DiaryVO> list = new ArrayList<DiaryVO>();
		DiaryVO vo;
		conn = JdbcUtil.connect();
		String sql = "SELECT WDATE,CONTENTS FROM DIARY ORDER BY WDATE";
		try {
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();
			while (rs.next()) {
				vo = new DiaryVO();
				vo.setWdate(rs.getString(1));
				vo.setContents(rs.getString(2));
				list.add(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.disconnect(conn);
		}
		return list;
	}

}
