package edu.ucdenver.bios.bugreport.client;

import edu.ucdenver.bios.bugreport.client.EmailContent;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface SendEmailServiceAsync {
    void sendEmail(EmailContent content, AsyncCallback callback)throws Exception;
}
