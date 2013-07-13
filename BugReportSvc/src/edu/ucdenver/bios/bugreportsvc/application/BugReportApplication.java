package edu.ucdenver.bios.bugreportsvc.application;

import org.restlet.Application;
import org.restlet.Context;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import edu.ucdenver.bios.bugreportsvc.resource.DefaultResource;
import edu.ucdenver.bios.bugreportsvc.resource.ReportBugServerResource;

public class BugReportApplication extends Application
{
    /**
     * Class which dispatches the http requests to the appropriate handler
     * class for Bug Tracking Service.
     * @param parentContext
     */
    public BugReportApplication(final Context parentContext) throws Exception
    {
        super(parentContext);
        
        BugReportLogger.getInstance().info("Bug Tracking Service Starting..");
    }
    
    public Restlet createInBoundRoot()
    {
        //Create a router Restlet that routes each call to a new
        //instance of Resource.
        Router router = new Router(getContext());
        
        // Defines only one default route, self-identifies server
        router.attachDefault(DefaultResource.class);
        
        //Report Bug Server Resource
        router.attach("/bug", ReportBugServerResource.class);
        return router;
        
    }
}
