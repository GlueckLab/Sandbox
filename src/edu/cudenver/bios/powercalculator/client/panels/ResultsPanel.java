package edu.cudenver.bios.powercalculator.client.panels;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.NamedFrame;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.NamedNodeMap;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.XMLParser;
import com.google.gwt.xml.client.NodeList;

import edu.cudenver.bios.powercalculator.client.PowerCalculatorConstants;
import edu.cudenver.bios.powercalculator.client.listener.OptionsListener;

public class ResultsPanel extends Composite implements OptionsListener
{
    final DeckPanel deck = new DeckPanel();

    public static final String POWER_CURVE_FRAME = "powerCurveFrame";
    private static final int POWER_INDEX = 0;
    private static final int SAMPLE_SIZE_INDEX = 1;

    protected Label calculatedPower = new Label("N/A");
    protected Label simulatedPower = new Label("N/A");

    protected Label sampleSize = new Label("N/A");
    protected Label actualPower = new Label("N/A");

    // power curve panel
    protected VerticalPanel powerCurvePanel; 
    protected Image powerCurve;
    
    // hidden iframe to hold the power curve image data
    protected NamedFrame imageFrame = new NamedFrame(POWER_CURVE_FRAME);

    public ResultsPanel()
    {
        VerticalPanel panel = new VerticalPanel();
        
        // header, description
        HTML header = new HTML("Results: ");
        
        VerticalPanel resultsContainer = new VerticalPanel();
        deck.add(createPowerResultsPanel());
        deck.add(createSampleSizeResultsPanel());
        deck.showWidget(POWER_INDEX);
        
        powerCurvePanel = createPowerCurvePanel();

        //powerCurvePanel.setVisible(false);
        
        // layout the panel
        resultsContainer.add(deck);
        resultsContainer.add(powerCurvePanel);
        panel.add(header);
        panel.add(resultsContainer);
        
        // add style
        header.setStyleName(PowerCalculatorConstants.STYLE_WIZARD_STEP_HEADER);
        resultsContainer.setStyleName(PowerCalculatorConstants.STYLE_WIZARD_STEP_INPUT_CONTAINER);
        
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
        panel.add(imageFrame);
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
    
    public void setSampleSizeResults(String xmlResults)
    {
        Document doc = XMLParser.parse(xmlResults);

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
        
        deck.setVisible(true);
    }

    public void setPowerResults(String xmlResults)
    {
        Document doc = XMLParser.parse(xmlResults);

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
        
        deck.setVisible(true);
    }
    
    public void setErrorResults(String errMsg)
    {
        Window.alert(errMsg);
    }
    
    
    public void onShowCurve(boolean show, CurveOptions opts)
    {
        powerCurvePanel.setVisible(show);
    }
    
    public void onSolveFor(boolean power)
    {
        Window.alert("hello?");
        if (power)
            deck.showWidget(SAMPLE_SIZE_INDEX);
        else
            deck.showWidget(POWER_INDEX);
    }

}
