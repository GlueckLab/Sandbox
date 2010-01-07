package edu.cudenver.bios.powercalculator.client.panels;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SimplePanel extends Composite
{
    protected TextBox alpha = new TextBox();
    protected TextBox mu0 = new TextBox();
    protected TextBox muA = new TextBox();
    protected TextBox sigma = new TextBox();
    protected TextBox sampleSize = new TextBox();
    protected TextBox power = new TextBox();
    
    public SimplePanel()
    {
        // TODO: string constants!!!!
        VerticalPanel panel = new VerticalPanel();
        Grid grid = new Grid(5,2);
        grid.setWidget(0, 0, new HTML("Alpha (Type I error): "));
        grid.setWidget(0, 1, alpha);
        grid.setWidget(1, 0, new HTML("Estimated mean in control group (&mu;<sub>0</sub>): "));
        grid.setWidget(1, 1, muA);
        grid.setWidget(2, 0, new HTML("Estimated mean in comparison group (&mu;<sub>A</sub>): "));
        grid.setWidget(2, 1, mu0);
        grid.setWidget(3, 0, new HTML("Estimated variance of outcome measure: "));
        grid.setWidget(3, 1, sigma);
        grid.setWidget(4, 0, new HTML("Sample Size: "));
        grid.setWidget(4, 1, sampleSize);
        panel.add(grid);
        initWidget(panel);
    }
    
    public String getStudyXML(boolean forPower)
    {
        StringBuffer buffer = new StringBuffer();
        if (forPower)
        	buffer.append("<power>");
        else
        	buffer.append("<sampleSize>");
        
        buffer.append("<params alpha='" + alpha.getValue() + 
                "' sigma='" + sigma.getValue() + "' mu0='" + mu0.getValue() + 
                "' muA='" + muA.getValue());
        if (forPower)
        	buffer.append("' sampleSize='" + sampleSize.getValue() + "' /></power>");
        else
        	buffer.append(" power='" + power.getValue() + "' /></sampleSize>");

        return buffer.toString();
    }
        
}
