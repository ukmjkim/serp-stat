package com.serpstat.datapopulator.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;

public class ConnectionPool {
	String connString;
	String user;
	String pwd;

	LinkedList<Connection> pool = new LinkedList<Connection>();

	public String getConnString() {
		return connString;
	}

	public String getPwd() {
		return pwd;
	}

	public String getUser() {
		return user;
	}

	public ConnectionPool(String connString, String user, String pwd, int NumOfConnection) throws SQLException {
		this.connString = connString;
		for (int i = 0; i < NumOfConnection; i++) {
			pool.add(DriverManager.getConnection(connString, user, pwd));
		}
		this.user = user;
		this.pwd = pwd;
	}

	public synchronized Connection getConnection() throws SQLException {
		if (pool.isEmpty()) {
			pool.add(DriverManager.getConnection(connString, user, pwd));
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
