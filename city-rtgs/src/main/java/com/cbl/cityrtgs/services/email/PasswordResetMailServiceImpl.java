package com.cbl.cityrtgs.services.email;

import com.cbl.cityrtgs.models.dto.response.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Date;
import java.util.Properties;

@Service("passwordResetMailService")
@RequiredArgsConstructor
public class PasswordResetMailServiceImpl implements EmailService {

    @Value("${app.forget.password.email.url}")
    private String url;

    @Value("${mail.smtp.starttls.enable}")
    private String starttls;
    @Value("${mail.smtp.host}")
    private String smtpHost;
    @Value("${mail.smtp.port}")
    private int smtpPort;
    @Value("${mail.smtp.ssl.trust}")
    private String sslTrust;
    @Value("${mail.from.address}")
    private String fromAddress;

    /* @Override
     public ResponseDTO sendMail(String to) {
         Properties props = new Properties();
         props.put("mail.smtp.starttls.enable", starttls);
         props.put("mail.smtp.host", smtpHost);
         props.put("mail.smtp.port", smtpPort);
         props.put("mail.smtp.ssl.trust", sslTrust);

         String body = "<a href='" + url + "'>Click here</a>";

         try {

             Session session = Session.getDefaultInstance(props);
             MimeMessage message = new MimeMessage(session);
             message.setFrom(new InternetAddress(fromAddress));
             message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
             message.setSubject("Reset Password!");
             message.setText(body);
             message.setSentDate(new Date());
             Transport.send(message);

             return ResponseDTO.builder().error(false).message("Password reset mail sent!").build();
         } catch (Exception e) {

             return ResponseDTO.builder().error(true).message(e.getMessage()).build();
         }
     }*/
    @Override
    public ResponseDTO sendMail(String to) throws MessagingException {
        Message msg = new MimeMessage(configureEmail());
        msg.setFrom(new InternetAddress(fromAddress, false));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        //msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(ccAddress));
        msg.setSubject("Reset Password!");
        msg.setSentDate(new Date());
        String bodyText = "<a href='" + url + "'>Click here</a>";
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(bodyText, "text/html");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        /*MimeBodyPart attachPart = new MimeBodyPart();
        attachPart.attachFile(new File(fileName));
        multipart.addBodyPart(attachPart);*/
        msg.setContent(multipart);

        Transport.send(msg);
        return ResponseDTO.builder().error(false).message("Password reset mail sent!").build();
    }

    private Session configureEmail() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.ssl.starttls.enable", starttls);
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", smtpPort);
        //props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        props.put("mail.smtp.ssl.trust", sslTrust);

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("username", "password");
            }
        });
        return session;
    }
}
