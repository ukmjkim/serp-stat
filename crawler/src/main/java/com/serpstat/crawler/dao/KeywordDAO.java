package com.serpstat.crawler.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.serpstat.crawler.db.ConnectionPool;
import com.serpstat.crawler.model.Keyword;

public class KeywordDAO {
	public List<Keyword> fetchAllBySite(ConnectionPool pool, long siteId) throws SQLException {
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		String selectSQL = "SELECT KEYWORDS.ID AS ID, SITE_ID, SITES.URL AS URL, KEYWORD "
				+ " FROM KEYWORDS, SITES WHERE KEYWORDS.ID < 46 AND SITE_ID = ?";

		List<Keyword> list = new ArrayList<>();
		try {
			conn = pool.getConnection();
			preparedStatement = conn.prepareStatement(selectSQL);
			preparedStatement.setLong(1, siteId);
			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				Keyword keyword = new Keyword(rs.getLong("ID"), rs.getLong("SITE_ID"), rs.getString("URL"), rs.getString("KEYWORD"));
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
