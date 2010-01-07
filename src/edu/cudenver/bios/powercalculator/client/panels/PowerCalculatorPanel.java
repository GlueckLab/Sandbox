package edu.cudenver.bios.powercalculator.client.panels;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

public class PowerCalculatorPanel extends Composite
{
	private static final String STYLE = "powerCalculatorPanel";
	protected static StepsLeftPanel stepsLeftPanel = new StepsLeftPanel();
	protected static InputWizardPanel wizardPanel = new InputWizardPanel();
	
	public PowerCalculatorPanel()
	{
        VerticalPanel vPanel = new VerticalPanel();
        vPanel.setStyleName(STYLE);
        vPanel.add(stepsLeftPanel);
        vPanel.add(wizardPanel);
        wizardPanel.addNavigationListener(stepsLeftPanel);
        
        initWidget(vPanel);
	}
	
}
