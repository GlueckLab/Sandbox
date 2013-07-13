package edu.cudenver.bios.powercalculator.client.listener;

public interface CovariateListener
{
    public void onHasCovariate(boolean hasCovariate);
    
    public void onMean(double mean);
    
    public void onVariance(double variance);
    
}
