package controller.helpers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import model.Attachment;
import model.Contact;
import model.Phone;
import model.Contact.MartialStatus;
import model.Contact.Sex;

/**
 * This class contains:
 * all constants for parameters' names;
 * default parameters' values;
 * static methods mapping request to some bean
 * @author Yalchyk Ilya
 */
public class RequestHelper {
	
	private static final Map<String, String> defaultValues = new HashMap<>();

	public class CommonParameters {
		public static final String ID_PARAMETER = "id";
		public static final String ACTION_PARAMETER = "action";
		public static final String COMMENT_PARAMETER = "comment";
		public static final String COMPARISON_SIGN = "birthday_compare_sign";
	}
	
	public class ListSpecificParameters {
		public static final String COUNT_PARAMETER = "count";
		public static final String START_PARAMETER = "start";
		public static final String CHECK_CONTACT_PARAMETER = "check_contact";
		public static final String CHECK_PHONE_PARAMETER = "check_phone";
		public static final String CHECK_ATTACHMENT_PARAMETER = "check_attachment";
	}
	
	public class ContactSpecificParameters {
		public static final String FIRSTNAME_PARAMETER = "firstname";
		public static final String LASTNAME_PARAMETER = "lastname";
		public static final String MIDNAME_PARAMETER = "midname";
		public static final String BIRTHDAY_PARAMETER = "birthday";
		public static final String SEX_PARAMETER = "sex";
		public static final String CITIZENSHIP_PARAMETER = "citizenship";
		public static final String MARITAL_STATUS_PARAMETER = "marital status";
		public static final String WEB_SITE_PARAMETER = "web-site";
		public static final String EMAIL_PARAMETER = "email";
		public static final String JOB_PARAMETER = "job";
		public static final String COUNTRY_PARAMETER = "country";
		public static final String CITY_PARAMETER = "city";
		public static final String STREET_PARAMETER = "street";
		public static final String HOUSE_PARAMETER = "house";
		public static final String APARTMENT_PARAMETER = "apartment";
		public static final String POSTCODE_PARAMETER = "postcode";
		public static final String PHOTO_PARAMETER = "photo";
	}
	
	public class PhoneSpecificParameters {
		public static final String PHONE_ID_PARAMETER = "phone_id";
		public static final String CODE1_PARAMETER = "code1";
		public static final String CODE2_PARAMETER = "code2";
		public static final String PHONE_NUMBER_PARAMETER = "phone_number";
		public static final String PHONE_TYPE_PARAMETER = "phone_type_select";
	}
	
	public class AttachmentSpecificParameters {
		public static final String ATTACHMENT_ID_PARAMETER = "attachment_id";
		public static final String ATTACHMENT_NAME_PARAMETER = "attachment_name";
		public static final String ATTACHMENT_FILE_PARAMETER = "attachment";
	}
	
	public class MailSpecificParameters {
		public static final String RECIPIENTS_PARAMETER = "to";
		public static final String SUBJECT_PARAMETER = "subject";
		public static final String TEMPLATE_PARAMETER = "template";
		public static final String TEXT_PARAMETER = "text";
	}
	
	
	public static final String PHOTO_DEFAULT_VALUE = "images\\silhouette.png";	
	public static final String NEW_FLAG = "-1";
	public static final int NEW_ID = -1;
	public static final String REDIRECT_FLAG = "redirect";	
	
