package controller.helpers;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class loads mail properties, can send mail and catches mail exceptions
 * @author Yalchyk Ilya
 */
public class MailHelper {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private Session session;
	private Properties properties;
	
	/**
	 * Sets mail properties and gets session
	 * @param host mail host (by default is localhost)
	 */
	public MailHelper(String host) {
		if (host == null) {
			host = "localhost";
		}
		properties = new Properties();
		properties.setProperty("mail.smtp.host", host);
		properties.setProperty("charset", "utf-8");
		session = Session.getDefaultInstance(properties);
	}
	
	/**
	 * Sends letter from one address to another
	 * @param from sender
	 * @param to recipient
	 * @param subject subject of the letter
	 * @param text body of the letter
	 * @return true, if letter has been sent
	 */
	public boolean send(String from, String to, String subject, String text) {
		boolean successfulMailing = false;
		MimeMessage message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress(from));
			message.setSubject(subject, "UTF-8");
			message.setText(text, "UTF-8");
			message.setHeader("Content-Type","text/plain; charset=\"utf-8\"");
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
			Transport.send(message);
			successfulMailing = true;
		} catch (AddressException e) {
			logger.error("Wrong address.", e);
		} catch (MessagingException e) {
			logger.error("Can't set message field", e);
		}
		return successfulMailing;		
	}
}
