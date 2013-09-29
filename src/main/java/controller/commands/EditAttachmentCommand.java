package controller.commands;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.ContactControllerServlet;
import controller.helpers.RequestHelper;
import dao.IContactsDAO;
import model.Attachment;
import model.Contact;

/**
 * Gets new attachment from request and updates it in session contact.
 * Do not update attachment in DB.
 * Redirects on loading page
 * @author Yalchyk Ilya
 */
public class EditAttachmentCommand extends AbstractCommand {

	/**
	 * @see ICommand#execute(HttpServletRequest, HttpServletResponse, IContactsDAO)
	 */
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response, IContactsDAO dao) {
		
		Attachment attachment = RequestHelper.mapAttachment(request);
		Contact contact = (Contact) request.getSession().getAttribute(Contact.ATTRIBUTE_NAME);
		
		if (contact != null) {
			
			List<Attachment> attachments = contact.getAttachments();
			int id = attachment.getId();
			attachment.setId(attachments.get(id).getId());
			attachment.setAttachmentStatus(attachments.get(id).getAttachmentStatus());			
			attachments.set(id, attachment);
			
			try {
				response.sendRedirect(ContactControllerServlet.LOADING_PAGE);
			} catch (IOException e) {
				logger.error("Redirect failed.", e);
			}
		}
		return null;
	}

}
