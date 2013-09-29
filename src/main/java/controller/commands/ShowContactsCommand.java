package controller.commands;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Contact;
import controller.ContactControllerServlet;
import controller.helpers.RequestHelper;
import dao.DAOException;
import dao.IContactsDAO;

/**
 * Gets start and count parameters from request.
 * Gets contacts from DB.
 * Dispatch index page.
 * @author Yalchyk Ilya
 */
public class ShowContactsCommand extends AbstractCommand {

	/**
	 * @see ICommand#execute(HttpServletRequest, HttpServletResponse, IContactsDAO)
	 */
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response, IContactsDAO dao) throws DAOException {	

		String countAttribute = request.getParameter("count");
		String startAttribute = request.getParameter("start");
		request.getSession().removeAttribute("contact");
		if (!countAttribute.equals(RequestHelper.REDIRECT_FLAG) && !startAttribute.equals(RequestHelper.REDIRECT_FLAG)) {
			int count = Integer.parseInt(countAttribute);
			int start = Integer.parseInt(startAttribute);
			List<Contact> list = dao.getContacts(start, count);
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
