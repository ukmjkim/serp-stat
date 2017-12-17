package com.serpstat.crawler.main;

import static com.serpstat.crawler.common.Constants.CONFIG_FILE;
import static com.serpstat.crawler.common.Constants.NUM_OF_CONNECTION;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.serpstat.crawler.basic.BasicCrawler;
import com.serpstat.crawler.config.Configuration;
import com.serpstat.crawler.config.YAMLConfigLoader;
import com.serpstat.crawler.dao.KeywordDAO;
import com.serpstat.crawler.db.ConnectionPool;
import com.serpstat.crawler.model.Keyword;


public class App {
	private static Configuration config;
	static ConnectionPool pool = null;

	public static void main(String... args) throws Exception {
		loadConfiguration();
		spawningDatabasePool();

		List<Keyword> dataSet = (new KeywordDAO()).fetchAllBySite(pool, 1L);
		process(dataSet, true);

		closingDatabasePool();
	}

	private static void process(List<Keyword> dataSet, boolean advanced) throws Exception {
		System.out.printf("******************************************\n");
		System.out.printf("Crawling - Advanced: %b\n", advanced);
		System.out.printf("------------------------------------------\n");
		long start = System.currentTimeMillis();

		BasicCrawler crawler = new BasicCrawler(pool, dataSet);
		Thread t = new Thread(crawler);
		t.start();

		// Wait 30 minutes
		try {
			TimeUnit.MINUTES.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// Notify the finalization of the System
		crawler.shutdown();

		long end = System.currentTimeMillis();
		System.out.printf("Execution Time: %.2f seconds.\n", ((end - start) / 1000.0));
		System.out.printf("******************************************\n\n\n");
	}

	private static void loadConfiguration() {
		try {
			config = (new YAMLConfigLoader()).load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (config == null) {
			System.out.printf("Cannot find config file: %s\n", CONFIG_FILE);
			System.exit(0);
		}
	}

	private static void spawningDatabasePool() throws Exception {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Where is your MySQL JDBC Driver?");
			e.printStackTrace();
			new Exception();
		}

		int numDBConnection = Math.min(NUM_OF_CONNECTION, Runtime.getRuntime().availableProcessors());
		pool = new ConnectionPool(config.getDatabase().getHost(), config.getDatabase().getUser(),
				config.getDatabase().getPassword(), numDBConnection);
	}

	private static void closingDatabasePool() throws SQLException {
		pool.closeConnectionPool();
		System.out.println("Closing Database pool");
	}
}