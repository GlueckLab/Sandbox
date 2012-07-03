package edu.ucdenver.bios.bugreport.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import edu.ucdenver.bios.bugreport.client.EmailContent;
/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface SendEmailService extends RemoteService {
    
    void sendEmail(EmailContent content)throws Exception;
}
