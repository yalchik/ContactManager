package dao;

import java.util.List;

import model.Contact;

/**
 * Interface for data access objects
 * @author Yalchyk Ilya
 */
public interface IContactsDAO {

	/**
	 * Opens new connection to the data base,
	 * which is configured in db.property file
	 * @throws DAOConfigurationException connection hasn't been opened
	 */
	public void openConnection() throws DAOConfigurationException;
	
	/**
	 * Closes current connection to the data base,
	 * which has been opened earlier
	 */
	public void closeConnection();
	
	/**
	 * If there is contact with current ID, then returns it, else returns null
	 * @param id current ID
	 * @return contact with current ID or null
	 * @throws DAOException request to DB has been failed
	 */
	public Contact getContact(int id) throws DAOException;
	
	/**
	 * Gets count of contacts from DB
	 * Beginning with start number
	 * (skip 'start' number of contacts)
	 * @param start number of contacts for skipping
	 * @param count number of contacts for getting
	 * @return list of contacts
	 * @throws DAOException request to DB has been failed
	 */
	public List<Contact> getContacts(int start, int count) throws DAOException;
	
	/**
	 * Inserts current contact into DB
	 * @param newContact contact for inserting
	 * @return true, if inserting has been successful
	 * @throws DAOException request to DB has been failed
	 */
	public boolean createContact(Contact newContact) throws DAOException;
	
	/**
	 * Updates contact with current index using field values of newContact
	 * @param index id of old contact
	 * @param newContact contact for updating
	 * @return true, if updating has been successful
	 * @throws DAOException request to DB has been failed
	 */
	public boolean updateContact(int index, Contact newContact) throws DAOException;
	
	/**
	 * Deletes contact with current index
	 * @param index index of deleting contact
	 * @return 
	 * @throws DAOException 
	 */
	public boolean deleteContact(int index) throws DAOException;
			
	/**
	 * Finds contacts, that match current template contact.
	 * Match means, that template contact has field value, not equal to its default value, but equal to current contact's value.
	 * Default value means, that comparison of the field is not needed
	 * Exception for dates:
	 * relation between template field and current field is determined by dateCompareOperator (not only equality)
	 * Gets 'count' of contacts from DB, beginning with start number
	 * (skip 'start' number of contacts)
	 * If count = -1, then gets all contacts without limit
	 * @param templateContact contact with default or custom (valid) field values 
	 * @param dateCompareOperator operator for date comparison (<, >, <=, >=, =)	 * 
	 * @param start number of contacts for skipping
	 * @param count number of contacts for getting, or -1
	 * @return list of contacts
	 * @throws DAOException request to DB has been failed
	 */
	public List<Contact> findContactsLike(Contact templateContact, String dateCompareOperator, int start, int count) throws DAOException;
	
	/**
	 * Gets contacts from DB, which have birthday on current month and day
	 * @param month month of birthday
	 * @param day day of birthday
	 * @return list of contacts with birthday on current month and day
	 * @throws DAOException request to DB has been failed
	 */
	public List<Contact> findContactsWithBirthday(int month, int day) throws DAOException;
}
