package by.epam.periodicials_site.service.util;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

public class MailSender {
	
	public static void main(String[] args) {
		String to = "vladsvby@yandex.by";
		String from = "website@mail.com";
		
		Properties props = System.getProperties();
		props.setProperty("mail.smtp.host", "smtp.gmail.com");		
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");
		Authenticator authenticator = new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("vladossv@gmail.com", "23431993v");
			}
		};
		
		Session session = Session.getDefaultInstance(props, authenticator);
		MimeMessage message = new MimeMessage(session);
		try {
			message.setFrom(from);
			message.setRecipient(RecipientType.TO, new InternetAddress(to));
			message.setSubject("Java sender");
			message.setText("Hello from your programm");
			Transport.send(message);
			System.out.println("sended");
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
