package webScanner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.LinkedList;

public class URLManager {
	
	public static final String DB = "jdbc:sqlite:" + System.getProperty("user.dir") + "\\saved\\results.db";
	
	public static final String DRIVER = "org.sqlite.JDBC";
	
	private int maxDepth;
	
	private HashSet<String> visitedURLs;
	
	private HashSet<String> visitedDomains;
	
	private LinkedList<URLContainer> unvisited;
	
	private Statement statement;
	
	private Connection database;
	
	public URLManager(int maxDepth, URLContainer start) {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e1) {
			System.out.print(e1.getMessage());
			e1.printStackTrace();
		}
		this.maxDepth = maxDepth;
		this.visitedURLs = new HashSet<String>();
		this.visitedDomains = new HashSet<String>();
		this.unvisited = new LinkedList<URLContainer>();
		this.unvisited.addFirst(start);
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
	
	public synchronized void addVisited(URLContainer container) {
		try {
			this.statement.executeUpdate("INSERT INTO Results(URL, DEPTH, SCORE) VALUES('"
					+ container.toString() + "',"
					+ container.getDepth() + ","
					+ container.getScore() + ");");
		} catch (SQLException e) {
			System.out.print(e.getMessage());
		}
	}
	
	public synchronized void addLinks(LinkedList<URLContainer> list) {
		for (URLContainer temp : list) {
			if (temp.getDepth() < this.maxDepth) {
				if (!this.visitedURLs.contains(temp.toString())) {
					if (this.visitedDomains.contains(temp.getDomain())) {
						this.visitedURLs.add(temp.toString());
						this.unvisited.addLast(temp);
					} else {
						this.visitedURLs.add(temp.toString());
						this.visitedDomains.add(temp.getDomain());
						this.unvisited.addFirst(temp);
					}
				}
			}
		}
	}
	
	public synchronized URLContainer getUnvisited() {
		return this.unvisited.pollFirst();
	}
	
	public synchronized boolean isEmpty() {
		return this.unvisited.isEmpty();
	}
	
	public synchronized int numURLs() {
		return this.unvisited.size();
	}
}
