package edu.cudenver.bios.powercalculator.client.panels;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
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
	protected Label calculatedPower = new Label("");
	protected Label simulatedPower = new Label("");
	protected Label sampleSize = new Label("");
	
	protected Image powerCurve = new Image();
	
	public ResultsPanel()
	{
		VerticalPanel panel = new VerticalPanel();
		VerticalPanel powerPanel = new VerticalPanel();
		VerticalPanel sampleSizePanel = new VerticalPanel();

		panel.add(calculatedPower);
		panel.add(powerCurve);
		//calculatedPower.setVisible(false);
		initWidget(panel);
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
