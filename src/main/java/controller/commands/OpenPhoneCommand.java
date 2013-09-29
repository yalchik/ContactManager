package controller.commands;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.ContactControllerServlet;
import controller.helpers.RequestHelper;
import dao.IContactsDAO;
import model.Contact;
import model.Phone;

/**
 * Gets phone from session contact.
 * Do not use DB.
 * Dispatch phone page
 * @author Yalchyk Ilya
 */
public class OpenPhoneCommand extends AbstractCommand {

	/**
	 * @see ICommand#execute(HttpServletRequest, HttpServletResponse, IContactsDAO)
	 */
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response, IContactsDAO dao) {
		
		Phone phone = null;
		String action;
		
		// this ID is the index of the phone in the session contact's phones collection
		int id = Integer.parseInt(request.getParameter(RequestHelper.PhoneSpecificParameters.PHONE_ID_PARAMETER));
		
		// if requested phone is not new, then get it from session contact
		// else create a new one
		if (id != RequestHelper.NEW_ID) {
			Contact sessionContact = (Contact) request.getSession().getAttribute(Contact.ATTRIBUTE_NAME);
			List<Phone> phones = sessionContact.getPhones();
			phone = phones.get(id);
			if (phone.getPhoneStatus() == Phone.PhoneStatus.REMOVED) {
				phone = new Phone();
			}
			action = "edit_phone";
		}
		else {
			phone = new Phone();
			action = "create_phone";
		}
		request.setAttribute(Phone.ATTRIBUTE_NAME, phone);
		request.setAttribute("action", action);
		return ContactControllerServlet.PHONE_PAGE;
	}

}
