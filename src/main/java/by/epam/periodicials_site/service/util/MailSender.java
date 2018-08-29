package by.epam.periodicials_site.service.util;

import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.periodicials_site.entity.Issue;
import by.epam.periodicials_site.entity.LocaleType;
import by.epam.periodicials_site.entity.User;
import by.epam.periodicials_site.entity.dto.LocalizedPublication;

public class MailSender {
	
	private static final Logger logger = LogManager.getLogger(MailSender.class);
	
	private static final String AUTH_EMAIL = "auth.email";
	private static final String AUTH_PASSWORD = "auth.password";
	private static final String AUTH_PROPERTIES = "mail_authentication";
	private static final String FROM_HEADER = "periodicials.site@mail.com";
	private static final String SUBJECT_HEADER = "New issue on the Periodicials site";
	
	private static final String MESSAGE_TEXT = " Hello, %s %s!\n\n"
			+ "We have a new issue of the %s - %s\n\n";
	
	private static final Properties props;
	
	static {
		props = System.getProperties();
		ResourceBundle bundle = ResourceBundle.getBundle("mail");
		for (String key : bundle.keySet()) {
			props.put(key, bundle.getString(key));
		}
	}
	
	public static void sendNotifications(List<User> users, Issue issue, LocalizedPublication localizedPublication) {
		ResourceBundle authData = ResourceBundle.getBundle(AUTH_PROPERTIES);
			
		Authenticator authenticator = new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(authData.getString(AUTH_EMAIL), authData.getString(AUTH_PASSWORD));
			}
		};
				
		try {
			for (User user : users) {
				MimeMessage message = formMessage(authenticator, user, issue, localizedPublication);
				Transport.send(message);
			}
		} catch (MessagingException e) {
			logger.warn("Exception sending email", e);
		}

	}
	
	private static MimeMessage formMessage(Authenticator authenticator , User user, Issue issue, LocalizedPublication localizedPublication) throws AddressException, MessagingException {
		Session session = Session.getDefaultInstance(props, authenticator);
		MimeMessage message = new MimeMessage(session);
		
		message.setFrom(FROM_HEADER);
		message.setRecipient(RecipientType.TO, new InternetAddress(user.getEmail()));
		message.setSubject(SUBJECT_HEADER);
		message.setText(String.format(MESSAGE_TEXT, user.getName(),
													user.getSurName(), 
													localizedPublication.getNames().get(LocaleType.EN_US),
													issue.getDescription()));
		
		return message;
	}

}
