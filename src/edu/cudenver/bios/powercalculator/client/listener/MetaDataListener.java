package edu.cudenver.bios.powercalculator.client.listener;

public interface MetaDataListener
{
    public void onRandom(int col, double mean, double variance);
    
    public void onFixed(int col);
    
    public void onRowName(int row, String name);
}
