package com.serpstat.datapopulator.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.serpstat.datapopulator.db.ConnectionPool;
import com.serpstat.datapopulator.model.Entity;
import com.serpstat.datapopulator.model.Keyword;

public class KeywordDAO {
	public List<Entity> fetchAllBySite(ConnectionPool pool, long siteId) throws SQLException {
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		String selectSQL = "SELECT ID, KEYWORD FROM KEYWORDS WHERE SITE_ID = ?";

		List<Entity> list = new ArrayList<>();
		try {
			conn = pool.getConnection();
			preparedStatement = conn.prepareStatement(selectSQL);
			preparedStatement.setLong(1, siteId);
			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				Keyword keyword = new Keyword(rs.getLong("ID"), siteId, rs.getString("KEYWORD"));
				list.add(keyword);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (preparedStatement != null) {
				preparedStatement.close();
			}
			pool.returnConnection(conn);
		}

		return list;
	}
}
