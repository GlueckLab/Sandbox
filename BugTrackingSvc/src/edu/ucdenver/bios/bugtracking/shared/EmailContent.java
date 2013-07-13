package edu.ucdenver.bios.bugtracking.shared;

import java.io.Serializable;

public class EmailContent implements Serializable{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String subject= null;
    private String bugName = null;
    private String description = null;
    private String priority = "major";
    
    public void setSubject(String subject)
    {
        this.subject= subject;
    }
    
    public void setBugName(String bugName)
    {
        this.bugName = bugName;
    }
    
    public void setDescription(String description)
    {
        this.description = description;
    }
    public void setPriority(String priority)
    {
        this.priority= priority;
    }
    
    public String getSubject()
    {
        return subject;
    }
    
    public String getBugName()
    {
        return bugName;
    }
    
    public String getDescription()
    {
        return description;
    }
    public String getPriorrity()
    {
        return priority;
    }

}
