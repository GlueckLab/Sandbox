package edu.cudenver.bios.powercalculator.client.panels;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.cudenver.bios.powercalculator.client.PowerCalculatorConstants;
import edu.cudenver.bios.powercalculator.client.PowerCalculatorGUI;
import edu.cudenver.bios.powercalculator.client.TextValidation;
import edu.cudenver.bios.powercalculator.client.listener.InputWizardStepListener;
import edu.cudenver.bios.powercalculator.client.listener.MetaDataListener;
import edu.cudenver.bios.powercalculator.client.listener.OptionsListener;

public class OptionsPanel extends Composite 
implements MetaDataListener, ClickHandler
{
	private static final String SOLVE_FOR_GROUP = "solveFor";

	private static final int SAMPLE_SIZE_INDEX = 0;
	private static final int POWER_INDEX = 1;
	
	// solve for options
	protected RadioButton powerRb = new RadioButton(SOLVE_FOR_GROUP, PowerCalculatorGUI.constants.radioButtonPower());
	protected RadioButton sampleSizeRb = new RadioButton(SOLVE_FOR_GROUP, PowerCalculatorGUI.constants.radioButtonSampleSize());
	
    // power / sample size options 
	protected DeckPanel powerSampleSizeDeck = new DeckPanel();
    protected TextBox sampleSizeTextBox = new TextBox();
    protected TextBox powerTextBox = new TextBox();
    protected HTML detailsErrorHTML = new HTML();
    
	// curve options
	protected CheckBox showCurveCheckBox = new CheckBox(PowerCalculatorGUI.constants.checkBoxShowCurve());
	protected TextBox curveTitleTextBox = new TextBox();
	protected TextBox curveXAxisLabel = new TextBox();
	protected TextBox curveYAxisLabel = new TextBox();
    protected TextBox curveWidth = new TextBox();
    protected TextBox curveHeight = new TextBox();
    
    protected InputWizardStepListener wizard;
    protected int stepIndex = -1;
	protected String modelName = PowerCalculatorGUI.constants.modelGLMM();;
	protected ArrayList<OptionsListener> listeners = new ArrayList<OptionsListener>();

	protected int minimumN = -1;
	    
	public OptionsPanel(InputWizardStepListener wizard, int stepIndex)
	{
	    this.wizard = wizard;
	    this.stepIndex = stepIndex;
	    
		VerticalPanel panel = new VerticalPanel();

		// header, description
		HTML header = new HTML("Power &amp; Sample Size Options");
		HTML description = new HTML("Select whether you would like a power or sample size estimate and fill in the requested details.  You may also include a power curve in your results.");
		
		// layout the input panel
		VerticalPanel inputContainer = new VerticalPanel();
		inputContainer.add(createSolveForPanel());
		inputContainer.add(createDetailsPanel());
		inputContainer.add(createCurveOptionsPanel());		
		
		// layout the overall panel
		panel.add(header);
		panel.add(description);
		panel.add(inputContainer);
		
		// add style
		header.setStyleName(PowerCalculatorConstants.STYLE_WIZARD_STEP_HEADER);
		description.setStyleName(PowerCalculatorConstants.STYLE_WIZARD_STEP_DESCRIPTION);
		inputContainer.setStyleName(PowerCalculatorConstants.STYLE_WIZARD_STEP_INPUT_CONTAINER);
		panel.setStyleName(PowerCalculatorConstants.STYLE_WIZARD_STEP_PANEL);
		
		// initialize widget
		initWidget(panel);
	}


	private VerticalPanel createDetailsPanel()
	{
		VerticalPanel panel = new VerticalPanel();

		HTML header = new HTML(PowerCalculatorGUI.constants.panelLabelOptionsDetails());

		HorizontalPanel sampleSizeContainer = new HorizontalPanel();
        sampleSizeContainer.add(new HTML(PowerCalculatorGUI.constants.textLabelSampleSize()));
        sampleSizeContainer.add(sampleSizeTextBox);

        HorizontalPanel powerContainer = new HorizontalPanel();
        powerContainer.add(new HTML(PowerCalculatorGUI.constants.textLabelPower()));
        powerContainer.add(powerTextBox);
        
        powerSampleSizeDeck.add(sampleSizeContainer);
        powerSampleSizeDeck.add(powerContainer);
        
        HorizontalPanel detailsContainer = new HorizontalPanel();
        detailsContainer.add(powerSampleSizeDeck);
        detailsContainer.add(detailsErrorHTML);
        
        // add change handlers to error check the text box
        powerTextBox.addChangeHandler(new ChangeHandler() {
            public void onChange(ChangeEvent e)
            {
                try
                {
                    TextValidation.parseDouble(powerTextBox.getText(), 0, 1);
                    TextValidation.displayOkay(detailsErrorHTML, PowerCalculatorGUI.constants.okay());
                    wizard.onStepComplete(stepIndex);
                }
                catch (NumberFormatException nfe)
                {
                    TextValidation.displayError(detailsErrorHTML, PowerCalculatorGUI.constants.errorPowerInvalid());
                    powerTextBox.setText("");
                    wizard.onStepInProgress(stepIndex);
                }                
                
            }
        });
        sampleSizeTextBox.addChangeHandler(new ChangeHandler() {
            public void onChange(ChangeEvent e)
            {
                try
                {
                    int n = TextValidation.parseInteger(sampleSizeTextBox.getText(), 0, Integer.MAX_VALUE);
                    if (n % minimumN == 0)
                    {
                        TextValidation.displayOkay(detailsErrorHTML, PowerCalculatorGUI.constants.okay());
                        wizard.onStepComplete(stepIndex);
                    }
                    else
                    {
                        throw new NumberFormatException();
                    }
                }
                catch (NumberFormatException nfe)
                {
                    TextValidation.displayError(detailsErrorHTML, PowerCalculatorGUI.constants.errorSampleSizeInvalid() + minimumN);
                    sampleSizeTextBox.setText("");
                    wizard.onStepInProgress(stepIndex);
                }   
            }
        });
        // show the appropriate box
        powerSampleSizeDeck.showWidget((powerRb.getValue() ? SAMPLE_SIZE_INDEX : POWER_INDEX));

        // layout the panel
        panel.add(header);
        panel.add(detailsContainer);
        
        // add style
		header.setStyleName(PowerCalculatorConstants.STYLE_WIZARD_STEP_HEADER);
		header.addStyleDependentName(PowerCalculatorConstants.STYLE_WIZARD_STEP_SUBPANEL);
		panel.setStyleName(PowerCalculatorConstants.STYLE_WIZARD_STEP_PANEL);
		panel.addStyleDependentName(PowerCalculatorConstants.STYLE_WIZARD_STEP_SUBPANEL);
		detailsErrorHTML.setStyleName(PowerCalculatorConstants.MESSAGE_STYLE);
		detailsErrorHTML.addStyleDependentName(PowerCalculatorConstants.ERROR_STYLE);
		
		return panel;
	}

	private VerticalPanel createSolveForPanel()
	{
		// radio buttons indicating which calculation is desired
		VerticalPanel selectPanel = new VerticalPanel();

		// create the header
		HTML header = new HTML(PowerCalculatorGUI.constants.panelLabelOptionsSolveFor());

		// add click handlers on the selection buttons
		powerRb.addClickHandler(this);
		sampleSizeRb.addClickHandler(this);

		// layout the panel
		selectPanel.add(header);
		selectPanel.add(powerRb);
		selectPanel.add(sampleSizeRb);

		// set style
		header.setStyleName(PowerCalculatorConstants.STYLE_WIZARD_STEP_HEADER);
		header.addStyleDependentName(PowerCalculatorConstants.STYLE_WIZARD_STEP_SUBPANEL);
		selectPanel.setStyleName(PowerCalculatorConstants.STYLE_WIZARD_STEP_PANEL);
		selectPanel.addStyleDependentName(PowerCalculatorConstants.STYLE_WIZARD_STEP_SUBPANEL);

		// select power by default
		powerRb.setValue(true);

		return selectPanel;
	}

	private VerticalPanel createCurveOptionsPanel()
	{
		VerticalPanel panel = new VerticalPanel();

		// create the header
		HTML header = new HTML(PowerCalculatorGUI.constants.panelLabelOptionsGraphics());

		// add handler for show curve checkbox
		showCurveCheckBox.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent e)
			{
				notifyOnShowCurve(showCurveCheckBox.getValue());
			}
		});

		// title, label options
		Grid grid = new Grid(3,2);
		grid.setWidget(0,0, new HTML(PowerCalculatorGUI.constants.textLabelCurveTitle()));
		grid.setWidget(0,1, curveTitleTextBox);
		grid.setWidget(1,0, new HTML(PowerCalculatorGUI.constants.textLabelCurveXAxis()));
		grid.setWidget(1,1, curveXAxisLabel);
		grid.setWidget(2,0, new HTML(PowerCalculatorGUI.constants.textLabelCurveYAxis()));
		grid.setWidget(2,1, curveYAxisLabel);
