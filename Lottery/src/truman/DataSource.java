package truman;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public enum DataSource {

	INSTANCE;
	
	private static final String url = "jdbc:mysql://localhost:3306";
	private static final String user = "truman";
	private static final String pwd = "02mb94";

	private Connection conn = null;

	static {
		try {
			//new jdbcDriver();
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Connection getConnection() throws SQLException {
		// get connection
		if(conn==null)
			conn = DriverManager.getConnection(url, user, pwd);
		return conn;
	}

	public Statement getStatement() throws SQLException {
		Statement stmt = null;
		// get connection
		conn = DriverManager.getConnection(url, user, pwd);
		stmt = conn.createStatement();
		return stmt;
	}

	public void close() {
		try {
			if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
