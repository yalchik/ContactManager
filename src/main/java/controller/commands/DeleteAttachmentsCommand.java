package controller.commands;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.helpers.RequestHelper;
import dao.IContactsDAO;
import model.Attachment;
import model.Contact;
import model.Attachment.AttachmentStatus;

/**
 * Gets indexes of attachments from request and
 * sets status 'removed' for those attachments in session contact
 * Do not delete attachments objects neither from DB nor from session contact
 * Redirects on contact's page
 * @author Yalchyk Ilya
 */
public class DeleteAttachmentsCommand extends AbstractCommand {

	/**
	 * @see ICommand#execute(HttpServletRequest, HttpServletResponse, IContactsDAO)
	 */
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response, IContactsDAO dao) {
		
		Contact contact = (Contact) request.getSession().getAttribute(Contact.ATTRIBUTE_NAME);
		
		String[] deletingAttachmentsIndexes = request.getParameterValues(RequestHelper.ListSpecificParameters.CHECK_ATTACHMENT_PARAMETER);		
		for (int i = 0; i < deletingAttachmentsIndexes.length; i++) {
			int index = Integer.parseInt(deletingAttachmentsIndexes[i]);
			Attachment attachment = contact.getAttachments().get(index);
			if (attachment.getAttachmentStatus() == AttachmentStatus.NEW) {
				contact.getAttachments().remove(attachment);
			}
			else {
				attachment.setAttachmentStatus(AttachmentStatus.REMOVED);
			}
		}
		try {
			response.sendRedirect("/Contact/contact.do?id=" + contact.getId());
		} catch (IOException e) {
			logger.error("Redirect failed", e);
		}
		return null;
	}

}
