package webScanner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBManager {
	
	public static final String DB = "jdbc:sqlite:" + System.getProperty("user.dir") + "\\saved\\results.db";
	
	public static final String DRIVER = "org.sqlite.JDBC";
	
	private Statement statement;
	
	private Connection database;
	
	public DBManager() {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e1) {
			System.out.print(e1.getMessage());
			e1.printStackTrace();
		}
		try {
			database = DriverManager.getConnection(DB);
			this.statement = database.createStatement();
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS Results "
					+ "(ID INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ "URL VARCHAR(350), "
					+ "DEPTH INTEGER, "
					+ "SCORE INTEGER);");
		} catch (SQLException e) {
			System.out.print(e.getMessage());
		}
	}
	
	public void addVisited(URLContainer container) {
		try {
			this.statement.executeUpdate("INSERT INTO Results(URL, DEPTH, SCORE) VALUES('"
					+ container.toString() + "',"
					+ container.getDepth() + ","
					+ container.getScore() + ");");
		} catch (SQLException e) {
			System.out.print(e.getMessage());
		}
	}
	
	public void close() {
		try {
			this.statement.close();
			this.database.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
