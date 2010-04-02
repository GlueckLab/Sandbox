package edu.cudenver.bios.powercalculator.client.listener;

public interface StudyDesignChangeListener
{
    public void onAlpha(String alpha);
    
    public void onDesign(int row, int col, String value);
    
    public void onDesignRowMetaData(int row, String name);
        
    public void onDesignColumnMetaData(int col, boolean isRandom, String mean, String variance);
    
    public void onBeta(int row, int col, String value);
    
    public void onTheta(int row, int col, String value);
    
    public void onBetweenSubjectContrast(int row, int col, String value);

    public void onWithinSubjectContrast(int row, int col, String value);

    public void onSigmaError(int row, int col, String value);   
    
    public void onSigmaOutcome(int row, int col, String value);
    
    public void onSigmaCovariateOutcome(int row, int col, String value);
    
    public void onSigmaCovariate(int row, int col, String value);
}
