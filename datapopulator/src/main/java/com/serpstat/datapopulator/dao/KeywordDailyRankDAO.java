package com.serpstat.datapopulator.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.serpstat.datapopulator.db.ConnectionPool;
import com.serpstat.datapopulator.model.KeywordDailyRank;

public class KeywordDailyRankDAO {
	public void saveAll(ConnectionPool pool, List<KeywordDailyRank> keywordDailyRankList)
			throws SQLException {
		Connection conn = null;
		PreparedStatement preparedStatement = null;

		String insertSQL = "INSERT INTO KEYWORD_DAILY_RANKS " + "(KEYWORD_ID, RANK, DISPLAY_RANK, BASE_RANK, "
				+ "MOVEMENT, URL_ID, ADVERTISER_COMPETITION, GLOBAL_MONTHLY_SEARCHES, REGIONAL_MONTHLY_SEARCHES, "
				+ "CPC, GOOGLE_RESULTS, GOOGLE_KEI, " + "CREATED_AT, UPDATED_AT) VALUES"
				+ " (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";

		conn = pool.getConnection();
		try {
			conn.setAutoCommit(false);
			preparedStatement = conn.prepareStatement(insertSQL);
			for (KeywordDailyRank keywordDailyRank : keywordDailyRankList) {
				preparedStatement.setLong(1, keywordDailyRank.getKeywordId());
				preparedStatement.setInt(2, keywordDailyRank.getRank());
				preparedStatement.setInt(3, keywordDailyRank.getDisplayRank());
				preparedStatement.setInt(4, keywordDailyRank.getBaseRank());
				preparedStatement.setInt(5, keywordDailyRank.getMovement());
				preparedStatement.setInt(6, keywordDailyRank.getUrlId());

				preparedStatement.setFloat(7, keywordDailyRank.getAdvertiserCompetition());
				preparedStatement.setInt(8, keywordDailyRank.getGlobalMonthlySearches());
				preparedStatement.setInt(9, keywordDailyRank.getRegionalMonthlySearches());
				preparedStatement.setFloat(10, keywordDailyRank.getCpc());
				preparedStatement.setLong(11, keywordDailyRank.getGoogleResults());
				preparedStatement.setFloat(12, keywordDailyRank.getGoogleKEI());

				preparedStatement.setDate(13, new java.sql.Date(keywordDailyRank.getCreatedAt().getTime()));
				preparedStatement.setDate(14, new java.sql.Date(keywordDailyRank.getUpdatedAt().getTime()));
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

	public void deleteAll(ConnectionPool pool, List<KeywordDailyRank> keywordDailyRankList)
			throws SQLException {
		Connection conn = null;
		PreparedStatement preparedStatement = null;

		String deleteSQL = "DELETE FROM KEYWORD_DAILY_RANKS WHERE KEYWORD_ID = ? AND CREATED_AT BETWEEN ? AND ?";

		conn = pool.getConnection();
		try {
			conn.setAutoCommit(false);
			KeywordDailyRank startKeywordDailyRank = keywordDailyRankList.get(0);
			KeywordDailyRank endKeywordDailyRank = keywordDailyRankList.get(keywordDailyRankList.size() - 1);
			preparedStatement = conn.prepareStatement(deleteSQL);
			preparedStatement.setLong(1, startKeywordDailyRank.getKeywordId());
			preparedStatement.setDate(2, new java.sql.Date(startKeywordDailyRank.getCreatedAt().getTime()));
			preparedStatement.setDate(3, new java.sql.Date(endKeywordDailyRank.getUpdatedAt().getTime()));
			preparedStatement.executeUpdate();
			conn.setAutoCommit(false);
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
