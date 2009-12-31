package edu.cudenver.bios.powercalculator.client.panels;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

public class BasicPanel extends Composite
{
	
	public BasicPanel()
	{
		Button foo = new Button("Basic Entry");
		VerticalPanel panel = new VerticalPanel();
		
		panel.add(foo);
		initWidget(panel);
	}
}
