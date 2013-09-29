package dao;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

import controller.helpers.RequestHelper;
import model.*;
import model.Attachment.*;
import model.Contact.*;
import model.Phone.*;

public class MySqlContactsDAO implements IContactsDAO {
		
	/** name of the table with contacts in DB */
	private static final String CONTACTS_TABLE = "contacts";
	
	private static final String ID_COLUMN = "id";
	private static final String FIRSTNAME_COLUMN = "firstname";
	private static final String LASTNAME_COLUMN = "lastname";
	private static final String MIDNAME_COLUMN = "midname";
	private static final String BIRTHDAY_COLUMN = "birthday";
	private static final String SEX_COLUMN = "sex";
	private static final String CITIZENSHIP_COLUMN = "citizenship";
	private static final String MARITAL_STATUS_COLUMN = "marital_status";
	private static final String URL_COLUMN = "website";
	private static final String EMAIL_COLUMN = "email";
	private static final String JOB_COLUMN = "job";
	private static final String COUNTRY_COLUMN = "country";
	private static final String CITY_COLUMN = "city";
	private static final String STREET_COLUMN = "street";
	private static final String HOUSE_COLUMN = "house";
	private static final String APARTMENT_COLUMN = "apartment";
	private static final String POSTCODE_COLUMN = "postcode";
	private static final String PHOTO_COLUMN = "photo";
		
	/** current connection to DB */
	private Connection connection;
		
	private MySqlPhonesDAO phonesDAO;
	private MySqlAttachmentsDAO attachmentsDAO;
	
	private static final String GET_CONTACT_QUERY;
	private static final String GET_CONTACTS_QUERY;
	private static final String CREATE_CONTACT_QUERY;
	private static final String UPDATE_CONTACT_QUERY;
	private static final String DELETE_CONTACT_QUERY;
	private static final String BIRTHDAY_CONTACT_QUERY;
	
	/**
	 * Initialization of contact queries
	 */
	static {

		StringBuffer queryBuffer = new StringBuffer();
		
		GET_CONTACT_QUERY = String.format("SELECT * FROM %s WHERE %s = ?", CONTACTS_TABLE, ID_COLUMN);
		
		GET_CONTACTS_QUERY = String.format("SELECT * FROM %s LIMIT ?, ?", CONTACTS_TABLE);
				
		queryBuffer.append(String.format("INSERT INTO %s SET", CONTACTS_TABLE));
		queryBuffer.append(String.format(" %s = ? ,", FIRSTNAME_COLUMN));
		queryBuffer.append(String.format(" %s = ? ,", LASTNAME_COLUMN));
		queryBuffer.append(String.format(" %s = ? ,", MIDNAME_COLUMN));
		queryBuffer.append(String.format(" %s = ? ,", BIRTHDAY_COLUMN));
		queryBuffer.append(String.format(" %s = ? ,", SEX_COLUMN));
		queryBuffer.append(String.format(" %s = ? ,", CITIZENSHIP_COLUMN));
		queryBuffer.append(String.format(" %s = ? ,", MARITAL_STATUS_COLUMN));
		queryBuffer.append(String.format(" %s = ? ,", URL_COLUMN));
		queryBuffer.append(String.format(" %s = ? ,", EMAIL_COLUMN));
		queryBuffer.append(String.format(" %s = ? ,", JOB_COLUMN));
		queryBuffer.append(String.format(" %s = ? ,", COUNTRY_COLUMN));
		queryBuffer.append(String.format(" %s = ? ,", CITY_COLUMN));
		queryBuffer.append(String.format(" %s = ? ,", STREET_COLUMN));
		queryBuffer.append(String.format(" %s = ? ,", HOUSE_COLUMN));
		queryBuffer.append(String.format(" %s = ? ,", APARTMENT_COLUMN));
		queryBuffer.append(String.format(" %s = ? ;", POSTCODE_COLUMN));
		
		CREATE_CONTACT_QUERY = queryBuffer.toString();
		queryBuffer.setLength(0);
		
		queryBuffer.append(String.format("UPDATE %s SET", CONTACTS_TABLE));		
		queryBuffer.append(String.format(" %s = ? ,", FIRSTNAME_COLUMN));
		queryBuffer.append(String.format(" %s = ? ,", LASTNAME_COLUMN));
		queryBuffer.append(String.format(" %s = ? ,", MIDNAME_COLUMN));
		queryBuffer.append(String.format(" %s = ? ,", BIRTHDAY_COLUMN));
		queryBuffer.append(String.format(" %s = ? ,", SEX_COLUMN));
		queryBuffer.append(String.format(" %s = ? ,", CITIZENSHIP_COLUMN));
		queryBuffer.append(String.format(" %s = ? ,", MARITAL_STATUS_COLUMN));
		queryBuffer.append(String.format(" %s = ? ,", URL_COLUMN));
		queryBuffer.append(String.format(" %s = ? ,", EMAIL_COLUMN));
		queryBuffer.append(String.format(" %s = ? ,", JOB_COLUMN));
		queryBuffer.append(String.format(" %s = ? ,", COUNTRY_COLUMN));
		queryBuffer.append(String.format(" %s = ? ,", CITY_COLUMN));
		queryBuffer.append(String.format(" %s = ? ,", STREET_COLUMN));
		queryBuffer.append(String.format(" %s = ? ,", HOUSE_COLUMN));
		queryBuffer.append(String.format(" %s = ? ,", APARTMENT_COLUMN));
		queryBuffer.append(String.format(" %s = ? ,", POSTCODE_COLUMN));
		queryBuffer.append(String.format(" %s = ? ", PHOTO_COLUMN));
		queryBuffer.append(String.format(" WHERE %s = ?;", ID_COLUMN));
		
		UPDATE_CONTACT_QUERY = queryBuffer.toString();
		queryBuffer.setLength(0);
		
		DELETE_CONTACT_QUERY = String.format("DELETE FROM %s WHERE %s = ?", CONTACTS_TABLE, ID_COLUMN);

		BIRTHDAY_CONTACT_QUERY = String.format("SELECT * FROM %s WHERE %s LIKE ?", CONTACTS_TABLE, BIRTHDAY_COLUMN);
	}
	
