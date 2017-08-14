package com.test.teststatus.db;


import org.apache.log4j.Logger;

import java.sql.*;

/**
 * 数据库连接池管理工具类
 */
public class DBManager {
	private static Logger log = Logger.getLogger(DBManager.class);
	public static final String LOCAL_MYSQL = "localmysql";
	/**
	 * 公共数据库连接(查询用户所在分区专用).
	 * @return
	 */
	public static Connection getSelectPartConnection() {
		try {
			Class.forName("org.logicalcobwebs.proxool.ProxoolDriver");
		} catch (ClassNotFoundException e) {
			log.error(e.getMessage());
			return null;
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
		Connection conn = null;
		try {
			conn = DriverManager.getConnection("proxool." + LOCAL_MYSQL);
		} catch (SQLException e) {
			log.error(e.getMessage());
			return null;
		}
		return conn;
	}

	/**
	 * 关闭连接
	 * @param rs
	 * @param stmt
	 * @param conn
	 */
	public static void close(ResultSet rs, PreparedStatement stmt,
			Connection conn) {

		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (stmt != null) {
				stmt.close();
				stmt = null;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		close(conn);
	}

	/**
	 * 获取连接
	 * @param dbname
	 * @return
	 */
	public static synchronized Connection getConnection(String dbname) {

		try {
			Class.forName("org.logicalcobwebs.proxool.ProxoolDriver");
		} catch (ClassNotFoundException e) {
			log.error(e.getMessage());
			return null;
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}

		Connection conn = null;
		try {
			int i = 0;
			while (i < 10) {
				conn = DriverManager.getConnection("proxool." + dbname);
				if (conn != null) {
					break;
				}
				if (conn == null) {
					log.info("数据库连接池无可用连接，等待0.1秒钟再次获取连接！");
					try {

						Thread.sleep(100);

					} catch (Exception e) {
					}

				}
				i++;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			log.error("获取数据库连接异常！",e);
			return null;
		}
		return conn;
	}


	/**
	 * 关闭 Statement 及其子接口(PreparedStatement, CallableStatement).
	 * @param stmt
	 */
	public static void close(Statement stmt) {
		try {
			if (stmt != null) {
				stmt.close();
				stmt = null;
			}
		} catch (SQLException ex) {
			log.error(ex.getMessage());
		}
	}

	/**
	 * 关闭数据库连接, 释放回数据库连接池.
	 * @param conn
	 */
	public static void close(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
				conn = null;
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		}
	}

	/**
	 * 关闭ResultSet.
	 * @param rs
	 */
	public static void close(ResultSet rs) {

		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
		} catch (SQLException e) {
			log.error(e.getMessage());
		}
	}
}
