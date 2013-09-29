package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Contact bean
 * @author Yalchyk Ilya
 */
public class Contact implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * When we set or get contact as the attribute of request, or session, or smth else
	 * its name must be ATTRIBUTE_NAME
	 */
	public static final String ATTRIBUTE_NAME = "contact";
	
	public enum Sex {
		UNKNOWN,		// user wasn't defined sex for contact
		MALE,
		FEMALE
	}
	
	public enum MartialStatus {
		UNKNOWN,		// user wasn't defined martial status for contact
		SINGLE,
		MARRIED
	}
	
	/**
	 * id of current contact in DB
	 * possible values: [-1; inf]
	 * -1 means that contact hasn't been inserted in DB yet
	 */
	private int id;
	
	private String firstname;
	private String lastname;
	private String midname;
	//private LocalDate birthday;
	private String birthday;
	private Sex sex;
	private String citizenship;
	private MartialStatus martialStatus = MartialStatus.SINGLE;
	private String url;
	private String email;
	private String job;
	private String country;
	private String city;
	private String street;
	private int house;
	private int apartment;
	private int postcode;
	private String photoPath;
	
	private List<Phone> phones;
	private List<Attachment> attachments;
	
	public Contact() {
		setId(-1);
		setFirstname("");
		setLastname("");
		setMidname("");
		//setBirthday(new LocalDate());
		setBirthday("");
		setSex(Sex.UNKNOWN);
		setCitizenship("");
		setMartialStatus(MartialStatus.UNKNOWN);
		setUrl("");
		setEmail("");
		setJob("");
		setCountry("");
		setCity("");
		setStreet("");
		setHouse(0);
		setApartment(0);
		setPostcode(0);
		setPhotoPath("images\\silhouette.png");
		
		setPhones(new ArrayList<Phone>());
		setAttachments(new ArrayList<Attachment>());
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getMidname() {
		return midname;
	}
	public void setMidname(String midname) {
		this.midname = midname;
	}
	/*
	public LocalDate getBirthday() {
		return birthday;
	}
	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}
	*/
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public Sex getSex() {
		return sex;
	}
	public void setSex(Sex sex) {
		this.sex = sex;
	}
	public String getCitizenship() {
		return citizenship;
	}
	public void setCitizenship(String citizenship) {
		this.citizenship = citizenship;
	}
	public MartialStatus getMartialStatus() {
		return martialStatus;
	}
	public void setMartialStatus(MartialStatus martialStatus) {
		this.martialStatus = martialStatus;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public int getHouse() {
		return house;
	}
	public void setHouse(int house) {
		this.house = house;
	}
	public int getApartment() {
		return apartment;
	}
	public void setApartment(int apartment) {
		this.apartment = apartment;
	}
	public int getPostcode() {
		return postcode;
	}
	public void setPostcode(int postcode) {
		this.postcode = postcode;
	}
	
	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	public List<Phone> getPhones() {
		return phones;
	}

	public void setPhones(List<Phone> phones) {
		this.phones = phones;
	}

	public List<Attachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}
/*
	public boolean equals(Contact contact) {
		if (StringUtils.isNotBlank(this.getFirstname())
				&& StringUtils.isNotBlank(contact.getFirstname())
				&& !this.getFirstname().equalsIgnoreCase(contact.getFirstname())) {
			return false;
		}
		else if (StringUtils.isNotBlank(this.getLastname())
				&& StringUtils.isNotBlank(contact.getLastname())
				&& !this.getLastname().equalsIgnoreCase(contact.getLastname())) {
			return false;
		}
		else if (StringUtils.isNotBlank(this.getMidname())
				&& StringUtils.isNotBlank(contact.getMidname())
				&& !this.getMidname().equalsIgnoreCase(contact.getMidname())) {
			return false;
		}
		else if (StringUtils.isNotBlank(this.getMidname())
				&& StringUtils.isNotBlank(contact.getMidname())
				&& !this.getMidname().equalsIgnoreCase(contact.getMidname())) {
			return false;
		}
		else if (this.getSex() != null && contact.getSex() != null && this.getSex() != contact.getSex()) {
			return false;
		}
		else if (this.getCitizenship() != null && contact.getCitizenship() != null && this.getCitizenship() != contact.getCitizenship()) {
			return false;
		}
		else if (this.getMartialStatus() != null && contact.getMartialStatus() != null && this.getMartialStatus() != contact.getMartialStatus()) {
			return false;
		}
		else if (this.getUrl() != null && contact.getUrl() != null && this.getUrl() != contact.getUrl()) {
			return false;
		}
		else if (this.getEmail() != null && contact.getEmail() != null && this.getEmail() != contact.getEmail()) {
			return false;
		}
		else if (this.getJob() != null && contact.getJob() != null && this.getJob() != contact.getJob()) {
			return false;
		}
		else if (this.getCountry() != null && contact.getCountry() != null && this.getCountry() != contact.getCountry()) {
			return false;
		}
		else if (this.getCity() != null && contact.getCity() != null && this.getCity() != contact.getCity()) {
			return false;
		}
		else if (this.getStreet() != null && contact.getStreet() != null && this.getStreet() != contact.getStreet()) {
			return false;
		}
		else if (this.getHouse() != contact.getHouse()) {
			return false;
		}
		else if (this.getApartment() != contact.getApartment()) {
			return false;
		}
		else if (this.getPostcode() != contact.getPostcode()) {
			return false;
		}
		else {
			return true;
		}
	}
*/
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("ID = " + getId() + "\n");
		buffer.append("Firstname = " + getFirstname() + "\n");
		buffer.append("Lastname = " + getLastname() + "\n");
		buffer.append("Midname = " + getMidname() + "\n");
		buffer.append("Birthday = " + getBirthday() + "\n");
		buffer.append("Sex = " + getSex() + "\n");
		buffer.append("Citizenship = " + getCitizenship() + "\n");
		buffer.append("MartialStatus = " + getMartialStatus() + "\n");
		buffer.append("Web-site = " + getUrl() + "\n");
		buffer.append("Email = " + getEmail() + "\n");
		buffer.append("Job = " + getJob() + "\n");
		buffer.append("Country = " + getCountry() + "\n");
		buffer.append("City = " + getCity() + "\n");
		buffer.append("Street = " + getStreet() + "\n");
		buffer.append("House = " + getHouse() + "\n");
		buffer.append("Apartment = " + getApartment() + "\n");
		buffer.append("Postcode = " + getPostcode() + "\n");
		buffer.append("Phones: \n");
		for (Phone p : phones) {
			buffer.append(p.toString());
		}
		for (Attachment a : attachments) {
			buffer.append(a.toString());
		}
		return buffer.toString();
	}
}
