package controller.commands;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.ContactControllerServlet;
import controller.helpers.RequestHelper;
import dao.IContactsDAO;
import model.Contact;
import model.Phone;
import model.Phone.PhoneStatus;

/**
 * Gets new phone from request and adds it to session contact.
 * Do not save phone in DB.
 * Redirects on loading page.
 * @author Yalchyk Ilya
 */
public class CreatePhoneCommand extends AbstractCommand {

	/**
	 * @see ICommand#execute(HttpServletRequest, HttpServletResponse, IContactsDAO)
	 */
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response, IContactsDAO dao) {
		
		Phone phone = RequestHelper.mapPhone(request);
		Contact contact = (Contact) request.getSession().getAttribute(Contact.ATTRIBUTE_NAME);
		if (contact != null) {
			phone.setContactID(contact.getId());
			phone.setPhoneStatus(PhoneStatus.NEW);
			phone.setId(contact.getPhones().size());
			contact.getPhones().add(phone);
			
			try {
				response.sendRedirect(ContactControllerServlet.LOADING_PAGE);
			} catch (IOException e) {
				logger.error("Redirect failed.", e);
			}
		}
		return null;
	}

}
