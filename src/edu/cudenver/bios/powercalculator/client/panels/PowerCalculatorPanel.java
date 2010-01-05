package edu.cudenver.bios.powercalculator.client.panels;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

public class PowerCalculatorPanel extends Composite
{
	protected static StepsLeftPanel stepsLeftPanel = new StepsLeftPanel();
	protected static InputWizardPanel wizardPanel = new InputWizardPanel();
	
	public PowerCalculatorPanel()
	{
        VerticalPanel vPanel = new VerticalPanel();
        vPanel.setStyleName("powerCalculatorPanel");
        vPanel.add(stepsLeftPanel);
        vPanel.add(wizardPanel);
        wizardPanel.addNavigationListener(stepsLeftPanel);
        
        initWidget(vPanel);
	}
	
}
