package edu.cudenver.bios.powercalculator.client.listener;

public interface MetaDataListener
{    
    public void onCovariate(boolean hasCovariate);
    
    public void onMinimumSampleSize(int minimumN);
}
