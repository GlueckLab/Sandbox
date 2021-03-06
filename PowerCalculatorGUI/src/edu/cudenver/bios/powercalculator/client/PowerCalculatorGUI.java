package edu.cudenver.bios.powercalculator.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;

import edu.cudenver.bios.powercalculator.client.panels.InputWizardPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class PowerCalculatorGUI implements EntryPoint
{
    /**
     * This is the entry point method.
     */
	
    // string constants for internationalization 
    public static final PowerCalculatorConstants constants =  
    	(PowerCalculatorConstants) GWT.create(PowerCalculatorConstants.class); 
    
    public void onModuleLoad()
    {        
        // add the gwt elements to the root panel
        RootPanel.get("powerCalculatorWizard").add(new InputWizardPanel());
        RootPanel.get("powerCalculatorWizard").setStyleName("powerCalculatorPanel");
        // set root style so it recognizes standard css elements like "body"
        RootPanel.get().setStyleName("body");
    }
}
