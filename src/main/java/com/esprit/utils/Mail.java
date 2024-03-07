package com.esprit.utils;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mail {

    private static Session createMailSession(String email, String password) {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        return Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, password);
            }
        });
    }

    public static void sendCommentaireMail(String recipient,String Pubname,String username) {
        try {
            String myAccountEmail = "davincisdata@gmail.com";
            String password = "gxke jnqk tzig jjoo";

            Session session = createMailSession(myAccountEmail, password);

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject("Davinci's Data");
            message.setContent(username+ "has commented on your Publication: ", "text/html");

            Transport.send(message);
            System.out.println("Activation email sent successfully");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }



}