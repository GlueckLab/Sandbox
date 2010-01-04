package edu.cudenver.bios.powercalculator.client.panels;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ResultsPanel extends Composite implements ClickHandler
{
	
	public ResultsPanel()
	{
		VerticalPanel panel = new VerticalPanel();
		
		panel.add(new Button("options"));
		initWidget(panel);
	}
	
	public void onClick(ClickEvent e)
	{
		
	}
}
