/* Bug Report Service is for GLIMMPSE Software System.
 * Processes the incomming HTTP requests to report the bug.
 * 
 * Copyright (C) 2010 Regents of the University of Colorado.
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301, USA.
 */
package edu.ucdenver.bios.bugreportsvc.resource;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import edu.ucdenver.bios.bugreportsvc.application.BugReportSvcConstants;
import edu.ucdenver.bios.webservice.common.domain.BugReport;
/**
 * 
 * @author VIJAY AKULA
 *
 */

public class ReportBugServerResource implements ReportBugResource
{
    /**
     * Implementation of the {@link ReportBugResource} interface.
     * 
     * It will retrun a success message if the bug reporting is successful
     * else returns a exception message.
     */
    @Override
    public String sendEmail(BugReport bugReport) {
        
        
        Properties props = new Properties();
        props.put(BugReportSvcConstants.SMTP_HOST, 
                        BugReportSvcConstants.SMTP_HOST_NAME);
        props.put(BugReportSvcConstants.SMTP_PORT, 
                        BugReportSvcConstants.SMTP_PORT_NUMBER);
        props.put(BugReportSvcConstants.SMTP_CLASS, 
                        BugReportSvcConstants.SMTP_CLASS_NAME);
        props.put(BugReportSvcConstants.SMTP_AUTHENTICATION, "true");
        props.put(BugReportSvcConstants.SMTP_PORT_MAIL,
                        BugReportSvcConstants.SMTP_PORT_NUMBER);
        
        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication 
                        getPasswordAuthentication() {
                            return new PasswordAuthentication(BugReportSvcConstants.USER_NAME, 
                                    BugReportSvcConstants.PASSWORD);
                    }
                });
        
        try {
            
            MimeMessage message = new MimeMessage(session);
            
            message.setFrom(new InternetAddress(BugReportSvcConstants.USER_NAME));
            
            message.setRecipients(Message.RecipientType.TO, BugReportSvcConstants.TO);
            
            message.setSubject(bugReport.getSubject());
            
            message.setText(bugReport.getBugName()+
                    "\n\n"+bugReport.getPriorrity()+
                    "\n\n"+bugReport.getDescription());
            
            Transport.send(message);
            
            return (BugReportSvcConstants.SUCCESSFULL_MSG);
 
        } 
        catch (MessagingException e) 
        {
            return e.getMessage();
            
        }
    }
}
