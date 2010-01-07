package edu.cudenver.bios.powercalculator.client.panels;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class OptionsPanel extends Composite
{
	protected CheckBox powerCheckBox = new CheckBox("Estimate power for the following sample size");
	protected CheckBox sampleSizeCheckBox = new CheckBox("Estimate sample size for the following power");
	protected TextBox sampleSizeTextBox = new TextBox();
	protected TextBox powerTextBox = new TextBox();
	
	public OptionsPanel()
	{
		VerticalPanel panel = new VerticalPanel();
		
		// build power options
		panel.add(powerCheckBox);
		HorizontalPanel powerPanel = new HorizontalPanel();
		powerPanel.add(new HTML("Sample size: "));
		powerPanel.add(sampleSizeTextBox);
		sampleSizeTextBox.setEnabled(false);
		panel.add(powerPanel);
		
		// build sample size options
		panel.add(sampleSizeCheckBox);
		HorizontalPanel sampleSizePanel = new HorizontalPanel();
		sampleSizePanel.add(new HTML("Desired Power: "));
		sampleSizePanel.add(powerTextBox);
		powerTextBox.setEnabled(false);
		panel.add(sampleSizePanel);
		
		// add handlers to enable/disable the power options
		powerCheckBox.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent e)
			{
				enablePowerOptions(powerCheckBox.getValue());
			}
		});
		sampleSizeCheckBox.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent e)
			{
				enableSampleSizeOptions(powerCheckBox.getValue());
			}
		});
		
		// set style 
		
		// initialize widget
		initWidget(panel);
	}
	
	private void enablePowerOptions(boolean enabled)
	{
		sampleSizeTextBox.setEnabled(enabled);
	}
	
	private void enableSampleSizeOptions(boolean enabled)
	{
		powerTextBox.setEnabled(enabled);
	}
	
	public String getSampleSize()
	{
		return sampleSizeTextBox.getText();
	}
	
	public String getPower()
	{
		return powerTextBox.getText();
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
