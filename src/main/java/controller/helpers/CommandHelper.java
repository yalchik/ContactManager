package controller.helpers;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.commands.*;

/**
 * This class creates command objects
 * @author Yalchyk Ilya
 */
public class CommandHelper {
	
	private static final String INDEX_KEY = "index";
	private static final String LIST_KEY = "list";
	private static final String SEARCH_CONTACT_KEY = "search";
	
	private static final String OPEN_MAIL_KEY = "open_mail";
	private static final String SEND_MAIL_KEY = "send_mail";
	
	private static final String OPEN_CONTACT_KEY = "contact";
	private static final String CREATE_CONTACT_KEY = "create";
	private static final String EDIT_CONTACT_KEY = "edit";
	private static final String DELETE_CONTACT_KEY = "delete";
	
	private static final String OPEN_PHONE_KEY = "phone";
	private static final String CREATE_PHONE_KEY = "create_phone";
	private static final String EDIT_PHONE_KEY = "edit_phone";
	private static final String DELETE_PHONE_KEY = "delete_phones";	
	
	private static final String OPEN_ATTACHMENT_KEY = "attachment";
	private static final String CREATE_ATTACHMENT_KEY = "create_attachment";
	private static final String EDIT_ATTACHMENT_KEY = "edit_attachment";
	private static final String DELETE_ATTACHMENT_KEY = "delete_attachments";
	
	private static final String ATTACHMENT_SUBMIT_KEY = "submit_attachment";
		
	private static final String PHOTO_SUBMIT_KEY = "photo";
	
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * If there is action string in request parameter, then uses it.
	 * If there isn't, then parses request URI and take action string
	 * between '/' and '.do'
	 * @param request servlet request
	 * @return command object for this request
	 */
	public ICommand getCommand(HttpServletRequest request) {
		String action = request.getParameter(RequestHelper.CommonParameters.ACTION_PARAMETER);
		// if action isn't situated as parameter of request
		// then look for the action in the URI
		if (action == null || action.isEmpty()) {
			logger.info("there is no action parameter in the request. Taking action from URI");
			String uri = request.getRequestURI();
			int end = uri.indexOf(".do");
			int start = uri.lastIndexOf("/", end) + 1;
			action = uri.substring(start, end);
        }
		logger.debug("action: {}", action);
		return getCommand(action);
	}
	
	/**
	 * Maps action string to command and creates this command object
	 * @param action command key
	 * @return command object
	 */
	private ICommand getCommand(String action) {
		ICommand command = null;
		switch (action) {
		case LIST_KEY:
		case INDEX_KEY:
			command = new ShowContactsCommand();
			break;
		case OPEN_CONTACT_KEY:
			command = new OpenContactCommand();
			break;
		case CREATE_CONTACT_KEY:
			command = new CreateContactCommand();
			break;
		case EDIT_CONTACT_KEY:
			command = new EditContactCommand();
			break;
		case DELETE_CONTACT_KEY:
			command = new DeleteContactsCommand();
			break;			
		case OPEN_PHONE_KEY:
			command = new OpenPhoneCommand();
			break;
		case CREATE_PHONE_KEY:
			command = new CreatePhoneCommand();
			break;
		case EDIT_PHONE_KEY:
			command = new EditPhoneCommand();
			break;
		case DELETE_PHONE_KEY:
			command = new DeletePhonesCommand();
			break;
		case OPEN_ATTACHMENT_KEY:
			command = new OpenAttachmentCommand();
			break;
		case CREATE_ATTACHMENT_KEY:
			command = new CreateAttachmentCommand();
			break;
		case EDIT_ATTACHMENT_KEY:
			command = new EditAttachmentCommand();
			break;
		case DELETE_ATTACHMENT_KEY:
			command = new DeleteAttachmentsCommand();
			break;
		case PHOTO_SUBMIT_KEY:
			command = new SubmitPhotoCommand();
			break;
		case SEARCH_CONTACT_KEY:
			command = new SearchContactsCommand();
			break;
		case OPEN_MAIL_KEY:
			command = new OpenMailCommand();
			break;
		case SEND_MAIL_KEY:
			command = new SendMailCommand();
			break;
		case ATTACHMENT_SUBMIT_KEY:
			command = new SubmitAttachmentCommand();
			break;
		default:
			command = null;
			
		}
		return command;
	}
}
