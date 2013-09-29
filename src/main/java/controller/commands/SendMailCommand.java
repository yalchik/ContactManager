package controller.commands;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import controller.ContactControllerServlet;
import controller.helpers.MailHelper;
import controller.helpers.RequestHelper;
import dao.IContactsDAO;

/**
 * Gets a letter from request and send it to every recipient.
 * Redirect to default page.
 * @author Yalchyk Ilya
 */
public class SendMailCommand extends AbstractCommand {

	/**
	 * @see ICommand#execute(HttpServletRequest, HttpServletResponse, IContactsDAO)
	 */
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response, IContactsDAO dao) {		

		String host = request.getServletContext().getInitParameter("mail.smtp.host");
		MailHelper mailHelper = new MailHelper(host);

		String adminEmail = request.getServletContext().getInitParameter("admin_email");
		String subject = request.getParameter(RequestHelper.MailSpecificParameters.SUBJECT_PARAMETER);
		String text = request.getParameter(RequestHelper.MailSpecificParameters.TEXT_PARAMETER);
		String to = request.getParameter(RequestHelper.MailSpecificParameters.RECIPIENTS_PARAMETER);
		
		if (StringUtils.isNotBlank(to)) {
			String[] recipients = to.split(";");
			for (int i = 0; i < recipients.length; i++) {
				if (StringUtils.isNotBlank(recipients[i])) {
					mailHelper.send(adminEmail, recipients[i], subject, text);
				}
			}
				
			try {
				response.sendRedirect(ContactControllerServlet.DEFAULT_PAGE);
			} catch (IOException e) {
				logger.error("Redirect failed", e);
			}
		}
		else {
			request.setAttribute("message", "Wrong address");
			return ContactControllerServlet.ERROR_PAGE;
		}
		return null;
	}

}
