package com.serpstat.datapopulator.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.serpstat.datapopulator.db.ConnectionPool;
import com.serpstat.datapopulator.model.KeywordRank;

public class KeywordRankDAO {
	public void saveAll(ConnectionPool pool, List<KeywordRank> keywordRankList) throws SQLException {
		Connection conn = null;
		PreparedStatement preparedStatement = null;

		String insertSQL = "INSERT INTO KEYWORD_RANKS" + "(KEYWORD_ID, RANK, DISPLAY_RANK, BASE_RANK, "
				+ "MOVEMENT, URL_ID, CREATED_AT, UPDATED_AT) VALUES" + " (?, ?, ?, ?, ?, ?, ?, ?) ";

		conn = pool.getConnection();
		try {
			conn.setAutoCommit(false);
			preparedStatement = conn.prepareStatement(insertSQL);
			for (KeywordRank keywordRank : keywordRankList) {
				preparedStatement.setLong(1, keywordRank.getKeywordId());
				preparedStatement.setInt(2, keywordRank.getRank());
				preparedStatement.setInt(3, keywordRank.getDisplayRank());
				preparedStatement.setInt(4, keywordRank.getBaseRank());
				preparedStatement.setInt(5, keywordRank.getMovement());
				preparedStatement.setInt(6, keywordRank.getUrlId());
				preparedStatement.setDate(7, new java.sql.Date(keywordRank.getCreatedAt().getTime()));
				preparedStatement.setDate(8, new java.sql.Date(keywordRank.getUpdatedAt().getTime()));
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

	public void deleteAll(ConnectionPool pool, List<KeywordRank> keywordRankList) throws SQLException {
		Connection conn = null;
		PreparedStatement preparedStatement = null;

		String deleteSQL = "DELETE FROM KEYWORD_RANKS WHERE KEYWORD_ID = ? AND CREATED_AT BETWEEN ? AND ?";

		conn = pool.getConnection();
		try {
			conn.setAutoCommit(false);
			KeywordRank startKeywordRank = keywordRankList.get(0);
			KeywordRank endKeywordRank = keywordRankList.get(keywordRankList.size() - 1);
			preparedStatement = conn.prepareStatement(deleteSQL);
			preparedStatement.setLong(1, startKeywordRank.getKeywordId());
			preparedStatement.setDate(2, new java.sql.Date(startKeywordRank.getCreatedAt().getTime()));
			preparedStatement.setDate(3, new java.sql.Date(endKeywordRank.getUpdatedAt().getTime()));
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
