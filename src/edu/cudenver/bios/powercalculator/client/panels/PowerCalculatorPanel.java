package edu.cudenver.bios.powercalculator.client.panels;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

public class PowerCalculatorPanel extends Composite
{
	private static final String STYLE = "powerCalculatorPanel";
	protected static InputWizardPanel wizardPanel = new InputWizardPanel();
	
	public PowerCalculatorPanel()
	{
        VerticalPanel vPanel = new VerticalPanel();
        vPanel.setStyleName(STYLE);
        vPanel.add(wizardPanel);        
        initWidget(vPanel);
	}
	
}
