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
    protected static final String START_STEP_STYLE = "stepsLeftStart";    
    protected static final String STUDY_STEP_STYLE = "stepsLeftStudy";    
    protected static final String OPTIONS_STEP_STYLE = "stepsLeftOptions";    
    protected static final String RESULTS_STEP_STYLE = "stepsLeftResults";    
    
    protected HTML startStep = new HTML(PowerCalculatorGUI.constants.stepStart());
    protected Label startNumber = new Label();
    protected HTML studyStep = new HTML(PowerCalculatorGUI.constants.stepStudy());
    protected Label studyNumber = new Label();
    protected HTML optionsStep = 
        new HTML(PowerCalculatorGUI.constants.stepOptions());
    protected Label optionsNumber = new Label();
    protected HTML resultsStep = new HTML(PowerCalculatorGUI.constants.stepResults());
    protected Label resultsNumber = new Label();
    
    private Widget currentStep = startStep;
    
    public StepsLeftPanel()
    {        
        HorizontalPanel panel = new HorizontalPanel();
        panel.add(startNumber);
        panel.add(startStep);
        panel.add(new HTML(PowerCalculatorGUI.constants.stepSpacer()));
        panel.add(studyNumber);
        panel.add(studyStep);
        panel.add(new HTML(PowerCalculatorGUI.constants.stepSpacer()));
        panel.add(optionsNumber);
        panel.add(optionsStep);
        panel.add(new HTML(PowerCalculatorGUI.constants.stepSpacer()));
        panel.add(resultsNumber);
        panel.add(resultsStep);
        panel.setStyleName(PANEL_STYLE);
        
        startNumber.setStyleName(START_STEP_STYLE);
        startNumber.addStyleDependentName(SELECTED_STYLE);
        startStep.setStyleName(STYLE);
        startStep.addStyleDependentName(SELECTED_STYLE);
        studyNumber.setStyleName(STUDY_STEP_STYLE);
        studyNumber.addStyleDependentName(DESELECTED_STYLE);
        studyStep.setStyleName(STYLE);
        studyStep.addStyleDependentName(DESELECTED_STYLE);
        optionsNumber.setStyleName(OPTIONS_STEP_STYLE);
        optionsNumber.addStyleDependentName(DESELECTED_STYLE);
        optionsStep.setStyleName(STYLE);
        optionsStep.addStyleDependentName(DESELECTED_STYLE);
        resultsNumber.setStyleName(RESULTS_STEP_STYLE);
        resultsNumber.addStyleDependentName(DESELECTED_STYLE);
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
            setStep(startNumber, startStep, studyNumber, studyStep);
        else if (currentStep == studyStep)
            setStep(studyNumber, studyStep, optionsNumber, optionsStep);
        else if (currentStep == optionsStep)
            setStep(optionsNumber, optionsStep, resultsNumber, resultsStep);
    }
    
    /**
     * Call back when "previous" navigation button is clicked
     * Does nothing if already at beginning of step list
     */
    public void onPrevious()
    {
        if (currentStep == studyStep)
            setStep(studyNumber, studyStep, startNumber, startStep);
        else if (currentStep == optionsStep)
            setStep(optionsNumber, optionsStep, studyNumber, studyStep);
        else if (currentStep == resultsStep)
            setStep(resultsNumber, resultsStep, optionsNumber, optionsStep);

    }
    
    /**
     * Return to first step
     */
    public void onCancel()
    {
    	if (currentStep == studyStep)
    	    setStep(studyNumber, studyStep, startNumber, startStep);
    	else if (currentStep == optionsStep)
            setStep(optionsNumber, optionsStep, startNumber, startStep);
    	else if (currentStep == resultsStep)
            setStep(resultsNumber, resultsStep, startNumber, startStep);

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
    protected void setStep(Widget oldNumber, Widget oldStep,
            Widget newNumber, Widget newStep)
    {    	
        currentStep = newStep;

        // deselect the old widgets
        oldNumber.removeStyleDependentName(SELECTED_STYLE);
        oldNumber.addStyleDependentName(DESELECTED_STYLE);
        oldStep.removeStyleDependentName(SELECTED_STYLE);
        oldStep.addStyleDependentName(DESELECTED_STYLE);
        
        // select the new widgets
        newNumber.removeStyleDependentName(DESELECTED_STYLE);
        newNumber.addStyleDependentName(SELECTED_STYLE);
        newStep.removeStyleDependentName(DESELECTED_STYLE);
        newStep.addStyleDependentName(SELECTED_STYLE);
    }
}
