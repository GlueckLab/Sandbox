package edu.cudenver.bios.powercalculator.client.panels;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.cudenver.bios.powercalculator.client.PowerCalculatorGUI;
import edu.cudenver.bios.powercalculator.client.listener.MatrixResizeListener;
import edu.cudenver.bios.powercalculator.client.listener.MetaDataListener;
import edu.cudenver.bios.powercalculator.client.listener.OptionsListener;

public class LinearModelDetailsPanel extends Composite 
implements OptionsListener, MatrixResizeListener, MetaDataListener
{
    private static final String STYLE = "optionsPanel";
    
    private static final String DEFAULT_REPS = "2";
    private static final String DEFAULT_RATIO = "1";
    private static final int DEFAULT_N = 3;

    private static final int REPS_COLUMN = 1;
    private static final int RATIO_COLUMN = 2;
    
    protected HorizontalPanel powerContainer;
    protected TextBox powerTextBox = new TextBox();
    protected boolean solveForPower;
    
  // row meta data parameters for GLMM, +1 for table header
    protected Grid rowMetaData = new Grid(DEFAULT_N+1, 3);
    
    public LinearModelDetailsPanel(boolean solveForPower)
    {
        this.solveForPower = solveForPower;
        VerticalPanel panel = new VerticalPanel();

        // add a desired power text box - only show if "solve for" is set to sample size
        powerContainer = new HorizontalPanel();
        powerContainer.add(new HTML(PowerCalculatorGUI.constants.textLabelPower()));
        powerContainer.add(powerTextBox);
        powerContainer.setVisible(!solveForPower);
        
        // initialize the row meta data
        rowMetaData.setWidget(0, 0, new HTML("Group"));
        rowMetaData.setWidget(0, 1, new HTML("#Subjects (n)"));
        rowMetaData.setWidget(0, 2, new HTML("Ratio of Group Sizes"));
        for(int r = 1; r < rowMetaData.getRowCount(); r++) initRow(r);
        
        panel.add(powerContainer);
        panel.add(rowMetaData);
        
        initWidget(panel);
    }
        
    private void initRow(int row) 
    {
        HTML id = new HTML(Integer.toString(row));
        rowMetaData.setWidget(row, 0, id);
        
        TextBox reps = new TextBox();
        reps.setText(DEFAULT_REPS);             
        rowMetaData.setWidget(row, 1, reps);
        
        TextBox ratio = new TextBox();
        ratio.setText(DEFAULT_RATIO);               
        rowMetaData.setWidget(row, 2, ratio);
    }
    
    public String getRowMetaDataXML()
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append("<rowMetaData>");
        for(int r = 1; r < rowMetaData.getRowCount(); r++)
        {
            TextBox reps = (TextBox) rowMetaData.getWidget(r, REPS_COLUMN);
            buffer.append("<r ");
            TextBox ratio = (TextBox) rowMetaData.getWidget(r, RATIO_COLUMN);
            buffer.append(" reps='" + reps.getValue() + "' ratio='" + ratio.getText() + "'");
            buffer.append("/>");
        }
        buffer.append("</rowMetaData>");
        return buffer.toString();
    }
    
    public String getPower()
    {
        return powerTextBox.getText();
    }
    
    public void onSolveFor(boolean forPower)
    {
        powerContainer.setVisible(!forPower);
    }
    
    public void onShowCurve(boolean showCurve, CurveOptions opts) {}
    
    public void onMatrixResize(int rows, int cols)
    {
        int oldRows = rowMetaData.getRowCount()-1;
        rowMetaData.resize(rows+1, rowMetaData.getColumnCount()); //+1 for table header
        if (rows > oldRows) 
        {
            // initialize the new rows
            for(int r = oldRows; r < rows+1; r++) initRow(r);
        }
    }
    
    public void onRowName(int row, String name)
    {
        HTML label = (HTML) rowMetaData.getWidget(row, 0);
        label.setText(name);
    }
    
    public void onFixed(int col) {}
    
    public void onRandom(int col, double mean, double variance) {}
}
