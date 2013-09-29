package controller.jobs;

import java.util.List;

import model.Contact;

import org.joda.time.LocalDate;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.helpers.MailHelper;
import dao.DAOConfigurationException;
import dao.DAOException;
import dao.IContactsDAO;
import dao.MySqlContactsDAO;


/**
 * Checks if there are contacts with birthday today
 * If there are, then send to admin on email
 * the list of their names
 * @author Yalchyk Ilya
 */
public class MailingJob implements Job {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/** field 'from' of messages */
	private static final String serviceEmail = "mailing@job.com";
	
	/** field 'subject' of messages */
	private static final String serviceSubject = "Contacts' birthday";
	
	/**
	 * @see Job#execute(JobExecutionContext)
	 */
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {

		int day = LocalDate.now().dayOfMonth().get();
		int month = LocalDate.now().monthOfYear().get();
		
		IContactsDAO dao = new MySqlContactsDAO();
		try {			
			dao.openConnection();			
			//List<Contact> contacts = dao.findContactsLike(contactTemplateWithBirthday, "=", 0, -1);
			List<Contact> contacts = dao.findContactsWithBirthday(month, day);
			if (!contacts.isEmpty()) {
				String adminEmail = context.getMergedJobDataMap().getString("adminEmail");
				String host = context.getMergedJobDataMap().getString("host");
				StringBuffer textBuffer = new StringBuffer();				
				for (Contact c : contacts) {
					textBuffer.append(String.format("%s %s %s \n", c.getFirstname(), c.getLastname(), c.getMidname()));					
				}				
				MailHelper mailHelper = new MailHelper(host);
				boolean successfulMailing = mailHelper.send(serviceEmail, adminEmail, serviceSubject, textBuffer.toString());
				if (!successfulMailing) {
					logger.warn("mail hasn't been sent");
				}
			}
			else {
				logger.info("there are no contacts with birthday today");
			}
		} catch (DAOConfigurationException e) {
			logger.error("DB connection hasn't been opened", e);
		} catch (DAOException e) {
			logger.error("Database operation hasn't been executed", e);
		} catch (Exception e) {
			logger.error("Exception has been occured", e);
		} finally {
			try {
				dao.closeConnection();
			} catch (RuntimeException e) {
				logger.error("DB connection hasn't been closed", e);
			}
		}
	}
}
