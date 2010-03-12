package edu.cudenver.bios.powercalculator.client.panels;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.cudenver.bios.powercalculator.client.PowerCalculatorGUI;
import edu.cudenver.bios.powercalculator.client.listener.MatrixResizeListener;
import edu.cudenver.bios.powercalculator.client.listener.MetaDataListener;
import edu.cudenver.bios.powercalculator.client.listener.OptionsListener;

public class TwoGroupDetailsPanel extends Composite
implements OptionsListener
{
    private static final String STYLE = "optionsPanel";

    // simple power / sample size options (for basic models such as T-test)
    protected HorizontalPanel sampleSizeContainer;
    protected TextBox sampleSizeTextBox = new TextBox();
    protected HorizontalPanel powerContainer;
    protected TextBox powerTextBox = new TextBox();
    
    public TwoGroupDetailsPanel(boolean solveForPower)
    {
        VerticalPanel panel = new VerticalPanel();

        sampleSizeContainer = new HorizontalPanel();
        sampleSizeContainer.add(new HTML(PowerCalculatorGUI.constants.textLabelSampleSize()));
        sampleSizeContainer.add(sampleSizeTextBox);

        powerContainer = new HorizontalPanel();
        powerContainer.add(new HTML(PowerCalculatorGUI.constants.textLabelPower()));
        powerContainer.add(powerTextBox);

        powerContainer.setStyleName(STYLE);
        sampleSizeContainer.setStyleName(STYLE);
        panel.add(sampleSizeContainer);
        panel.add(powerContainer);

        powerContainer.setVisible(!solveForPower);
        sampleSizeContainer.setVisible(solveForPower);
        
        initWidget(panel);
    }
    
    public String getPower()
    {
        return powerTextBox.getText();
    }
    
    public String getSampleSize()
    {
        return sampleSizeTextBox.getText();
    }
    
    public void onSolveFor(boolean forPower)
    {
        powerContainer.setVisible(!forPower);
        sampleSizeContainer.setVisible(forPower);
    }
    
    public void onShowCurve(boolean showCurve, CurveOptions opts) {};
}
