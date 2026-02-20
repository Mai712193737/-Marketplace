package service;

import jakarta.mail.*;
import jakarta.mail.internet.*;

import java.util.Properties;

public class EmailService {

    private static final String FROM_EMAIL = "marketplace.teem@gmail.com";
    private static final String APP_PASSWORD = "ipqdpqjdqibedbio";

    public static void sendWelcomeEmail(String toEmail, String name) {

        String host = "smtp.gmail.com";

        Properties props = new Properties();

        // SMTP Configuration
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");


        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.smtp.connectiontimeout", "5000");
        props.put("mail.smtp.timeout", "5000");
        props.put("mail.smtp.writetimeout", "5000");

        Session session = Session.getInstance(props,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(FROM_EMAIL, APP_PASSWORD);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_EMAIL));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(toEmail)
            );

            message.setSubject("Welcome To Marketplace System 🎉");

            String htmlContent =
                    "<div style='font-family:Arial;padding:20px'>" +
                            "<h2 style='color:#2E86C1;'>Welcome " + name + " 👋</h2>" +
                            "<p>Your account has been created successfully.</p>" +
                            "<p>We are happy to have you with us ❤️</p>" +
                            "<hr>" +
                            "<small>Marketplace System Team</small>" +
                            "</div>";

            message.setContent(htmlContent, "text/html; charset=utf-8");

            Transport.send(message);

            System.out.println("✅ Email sent successfully to " + toEmail);

        } catch (AuthenticationFailedException e) {

            System.out.println("❌ Gmail Authentication Failed!");
          //  System.out.println();
            e.printStackTrace();

        } catch (MessagingException e) {

            System.out.println("❌ Failed to send email.");
            e.printStackTrace();
        }
    }
}
