package com.serpstat.datapopulator.populator.table;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.serpstat.datapopulator.dao.KeywordStatDAO;
import com.serpstat.datapopulator.db.ConnectionPool;
import com.serpstat.datapopulator.model.KeywordStat;

public final class KeywordStatPopulator {
	static final int min = 1;
	static final int max = 1000000000;
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

		List<KeywordStat> keywordStatList = new ArrayList<>();
		while (cStart.before(cEnd)) {
			long googleResults = rand.nextInt((max - min) + 1) + min;
			float googleKEI = 5.5f;
			KeywordStat keywordStat = new KeywordStat(keywordId, googleResults, googleKEI, cStart.getTime(),
					cStart.getTime());
			keywordStatList.add(keywordStat);
			cStart.add(Calendar.DAY_OF_MONTH, 1);
		}

		(new KeywordStatDAO()).deleteAll(pool, keywordStatList);
		(new KeywordStatDAO()).saveAll(pool, keywordStatList);
		keywordStatList.clear();
	}
}
