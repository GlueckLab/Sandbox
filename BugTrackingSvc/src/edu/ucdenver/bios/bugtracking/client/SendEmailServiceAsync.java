package edu.ucdenver.bios.bugtracking.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

import edu.ucdenver.bios.bugtracking.shared.EmailContent;

public interface SendEmailServiceAsync{
    void sendEmail(EmailContent content, AsyncCallback callback)throws Exception;
}
