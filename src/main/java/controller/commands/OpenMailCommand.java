package controller.commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import controller.ContactControllerServlet;
import controller.helpers.RequestHelper;
import dao.DAOException;
import dao.IContactsDAO;

/**
 * Gets indexes of contacts from request, take their emails,
 * concatenates them with ';' separator.
 * Dispatch mail page
 * @author Yalchyk Ilya
 */
public class OpenMailCommand extends AbstractCommand {

	/**
	 * @see ICommand#execute(HttpServletRequest, HttpServletResponse, IContactsDAO)
	 */
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response, IContactsDAO dao) throws DAOException {
		
		String[] checkedContactsID = request.getParameterValues(RequestHelper.ListSpecificParameters.CHECK_CONTACT_PARAMETER);
		StringBuffer recipientsBuffer = new StringBuffer();
		if (checkedContactsID != null) {
			for (int i = 0; i < checkedContactsID.length; i++) {
				int index = Integer.parseInt(checkedContactsID[i]);
				String email = dao.getContact(index).getEmail();
				if (StringUtils.isNotBlank(email)) {
					recipientsBuffer.append(email);
					recipientsBuffer.append(';');
				}
			}			
			recipientsBuffer.deleteCharAt(recipientsBuffer.length() - 1);	// delete last ';'
		}
		request.setAttribute("recipients", recipientsBuffer.toString());
		return ContactControllerServlet.MAIL_PAGE;
	}

}
