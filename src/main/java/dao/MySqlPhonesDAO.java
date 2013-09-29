package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Phone;
import model.Phone.PhoneType;

public class MySqlPhonesDAO {

	/** name of the table with phones in DB */
	private static final String PHONES_TABLE = "phones";
	
	private static final String ID_COLUMN = "id";
	private static final String COUNTRY_CODE_COLUMN = "code1";
	private static final String OPERATOR_CODE_COLUMN = "code2";
	private static final String PHONE_NUMBER_COLUMN = "phoneNumber";
	private static final String PHONE_TYPE_COLUMN = "phoneType";
	private static final String COMMENT_COLUMN = "comment";
	private static final String CONTACT_ID_COLUMN = "contact_id";
	
	/** current connection to the DB */
	private Connection connection;
	
	private static final String GET_PHONES_QUERY;
	private static final String CREATE_PHONE_QUERY;
	private static final String UPDATE_PHONE_QUERY;
	private static final String DELETE_PHONE_QUERY;
	
	/**
	 * Initialization of phone queries
	 */
	static {
		
		StringBuffer queryBuffer = new StringBuffer();
		
		GET_PHONES_QUERY = String.format("SELECT * FROM %s WHERE %s = ?", PHONES_TABLE, CONTACT_ID_COLUMN);
		
		queryBuffer.append(String.format("INSERT INTO %s SET", PHONES_TABLE));
		queryBuffer.append(String.format(" %s = ? ,", COUNTRY_CODE_COLUMN));
		queryBuffer.append(String.format(" %s = ? ,", OPERATOR_CODE_COLUMN));
		queryBuffer.append(String.format(" %s = ? ,", PHONE_NUMBER_COLUMN));
		queryBuffer.append(String.format(" %s = ? ,", PHONE_TYPE_COLUMN));
		queryBuffer.append(String.format(" %s = ? ,", COMMENT_COLUMN));
		queryBuffer.append(String.format(" %s = ? ;", CONTACT_ID_COLUMN));
		
		CREATE_PHONE_QUERY = queryBuffer.toString();
		queryBuffer.setLength(0);
		
		queryBuffer.append(String.format("UPDATE %s SET", PHONES_TABLE));		
		queryBuffer.append(String.format(" %s = ? ,", COUNTRY_CODE_COLUMN));
		queryBuffer.append(String.format(" %s = ? ,", OPERATOR_CODE_COLUMN));
		queryBuffer.append(String.format(" %s = ? ,", PHONE_NUMBER_COLUMN));
		queryBuffer.append(String.format(" %s = ? ,", PHONE_TYPE_COLUMN));
		queryBuffer.append(String.format(" %s = ? ,", COMMENT_COLUMN));
		queryBuffer.append(String.format(" %s = ?", CONTACT_ID_COLUMN));
		queryBuffer.append(String.format(" WHERE %s = ?;", ID_COLUMN));
		
		UPDATE_PHONE_QUERY = queryBuffer.toString();
		queryBuffer.setLength(0);
		
		DELETE_PHONE_QUERY = String.format("DELETE FROM %s WHERE %s = ?", PHONES_TABLE, ID_COLUMN);
	}
	
	/**
	 * Sets connection (should be created in contactsDAO)
	 * @param connection connection to DB
	 */
	public MySqlPhonesDAO(Connection connection) {
		this.connection = connection;
	}
	
	public List<Phone> getPhones(int contactIndex) throws SQLException {
		
		List<Phone> phonesList = new ArrayList<>();
		
		ResultSet result = null;
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(GET_PHONES_QUERY);
			preparedStatement.setInt(1, contactIndex);
			result = preparedStatement.executeQuery();
			while (result.next()) {
				phonesList.add(map(result));
            }
		} catch (SQLException e) {
			throw e;
        } finally {
        	DAOUtil.close(result);
        	DAOUtil.close(preparedStatement);
        }
		return phonesList;
	}
	
	public boolean createPhone(Phone phone) throws SQLException {
		
		int i = 1;
		PreparedStatement preparedStatement = connection.prepareStatement(CREATE_PHONE_QUERY);
		preparedStatement.setString(i++, phone.getCode1());
		preparedStatement.setString(i++, phone.getCode2());
		preparedStatement.setString(i++, phone.getPhoneNumber());
		preparedStatement.setString(i++, phone.getPhoneType().toString().toLowerCase());
		preparedStatement.setString(i++, phone.getComment());
		preparedStatement.setInt(i++, phone.getContactID());

		int changed = preparedStatement.executeUpdate();
		if (changed != 1) {
			return false;
		}
		return true;
	}
	
	public boolean updatePhone(int index, Phone phone) throws SQLException {
		
		int i = 1;
		PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PHONE_QUERY);
		preparedStatement.setString(i++, phone.getCode1());
		preparedStatement.setString(i++, phone.getCode2());
		preparedStatement.setString(i++, phone.getPhoneNumber());
		preparedStatement.setString(i++, phone.getPhoneType().toString().toLowerCase());
		preparedStatement.setString(i++, phone.getComment());
		preparedStatement.setInt(i++, phone.getContactID());
		preparedStatement.setInt(i++, index);

		int changed = preparedStatement.executeUpdate();
		if (changed != 1) {
			return false;
		}
		return true;
	}
	
	public boolean deletePhone(int index) throws SQLException {

		PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PHONE_QUERY);
		preparedStatement.setInt(1, index);
		int changed = preparedStatement.executeUpdate();
		if (changed != 1) {
			return false;
		}
		return true;
	}
	
	private static Phone map(ResultSet result) throws SQLException {
		
		Phone phone = new Phone();
		
		int id = result.getInt(ID_COLUMN);
		String countryCode = result.getString(COUNTRY_CODE_COLUMN);
		String operatorCode = result.getString(OPERATOR_CODE_COLUMN);
		String phoneNumber = result.getString(PHONE_NUMBER_COLUMN);
		String phoneType = result.getString(PHONE_TYPE_COLUMN);
		String comment = result.getString(COMMENT_COLUMN);
		int contactID = result.getInt(CONTACT_ID_COLUMN);
		
		phone.setId(id);
		phone.setCode1(countryCode);
		phone.setCode2(operatorCode);
		phone.setPhoneNumber(phoneNumber);
		phone.setPhoneType(PhoneType.valueOf(phoneType.toUpperCase()));
		phone.setComment(comment);
		phone.setContactID(contactID);
		
		return phone;
	}
}
