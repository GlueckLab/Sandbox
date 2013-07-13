package edu.ucdenver.bios.bugreportsvc.application;

import org.apache.log4j.Logger;

public class BugReportLogger 
{
    private static Logger instance = null;

    /**
     * Create a new logging object.
     */
    private BugReportLogger() 
    {
    }

    /**
     * Create a single instance of a logging class.
     * @return Logger object
     */
    public static Logger getInstance() 
    {
        if (instance == null) 
        {
            instance = Logger.getLogger("edu.ucdenver.bios.bugtrackingsvc.BugReport");
        }

        return instance;
    }

}
