package model;

import java.io.Serializable;

/**
 * Phone bean
 * @author Yalchyk Ilya
 */
public class Phone implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * When we set or get attachment as the attribute of request, or session, or smth else
	 * its name must be ATTRIBUTE_NAME
	 */
	public static final String ATTRIBUTE_NAME = "phone";
	
	public enum PhoneType {
		MOBILE,
		HOME
	}
	
	/**
	 * Necessary to determine operation for DAO (create, update or delete phone)
	 * because when client works with session, phones can be added, updated or removed
	 * and DAO wouldn't learn, what to do with every phone, if there was no status.
	 * NORMAL: there is such phone in DB, and need to update it
	 * NEW: there is not such phone in DB, and need to insert it
	 * REMOVED: there is such phone in DB, and need to delete it
	 * @author Yalchyk Ilya
	 */
	public enum PhoneStatus {
		NORMAL,
		NEW,
		REMOVED
	}
	
	/**
	 * May be either as ID of phone in DB,
	 * or ID of phone in session contact's phones collection.
	 * That is depend on context of using.
	 * Possible values: [-1, inf]
	 * -1 means phone wasn't added neither in DB
	 * nor in session contact's phones collection.
	 */
	private int id;
	
	/**
	 * May be country code
	 */
	private String code1;
	
	/**
	 * May be operator code (for mobile phones)
	 * or city code (for home phones)
	 */
	private String code2;
	
	/**
	 * Must contains only numbers (without separators like '-')
	 */
	private String phoneNumber;
	
	/**
	 * mobile or home
	 * semantic of 'code2' field depends on this field
	 */
	private PhoneType phoneType;
	
	/**
	 * comment to attachment, which user types
	 */
	private String comment;
	
	/**
	 * id of contact, to which phone is related
	 * Possible values: [-1, inf]
	 * -1 means that phone hasn't been related to any contact yet
	 */
	private int contactID;
	
	/**
	 * @see PhoneStatus
	 */
	private PhoneStatus phoneStatus;
	
	public Phone() {
		this.setId(-1);
		this.setCode1("");
		this.setCode2("");
		this.setPhoneNumber("");
		this.setPhoneType(PhoneType.MOBILE);
		this.setComment("");
		this.setContactID(-1);
		this.setPhoneStatus(PhoneStatus.NORMAL);
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCode1() {
		return code1;
	}
	public void setCode1(String code1) {
		this.code1 = code1;
	}
	public String getCode2() {
		return code2;
	}
	public void setCode2(String code2) {
		this.code2 = code2;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public PhoneType getPhoneType() {
		return phoneType;
	}
	public void setPhoneType(PhoneType phoneType) {
		this.phoneType = phoneType;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public int getContactID() {
		return contactID;
	}
	public void setContactID(int contactID) {
		this.contactID = contactID;
	}

	public PhoneStatus getPhoneStatus() {
		return phoneStatus;
	}

	public void setPhoneStatus(PhoneStatus phoneStatus) {
		this.phoneStatus = phoneStatus;
	}
	
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("ID = " + getId() + "\n");
		buffer.append("Code 1 = " + getCode1() + "\n");
		buffer.append("Code 2 = " + getCode2() + "\n");
		buffer.append("Phone number = " + getPhoneNumber() + "\n");
		buffer.append("Phone type = " + getPhoneType() + "\n");
		buffer.append("Comment = " + getComment() + "\n");
		buffer.append("Contact ID = " + getContactID() + "\n");
		buffer.append("Phone status = " + getPhoneStatus() + "\n");
		return buffer.toString();
	}
}
