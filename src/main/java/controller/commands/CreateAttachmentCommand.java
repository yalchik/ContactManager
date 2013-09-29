package controller.commands;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.ContactControllerServlet;
import controller.helpers.RequestHelper;
import dao.IContactsDAO;
import model.Attachment;
import model.Contact;
import model.Attachment.AttachmentStatus;

/**
 * Gets new attachment from request and adds it to session contact.
 * Do not save attachment in DB.
 * Redirects on loading page.
 * @author Yalchyk Ilya
 */
public class CreateAttachmentCommand extends AbstractCommand {

	/**
	 * @see ICommand#execute(HttpServletRequest, HttpServletResponse, IContactsDAO)
	 */
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response, IContactsDAO dao) {
		
		Attachment attachment = RequestHelper.mapAttachment(request);
		Contact contact = (Contact) request.getSession().getAttribute(Contact.ATTRIBUTE_NAME);
		if (contact != null) {
			attachment.setContactID(contact.getId());
			attachment.setAttachmentStatus(AttachmentStatus.NEW);
			attachment.setId(contact.getPhones().size());
			contact.getAttachments().add(attachment);
			
			try {
				response.sendRedirect(ContactControllerServlet.LOADING_PAGE);
			} catch (IOException e) {
				logger.error("Redirect failed.", e);
			}
		}
		return null;
	}

}