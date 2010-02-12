package edu.cudenver.bios.powercalculator.client.panels;

public interface OptionsListener
{
    public void onSolveFor(boolean solvingForPower);
    
    public void onShowCurve(boolean showCurve, CurveOptions opts);
}
