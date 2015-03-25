package com.gdufs.gd.relation.db;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

public class ConnectionPool {
	private DataSource ds;
	private static ConnectionPool pool;

	private ConnectionPool() {
		ds = DBUtil.getComboPooledDataSource("jdbc");
	}

	public static final ConnectionPool getInstance() {
		if (pool == null) {
			try {
				pool = new ConnectionPool();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return pool;
	}

	public synchronized final Connection getConnection() {
		try {
			return ds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}