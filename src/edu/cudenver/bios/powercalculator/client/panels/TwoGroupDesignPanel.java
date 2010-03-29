package edu.cudenver.bios.powercalculator.client.panels;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.NamedNodeMap;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;

import edu.cudenver.bios.powercalculator.client.PowerCalculatorGUI;

public class TwoGroupDesignPanel extends Composite
{
	private static final String STYLE = "twoGroupDesignPanel";
    protected TextBox alphaTextBox = new TextBox();
    protected TextBox mu0TextBox = new TextBox();
    protected TextBox muATextBox = new TextBox();
    protected TextBox sigmaTextBox = new TextBox();
    
    public TwoGroupDesignPanel()
    {
        // TODO: string constants!!!!
        VerticalPanel panel = new VerticalPanel();
        Grid grid = new Grid(4,2);
        grid.setWidget(0, 0, new HTML(PowerCalculatorGUI.constants.textLabelAlpha()));
        grid.setWidget(0, 1, alphaTextBox);
        grid.setWidget(1, 0, new HTML(PowerCalculatorGUI.constants.textLabelMu0()));
        grid.setWidget(1, 1, mu0TextBox);
        grid.setWidget(2, 0, new HTML(PowerCalculatorGUI.constants.textLabelMuA()));
        grid.setWidget(2, 1, muATextBox);
        grid.setWidget(3, 0, new HTML(PowerCalculatorGUI.constants.textLabelSigma()));
        grid.setWidget(3, 1, sigmaTextBox);
        panel.add(grid);
        initWidget(panel);
    }
    
    public String getAlpha()
    {
    	return alphaTextBox.getText();
    }
    
    public String getNullMean()
    {
    	return mu0TextBox.getText();
    }
    
    public String getAlternativeMean()
    {
    	return muATextBox.getText();
    }
    
    public String getSigma()
    {
    	return sigmaTextBox.getText();
    }        
    
    public void loadFromXMLDocument(Document doc)
    {
    	Node studyNode = doc.getElementsByTagName("study").item(0);
    	if (studyNode != null)
    	{
    		Node alpha = studyNode.getAttributes().getNamedItem("alpha");
    		if (alpha != null) alphaTextBox.setValue(alpha.getNodeValue());

    		// parse the remaining matrices
    		NodeList matrixNodes = doc.getElementsByTagName("matrix");
    		for(int i = 0; i < matrixNodes.getLength(); i++)
    		{
    			Node matrixNode = matrixNodes.item(i);
    			NamedNodeMap attrs = matrixNode.getAttributes();
    			Node nameNode = attrs.getNamedItem("name");
    			if (nameNode != null)
    			{
    				String name = nameNode.getNodeValue();
    				if (name.equals("beta"))
    				{
    					// retrieve the mu0 (entry 0,0), muA (entry 1,0) from the beta matrix				
    					NodeList rowNodeList = matrixNode.getChildNodes();
    					for(int r = 0; r < rowNodeList.getLength(); r++)
    					{
    						Node firstColNode = rowNodeList.item(r).getFirstChild();
    						if (firstColNode != null)
    						{
    							Node colItem = firstColNode.getFirstChild();
    							if (r == 0)
    							{
    								if (colItem != null) mu0TextBox.setText(colItem.getNodeValue());
    							}
    							else if (r == 1)
    							{
    								if (colItem != null) muATextBox.setText(colItem.getNodeValue());
    							}
    						}
    					}
    				}
    				else if (name.equals("sigmaError"))
    				{
    					// retrieve the variance from the sigma matrix (entry 0,0)		
    					Node firstRowNode = matrixNode.getFirstChild();
    					if (firstRowNode != null)
    					{
    						Node firstColNode = firstRowNode.getFirstChild();
    						if (firstColNode != null)
    						{
    							Node colItem = firstColNode.getFirstChild();
    							if (colItem != null) sigmaTextBox.setText(colItem.getNodeValue());
    						}
    					}
    				}
    			}
    		}
    	}
    }
}
