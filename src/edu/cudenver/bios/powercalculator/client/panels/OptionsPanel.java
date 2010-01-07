package edu.cudenver.bios.powercalculator.client.panels;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class OptionsPanel extends Composite
{
	protected TextBox sampleSizeTextBox = new TextBox();
	protected TextBox powerTextBox = new TextBox();
	
	public OptionsPanel()
	{
		VerticalPanel panel = new VerticalPanel();
		
		panel.add(new HTML("Power Options:"));
		HorizontalPanel powerPanel = new HorizontalPanel();
		powerPanel.add(new HTML("Sample size: "));
		powerPanel.add(sampleSizeTextBox);
		
		
		panel.add(new HTML("Sample Size Options:"));
		HorizontalPanel sampleSizePanel = new HorizontalPanel();
		sampleSizePanel.add(new HTML("Desired Power: "));
		sampleSizePanel.add(powerTextBox);
		
		panel.add(powerPanel);
		panel.add(sampleSizePanel);
		
		initWidget(panel);
	}
	
	
	public void setModel()
	{
		
	}

	private Grid createCurveOptionsPanel()
	{
		Grid grid = new Grid(2,2);
		return grid;
	}
}
