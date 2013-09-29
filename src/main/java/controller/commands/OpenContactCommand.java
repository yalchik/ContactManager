package controller.commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.ContactControllerServlet;
import controller.helpers.RequestHelper;
import dao.DAOException;
import dao.IContactsDAO;
import model.Contact;

/**
 * Gets session contact. Do not use DB.
 * Dispatch contact page
 * @author Yalchyk Ilya
 */
public class OpenContactCommand extends AbstractCommand {

	/**
	 * @see ICommand#execute(HttpServletRequest, HttpServletResponse, IContactsDAO)
	 */
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response, IContactsDAO dao) throws DAOException {
		
		Contact sessionContact = (Contact) request.getSession().getAttribute(Contact.ATTRIBUTE_NAME);
		int id = Integer.parseInt(request.getParameter(RequestHelper.CommonParameters.ID_PARAMETER));
				
		// if requested contact don't match with session contact, then get it from DB.
		// if there is not such contact in DB, then create new one.
		// this contact becomes a new session contact
		if (sessionContact == null || sessionContact.getId() != id) {
			Contact contact = id == RequestHelper.NEW_ID
					? new Contact()
					: dao.getContact(id);
			request.getSession().setAttribute(Contact.ATTRIBUTE_NAME, contact);
		}
		return ContactControllerServlet.CONTACT_PAGE;
	}

}
