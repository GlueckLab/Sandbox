package edu.cudenver.bios.powercalculator.client.panels;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.NamedNodeMap;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.XMLParser;
import com.google.gwt.xml.client.NodeList;

public class ResultsPanel extends Composite
{
    final DeckPanel deck = new DeckPanel();

    private static final int POWER_INDEX = 0;
    private static final int SAMPLE_SIZE_INDEX = 1;

    protected Label calculatedPower = new Label("N/A");
    protected Label simulatedPower = new Label("N/A");

    protected Label sampleSize = new Label("N/A");
    protected Label actualPower = new Label("N/A");

    // power curve panel
    protected VerticalPanel powerCurvePanel; 
    protected Image powerCurve;
    
    public ResultsPanel()
    {
        VerticalPanel panel = new VerticalPanel();

        deck.add(createPowerResultsPanel());
        deck.add(createSampleSizeResultsPanel());

        // add the power/sample panels to the main panel
        panel.add(deck);

        powerCurvePanel = createPowerCurvePanel();
        panel.add(powerCurvePanel);
        powerCurvePanel.setVisible(false);
        
        // initialize the widget
        initWidget(panel);
    }

    private VerticalPanel createPowerResultsPanel()
    {
        VerticalPanel powerPanel = new VerticalPanel();
        // build power results panel
        Grid powerGrid = new Grid(2,2);
        powerGrid.setWidget(0, 0, new HTML("Power (calculated): "));
        powerGrid.setWidget(0,1, calculatedPower);
        powerGrid.setWidget(1, 0, new HTML("Power (simulated): "));
        powerGrid.setWidget(1,1, simulatedPower);
        powerPanel.add(powerGrid);
        return powerPanel;
    }

    private VerticalPanel createPowerCurvePanel()
    {
        VerticalPanel panel = new VerticalPanel();
        panel.add(new HTML("Power Curve: "));
        powerCurve = new Image();
        panel.add(powerCurve);
        return panel;
    }
    
    private VerticalPanel createSampleSizeResultsPanel()
    {
        VerticalPanel sampleSizePanel = new VerticalPanel();
        // build the sample size results panel
        Grid sampleSizeGrid = new Grid(1,2);
        sampleSizeGrid.setWidget(0, 0, new HTML("Sample Size: "));
        sampleSizeGrid.setWidget(0,1, sampleSize);
        sampleSizePanel.add(new HTML("Sample Size Results: "));
        sampleSizePanel.add(sampleSizeGrid);
        return sampleSizePanel;
    }

    public void clearResults()
    {
        // TODO: do we need to empty the values?
        deck.setVisible(false);
    }

    private void setCurveResults(Document doc)
    {
        // parse the power curve
        NodeList imageList = doc.getElementsByTagName("curveImg");
        if (imageList.getLength() > 0)
        {
            Node node = imageList.item(0);
            powerCurve.setUrl("data:image/jpg;base64," + node.getFirstChild().getNodeValue());
            powerCurvePanel.setVisible(true);
        }
        else
        {
            powerCurvePanel.setVisible(false);
        }
    }
    
    public void setSampleSizeResults(String xmlResults)
    {
        Document doc = XMLParser.parse(xmlResults);

        setCurveResults(doc);

        // get the calculated and simulated power
        NodeList sampleSizeList = doc.getElementsByTagName("sampleSize");
        if (sampleSizeList.getLength() > 0)
        {
            Node sampleSizeNode = sampleSizeList.item(0);
            NamedNodeMap attrs = sampleSizeNode.getAttributes();
            if (attrs != null)
            {
                Node powerAttr = attrs.getNamedItem("power");
                if (powerAttr != null) actualPower.setText(powerAttr.getNodeValue());

                Node sampleSizeAttr = attrs.getNamedItem("sampleSize");
                if (sampleSizeAttr != null) sampleSize.setText(sampleSizeAttr.getNodeValue());
            }
        }       
        
        deck.showWidget(SAMPLE_SIZE_INDEX);
        deck.setVisible(true);
    }

    public void setPowerResults(String xmlResults)
    {
        Document doc = XMLParser.parse(xmlResults);

        setCurveResults(doc);

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
        
        deck.showWidget(POWER_INDEX);
        deck.setVisible(true);
    }
}
