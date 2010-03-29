package edu.cudenver.bios.powercalculator.client.panels;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Node;

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
		
		panel.add(new HTML("GLMM study design view (under construction)"));
		return panel;
	}
	
	public void loadFromXMLDocument(Document doc)
	{
    	Node studyNode = doc.getElementsByTagName("study").item(0);
    	if (studyNode != null)
    	{
    		Node alpha = studyNode.getAttributes().getNamedItem("alpha");
    		if (alpha != null)
    		{
    			
    		}
    	}
	}
}
