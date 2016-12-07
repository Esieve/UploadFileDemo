package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import entity.File;
import util.DBUtil;

public class FileService {
	private Connection conn = null;
	private PreparedStatement pstmt = null;

	public void save(File file) throws SQLException {
		conn = DBUtil.getCONN();
		String sql = "insert into file(username,filename,filecontent) values (?,?,?)";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, file.getUserName());
		pstmt.setString(2, file.getFileName());
		pstmt.setBytes(3, file.getFileContent());
		pstmt.executeUpdate();
		if (conn != null) {
			conn.close();
		}
		if (pstmt != null) {
			pstmt.close();
		}
	}
}