	/**
	 * @see IContactsDAO#openConnection()
	 */
	public void openConnection() throws DAOConfigurationException {
		try {
			connection = DAOUtil.getConnection();
        } catch (ClassNotFoundException e) {
        	throw new DAOConfigurationException("Cannot load mysql driver. Check mysql-connector.", e);
        } catch (SQLException e) {
            throw new DAOConfigurationException("Cannot get connection from driver manager (check password in property file)", e);
        } catch (IOException e) {
        	throw new DAOConfigurationException("Property file is not available", e);
        }
		initOtherDAOs();
	}
	
	/**
	 * Initialization of phoneDAO and attachmentsDAO,
	 */
	private void initOtherDAOs() {
		phonesDAO = new MySqlPhonesDAO(connection);
		attachmentsDAO = new MySqlAttachmentsDAO(connection);
	}
	
	/**
	 * @see IContactsDAO#closeConnection()
	 */
	public void closeConnection() {
		DAOUtil.close(connection);
	}
	
	/**
	 * @see IContactsDAO#getContact(int)
	 */
	public Contact getContact(int id) throws DAOException {
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {			
			preparedStatement = connection.prepareStatement(GET_CONTACT_QUERY);
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			resultSet.next();
			Contact contact = map(resultSet);
			
			List<Phone> phones = phonesDAO.getPhones(contact.getId());
			contact.setPhones(phones);
			
			List<Attachment> attachments = attachmentsDAO.getAttachments(contact.getId());
			contact.setAttachments(attachments);
			
			return contact;
			
		} catch (SQLException e) {
			throw new DAOException(e);			
        } finally {
        	DAOUtil.close(resultSet);
        	DAOUtil.close(preparedStatement);        	
        }
	}
	
