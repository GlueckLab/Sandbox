package edu.cudenver.bios.powercalculator.client.panels;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;

import edu.cudenver.bios.powercalculator.client.PowerCalculatorConstants;
import edu.cudenver.bios.powercalculator.client.PowerCalculatorGUI;

public class StepsLeftPanel extends Composite implements NavigationListener
{
	protected static final String STYLE = "stepsLeft";
    protected static final String SELECTED_STYLE = "selected";
    protected static final String DESELECTED_STYLE = "deselected";
    
    protected static final int START_INDEX = 1;
    protected static final int STUDY_INDEX = START_INDEX + 2;
    protected static final int OPTIONS_INDEX = STUDY_INDEX + 2;
    protected static final int RESULTS_INDEX = OPTIONS_INDEX + 2;
    
    protected int currentStep = 0;
    
    Grid grid = new Grid(1,RESULTS_INDEX+2);
    HTML startStep = new HTML(PowerCalculatorGUI.constants.startStep());
    HTML studyStep = new HTML(PowerCalculatorGUI.constants.studyStep());
    HTML powerSampleSizeStep = 
        new HTML(PowerCalculatorGUI.constants.optionsStep());
    HTML resultsStep = new HTML(PowerCalculatorGUI.constants.resultsStep());
    
    public StepsLeftPanel()
    {
    	HTML spacer = new HTML(PowerCalculatorGUI.constants.stepSpacer());
        studyStep.setStyleName(STYLE);
        studyStep.addStyleDependentName(DESELECTED_STYLE);
        
        grid.setWidget(0,0,new HTML(PowerCalculatorGUI.constants.stepSpacer()));
        grid.setWidget(0,START_INDEX, startStep);
        grid.setWidget(0,START_INDEX+1,new HTML(PowerCalculatorGUI.constants.stepSpacer()));
        grid.setWidget(0,STUDY_INDEX, studyStep);
        grid.setWidget(0,STUDY_INDEX+1,new HTML(PowerCalculatorGUI.constants.stepSpacer()));
        grid.setWidget(0,OPTIONS_INDEX, powerSampleSizeStep);
        grid.setWidget(0,OPTIONS_INDEX+1,new HTML(PowerCalculatorGUI.constants.stepSpacer()));
        grid.setWidget(0,RESULTS_INDEX, resultsStep);
        grid.setWidget(0,RESULTS_INDEX+1,new HTML(PowerCalculatorGUI.constants.stepSpacer()));
        
        
        startStep.setStyleName(STYLE);
        startStep.addStyleDependentName(SELECTED_STYLE);
        studyStep.setStyleName(STYLE);
        studyStep.addStyleDependentName(DESELECTED_STYLE);
        powerSampleSizeStep.setStyleName(STYLE);
        powerSampleSizeStep.addStyleDependentName(DESELECTED_STYLE);
        resultsStep.setStyleName(STYLE);
        resultsStep.addStyleDependentName(DESELECTED_STYLE);
        
        currentStep = START_INDEX;
        initWidget(grid);
    }
    
    /**
     * Call back when "next" navigation button is clicked
     * Does nothing if already at end of step list
     */
    public void onNext()
    {
        if (currentStep != RESULTS_INDEX)
        {
            setStep(currentStep+2, currentStep);
        }
    }
    
    /**
     * Call back when "previous" navigation button is clicked
     * Does nothing if already at beginning of step list
     */
    public void onPrevious()
    {
        if (currentStep != START_INDEX)
        {
            setStep(currentStep-2, currentStep);
        }

    }
    
    /**
     * Return to first step
     */
    public void onCancel()
    {
    	if (currentStep != START_INDEX)
    	{
    		setStep(START_INDEX, currentStep);
    	}
    }
    
    /**
     * Highlight the current step in the user navigation
     * 
     * Select the "step" at the new index, and unselect the step
     * at the old index by updating the dependent style sheet names
     * 
     * @param newIndex
     * @param prevIndex
     */
    protected void setStep(int newIndex, int prevIndex)
    {    	
        HTML selected = (HTML) grid.getWidget(0, newIndex);
        HTML previous = (HTML) grid.getWidget(0, prevIndex);
        
        if (selected != null) 
        {
        	selected.removeStyleDependentName(DESELECTED_STYLE);
        	selected.addStyleDependentName(SELECTED_STYLE);
        }
        if (previous != null) 
        {
        	previous.removeStyleDependentName(SELECTED_STYLE);
        	previous.addStyleDependentName(DESELECTED_STYLE);
        }
        currentStep = newIndex;
    }
}
