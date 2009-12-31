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
    protected static final int MAX_STEPS = 4;
    
    int currentStep = 0;
    
    Grid grid = new Grid(1,MAX_STEPS);
    HTML startStep = new HTML(PowerCalculatorGUI.constants.startStep());
    HTML studyStep = new HTML(PowerCalculatorGUI.constants.studyStep());
    HTML powerSampleSizeStep = 
        new HTML(PowerCalculatorGUI.constants.optionsStep());
    HTML resultsStep = new HTML(PowerCalculatorGUI.constants.resultsStep());
    
    public StepsLeftPanel()
    {
        grid.setWidget(0,0, startStep);
        grid.setWidget(0,1, studyStep);
        grid.setWidget(0,2, powerSampleSizeStep);
        grid.setWidget(0,3, resultsStep);
        
        startStep.setStyleName(STYLE);
        startStep.addStyleDependentName(SELECTED_STYLE);
        studyStep.setStyleName(STYLE);
        studyStep.addStyleDependentName(DESELECTED_STYLE);
        powerSampleSizeStep.setStyleName(STYLE);
        powerSampleSizeStep.addStyleDependentName(DESELECTED_STYLE);
        resultsStep.setStyleName(STYLE);
        resultsStep.addStyleDependentName(DESELECTED_STYLE);
        
        initWidget(grid);
    }
    
    /**
     * Call back when "next" navigation button is clicked
     * Does nothing if already at end of step list
     */
    public void onNext()
    {
        currentStep++;
        if (currentStep < MAX_STEPS)
        {
            setStep(currentStep, currentStep - 1);
        }
    }
    
    /**
     * Call back when "previous" navigation button is clicked
     * Does nothing if already at beginning of step list
     */
    public void onPrevious()
    {
        currentStep--;
        if (currentStep >= 0)
        {
            setStep(currentStep, currentStep + 1);
        }

    }
    
    /**
     * Return to first step
     */
    public void onCancel()
    {
    	if (currentStep > 0)
    	{
    		setStep(0, currentStep);
    		currentStep = 0;
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
    	if (newIndex < 0 || newIndex > MAX_STEPS || prevIndex < 0 || prevIndex > MAX_STEPS) return;
    	
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
    }
}
