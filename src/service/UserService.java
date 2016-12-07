package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entity.User;
import util.DBUtil;

public class UserService {
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	public boolean signIn(User user) throws SQLException {
		String sql = "select * from user where username=? and password=?";
		conn = DBUtil.getCONN();
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, user.getUserName());
		pstmt.setString(2, user.getPassword());
		rs = pstmt.executeQuery();
		boolean ans = false;
		if (rs.next()) {
			rs.close();
			ans = true;
		}
		if (conn != null) {
			conn.close();
		}
		if (pstmt != null) {
			pstmt.close();
		}
		return ans;
	}

	public void signUp(User user) throws SQLException {
		String sql = "insert into user(username,password) values (?,?)";
		conn = DBUtil.getCONN();
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, user.getUserName());
		pstmt.setString(2, user.getPassword());
		pstmt.executeUpdate();
		if (conn != null) {
			conn.close();
		}
		if (pstmt != null) {
			pstmt.close();
		}
	}
}
