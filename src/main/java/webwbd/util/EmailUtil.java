package webwbd.util;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import io.github.cdimascio.dotenv.Dotenv;

public class EmailUtil {
    private static final String EMAIL_HOST = Dotenv.load().get("EMAIL_HOST", "smtp.gmail.com");
    private static final String EMAIL_PORT = Dotenv.load().get("EMAIL_PORT", "465");
    private static final boolean EMAIL_USE_SSL = Boolean.parseBoolean(Dotenv.load().get("EMAIL_USE_SSL", "true"));
    private static final String EMAIL_SOURCE = Dotenv.load().get("EMAIL_SOURCE");
    private static final String EMAIL_PASSWORD = Dotenv.load().get("EMAIL_PASSWORD");
    public static void sendMail(String to, String subject, String body) throws MessagingException {

        Properties props = new Properties();
        props.put("mail.smtp.host", EMAIL_HOST);
        props.put("mail.smtp.port", EMAIL_PORT);
        props.put("mail.smtp.ssl.enable", EMAIL_USE_SSL);
        props.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(props, new javax.mail.Authenticator(){
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_SOURCE, EMAIL_PASSWORD);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL_SOURCE));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setText(body);
            Transport.send(message);
        } catch (MessagingException e) {
            throw new MessagingException(e.getMessage());
        }
    }
}
