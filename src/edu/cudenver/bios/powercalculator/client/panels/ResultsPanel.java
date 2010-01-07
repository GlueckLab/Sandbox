package edu.cudenver.bios.powercalculator.client.panels;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.NamedNodeMap;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.XMLParser;
import com.google.gwt.xml.client.NodeList;

public class ResultsPanel extends Composite
{
	protected VerticalPanel powerPanel = new VerticalPanel();
	protected VerticalPanel sampleSizePanel = new VerticalPanel();
	protected Label calculatedPower = new Label("N/A");
	protected Label simulatedPower = new Label("N/A");
	protected Label sampleSize = new Label("N/A");
	
	protected Image powerCurve = new Image();
	protected Image sampleSizeCurve = new Image();
	
	public ResultsPanel()
	{
		VerticalPanel panel = new VerticalPanel();
		
		// build power results panel
		Grid powerGrid = new Grid(2,2);
		powerGrid.setWidget(0, 0, new HTML("Power (calculated): "));
		powerGrid.setWidget(0,1, calculatedPower);
		powerGrid.setWidget(1, 0, new HTML("Power (simulated): "));
		powerGrid.setWidget(1,1, simulatedPower);
		powerPanel.add(new HTML("Power Results: "));
		powerPanel.add(powerGrid);
		powerPanel.add(new HTML("Power Curve: "));
		powerPanel.add(powerCurve);

		// build the sample size results panel
		Grid sampleSizeGrid = new Grid(1,2);
		sampleSizeGrid.setWidget(0, 0, new HTML("Sample Size: "));
		sampleSizeGrid.setWidget(0,1, sampleSize);
		sampleSizePanel.add(new HTML("Sample Size Results: "));
		sampleSizePanel.add(sampleSizeGrid);
		sampleSizePanel.add(new HTML("Power Curve: "));
		sampleSizePanel.add(sampleSizeCurve);
		
		// add the power/sample panels to the main panel
		panel.add(powerPanel);
		panel.add(sampleSizePanel);
		
		// hide the panels on initialization
		//powerPanel.setVisible(false);
		//sampleSizePanel.setVisible(false);
		
		// add style
		
		// initialize the widget
		initWidget(panel);
	}
	
	public void clearResults()
	{
		// TODO: do we need to empty the values?
		powerPanel.setVisible(false);
		sampleSizePanel.setVisible(false);
	}
	
	public void setPowerResults(String xmlResults)
	{
		powerPanel.setVisible(true);
	}
	
	public void setSampleSizeResults(String xmlResults)
	{
		sampleSizePanel.setVisible(true);
	}
	
	public void setResults(String xmlResults)
	{
		Document doc = XMLParser.parse(xmlResults);
		
		// parse the power curve
		NodeList imageList = doc.getElementsByTagName("curveImg");
		if (imageList.getLength() > 0)
		{
			Node node = imageList.item(0);
			powerCurve.setUrl("data:image/jpg;base64," + node.getFirstChild().getNodeValue());
		}

		// get the calculated and simulated power
		NodeList powerList = doc.getElementsByTagName("power");
		if (powerList.getLength() > 0)
		{
			Node power = powerList.item(0);
			NamedNodeMap attrs = power.getAttributes();
			if (attrs != null)
			{
				Node calculated = attrs.getNamedItem("calculated");
				if (calculated != null) calculatedPower.setText(calculated.getNodeValue());
			}
		}		
	}
}