	/**
	 * Initialize default parameters' values
	 */
	static {
		
		final String EMPTY_STRING = "";
		//final String COUNT_DEFAULT_VALUE = "10";
		//final String START_DEFAULT_VALUE = "0";
		final String CHECK_CONTACT_DEFAULT_VALUE = "0";
		//final String FIRSTNAME_DEFAULT_VALUE = "unnamed contact";
		final String BIRTHDAY_DEFAULT_VALUE = "";// = new LocalDate().toString();
		final String SEX_DEFAULT_VALUE = "unknown";
		final String MARTIAL_STATUS_DEFAULT_VALUE = "unknown";
		final String PHONE_TYPE_DEFAULT_VALUE = "mobile";
		final String NUL = "0";
		
		defaultValues.put(CommonParameters.ACTION_PARAMETER, EMPTY_STRING);
		defaultValues.put(CommonParameters.ID_PARAMETER, NEW_FLAG);
		defaultValues.put(CommonParameters.COMMENT_PARAMETER, EMPTY_STRING);
		
		defaultValues.put(ListSpecificParameters.COUNT_PARAMETER, REDIRECT_FLAG);
		defaultValues.put(ListSpecificParameters.START_PARAMETER, REDIRECT_FLAG);
		defaultValues.put(ListSpecificParameters.CHECK_CONTACT_PARAMETER, CHECK_CONTACT_DEFAULT_VALUE);
				
		defaultValues.put(ContactSpecificParameters.FIRSTNAME_PARAMETER, EMPTY_STRING);
		defaultValues.put(ContactSpecificParameters.LASTNAME_PARAMETER, EMPTY_STRING);
		defaultValues.put(ContactSpecificParameters.MIDNAME_PARAMETER, EMPTY_STRING);
		defaultValues.put(ContactSpecificParameters.BIRTHDAY_PARAMETER, BIRTHDAY_DEFAULT_VALUE);
		defaultValues.put(ContactSpecificParameters.SEX_PARAMETER, SEX_DEFAULT_VALUE);
		defaultValues.put(ContactSpecificParameters.CITIZENSHIP_PARAMETER, EMPTY_STRING);
		defaultValues.put(ContactSpecificParameters.MARITAL_STATUS_PARAMETER, MARTIAL_STATUS_DEFAULT_VALUE);
		defaultValues.put(ContactSpecificParameters.WEB_SITE_PARAMETER, EMPTY_STRING);
		defaultValues.put(ContactSpecificParameters.EMAIL_PARAMETER, EMPTY_STRING);
		defaultValues.put(ContactSpecificParameters.JOB_PARAMETER, EMPTY_STRING);
		defaultValues.put(ContactSpecificParameters.COUNTRY_PARAMETER, EMPTY_STRING);
		defaultValues.put(ContactSpecificParameters.CITY_PARAMETER, EMPTY_STRING);
		defaultValues.put(ContactSpecificParameters.STREET_PARAMETER, EMPTY_STRING);
		defaultValues.put(ContactSpecificParameters.HOUSE_PARAMETER, NUL);
		defaultValues.put(ContactSpecificParameters.APARTMENT_PARAMETER, NUL);
		defaultValues.put(ContactSpecificParameters.POSTCODE_PARAMETER, NUL);
		defaultValues.put(ContactSpecificParameters.PHOTO_PARAMETER, PHOTO_DEFAULT_VALUE);
		
		defaultValues.put(PhoneSpecificParameters.PHONE_ID_PARAMETER, NEW_FLAG);		
		defaultValues.put(PhoneSpecificParameters.CODE1_PARAMETER, EMPTY_STRING);
		defaultValues.put(PhoneSpecificParameters.CODE2_PARAMETER, EMPTY_STRING);
		defaultValues.put(PhoneSpecificParameters.PHONE_NUMBER_PARAMETER, EMPTY_STRING);
		defaultValues.put(PhoneSpecificParameters.PHONE_TYPE_PARAMETER, PHONE_TYPE_DEFAULT_VALUE);		
		
		defaultValues.put(AttachmentSpecificParameters.ATTACHMENT_ID_PARAMETER, NEW_FLAG);
		defaultValues.put(AttachmentSpecificParameters.ATTACHMENT_NAME_PARAMETER, EMPTY_STRING);
		defaultValues.put(AttachmentSpecificParameters.ATTACHMENT_FILE_PARAMETER, EMPTY_STRING);
		
		defaultValues.put(MailSpecificParameters.RECIPIENTS_PARAMETER, EMPTY_STRING);
		defaultValues.put(MailSpecificParameters.SUBJECT_PARAMETER, EMPTY_STRING);
		defaultValues.put(MailSpecificParameters.TEXT_PARAMETER, EMPTY_STRING);
	}
	
	/**
	 * Gets default value of the parameter
	 * @param parameter name of the parameter, which default value we want receive
	 * @return default value of the parameter
	 */
	public static String getDefaultValue(String parameter) {
		return defaultValues.get(parameter);
	}
	
	/**
	 * Gets contact from request, and gets phones, attachments and photo from session
	 * @param request http request with contact data
	 * @return contact with phones, attachments and photo
	 */
	public static Contact getFullContact(HttpServletRequest request) {
		Contact contact = mapContact(request);
		Contact contactFromSession = (Contact) request.getSession().getAttribute(Contact.ATTRIBUTE_NAME);
		if (contactFromSession != null) {
			contact.setPhotoPath(contactFromSession.getPhotoPath());
			contact.setPhones(contactFromSession.getPhones());
			contact.setAttachments(contactFromSession.getAttachments());
		}
		else {
			System.out.println("warning. empty session");
		}
		return contact;
	}
	