//		grid.setWidget(3, 0, new HTML("Width"));
//		grid.setWidget(3, 1, curveWidth);
//		grid.setWidget(4, 0, new HTML("Height"));
//		grid.setWidget(4, 1, curveHeight);
	        
		// layout the panel
		panel.add(header);
		panel.add(showCurveCheckBox);
		panel.add(grid);

		// set style
        header.setStyleName(PowerCalculatorConstants.STYLE_WIZARD_STEP_HEADER);
        header.addStyleDependentName(PowerCalculatorConstants.STYLE_WIZARD_STEP_SUBPANEL);
        panel.setStyleName(PowerCalculatorConstants.STYLE_WIZARD_STEP_PANEL);
        panel.addStyleDependentName(PowerCalculatorConstants.STYLE_WIZARD_STEP_SUBPANEL);

		return panel;
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
	    CurveOptions curveOpts = null;
	    if (showCurve)
	    {
	        curveOpts = new CurveOptions();
	        curveOpts.title = curveTitleTextBox.getText();
	        curveOpts.xAxisLabel = this.curveXAxisLabel.getText();
	        curveOpts.yAxisLabel = this.curveYAxisLabel.getText();
	    }
		for(OptionsListener listener: listeners) listener.onShowCurve(showCurve, curveOpts);
	}

    public String getGraphicsOptions()
    {      
        boolean showCurve = showCurveCheckBox.getValue();
        if (showCurve)
        {
            StringBuffer buffer = new StringBuffer();
            buffer.append("<curve ");
            buffer.append("title='" + curveTitleTextBox.getText() + "' ");
            buffer.append("xlabel='" + curveXAxisLabel.getText() + "' ");
            buffer.append("ylabel='" + curveYAxisLabel.getText() + "' ");
//            if (!curveWidth.getText().isEmpty())
//                buffer.append("width='" + curveWidth.getText() + "' ");
//            if (!curveHeight.getText().isEmpty())
//                buffer.append("height='" + curveHeight.getText() + "' ");
            buffer.append("/>");  
            return buffer.toString();
        }
        else
        {
            // no graphics options set
            return ""; 
        }
    }
    
    public String getPowerAttributes()
    {
        return "sampleSize='" + sampleSizeTextBox.getValue() + "'";            
    }
    
    public String getSampleSizeAttributes()
    {
        return "power='" + powerTextBox.getValue() + "'";
    }    

    // meta data listener callbacks
    public void onCovariate(boolean hasCovariate) {}
    
    public void onMinimumSampleSize(int minimumN)
    {
    	this.minimumN = minimumN;
    }
    
    public void onClick(ClickEvent e)
    {
        detailsErrorHTML.setText("");
        powerSampleSizeDeck.showWidget((powerRb.getValue() ? SAMPLE_SIZE_INDEX : POWER_INDEX));        
        notifyOnSolvingFor(powerRb.getValue());
    }
    
    public int getSampleSize()
    {
        return Integer.parseInt(sampleSizeTextBox.getText());
    }
    
    public void reset()
    {
        // select solve for power by default
        powerRb.setValue(true);
        notifyOnSolvingFor(true);

        // clear power/sample size options
        sampleSizeTextBox.setText("");
        powerTextBox.setText("");
        
        // reset curve options
        showCurveCheckBox.setValue(false);
        curveTitleTextBox.setText("");
        curveXAxisLabel.setText("");
        curveYAxisLabel.setText("");
        curveWidth.setText("");
        curveHeight.setText("");
        notifyOnShowCurve(false);
    }
}
