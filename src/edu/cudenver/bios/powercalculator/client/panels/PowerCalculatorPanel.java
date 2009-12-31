package edu.cudenver.bios.powercalculator.client.panels;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

public class PowerCalculatorPanel extends Composite implements NavigationListener
{
	protected static StepsLeftPanel stepsLeftPanel = new StepsLeftPanel();
	protected static InputWizardPanel inputPanel = new InputWizardPanel();
	protected static NavigationPanel navPanel = new NavigationPanel();
	
	public PowerCalculatorPanel()
	{
        // set up the navigation callbacks
        navPanel.addNavigationListener(this);
        
        VerticalPanel vPanel = new VerticalPanel();
        vPanel.setStyleName("powerCalculatorPanel");
        vPanel.add(stepsLeftPanel);
        vPanel.add(inputPanel);
        vPanel.add(navPanel);
        initWidget(vPanel);
	}
	
    public void onNext()
    {
    	if (inputPanel.canContinue())
    	{
    		stepsLeftPanel.onNext();
    		inputPanel.onNext();
    	}
    }
    
    public void onPrevious()
    {
    	stepsLeftPanel.onPrevious();
    	inputPanel.onPrevious();
    }
    
    public void onCancel()
    {
        stepsLeftPanel.onCancel();
        inputPanel.onCancel();
    }
}
