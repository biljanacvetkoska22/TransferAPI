package dboperations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;


import com.transfer.model.Transfer;
/*
 * Used to connect to the h2 database.
   See application.properties 
   Account table is created using migration. See src/main/resources/db/migration
 */
public class JavaH2Database implements IJavaH2Database {
	private static final String DATABASE_DRIVER = "org.h2.Driver";
	private static final String DATABASE_CONNECTION = "jdbc:h2:file:~/accounts";
	private static final String DATABASE_USER = "sa";
	private static final String DATABASE_PASSWORD = "YOURNEWPASSWORD";
	private static Connection connection;


	public JavaH2Database() {

		if(connection == null)
		{
			connection = getDBConnection();
		}
		
	}

	@Override
	public List<Transfer> getTransactionsByDate(String dateFrom, String dateTo) {

		String getTransfersQuery = "SELECT SOURCE_ID, DESTINATION_ID, AMOUNT,  TRANSFERED_AT  FROM TRANSFER "
				+ "WHERE TRANSFERED_AT >= '" + dateFrom + "' AND  TRANSFERED_AT <= '" + dateTo + "'";		

		List<Transfer> result = new ArrayList<Transfer>();

		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(getTransfersQuery);
			while (rs.next()) {

				result.add(new Transfer(rs.getString("SOURCE_ID"), rs.getString("DESTINATION_ID"), rs.getInt("AMOUNT"),
						rs.getDate("TRANSFERED_AT")));

			}

			return result;
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
		return result;

	}

	@Override
	public String getFrequentAccount() {
		String createSQLQuery = "SELECT SOURCE_ID, COUNT(SOURCE_ID) AS COUNTEDMAX FROM TRANSFER "
				+ "GROUP BY (SOURCE_ID) " + "ORDER BY (COUNTEDMAX) " + "DESC " + "LIMIT 1;";
		
		String result;
		try {
			// Set auto commit to false
			connection.setAutoCommit(false);

			// Create a Statement Object
			Statement statement = connection.createStatement();

			// Execute the statement
			ResultSet rs = statement.executeQuery(createSQLQuery);

			// String result;
			rs.first();

			result = rs.getString(1);
			// Close the Statement Object
			statement.close();
			
			return result;
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}

		return "Failed";
	}
	
	// Make a connection to the H2 Database
	private static Connection getDBConnection() {

		Connection H2DBConnection = null;

		try {
			Class.forName(DATABASE_DRIVER);
		} catch (ClassNotFoundException ex) {
			System.out.println(ex.toString());
		}
		try {
			H2DBConnection = DriverManager.getConnection(DATABASE_CONNECTION, DATABASE_USER, DATABASE_PASSWORD);

			return H2DBConnection;
		} catch (SQLException ex) {
			System.out.println(ex.toString());
		}

		return H2DBConnection;
	}

	@Override
	public void AlterCreateTransfer() {

		Connection connection = getDBConnection();

		String getTransfersQuery1 = "CREATE TABLE IF NOT EXISTS TRANSFER(\r\n" + "	NUMBER INT AUTO_INCREMENT,\r\n"
				+ "	SOURCEID VARCHAR(2000),\r\n" + "	DESTINATIONID VARCHAR(2000),\r\n" + "	AMOUNT INT,\r\n"
				+ "	TRANSFEREDAT DATE, \r\n" + "); ";

		try {
			connection.setAutoCommit(false);

			Statement statement = connection.createStatement();
		
			statement.execute(getTransfersQuery1);
			
			statement.close();			

		} catch (SQLException ex) {
			System.out.println(ex.toString());
		}

	}
}
