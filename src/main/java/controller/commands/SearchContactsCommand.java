package controller.commands;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.ContactControllerServlet;
import controller.helpers.RequestHelper;
import dao.DAOException;
import dao.IContactsDAO;
import model.Contact;

/**
 * Gets template contact, birthday comparison sign, start and count parameters from request.
 * Search fit contacts in DB.
 * Dispatch index page.
 * @author Yalchyk Ilya
 */
public class SearchContactsCommand extends AbstractCommand {

	/**
	 * @see ICommand#execute(HttpServletRequest, HttpServletResponse, IContactsDAO)
	 */
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response, IContactsDAO dao) throws DAOException {
		
		Contact contact = RequestHelper.mapContact(request);
		String birthday_comparison_sign = request.getParameter("birthday_compare_sign");
		String countAttribute = request.getParameter("count");
		String startAttribute = request.getParameter("start");
		if (!countAttribute.equals(RequestHelper.REDIRECT_FLAG) && !startAttribute.equals(RequestHelper.REDIRECT_FLAG)) {
			int count = Integer.parseInt(countAttribute);
			int start = Integer.parseInt(startAttribute);
			List<Contact> list = dao.findContactsLike(contact, birthday_comparison_sign, start, count);
			request.setAttribute("contacts", list);
			return ContactControllerServlet.INDEX_PAGE;
		}
		else {
			try {
				response.sendRedirect(ContactControllerServlet.DEFAULT_PAGE);
			} catch (IOException e) {
				logger.error("Redirect failed.", e);
			}
		}
		return null;
		
		
	}

}
