package edu.cudenver.bios.powercalculator.client.listener;

import com.google.gwt.user.client.ui.Widget;

public interface InputWizardStepListener
{
    public void onStepInProgress(int stepIndex);
    
    public void onStepComplete(int stepIndex);
}