	/**
	 * Gets contact from request
	 * @param request http request with contact data
	 * @return contact without phones, attachments and photo
	 */
	public static Contact mapContact(HttpServletRequest request) {
		Contact contact = new Contact();
		
		String id = request.getParameter(CommonParameters.ID_PARAMETER);
		String firstname = request.getParameter(ContactSpecificParameters.FIRSTNAME_PARAMETER);
		String lastname = request.getParameter(ContactSpecificParameters.LASTNAME_PARAMETER);
		String midname = request.getParameter(ContactSpecificParameters.MIDNAME_PARAMETER);
		String birthday = request.getParameter(ContactSpecificParameters.BIRTHDAY_PARAMETER);
		String sex = request.getParameter(ContactSpecificParameters.SEX_PARAMETER);
		String citizenship = request.getParameter(ContactSpecificParameters.CITIZENSHIP_PARAMETER);
		String martial_status = request.getParameter(ContactSpecificParameters.MARITAL_STATUS_PARAMETER);
		String url = request.getParameter(ContactSpecificParameters.WEB_SITE_PARAMETER);
		String email = request.getParameter(ContactSpecificParameters.EMAIL_PARAMETER);
		String job = request.getParameter(ContactSpecificParameters.JOB_PARAMETER);
		String country = request.getParameter(ContactSpecificParameters.COUNTRY_PARAMETER);
		String city = request.getParameter(ContactSpecificParameters.CITY_PARAMETER);
		String street = request.getParameter(ContactSpecificParameters.STREET_PARAMETER);
		String house = request.getParameter(ContactSpecificParameters.HOUSE_PARAMETER);
		String apartment = request.getParameter(ContactSpecificParameters.APARTMENT_PARAMETER);
		String postcode = request.getParameter(ContactSpecificParameters.POSTCODE_PARAMETER);		
		
		contact.setId(Integer.parseInt(id));
		contact.setApartment(Integer.parseInt(apartment));
		//contact.setBirthday(birthday.equals("") ? new LocalDate() : LocalDate.parse(birthday));
		//contact.setBirthday(LocalDate.parse(birthday));
		contact.setBirthday(birthday);
		contact.setCitizenship(citizenship);
		contact.setCity(city);
		contact.setCountry(country);
		contact.setEmail(email);
		contact.setFirstname(firstname);
		contact.setHouse(Integer.parseInt(house));
		contact.setJob(job);
		contact.setLastname(lastname);
		contact.setMartialStatus(MartialStatus.valueOf(martial_status.toUpperCase()));
		contact.setMidname(midname);
		contact.setPostcode(Integer.parseInt(postcode));
		contact.setSex(Sex.valueOf(sex.toUpperCase()));
		contact.setStreet(street);
		contact.setUrl(url);
		
		return contact;
	}
	
	/**
	 * Gets phone from request
	 * @param request http request with phone data
	 * @return phone
	 */
	public static Phone mapPhone(HttpServletRequest request) {
		Phone phone = new Phone();	
		
		String phone_id = request.getParameter(PhoneSpecificParameters.PHONE_ID_PARAMETER);
		String code1 = request.getParameter(PhoneSpecificParameters.CODE1_PARAMETER);
		String code2 = request.getParameter(PhoneSpecificParameters.CODE2_PARAMETER);
		String phoneNumber = request.getParameter(PhoneSpecificParameters.PHONE_NUMBER_PARAMETER);
		String phoneType = request.getParameter(PhoneSpecificParameters.PHONE_TYPE_PARAMETER);
		String comment = request.getParameter(CommonParameters.COMMENT_PARAMETER);
		String contact_id = request.getParameter(CommonParameters.ID_PARAMETER);
		
		phone.setId(Integer.parseInt(phone_id));
		phone.setCode1(code1);
		phone.setCode2(code2);
		phone.setPhoneNumber(phoneNumber);
		phone.setPhoneType(Phone.PhoneType.valueOf(phoneType.toUpperCase()));
		phone.setComment(comment);
		phone.setContactID(Integer.parseInt(contact_id));
		
		return phone;
	}
	
	/**
	 * Gets attachment from request
	 * @param request http request with attachment data
	 * @return attachment
	 */
	public static Attachment mapAttachment(HttpServletRequest request) {
		// mapping is done by SubmitAttachmentCommand
		Attachment attachment = (Attachment) request.getAttribute(Attachment.ATTRIBUTE_NAME);
		return attachment;
	}

}
