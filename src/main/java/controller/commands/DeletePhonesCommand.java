package controller.commands;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.helpers.RequestHelper;
import dao.IContactsDAO;
import model.Contact;
import model.Phone;
import model.Phone.PhoneStatus;

/**
 * Gets indexes of phones from request and
 * sets status 'removed' for those phones in session contact
 * Do not delete phones objects neither from DB nor from session contact
 * Redirects on contact's page
 * @author Yalchyk Ilya
 */
public class DeletePhonesCommand extends AbstractCommand {

	/**
	 * @see ICommand#execute(HttpServletRequest, HttpServletResponse, IContactsDAO)
	 */
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response, IContactsDAO dao) {
		
		Contact contact = (Contact) request.getSession().getAttribute(Contact.ATTRIBUTE_NAME);
		
		String[] deletingPhonesIndexes = request.getParameterValues(RequestHelper.ListSpecificParameters.CHECK_PHONE_PARAMETER);		
		for (int i = 0; i < deletingPhonesIndexes.length; i++) {
			//int index = Integer.parseInt(deletingPhonesIndexes[i]) - 1;
			int index = Integer.parseInt(deletingPhonesIndexes[i]);
			Phone phone = contact.getPhones().get(index);
			if (phone.getPhoneStatus() == PhoneStatus.NEW) {
				contact.getPhones().remove(phone);
			}
			else {
				phone.setPhoneStatus(PhoneStatus.REMOVED);
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
