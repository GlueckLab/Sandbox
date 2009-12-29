package edu.cudenver.bios.powercalculator.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

import edu.cudenver.bios.powercalculator.client.panels.InputWizardPanel;
import edu.cudenver.bios.powercalculator.client.panels.NavigationPanel;
import edu.cudenver.bios.powercalculator.client.panels.StepsLeftPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class PowerCalculatorGUI implements EntryPoint
{
    /**
     * This is the entry point method.
     */
    static final StepsLeftPanel stepsLeftPanel = new StepsLeftPanel();
    static final InputWizardPanel inputPanel = new InputWizardPanel();
    static final NavigationPanel navPanel = new NavigationPanel();
    
    
    public void onModuleLoad()
    {
        // set up callbacks between the panels
        navPanel.addNavigationListener(stepsLeftPanel);
        navPanel.addNavigationListener(inputPanel);
        
        // add the gwt elements to the root panel
        RootPanel.get("stepsLeftPanel").add(stepsLeftPanel);
        RootPanel.get("inputPanel").add(inputPanel);
        RootPanel.get("navigationPanel").add(navPanel);
    }
}
