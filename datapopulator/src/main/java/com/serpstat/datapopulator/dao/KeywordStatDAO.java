package com.serpstat.datapopulator.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.serpstat.datapopulator.db.ConnectionPool;
import com.serpstat.datapopulator.model.KeywordStat;

public class KeywordStatDAO {
	public void saveAll(ConnectionPool pool, List<KeywordStat> keywordStatList) throws SQLException {
		Connection conn = null;
		PreparedStatement preparedStatement = null;

		String insertSQL = "INSERT INTO KEYWORD_STATS "
				+ "(KEYWORD_ID, GOOGLE_RESULTS, GOOGLE_KEI, CREATED_AT, UPDATED_AT) VALUES" + " (?, ?, ?, ?, ?) ";

		conn = pool.getConnection();
		try {
			conn.setAutoCommit(false);
			preparedStatement = conn.prepareStatement(insertSQL);
			for (KeywordStat keywordStat : keywordStatList) {
				preparedStatement = conn.prepareStatement(insertSQL);
				preparedStatement.setLong(1, keywordStat.getKeywordId());
				preparedStatement.setLong(2, keywordStat.getGoogleResults());
				preparedStatement.setFloat(3, keywordStat.getGoogleKEI());
				preparedStatement.setDate(4, new java.sql.Date(keywordStat.getCreatedAt().getTime()));
				preparedStatement.setDate(5, new java.sql.Date(keywordStat.getUpdatedAt().getTime()));
				preparedStatement.addBatch();
			}
			preparedStatement.executeBatch();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (preparedStatement != null) {
				preparedStatement.close();
			}
			pool.returnConnection(conn);
		}
	}

	public void deleteAll(ConnectionPool pool, List<KeywordStat> keywordStatList) throws SQLException {
		Connection conn = null;
		PreparedStatement preparedStatement = null;

		String deleteSQL = "DELETE FROM KEYWORD_STATS WHERE KEYWORD_ID = ? AND CREATED_AT BETWEEN ? AND ?";

		conn = pool.getConnection();
		try {
			conn.setAutoCommit(false);
			KeywordStat startKeywordStat = keywordStatList.get(0);
			KeywordStat endKeywordStat = keywordStatList.get(keywordStatList.size() - 1);
			preparedStatement = conn.prepareStatement(deleteSQL);
			preparedStatement.setLong(1, startKeywordStat.getKeywordId());
			preparedStatement.setDate(2, new java.sql.Date(startKeywordStat.getCreatedAt().getTime()));
			preparedStatement.setDate(3, new java.sql.Date(endKeywordStat.getUpdatedAt().getTime()));
			preparedStatement.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (preparedStatement != null) {
				preparedStatement.close();
			}
			pool.returnConnection(conn);
		}
	}
}
