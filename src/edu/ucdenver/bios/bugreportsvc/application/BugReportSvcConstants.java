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
package edu.ucdenver.bios.bugreportsvc.application;
/**
 * 
 * @author VIJAY AKULA
 *
 */
public class BugReportSvcConstants 
{
    private BugReportSvcConstants()
    {
    }
    
    public static final String SMTP_HOST = "mail.smtp.host";
    
    public static final String SMTP_HOST_NAME = "mail.gmail.com";
    
    public static final String SMTP_PORT = "mail.smtp.socketFactory.port";
    
    public static final String SMTP_PORT_NUMBER = "465";
    
    public static final String SMTP_CLASS = "mail.smtp.socketFactory.class";
    
    public static final String SMTP_CLASS_NAME = "javax.net.ssl.SSLSocketFactory";
    
    public static final String SMTP_AUTHENTICATION = "mail.smtp.auth";
    
    public static final String SMTP_PORT_MAIL = "mail.smtp.port";
    
    public static final String USER_NAME = "vcakula@gmail.com";
    
    public static final String PASSWORD = "tanu@1229";
    
    public static final String TO = "bugs@mixpow01.repositoryhosting.com";
    
    public static final String SUCCESSFULL_MSG = "Bug Reported Successfully...";
}
