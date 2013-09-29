package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Attachment;

public class MySqlAttachmentsDAO {

	/** name of the table with attachments in DB */
	private static final String ATTACHMENTS_TABLE = "attachments";
	
	private static final String ID_COLUMN = "id";
	private static final String NAME_COLUMN = "name";
	private static final String PATH_COLUMN = "path";
	private static final String DATE_COLUMN = "date";
	private static final String COMMENT_COLUMN = "comment";
	private static final String CONTACT_ID_COLUMN = "contact_id";
	
	/** current connection to the DB */
	private Connection connection;
	
	private static final String GET_ATTACHMENTS_QUERY;
	private static final String CREATE_ATTACHMENT_QUERY;
	private static final String UPDATE_ATTACHMENT_QUERY;
	private static final String DELETE_ATTACHMENT_QUERY;
	
	/**
	 * Initialization of attachment queries
	 */
	static {
		
		StringBuffer queryBuffer = new StringBuffer();
		
		GET_ATTACHMENTS_QUERY = String.format("SELECT * FROM %s WHERE %s = ?", ATTACHMENTS_TABLE, CONTACT_ID_COLUMN);
		
		queryBuffer.append(String.format("INSERT INTO %s SET", ATTACHMENTS_TABLE));
		queryBuffer.append(String.format(" %s = ? ,", NAME_COLUMN));
		queryBuffer.append(String.format(" %s = ? ,", PATH_COLUMN));
		queryBuffer.append(String.format(" %s = ? ,", COMMENT_COLUMN));
		queryBuffer.append(String.format(" %s = ? ,", DATE_COLUMN));
		queryBuffer.append(String.format(" %s = ? ;", CONTACT_ID_COLUMN));
		
		CREATE_ATTACHMENT_QUERY = queryBuffer.toString();
		queryBuffer.setLength(0);
		
		queryBuffer.append(String.format("UPDATE %s SET", ATTACHMENTS_TABLE));		
		queryBuffer.append(String.format(" %s = ? ,", NAME_COLUMN));
		queryBuffer.append(String.format(" %s = ? ,", PATH_COLUMN));
		queryBuffer.append(String.format(" %s = ? ,", COMMENT_COLUMN));
		queryBuffer.append(String.format(" %s = ? ,", DATE_COLUMN));
		queryBuffer.append(String.format(" %s = ?", CONTACT_ID_COLUMN));
		queryBuffer.append(String.format(" WHERE %s = ?;", ID_COLUMN));
		
		UPDATE_ATTACHMENT_QUERY = queryBuffer.toString();
		queryBuffer.setLength(0);
		
		DELETE_ATTACHMENT_QUERY = String.format("DELETE FROM %s WHERE %s = ?", ATTACHMENTS_TABLE, ID_COLUMN);
	}
	
	/**
	 * Sets connection (should be created in contactsDAO)
	 * @param connection connection to DB
	 */
	public MySqlAttachmentsDAO(Connection connection) {
		this.connection = connection;
	}
	
	public List<Attachment> getAttachments(int contactIndex) throws SQLException {
		
		List<Attachment> attachmentsList = new ArrayList<>();
		
		ResultSet result = null;
		PreparedStatement preparedStatement = null;
		try {			
			preparedStatement = connection.prepareStatement(GET_ATTACHMENTS_QUERY);
			preparedStatement.setInt(1, contactIndex);
			result = preparedStatement.executeQuery();
			while (result.next()) {
				attachmentsList.add(map(result));
            }
		} catch (SQLException e) {
			throw e;
		} finally {
			DAOUtil.close(result);
        	DAOUtil.close(preparedStatement);
        }
		return attachmentsList;
	}
	
	public boolean createAttachment(Attachment attachment) throws SQLException {
		
		int i = 1;
		int changed;
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(CREATE_ATTACHMENT_QUERY);
			preparedStatement.setString(i++, attachment.getName());
			preparedStatement.setString(i++, attachment.getPath());
			preparedStatement.setString(i++, attachment.getComment());
			preparedStatement.setString(i++, attachment.getDate());
			preparedStatement.setInt(i++, attachment.getContactID());
	
			changed = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
        	DAOUtil.close(preparedStatement);
        }
		
		if (changed != 1) {
			return false;
		}
		return true;
	}
	
	public boolean updateAttachment(int index, Attachment attachment) throws SQLException {

		int i = 1;
		PreparedStatement preparedStatement = null;
		preparedStatement = connection.prepareStatement(UPDATE_ATTACHMENT_QUERY);
		preparedStatement.setString(i++, attachment.getName());
		preparedStatement.setString(i++, attachment.getPath());
		preparedStatement.setString(i++, attachment.getComment());
		preparedStatement.setString(i++, attachment.getDate());
		preparedStatement.setInt(i++, attachment.getContactID());
		preparedStatement.setInt(i++, index);

		int changed = preparedStatement.executeUpdate();
		if (changed != 1) {
			return false;
		}
		return true;
	}
	
	public boolean deleteAttachment(int index) throws SQLException {

		PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ATTACHMENT_QUERY);
		preparedStatement.setInt(1, index);
		int changed = preparedStatement.executeUpdate();
		if (changed != 1) {
			return false;
		}
		return true;
	}

	private static Attachment map(ResultSet result) throws SQLException {
		
		Attachment attachment = new Attachment();
		
		int id = result.getInt(ID_COLUMN);
		String name = result.getString(NAME_COLUMN);
		String path = result.getString(PATH_COLUMN);
		String date = result.getString(DATE_COLUMN);
		String comment = result.getString(COMMENT_COLUMN);
		int contactID = result.getInt(CONTACT_ID_COLUMN);
		
		attachment.setId(id);
		attachment.setName(name);
		attachment.setPath(path);
		attachment.setDate(date);
		attachment.setComment(comment);
		attachment.setContactID(contactID);

		return attachment;
	}

}