	/**
	 * @see IContactsDAO#getContacts(int, int)
	 */
	public List<Contact> getContacts(int start, int count) throws DAOException {

		List<Contact> contactsList = new ArrayList<>();
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = connection.prepareStatement(GET_CONTACTS_QUERY);
			preparedStatement.setInt(1, start);
			preparedStatement.setInt(2, count);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				contactsList.add(map(resultSet));
            }
		} catch (SQLException e) {
			throw new DAOException(e);
        } finally {
        	DAOUtil.close(preparedStatement);
        	DAOUtil.close(resultSet);
        }
		return contactsList;
	}

	/**
	 * @see IContactsDAO#findContactsLike(Contact, String)
	 */
	public List<Contact> findContactsLike(Contact templateContact,
			String dateCompareOperator, int start, int count) throws DAOException {
		
		List<Contact> contactsList = new ArrayList<>();
		
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		BitSet templateMask = makeTemplateMask(templateContact);
		String queryString = makeSearchQuery(templateMask, dateCompareOperator, start, count);
		try {
			preparedStatement = connection.prepareStatement(queryString);
			attachTemplateContactToPreparedStatement(preparedStatement, templateContact, templateMask, start, count);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				contactsList.add(map(resultSet));
            }
		} catch (SQLException e) {
			throw new DAOException(e);
        } finally {
        	DAOUtil.close(resultSet);
        	DAOUtil.close(preparedStatement);        	
        }
		return contactsList;
	}

	/**
	 * If templateMask[i] == true,
	 * then puts value of i-th field of templateContact
	 * to the next preparedStatement parameter 
	 * @param preparedStatement search statement
	 * @param templateContact contact with search criteria
	 * @param templateMask bit mask, which shows what criteria are set
	 * @throws SQLException cannot set parameter to prepared statement
	 */
	private static void attachTemplateContactToPreparedStatement(
			PreparedStatement preparedStatement, Contact templateContact, BitSet templateMask, int start, int count)
					throws SQLException {
		
		int i = 0;
		int j = 1;
		if (templateMask.get(i++)) {
			preparedStatement.setString(j++, templateContact.getFirstname());
		}
		if (templateMask.get(i++)) {
			preparedStatement.setString(j++, templateContact.getLastname());
		}
		if (templateMask.get(i++)) {
			preparedStatement.setString(j++, templateContact.getMidname());
		}
		if (templateMask.get(i++)) {
			preparedStatement.setString(j++, templateContact.getBirthday());
		}
		if (templateMask.get(i++)) {
			preparedStatement.setString(j++, templateContact.getSex().toString());
		}
		if (templateMask.get(i++)) {
			preparedStatement.setString(j++, templateContact.getCitizenship());
		}
		if (templateMask.get(i++)) {
			preparedStatement.setString(j++, templateContact.getMartialStatus().toString());
		}
		if (templateMask.get(i++)) {
			preparedStatement.setString(j++, templateContact.getUrl());
		}
		if (templateMask.get(i++)) {
			preparedStatement.setString(j++, templateContact.getEmail());
		}
		if (templateMask.get(i++)) {
			preparedStatement.setString(j++, templateContact.getJob());
		}
		if (templateMask.get(i++)) {
			preparedStatement.setString(j++, templateContact.getCountry());
		}
		if (templateMask.get(i++)) {
			preparedStatement.setString(j++, templateContact.getCity());
		}
		if (templateMask.get(i++)) {
			preparedStatement.setString(j++, templateContact.getStreet());
		}
		if (templateMask.get(i++)) {
			preparedStatement.setInt(j++, templateContact.getHouse());
		}
		if (templateMask.get(i++)) {
			preparedStatement.setInt(j++, templateContact.getApartment());
		}
		if (templateMask.get(i++)) {
			preparedStatement.setInt(j++, templateContact.getPostcode());
		}
		if (start != -1 && count != -1) {
			preparedStatement.setInt(j++, start);
			preparedStatement.setInt(j++, count);
		}
	}
	
	/**
	 * If templateMask[i] == true, then puts i-th column to the sql request.
	 * @param templateMask bit mask, which shows what criteria are set
	 * @param dateCompareOperator operator for date comparison (<, >, <=, >=, =)
	 * @param start number of contacts for skipping
	 * @param count number of contacts for getting, or -1
	 * @return query parametric string for sql request
	 */
	private static String makeSearchQuery(BitSet templateMask, String dateCompareOperator, int start, int count) {
		
		StringBuffer searchQuery = new StringBuffer(String.format("SELECT * FROM %s WHERE 1=1", CONTACTS_TABLE));
		
		int i = 0;
		if (templateMask.get(i++)) {
			searchQuery.append(String.format(" AND %s = ?", FIRSTNAME_COLUMN));
		}
		if (templateMask.get(i++)) {
			searchQuery.append(String.format(" AND %s = ?", LASTNAME_COLUMN));
		}
		if (templateMask.get(i++)) {
			searchQuery.append(String.format(" AND %s = ?", MIDNAME_COLUMN));
		}
		if (templateMask.get(i++)) {
			searchQuery.append(String.format(" AND %s %s ?", BIRTHDAY_COLUMN, dateCompareOperator));
		}
		if (templateMask.get(i++)) {
			searchQuery.append(String.format(" AND %s = ?", SEX_COLUMN));
		}
		if (templateMask.get(i++)) {
			searchQuery.append(String.format(" AND %s = ?", CITIZENSHIP_COLUMN));
		}
		if (templateMask.get(i++)) {
			searchQuery.append(String.format(" AND %s = ?", MARITAL_STATUS_COLUMN));
		}
		if (templateMask.get(i++)) {
			searchQuery.append(String.format(" AND %s = ?", URL_COLUMN));
		}
		if (templateMask.get(i++)) {
			searchQuery.append(String.format(" AND %s = ?", EMAIL_COLUMN));
		}
		if (templateMask.get(i++)) {
			searchQuery.append(String.format(" AND %s = ?", JOB_COLUMN));
		}
		if (templateMask.get(i++)) {
			searchQuery.append(String.format(" AND %s = ?", COUNTRY_COLUMN));
		}
		if (templateMask.get(i++)) {
			searchQuery.append(String.format(" AND %s = ?", CITY_COLUMN));
		}
		if (templateMask.get(i++)) {
			searchQuery.append(String.format(" AND %s = ?", STREET_COLUMN));
		}
		if (templateMask.get(i++)) {
			searchQuery.append(String.format(" AND %s = ?", HOUSE_COLUMN));
		}
		if (templateMask.get(i++)) {
			searchQuery.append(String.format(" AND %s = ?", APARTMENT_COLUMN));
		}
		if (templateMask.get(i++)) {
			searchQuery.append(String.format(" AND %s = ?", POSTCODE_COLUMN));
		}
		
		if (count != -1) {
			searchQuery.append(" LIMIT ?, ?");
		}
		searchQuery.append(";");
		
		return searchQuery.toString();
	}
	
	/**
	 * If the value of i-th field of templateContact is not default
	 * then templateMask[i] == true
	 * That means, that value will be included to sql request
	 * Default value means, that no need to include it to sql request
	 * @param templateContact contact with search criteria
	 * @return template mask
	 */
	private static BitSet makeTemplateMask(Contact templateContact) {
		
		final int numberOfFields = 16;
		BitSet templateMask = new BitSet(numberOfFields);

		int i = 0;
		
		if (!templateContact.getFirstname().equals(
				RequestHelper.getDefaultValue(
						RequestHelper.ContactSpecificParameters.FIRSTNAME_PARAMETER))) {
			templateMask.set(i);
		}
		i++;
		if (!templateContact.getLastname().equals(
				RequestHelper.getDefaultValue(
						RequestHelper.ContactSpecificParameters.LASTNAME_PARAMETER))) {
			templateMask.set(i);
		}
		i++;
		if (!templateContact.getMidname().equals(
				RequestHelper.getDefaultValue(
						RequestHelper.ContactSpecificParameters.MIDNAME_PARAMETER))) {
			
			templateMask.set(i);
		}
		i++;
		if (!templateContact.getBirthday().equals(
				RequestHelper.getDefaultValue(
						RequestHelper.ContactSpecificParameters.BIRTHDAY_PARAMETER))) {
			templateMask.set(i);
		}
		i++;
		if (!templateContact.getSex().equals(
				Contact.Sex.valueOf(
						RequestHelper.getDefaultValue(
								RequestHelper.ContactSpecificParameters.SEX_PARAMETER).toUpperCase()))) {
			templateMask.set(i);
		}
		i++;
		if (!templateContact.getCitizenship().equals(
				RequestHelper.getDefaultValue(
						RequestHelper.ContactSpecificParameters.CITIZENSHIP_PARAMETER))) {
			templateMask.set(i);
		}
		i++;
		if (!templateContact.getMartialStatus().equals(
				Contact.MartialStatus.valueOf(
						RequestHelper.getDefaultValue(
								RequestHelper.ContactSpecificParameters.MARITAL_STATUS_PARAMETER).toUpperCase()))) {
			templateMask.set(i);
		}
		i++;
		if (!templateContact.getUrl().equals(
				RequestHelper.getDefaultValue(
						RequestHelper.ContactSpecificParameters.WEB_SITE_PARAMETER))) {
			templateMask.set(i);
		}
		i++;
		if (!templateContact.getEmail().equals(
				RequestHelper.getDefaultValue(
						RequestHelper.ContactSpecificParameters.EMAIL_PARAMETER))) {
			templateMask.set(i);
		}
		i++;
		if (!templateContact.getJob().equals(
				RequestHelper.getDefaultValue(
						RequestHelper.ContactSpecificParameters.JOB_PARAMETER))) {
			templateMask.set(i);
		}
		i++;
		if (!templateContact.getCountry().equals(
				RequestHelper.getDefaultValue(
						RequestHelper.ContactSpecificParameters.COUNTRY_PARAMETER))) {
			templateMask.set(i);
		}
		i++;
		if (!templateContact.getCity().equals(
				RequestHelper.getDefaultValue(
						RequestHelper.ContactSpecificParameters.CITY_PARAMETER))) {
			templateMask.set(i);
		}
		i++;
		if (!templateContact.getStreet().equals(
				RequestHelper.getDefaultValue(
						RequestHelper.ContactSpecificParameters.STREET_PARAMETER))) {
			templateMask.set(i);
		}
		i++;
		if (!String.valueOf(templateContact.getHouse()).equals(
				RequestHelper.getDefaultValue(
						RequestHelper.ContactSpecificParameters.HOUSE_PARAMETER))) {
			templateMask.set(i);
		}
		i++;
		if (!String.valueOf(templateContact.getApartment()).equals(
				RequestHelper.getDefaultValue(
						RequestHelper.ContactSpecificParameters.APARTMENT_PARAMETER))) {
			templateMask.set(i);
		}
		i++;
		if (!String.valueOf(templateContact.getPostcode()).equals(
				RequestHelper.getDefaultValue(
						RequestHelper.ContactSpecificParameters.POSTCODE_PARAMETER))) {
			templateMask.set(i);
		}
		
		return templateMask;
	}
	
	/**
	 * @see IContactsDAO#createContact(Contact)
	 */
	public boolean createContact(Contact newContact) throws DAOException {
		
		int i = 1;
		PreparedStatement preparedStatement = null;
		try {			
			preparedStatement = connection.prepareStatement(CREATE_CONTACT_QUERY);
			preparedStatement.setString(i++, newContact.getFirstname());
			preparedStatement.setString(i++, newContact.getLastname());
			preparedStatement.setString(i++, newContact.getMidname());
			if (!newContact.getBirthday().equals("")) {
				preparedStatement.setString(i++, newContact.getBirthday());
			}
			else {
				preparedStatement.setNull(i++, java.sql.Types.DATE);
			}
			preparedStatement.setString(i++, newContact.getSex().toString().toLowerCase());
			preparedStatement.setString(i++, newContact.getCitizenship());
			preparedStatement.setString(i++, newContact.getMartialStatus().toString().toLowerCase());
			preparedStatement.setString(i++, newContact.getUrl());
			preparedStatement.setString(i++, newContact.getEmail());
			preparedStatement.setString(i++, newContact.getJob());
			preparedStatement.setString(i++, newContact.getCountry());
			preparedStatement.setString(i++, newContact.getCity());
			preparedStatement.setString(i++, newContact.getStreet());
			preparedStatement.setInt(i++, newContact.getHouse());
			preparedStatement.setInt(i++, newContact.getApartment());
			preparedStatement.setInt(i++, newContact.getPostcode());
			
			int changed = preparedStatement.executeUpdate();
			if (changed != 1) {
				return false;
			}
			
			int contactID = DAOUtil.lastInsertID(connection);
			createContactPhones(contactID, newContact.getPhones());
			createContactAttachments(contactID, newContact.getAttachments());			
			
		} catch (SQLException e) {
			throw new DAOException(e);
        } finally {
        	DAOUtil.close(preparedStatement);
        }
		return true;
	}
	
	/**
	 * Inserts phones into DB using phonesDAO
	 * @param contactID id of contact, which includes phones
	 * @param phones phones of the contact with id = contactID
	 * @throws SQLException cannot create phone
	 */
	private void createContactPhones(int contactID, List<Phone> phones) throws SQLException {
		for (Phone phone : phones) {
			if (phone.getPhoneStatus() == PhoneStatus.NEW) {
				phone.setContactID(contactID);
				phonesDAO.createPhone(phone);
			}
		}
	}
	
	/**
	 * Inserts attachments into DB using attachmentsDAO
	 * @param contactID id of contact, which includes attachments
	 * @param attachments attachments of the contact with id = contactID
	 * @throws SQLException cannot create attachment
	 */
	private void createContactAttachments(int contactID, List<Attachment> attachments) throws SQLException {
		for (Attachment attachment : attachments) {
			if (attachment.getAttachmentStatus() == AttachmentStatus.NEW) {
				attachment.setContactID(contactID);
				attachmentsDAO.createAttachment(attachment);
			}
		}
	}
	
	/**
	 * @see IContactsDAO#deleteContact(int)
	 */
	public boolean deleteContact(int index) throws DAOException {

		PreparedStatement preparedStatement = null;		
		try {
			preparedStatement = connection.prepareStatement(DELETE_CONTACT_QUERY);
			preparedStatement.setInt(1, index);
			int changed = preparedStatement.executeUpdate();
			if (changed != 1) {
				return false;
			}
		} catch (SQLException e) {
			throw new DAOException(e);
        } finally {
        	DAOUtil.close(preparedStatement);
        }
		return true;
	}
	
	/**
	 * @see IContactsDAO#updateContact(int, Contact)
	 */
	public boolean updateContact(int index, Contact newContact) throws DAOException {
		
		int i = 1;
		PreparedStatement preparedStatement = null;
		try {			
			preparedStatement = connection.prepareStatement(UPDATE_CONTACT_QUERY);
			preparedStatement.setString(i++, newContact.getFirstname());
			preparedStatement.setString(i++, newContact.getLastname());
			preparedStatement.setString(i++, newContact.getMidname());
			if (!newContact.getBirthday().equals("")) {
				preparedStatement.setString(i++, newContact.getBirthday().toString());
			}
			else {
				preparedStatement.setNull(i++, java.sql.Types.DATE);
			}
			preparedStatement.setString(i++, newContact.getSex().toString().toLowerCase());
			preparedStatement.setString(i++, newContact.getCitizenship());
			preparedStatement.setString(i++, newContact.getMartialStatus().toString().toLowerCase());
			preparedStatement.setString(i++, newContact.getUrl());
			preparedStatement.setString(i++, newContact.getEmail());
			preparedStatement.setString(i++, newContact.getJob());
			preparedStatement.setString(i++, newContact.getCountry());
			preparedStatement.setString(i++, newContact.getCity());
			preparedStatement.setString(i++, newContact.getStreet());
			preparedStatement.setInt(i++, newContact.getHouse());
			preparedStatement.setInt(i++, newContact.getApartment());
			preparedStatement.setInt(i++, newContact.getPostcode());
			preparedStatement.setString(i++, newContact.getPhotoPath());
			preparedStatement.setInt(i++, index);
			
			int changed = preparedStatement.executeUpdate();
			if (changed != 1) {
				return false;
			}
			
			updatePhones(newContact.getPhones());
			updateAttachments(newContact.getAttachments());			
			
		} catch (SQLException e) {
			throw new DAOException(e);
        } finally {
        	DAOUtil.close(preparedStatement);
        }
		return true;
	}
	
	/**
	 * Updates phones in DB using phonesDAO
	 * @param phones new phones
	 * @throws SQLException cannot update phone
	 */
	private void updatePhones(List<Phone> phones) throws SQLException {
		for (Phone phone : phones) {
			if (phone.getPhoneStatus() == PhoneStatus.NEW) {
				phonesDAO.createPhone(phone);
			}
			else if (phone.getPhoneStatus() == PhoneStatus.REMOVED) {
				phonesDAO.deletePhone(phone.getId());
			}
			else {
				phonesDAO.updatePhone(phone.getId(), phone);
			}
		}
	}
	
	/**
	 * Updates attachments in DB using attachmentsDAO
	 * @param attachments new attachments
	 * @throws SQLException cannot update attachment
	 */
	private void updateAttachments(List<Attachment> attachments) throws SQLException {
		for (Attachment attachment : attachments) {
			if (attachment.getAttachmentStatus() == AttachmentStatus.NEW) {
				attachmentsDAO.createAttachment(attachment);
			}
			else if (attachment.getAttachmentStatus() == AttachmentStatus.REMOVED) {
				attachmentsDAO.deleteAttachment(attachment.getId());
			}
			else {
				attachmentsDAO.updateAttachment(attachment.getId(), attachment);
			}
		}
	}
	
	/**
     * Map the current row of the given ResultSet to a Contact.
     * @param result The ResultSet of which the current row is to be mapped to a Contact.
     * @return The mapped Contact from the current row of the given ResultSet.
     * @throws SQLException If something fails at database level.
     */
	private static Contact map(ResultSet result) throws SQLException {
		
		Contact contact = new Contact();

		int id = result.getInt(ID_COLUMN);
		String firstname = result.getString(FIRSTNAME_COLUMN);
		String lastname = result.getString(LASTNAME_COLUMN);
		String midname = result.getString(MIDNAME_COLUMN);
		String birthday = result.getString(BIRTHDAY_COLUMN);
		String sex = result.getString(SEX_COLUMN);
		String citizenship = result.getString(CITIZENSHIP_COLUMN);
		String marital_status = result.getString(MARITAL_STATUS_COLUMN);
		String url = result.getString(URL_COLUMN);
		String email = result.getString(EMAIL_COLUMN);
		String job = result.getString(JOB_COLUMN);
		String country = result.getString(COUNTRY_COLUMN);
		String city = result.getString(CITY_COLUMN);
		String street = result.getString(STREET_COLUMN);
		int house = result.getInt(HOUSE_COLUMN);
		int apartment = result.getInt(APARTMENT_COLUMN);
		int postcode = result.getInt(POSTCODE_COLUMN);
		String photoPath = result.getString(PHOTO_COLUMN);
		
		contact.setId(id);
		contact.setApartment(apartment);
		if (birthday != null) {
			contact.setBirthday(birthday);
		}
		contact.setCitizenship(citizenship);
		contact.setCity(city);
		contact.setCountry(country);
		contact.setEmail(email);
		contact.setFirstname(firstname);
		contact.setHouse(house);
		contact.setJob(job);
		contact.setLastname(lastname);
		contact.setMartialStatus(MartialStatus.valueOf(marital_status.toUpperCase()));
		contact.setMidname(midname);
		contact.setPostcode(postcode);
		contact.setSex(Sex.valueOf(sex.toUpperCase()));
		contact.setStreet(street);
		contact.setUrl(url);
		if (photoPath != null) {
			contact.setPhotoPath(photoPath);
		}
		
		return contact;
	}
	
	/**
	 * @see IContactsDAO#findContactsWithBirthday(int, int)
	 */
	public List<Contact> findContactsWithBirthday(int month, int day) throws DAOException {

		String monthString = String.valueOf(month);
		if (monthString.length() == 1) {
			monthString = "0".concat(monthString);
		}
		String dayString = String.valueOf(day);
		if (dayString.length() == 1) {
			dayString = "0".concat(dayString);
		}
		String likeString = String.format("%c-%s-%s", '%', monthString, dayString);
		
		List<Contact> contactsList = new ArrayList<>();
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = connection.prepareStatement(BIRTHDAY_CONTACT_QUERY);			
			preparedStatement.setString(1, likeString);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				contactsList.add(map(resultSet));
            }
		} catch (SQLException e) {
			throw new DAOException(e);
        } finally {
        	DAOUtil.close(preparedStatement);
        	DAOUtil.close(resultSet);
        }
		return contactsList;
	}
}

