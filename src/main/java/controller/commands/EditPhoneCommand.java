package controller.commands;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.ContactControllerServlet;
import controller.helpers.RequestHelper;
import dao.IContactsDAO;
import model.Contact;
import model.Phone;

/**
 * Gets new phone from request and updates it in session contact.
 * Do not update phone in DB.
 * Redirects on loading page
 * @author Yalchyk Ilya
 */
public class EditPhoneCommand extends AbstractCommand {

	/**
	 * @see ICommand#execute(HttpServletRequest, HttpServletResponse, IContactsDAO)
	 */
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response, IContactsDAO dao) {
		
		Phone phone = RequestHelper.mapPhone(request);
		Contact contact = (Contact) request.getSession().getAttribute(Contact.ATTRIBUTE_NAME);
		
		if (contact != null) {
			
			List<Phone> phones = contact.getPhones();
			int id = phone.getId();
			phone.setId(phones.get(id).getId());
			phone.setPhoneStatus(phones.get(id).getPhoneStatus());			
			phones.set(id, phone);

			try {
				response.sendRedirect(ContactControllerServlet.LOADING_PAGE);
			} catch (IOException e) {
				logger.error("Redirect failed.", e);
			}
		}
		return null;
	}

}
