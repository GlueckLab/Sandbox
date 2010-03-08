package edu.cudenver.bios.powercalculator.client.panels;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.cudenver.bios.powercalculator.client.PowerCalculatorGUI;

public class SimplePanel extends Composite
{
    protected TextBox alpha = new TextBox();
    protected TextBox mu0 = new TextBox();
    protected TextBox muA = new TextBox();
    protected TextBox sigma = new TextBox();
    
    public SimplePanel()
    {
        // TODO: string constants!!!!
        VerticalPanel panel = new VerticalPanel();
        Grid grid = new Grid(4,2);
        grid.setWidget(0, 0, new HTML(PowerCalculatorGUI.constants.textLabelAlpha()));
        grid.setWidget(0, 1, alpha);
        grid.setWidget(1, 0, new HTML(PowerCalculatorGUI.constants.textLabelMu0()));
        grid.setWidget(1, 1, mu0);
        grid.setWidget(2, 0, new HTML(PowerCalculatorGUI.constants.textLabelMuA()));
        grid.setWidget(2, 1, muA);
        grid.setWidget(3, 0, new HTML(PowerCalculatorGUI.constants.textLabelSigma()));
        grid.setWidget(3, 1, sigma);
        panel.add(grid);
        initWidget(panel);
    }
    
    public String getAlpha()
    {
    	return alpha.getText();
    }
    
    public String getNullMean()
    {
    	return mu0.getText();
    }
    
    public String getAlternativeMean()
    {
    	return muA.getText();
    }
    
    public String getSigma()
    {
    	return sigma.getText();
    }        
}
