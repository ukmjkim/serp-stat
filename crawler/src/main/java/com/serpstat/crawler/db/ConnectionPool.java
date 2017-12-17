package com.serpstat.crawler.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;

import lombok.Data;

@Data
public class ConnectionPool {
	private String connectionString;
	private String user;
	private String password;
	
	LinkedList<Connection> pool = new LinkedList<Connection>();

	public ConnectionPool(String connectionString, String user, String password, int NumOfConnection) throws SQLException {
		this.connectionString = connectionString;
		this.user = user;
		this.password = password;
		
		for (int i = 0; i < NumOfConnection; i++) {
			pool.add(DriverManager.getConnection(connectionString, user, password));
		}
	}
	
	public synchronized Connection getConnection() throws SQLException {
		if (pool.isEmpty()) {
			pool.add(DriverManager.getConnection(connectionString, user, password));
		}
		return pool.pop();
	}

	public synchronized void returnConnection(Connection connection) {
		pool.push(connection);
	}

	public synchronized void closeConnectionPool() throws SQLException {
		while (!pool.isEmpty()) {
			pool.pop().close();
		}
	}
}
