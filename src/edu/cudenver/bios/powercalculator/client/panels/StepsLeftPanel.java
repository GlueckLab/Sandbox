package edu.cudenver.bios.powercalculator.client.panels;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import edu.cudenver.bios.powercalculator.client.PowerCalculatorGUI;
import edu.cudenver.bios.powercalculator.client.listener.NavigationListener;

public class StepsLeftPanel extends Composite implements NavigationListener
{
	protected static final String STYLE = "stepsLeftLabel";
	protected static final String PANEL_STYLE = "stepsLeftPanel";
	protected static final String SELECTED_STYLE = "selected";
    protected static final String DESELECTED_STYLE = "deselected";
    
    protected HTML startStep = new HTML(PowerCalculatorGUI.constants.stepStart());
    protected HTML studyStep = new HTML(PowerCalculatorGUI.constants.stepStudy());
    protected HTML optionsStep = new HTML(PowerCalculatorGUI.constants.stepOptions());
    protected HTML resultsStep = new HTML(PowerCalculatorGUI.constants.stepResults());
    
    private Widget currentStep = startStep;
    
    public StepsLeftPanel()
    {        
        HorizontalPanel panel = new HorizontalPanel();
        panel.add(startStep);
        panel.add(studyStep);
        panel.add(optionsStep);
        panel.add(resultsStep);
        
        // add style
        panel.setStyleName(PANEL_STYLE);
        startStep.setStyleName(STYLE);
        startStep.addStyleDependentName(SELECTED_STYLE);
        studyStep.setStyleName(STYLE);
        studyStep.addStyleDependentName(DESELECTED_STYLE);
        optionsStep.setStyleName(STYLE);
        optionsStep.addStyleDependentName(DESELECTED_STYLE);
        resultsStep.setStyleName(STYLE);
        resultsStep.addStyleDependentName(DESELECTED_STYLE);
        
        currentStep = startStep;
        initWidget(panel);
    }
    
    /**
     * Call back when "next" navigation button is clicked
     * Does nothing if already at end of step list
     */
    public void onNext()
    {
        if (currentStep == startStep)
            setStep(startStep, studyStep);
        else if (currentStep == studyStep)
            setStep(studyStep, optionsStep);
        else if (currentStep == optionsStep)
            setStep(optionsStep, resultsStep);
    }
    
    /**
     * Call back when "previous" navigation button is clicked
     * Does nothing if already at beginning of step list
     */
    public void onPrevious()
    {
        if (currentStep == studyStep)
            setStep(studyStep, startStep);
        else if (currentStep == optionsStep)
            setStep(optionsStep, studyStep);
        else if (currentStep == resultsStep)
            setStep(resultsStep, optionsStep);

    }
    
    /**
     * Return to first step
     */
    public void onCancel()
    {
    	if (currentStep == studyStep)
    	    setStep(studyStep, startStep);
    	else if (currentStep == optionsStep)
            setStep(optionsStep, startStep);
    	else if (currentStep == resultsStep)
            setStep(resultsStep, startStep);

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
    protected void setStep(Widget oldStep,Widget newStep)
    {    	
        currentStep = newStep;

        // deselect the old widgets
        oldStep.removeStyleDependentName(SELECTED_STYLE);
        oldStep.addStyleDependentName(DESELECTED_STYLE);
        
        // select the new widgets
        newStep.removeStyleDependentName(DESELECTED_STYLE);
        newStep.addStyleDependentName(SELECTED_STYLE);
    }
}
