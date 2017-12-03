package com.serpstat.datapopulator.populator.table;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.serpstat.datapopulator.dao.KeywordDailyRankDAO;
import com.serpstat.datapopulator.db.ConnectionPool;
import com.serpstat.datapopulator.model.KeywordDailyRank;

public final class KeywordDailyRankPopulator {
	static final int min = 1;
	static final int max = 10;
	static Random rand = new Random();

	public static synchronized void populate(ConnectionPool pool, Long keywordId, String startDate, String endDate)
			throws Exception {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");

		Date start = formatter.parse(startDate);
		Date end = formatter.parse(endDate);

		Calendar cStart = Calendar.getInstance();
		cStart.setTime(start);
		Calendar cEnd = Calendar.getInstance();
		cEnd.setTime(end);

		List<KeywordDailyRank> keywordDailyRankList = new ArrayList<>();
		while (cStart.before(cEnd)) {
			int rank = rand.nextInt((max - min) + 1) + min;
			KeywordDailyRank keywordDailyRank = new KeywordDailyRank(keywordId, rank, rank, rank + 2, 1, 1, rank * 1.1f,
					rank * 2000, rank * 1000, rank * 1.2f, rank * 3000L, rank * 1.3f, cStart.getTime(),
					cStart.getTime());
			keywordDailyRankList.add(keywordDailyRank);
			cStart.add(Calendar.DAY_OF_MONTH, 1);
		}

		(new KeywordDailyRankDAO()).deleteAll(pool, keywordDailyRankList);
		(new KeywordDailyRankDAO()).saveAll(pool, keywordDailyRankList);
		keywordDailyRankList.clear();
	}
}
