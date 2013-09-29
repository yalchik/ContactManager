package controller.commands;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.ContactControllerServlet;
import controller.helpers.RequestHelper;
import dao.IContactsDAO;
import model.Attachment;
import model.Attachment.AttachmentStatus;
import model.Contact;

/**
 * Gets attachment from session contact.
 * Do not use DB.
 * Dispatch attachment page
 * @author Yalchyk Ilya
 */
public class OpenAttachmentCommand extends AbstractCommand {

	/**
	 * @see ICommand#execute(HttpServletRequest, HttpServletResponse, IContactsDAO)
	 */
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response, IContactsDAO dao) {
		
		Attachment attachment = null;
		String action;
		
		// this ID is the index of the attachment in the session contact's attachments collection
		int id = Integer.parseInt(request.getParameter(RequestHelper.AttachmentSpecificParameters.ATTACHMENT_ID_PARAMETER));
		
		// if requested attachment is not new, then get it from session contact
		// else create a new one
		if (id != RequestHelper.NEW_ID) {
			Contact sessionContact = (Contact) request.getSession().getAttribute(Contact.ATTRIBUTE_NAME);
			List<Attachment> attachments = sessionContact.getAttachments();
			attachment = attachments.get(id);
			if (attachment.getAttachmentStatus() == AttachmentStatus.REMOVED) {
				attachment = new Attachment();
			}
			action = "edit_attachment";
		}
		else {
			attachment = new Attachment();
			action = "create_attachment";
		}
		request.setAttribute(Attachment.ATTRIBUTE_NAME, attachment);
		request.setAttribute("action", action);
		return ContactControllerServlet.ATTACHMENT_PAGE;
	}

}
