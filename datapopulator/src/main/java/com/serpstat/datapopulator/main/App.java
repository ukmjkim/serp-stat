package com.serpstat.datapopulator.main;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.serpstat.datapopulator.config.Configuration;
import com.serpstat.datapopulator.config.DBConnection;
import com.serpstat.datapopulator.config.YAMLConfigLoader;
import com.serpstat.datapopulator.dao.KeywordDAO;
import com.serpstat.datapopulator.db.ConnectionPool;
import com.serpstat.datapopulator.model.Entity;
import com.serpstat.datapopulator.populator.Populator;
import com.serpstat.datapopulator.populator.PopulatorFactory;

public class App {
	private static String startDate = "20160101";
	private static String endDate = new SimpleDateFormat("yyyyMMdd").format(new Date());;
	static ConnectionPool pool = null;

	public static void main(String... args) throws Exception {
		Configuration config = new Configuration();
		try {
			config = (new YAMLConfigLoader()).load();
		} catch (Exception e) {
			System.exit(0);
		}

		System.out.printf("******************************************\n");
		System.out.printf("We are going to fetch keyword list by site\n\n\n\n");

		spawningDatabasePool(config.getDatabase());

		List<Entity> dataSet = (new KeywordDAO()).fetchAllBySite(pool, 1L);
		process(dataSet, true, true);

		clearingDatabasePool();
		System.exit(0);
	}

	public static void process(List<Entity> dataSet, boolean parallel, boolean group) throws Exception {
		System.out.printf("******************************************\n");
		System.out.printf("Keyword Populator - Parallel: %b\n", parallel);
		System.out.printf("------------------------------------------\n");
		long start = System.currentTimeMillis();

		Populator populator = PopulatorFactory.getPopulator(pool, dataSet, startDate, endDate, parallel, group);
		populator.populate();
		populator.destroy();

		long end = System.currentTimeMillis();
		System.out.printf("Execution Time: %.2f seconds.\n", ((end - start) / 1000.0));
		System.out.printf("******************************************\n\n\n");
	}

	private static void spawningDatabasePool(DBConnection dbConnection) throws Exception {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Where is your MySQL JDBC Driver?");
			e.printStackTrace();
			new Exception();
		}

		int numDBConnection = Runtime.getRuntime().availableProcessors();

		try {
			pool = new ConnectionPool(dbConnection.getHost(), dbConnection.getUser(), dbConnection.getPwd(),
					numDBConnection);
		} catch (Exception e) {
			System.out.println("Cannot make connection pool");
			e.printStackTrace();
			new Exception();
		}
	}

	private static void clearingDatabasePool() throws SQLException {
		pool.closeConnectionPool();
	}
}
