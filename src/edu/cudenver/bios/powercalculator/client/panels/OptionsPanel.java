package edu.cudenver.bios.powercalculator.client.panels;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.cudenver.bios.powercalculator.client.PowerCalculatorGUI;

public class OptionsPanel extends Composite
{
    private static final String SOLVE_FOR_GROUP = "solveFor";
    
    private static final int INDEX_SIMPLE_POWER = 0;
    private static final int INDEX_SIMPLE_SAMPLE_SIZE = 1;
    private static final int INDEX_LINEAR_MODEL_POWER = 2;
    private static final int INDEX_LINEAR_MODEL_SAMPLE_SIZE = 3;
    
    // curve options
    protected CheckBox showCurveCheckBox = new CheckBox("Show Curve");
    
    // simple sample size params
	protected TextBox sampleSizeTextBox = new TextBox();
	protected TextBox powerTextBox = new TextBox();
	
	protected DeckPanel deck;
	protected String modelName;
	
	protected ArrayList<OptionsListener> listeners = new ArrayList<OptionsListener>();
	
	public OptionsPanel(String model)
	{
	    this.modelName = model;
		VerticalPanel panel = new VerticalPanel();
		
		// radio buttons indicating which calculation is desired
		HorizontalPanel selectPanel = new HorizontalPanel();
		selectPanel.add(new HTML("Solve for: "));
		RadioButton powerRb = new RadioButton(SOLVE_FOR_GROUP, "Power");
		powerRb.addClickHandler(new ClickHandler() {
		    public void onClick(ClickEvent e)
		    {
		        if (PowerCalculatorGUI.constants.testGLMM().equals(modelName))
		            deck.showWidget(INDEX_LINEAR_MODEL_POWER);
		        else
		            deck.showWidget(INDEX_SIMPLE_POWER);
                notifyOnSolvingFor(true);
		    }
		});
		selectPanel.add(powerRb);

		RadioButton sampleSizeRb = new RadioButton(SOLVE_FOR_GROUP, "Sample Size");
        sampleSizeRb.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent e)
            {
                if (PowerCalculatorGUI.constants.testGLMM().equals(modelName))
                    deck.showWidget(INDEX_LINEAR_MODEL_SAMPLE_SIZE);
                else
                    deck.showWidget(INDEX_SIMPLE_SAMPLE_SIZE);
                notifyOnSolvingFor(false);
            }
        });
		selectPanel.add(sampleSizeRb);
		
		// deck of possible option panels - power/sample size options
		// differ depending on which model is selected
		deck = new DeckPanel();
		deck.add(this.createSimplePowerPanel());
		deck.add(this.createSimpleSampleSizePanel());
		deck.add(this.createLinearModelPowerPanel());
		deck.add(this.createLinearModelSampleSizePanel());
		
		panel.add(selectPanel);
		panel.add(deck);
		panel.add(createCurveOptionsPanel());
		
		// set style 
		
		// initialize widget
		initWidget(panel);
	}
	
	public void setModel(String modelName)
	{
	    this.modelName = modelName;
	}

	private VerticalPanel createCurveOptionsPanel()
	{
	    VerticalPanel panel = new VerticalPanel();
	    
	    panel.add(showCurveCheckBox);
	    showCurveCheckBox.addClickHandler(new ClickHandler() {
	        public void onClick(ClickEvent e)
	        {
	            notifyOnShowCurve(showCurveCheckBox.getValue());
	        }
	    });
	    return panel;
	}
	
	private HorizontalPanel createSimplePowerPanel()
	{
        HorizontalPanel powerPanel = new HorizontalPanel();
        powerPanel.add(new HTML("Sample size: "));
        powerPanel.add(sampleSizeTextBox);
	    return powerPanel;
	}
	
	private VerticalPanel createLinearModelPowerPanel()
	{
	       VerticalPanel panel = new VerticalPanel();
	        return panel;
	}
	
	private HorizontalPanel createSimpleSampleSizePanel()
	{
        HorizontalPanel sampleSizePanel = new HorizontalPanel();
        sampleSizePanel.add(new HTML("Desired Power: "));
        sampleSizePanel.add(powerTextBox);
	    return sampleSizePanel;
	}

	private VerticalPanel createLinearModelSampleSizePanel()
	{
	    VerticalPanel panel = new VerticalPanel();
	    return panel;
	}

	public String getPower()
	{
	    return powerTextBox.getText();
	}
	
	public String getSampleSize()
	{
	    return sampleSizeTextBox.getText();
	}
	
	public String getRowMetaDataXML()
	{
	    // TODO: FINISH ME
	    return "<rowMetaData></rowMetaData>";
	}
	
	public void addListener(OptionsListener listener)
	{
	    listeners.add(listener);
	}
	
	private void notifyOnSolvingFor(boolean solveForPower)
	{
	    for(OptionsListener listener: listeners) listener.onSolveFor(solveForPower);
	}
	
	private void notifyOnShowCurve(boolean showCurve)
	{
	    for(OptionsListener listener: listeners) listener.onShowCurve(showCurve);
	}
}
