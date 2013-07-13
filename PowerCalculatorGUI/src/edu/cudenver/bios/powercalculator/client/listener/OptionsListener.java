package edu.cudenver.bios.powercalculator.client.listener;

import edu.cudenver.bios.powercalculator.client.panels.CurveOptions;

public interface OptionsListener
{
    public void onSolveFor(boolean solvingForPower);
    
    public void onShowCurve(boolean showCurve, CurveOptions opts);
       
}
