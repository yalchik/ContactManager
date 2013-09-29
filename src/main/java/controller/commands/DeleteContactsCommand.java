package controller.commands;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.ContactControllerServlet;
import controller.helpers.RequestHelper;
import dao.DAOException;
import dao.IContactsDAO;

/**
 * Gets indexes of contacts from request and deletes those contacts from DB.
 * Redirects on default page
 * @author Yalchyk Ilya
 */
public class DeleteContactsCommand extends AbstractCommand {

	/**
	 * @see ICommand#execute(HttpServletRequest, HttpServletResponse, IContactsDAO)
	 */
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response, IContactsDAO dao) throws DAOException {
		
		String[] deletingContactsIndexes = request.getParameterValues(RequestHelper.ListSpecificParameters.CHECK_CONTACT_PARAMETER);
		for (int i = 0; i < deletingContactsIndexes.length; i++) {
			int index = Integer.parseInt(deletingContactsIndexes[i]);
			boolean successfulDataAccess = dao.deleteContact(index);
			if (!successfulDataAccess) {
				throw new DAOException("Contact hasn't been deleted");
			}
		}
		try {
			response.sendRedirect(ContactControllerServlet.DEFAULT_PAGE);
		} catch (IOException e) {
			logger.error("Redirect failed", e);
		}
		return null;
	}

}
