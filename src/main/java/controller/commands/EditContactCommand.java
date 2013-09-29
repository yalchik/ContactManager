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
 * Gets contact with new data and updates it in DB.
 * Removes session contact.
 * Redirects to default page.
 * @author Yalchyk Ilya
 */
public class EditContactCommand extends AbstractCommand {
	
	/**
	 * @see ICommand#execute(HttpServletRequest, HttpServletResponse, IContactsDAO)
	 */
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response, IContactsDAO dao) throws DAOException {
		
		Contact contact = RequestHelper.getFullContact(request);
		
		boolean successfulDataAccess = dao.updateContact(contact.getId(), contact);
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
