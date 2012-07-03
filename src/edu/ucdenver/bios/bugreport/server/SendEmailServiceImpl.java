package edu.ucdenver.bios.bugreport.server;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import edu.ucdenver.bios.bugreport.client.SendEmailService;
import edu.ucdenver.bios.bugreport.client.EmailContent;


public class SendEmailServiceImpl extends RemoteServiceServlet implements
        SendEmailService {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Override
    public void sendEmail(EmailContent emailContent) throws MessagingException
    {

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
 
        Session session = Session.getDefaultInstance(props,
            new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("vcakula@gmail.com","tanu@1229");
                }
            });
 
        try {
 
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("vcakula@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("bugs@mixpow01.repositoryhosting.com"));
            message.setSubject(emailContent.getSubject());
            message.setText(emailContent.getBugName()+"\n\n"+emailContent.getPriorrity()+"\n\n"+emailContent.getDescription());
 
            Transport.send(message);
 
            System.out.println("Done");
 
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
}
}
