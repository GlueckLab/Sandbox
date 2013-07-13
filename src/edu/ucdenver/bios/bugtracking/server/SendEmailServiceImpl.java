package edu.ucdenver.bios.bugtracking.server;

import java.io.Serializable;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.SimpleEmail;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import edu.ucdenver.bios.bugtracking.client.SendEmailService;
import edu.ucdenver.bios.bugtracking.shared.EmailContent;

public class SendEmailServiceImpl extends RemoteServiceServlet implements SendEmailService, Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Override
    public void sendEmail(EmailContent emailContent) throws MessagingException
    {
        /*SimpleEmail email = new SimpleEmail();
        System.out.println("Entered Server");
        
        email.setHostName("smtp.gmail.com");
        email.setSslSmtpPort("465");
        email.setAuthenticator(new DefaultAuthenticator("vcakula@gmail.com", "tanu@1229"));
        email.setSSL(true);
        System.out.println("After SSL True");
        try 
        {
            System.out.println("Entered TRY");
            System.out.println("  "+emailContent.getSubject());
            email.setFrom("vcakula@gmail.com");
            email.setSubject(emailContent.getSubject());
            
            email.setMsg("\n"+emailContent.getBugName()+"\n\n"+
            emailContent.getPriorrity()+"\n\n"+emailContent.getDescription());
            
            email.addTo("bugs@mixpow01.repositoryhosting.com");
            System.out.println("Added To Reciepent");
            email.send();
            System.out.println("Done");
            
        }
        catch (Exception e) 
        {
            System.out.println("Entered Catch Block");
        }*/
  
        
        
        System.out.println("Entered Serverside Implementation");
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.ssl", "true");
        props.put("mail.smtp.socketFactory.fallback", "false");  
        
        System.out.println("After Properties");
        Session session = Session.getDefaultInstance(props,
            new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("vcakula@gmail.com","tanu@1229");
                }
            });
 
        try {
            System.out.println("Entered Try");
            
            MimeMessage message = new MimeMessage(session);
            
            message.setFrom(new InternetAddress("vcakula@gmail.com"));
            System.out.println("After senders email");
            message.setRecipients(Message.RecipientType.TO, "bugs@mixpow01.repositoryhosting.com");
            System.out.println("After Setting to and from");
            
            System.out.println("After the Reciepints are set");
            message.setSubject(emailContent.getSubject());
            System.out.println(""+emailContent.getSubject());
            message.setText(emailContent.getBugName()+"\n\n"+emailContent.getPriorrity()+"\n\n"+emailContent.getDescription());
            Transport.send(message);
            
            System.out.println("Done");
 
        } catch (MessagingException e) {
            System.out.println("Entered Exception");
            throw new RuntimeException(e);
        }
        }

}
