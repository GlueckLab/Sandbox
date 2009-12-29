package edu.cudenver.bios.powercalculator.client.panels;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;

public class StepsLeftPanel extends Composite implements NavigationListener
{
    protected static final String SELECTED_STYLE = "stepsLeftSelected";
    protected static final String DESELECTED_STYLE = "stepsLeftDeselected";
    protected static final int MAX_STEP = 4;
    
    int currentStep = 0;
    
    Grid grid = new Grid(1,4);
    HTML startStep = new HTML("Start &rarr;");
    HTML studyStep = new HTML("Input Study Design &rarr;");
    HTML powerSampleSizeStep = 
        new HTML("Set Power &amp; Sample Size Options &rarr;");
    HTML resultsStep = new HTML("View Results");
    
    public StepsLeftPanel()
    {
        grid.setWidget(0,0, startStep);
        grid.setWidget(0,1, studyStep);
        grid.setWidget(0,2, powerSampleSizeStep);
        grid.setWidget(0,3, resultsStep);
        
        startStep.setStyleName(SELECTED_STYLE);
        studyStep.setStyleName(DESELECTED_STYLE);
        powerSampleSizeStep.setStyleName(DESELECTED_STYLE);
        resultsStep.setStyleName(DESELECTED_STYLE);
        
        initWidget(grid);
    }
    
    
    public void onNext()
    {
        currentStep++;
        if (currentStep >= MAX_STEP)
            setStep(0, currentStep - 1);
        else
            setStep(currentStep, currentStep - 1);
    }
    
    public void onPrevious()
    {
        
    }
    
    public void onCancel()
    {
        
    }
    
    protected void setStep(int newIndex, int prevIndex)
    {
        HTML selected = (HTML) grid.getWidget(0, newIndex);
        HTML previous = (HTML) grid.getWidget(0, prevIndex);
        
        if (selected != null) selected.setStyleName("stepsLeftSelected");
        if (previous != null) previous.setStyleName("stepsLeftNonSelected");
    }
}
