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

    public SimplePanel()
    {
        // TODO: string constants!!!!
        VerticalPanel panel = new VerticalPanel();
        Grid grid = new Grid(4,2);
        grid.setWidget(0, 0, new HTML("Alpha (Type I error): "));
        grid.setWidget(0, 1, alpha);
        grid.setWidget(1, 0, new HTML("Estimated mean in control group (&mu;<sub>0</sub>): "));
        grid.setWidget(1, 1, muA);
        grid.setWidget(2, 0, new HTML("Estimated mean in comparison group (&mu;<sub>A</sub>): "));
        grid.setWidget(2, 1, mu0);
        grid.setWidget(3, 0, new HTML("Estimated variance of outcome measure: "));
        grid.setWidget(3, 1, sigma);
        panel.add(grid);
        initWidget(panel);
    }
    
    public String getStudyXML()
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append("<params alpha='" + alpha.getValue() + 
                "' sigma='" + sigma.getValue() + "' mu0='" + mu0.getValue() + 
                "' muA='" + muA.getValue() + "' />");
        return buffer.toString();
    }
}
