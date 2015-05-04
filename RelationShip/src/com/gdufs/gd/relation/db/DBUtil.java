package com.gdufs.gd.relation.db;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DBUtil {
	private static DBUtil dbutil;
	private static Properties p;
	static {
		InputStream is = DBUtil.class.getResourceAsStream("db.properties");
		p = new Properties();
		try {
			p.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private DBUtil() {

	}

	public static DBUtil getDBUtil() {

		if (dbutil == null) {
			dbutil = new DBUtil();
		}
		return dbutil;
	}

	/**
	 * 根据条件获取连接池
	 * 
	 * @param datasourcename
	 * @return
	 */
	public static ComboPooledDataSource getComboPooledDataSource(
			String datasourcename) {
		// 创建一个连接池对象
		ComboPooledDataSource cpds = new ComboPooledDataSource();
		try {
			cpds.setDriverClass(p.getProperty(datasourcename + ".driverClass")
					.trim());
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
		cpds.setJdbcUrl(p.getProperty(datasourcename + ".url").trim());
		cpds.setUser(p.getProperty(datasourcename + ".user").trim());
		cpds.setPassword(p.getProperty(datasourcename + ".pwd").trim());
		cpds.setInitialPoolSize(Integer.parseInt(p.getProperty(
				"c3p0.initialPoolSize").trim()));
		cpds.setMinPoolSize(Integer.parseInt(p.getProperty("c3p0.minPoolSize")
				.trim()));
		cpds.setMaxPoolSize(Integer.parseInt(p.getProperty("c3p0.maxPoolSize")
				.trim()));
		return cpds;

	}
}