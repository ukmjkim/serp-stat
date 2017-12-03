package com.serpstat.datapopulator.populator.table;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.serpstat.datapopulator.dao.KeywordRankDAO;
import com.serpstat.datapopulator.db.ConnectionPool;
import com.serpstat.datapopulator.model.KeywordRank;

public final class KeywordRankPopulator {
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

		List<KeywordRank> keywordRankList = new ArrayList<>();
		while (cStart.before(cEnd)) {
			int rank = rand.nextInt((max - min) + 1) + min;
			KeywordRank keywordRank = new KeywordRank(keywordId, rank, rank, rank + 2, 1, 1, cStart.getTime(),
					cStart.getTime());
			keywordRankList.add(keywordRank);
			cStart.add(Calendar.DAY_OF_MONTH, 1);
		}

		(new KeywordRankDAO()).deleteAll(pool, keywordRankList);
		(new KeywordRankDAO()).saveAll(pool, keywordRankList);
		keywordRankList.clear();
	}
}
