package model;

import java.io.Serializable;

import org.joda.time.LocalDate;

/**
 * Attachment bean
 * @author Yalchyk Ilya
 */
public class Attachment implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * When we set or get attachment as the attribute of request, or session, or smth else
	 * its name must be ATTRIBUTE_NAME
	 */
	public static final String ATTRIBUTE_NAME = "attachment";
	
	/**
	 * Necessary to determine operation for DAO (create, update or delete attachment)
	 * because when client works with session, attachments can be added, updated or removed
	 * and DAO wouldn't learn, what to do with every attachment, if there was no status.
	 * NORMAL: there is such attachment in DB, and need to update it
	 * NEW: there is not such attachment in DB, and need to insert it
	 * REMOVED: there is such attachment in DB, and need to delete it
	 * @author Yalchyk Ilya
	 */
	public enum AttachmentStatus {
		NORMAL,
		NEW,
		REMOVED
	}
	
	/**
	 * May be either as ID of attachment in DB,
	 * or ID of attachment in session contact's phones collection.
	 * That is depend on context of using.
	 * Possible values: [-1, inf]
	 * -1 means attachment wasn't added neither in DB
	 * nor in session contact's attachments collection.
	 */
	private int id;
	
	/**
	 * logical filename, which user types
	 */
	private String name;
	
	/**
	 * physical filename (user don't type it)
	 */
	private String path;
	
	/**
	 * comment to attachment, which user types
	 */
	private String comment;
	
	/**
	 * id of contact, to which attachment is related
	 * Possible values: [-1, inf]
	 * -1 means that attachment hasn't been related to any contact yet
	 */
	private int contactID;
	
	/**
	 * @see AttachmentStatus
	 */
	private AttachmentStatus attachmentStatus;
	
	/**
	 * Date of uploading attachment.
	 */
	private String date;
	
	public Attachment() {
		this.setId(-1);
		this.setName("");
		this.setPath("");
		this.setComment("");
		this.setContactID(-1);
		this.setAttachmentStatus(AttachmentStatus.NORMAL);
		this.setDate(new LocalDate().toString());		// current date
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
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
	public AttachmentStatus getAttachmentStatus() {
		return attachmentStatus;
	}
	public void setAttachmentStatus(AttachmentStatus attachmentStatus) {
		this.attachmentStatus = attachmentStatus;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("ID = " + getId() + "\n");
		buffer.append("Name = " + getName() + "\n");
		buffer.append("Path = " + getPath() + "\n");
		buffer.append("Comment = " + getComment() + "\n");
		buffer.append("Contact ID = " + getContactID() + "\n");
		buffer.append("Date = " + getDate() + "\n");
		return buffer.toString();
	}
	
}
