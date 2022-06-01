package controller;
import java.sql.Connection;
import java.sql.DriverManager;

/*
 * This Class, allows to Connect to Database
 */
public class ConnectDBController {
	private Connection c;

	/**
	 * Constructor of the Database Connection
	 */
	public ConnectDBController() {
		try {
			// Connection URL With User and Password
			c = DriverManager.getConnection("Jdbc:mysql://remotemysql.com/kFY9D6dHBr", "kFY9D6dHBr", "nYvgnwVsAK");
		} catch (Exception e) {
			// If There is a problem with the connection, an exception is thrown
			e.printStackTrace();
		}
	}

	/**
	 * This Getter, Gets the connection
	 * @return Connection to play with the game
	 */
	public Connection getConnection() {
		return c;
	}
}