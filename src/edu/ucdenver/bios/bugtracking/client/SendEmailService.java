package edu.ucdenver.bios.bugtracking.client;

import com.google.gwt.user.client.rpc.RemoteService;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import edu.ucdenver.bios.bugtracking.shared.EmailContent;

@RemoteServiceRelativePath("sendEmail")
public interface SendEmailService extends RemoteService {
    public void sendEmail(EmailContent content)throws Exception;
    /*public void sendEmail(String content)throws Exception;*/

}
