package edu.cudenver.bios.powercalculator.client.panels;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

public class BasicPanel extends Composite
{
	
	public BasicPanel()
	{
		VerticalPanel panel = new VerticalPanel();
		
		panel.add(createIndependentVariablePanel());
		initWidget(panel);
	}
	
	private VerticalPanel createIndependentVariablePanel()
	{
		VerticalPanel panel = new VerticalPanel();	
		
		
		return panel;
	}
}
