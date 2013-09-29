package dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Utility class for DAO's.
 * This class contains commonly used DAO logic.
 * @author Yalchyk Ilya
 */
public final class DAOUtil {

	private static final String PROPERTY_FILE = "/db.properties";
	private static final String DRIVER_PROPERTY_NAME = "driver";
	private static final String URL_PROPERTY_NAME = "url";
	private static final String USER_PROPERTY_NAME = "username";
	private static final String PASSWORD_PROPERTY_NAME = "password";
	
	private DAOUtil() {}
	
	/**
	 * Take the last insert id using the current connection
	 * @param connection current connection
	 * @return last insert id
	 * @throws SQLException last insert id hasn't been received
	 */
	public static int lastInsertID(Connection connection) throws SQLException {
		PreparedStatement preparedStatement = connection.prepareStatement("SELECT LAST_INSERT_ID();");
		ResultSet result = preparedStatement.executeQuery();
		result.next();
		return result.getInt(1);
	}
	
    /**
     * Converts the given java.util.Date to java.sql.Date.
     * @param date The java.util.Date to be converted to java.sql.Date.
     * @return The converted java.sql.Date.
     */
    public static java.sql.Date toSqlDate(java.util.Date javaDate) {
    	return (javaDate != null) ? new java.sql.Date(javaDate.getTime()) : null;
    }

	/**
	 * Takes connection properties from PROPERTY_FILE
	 * and gets connection with received properties from driver manager
	 * @return connection to DB
	 * @throws ClassNotFoundException cannot load mysql driver
	 * @throws SQLException cannot get connection from driver manager
	 * @throws IOException property file is not available
	 */
	public static Connection getConnection()
			throws ClassNotFoundException, SQLException, IOException {
		
        Properties prop = new Properties();
        prop.load(MySqlContactsDAO.class.getClassLoader().getResourceAsStream(PROPERTY_FILE));
        String driver = prop.getProperty(DRIVER_PROPERTY_NAME);
        String url = prop.getProperty(URL_PROPERTY_NAME);
        String user = prop.getProperty(USER_PROPERTY_NAME);
        String password = prop.getProperty(PASSWORD_PROPERTY_NAME);
        
        Class.forName(driver);
        return DriverManager.getConnection(url, user, password);
	}
	
    /**
     * Close the Connection. Any errors will cause RuntimeException.
     * @param connection The Connection to be closed quietly.
     */
    public static void close(Connection connection) {
        if (connection != null) {
        	try {
    			connection.close();
    		} catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Close the Statement. Any errors will cause RuntimeException.
     * @param statement The Statement to be closed quietly.
     */
    public static void close(PreparedStatement statement) {
        if (statement != null) {
        	try {
        		statement.close();
    		} catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Close the ResultSet. Any errors will cause RuntimeException.
     * @param resultSet The ResultSet to be closed quietly.
     */
    public static void close(ResultSet resultSet) {
        if (resultSet != null) {
        	try {
        		resultSet.close();
    		} catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
