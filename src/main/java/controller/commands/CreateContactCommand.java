package controller.commands;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.ContactControllerServlet;
import controller.helpers.RequestHelper;
import dao.DAOException;
import dao.IContactsDAO;
import model.Contact;

/**
 * Gets new contact and save it in DB with phones and attachments.
 * Removes session contact.
 * Redirects to default page.
 * @author Yalchyk Ilya
 */
public class CreateContactCommand extends AbstractCommand {

	/**
	 * @see ICommand#execute(HttpServletRequest, HttpServletResponse, IContactsDAO)
	 */
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response, IContactsDAO dao) throws DAOException {
		
		Contact contact = RequestHelper.getFullContact(request);
		
		if (contact.getId() != RequestHelper.NEW_ID) {
			logger.warn("Contact ID is not equal to NEW_ID = {}. "
					+ "Maybe user has changed it manually. "
					+ "Regardless, contact will be inserting into DB as new contact",
					contact.getId());
		}
		
		boolean successfulDataAccess = dao.createContact(contact);
		if (successfulDataAccess) {
			request.getSession().removeAttribute(Contact.ATTRIBUTE_NAME);
			try {
				response.sendRedirect(ContactControllerServlet.DEFAULT_PAGE);
			} catch (IOException e) {
				logger.error("Redirect failed.", e);
			}
		}
		else {
			throw new DAOException("Database hasn't been updated");
		}
		return null;
	}

}
