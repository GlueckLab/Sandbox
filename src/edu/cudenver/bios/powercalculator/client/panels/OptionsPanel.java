package edu.cudenver.bios.powercalculator.client.panels;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

public class OptionsPanel extends Composite
{
	public OptionsPanel()
	{
		VerticalPanel panel = new VerticalPanel();
		
		panel.add(new Button("options"));
		initWidget(panel);
	}
}
