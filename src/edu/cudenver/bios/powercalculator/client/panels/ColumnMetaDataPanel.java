package edu.cudenver.bios.powercalculator.client.panels;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ColumnMetaDataPanel extends Composite
{
    private static final String STYLE = "columnMetaData";
    private static final String CELL_STYLE = "columnMetaDataCell";   
    // single row of column meta data entry objects
    private Grid metaData = null;    
    
    private class ColumnMetaDataEntry extends Composite
    {
        private PopupPanel entryPanel = new PopupPanel();
        RadioButton fixedRb = new RadioButton("ColumnMetaData", "Fixed");
        RadioButton randomRb = new RadioButton("ColumnMetaData", "Random");
        HTML meanLabel = new HTML("Mean");
        TextBox meanTextBox = new TextBox();
        HTML varianceLabel = new HTML("Variance");
        TextBox varianceTextBox = new TextBox();
        HTML label = new HTML("Fixed");
        HTML errorMsg = new HTML(null);
        
        private ColumnMetaDataPanel parent;
        private int column;
        
        public ColumnMetaDataEntry(int columnIndex, ColumnMetaDataPanel parentPanel)
        {
            this.column = columnIndex;
            this.parent = parentPanel;
            
            // build the popup panel for selecting fixed/random
            VerticalPanel container = new VerticalPanel();
            container.add(fixedRb);
            fixedRb.setValue(true);
            setMeanVarianceEnabled(false);
            container.add(randomRb);
            Grid meanVarPanel = new Grid(2,2);
            
            meanVarPanel.setWidget(0,0,meanLabel);
            meanVarPanel.setWidget(0,1,meanTextBox);
            meanVarPanel.setWidget(1,0,varianceLabel);
            meanVarPanel.setWidget(1,1,varianceTextBox);
            container.add(meanVarPanel);
            container.add(errorMsg);
            container.add(new Button("Done", new ClickHandler() {
                public void onClick(ClickEvent e)
                {
                    // validate
                    if (fixedRb.getValue())
                    {
                        label.setText("Fixed");
                        parent.onFixed(column);
                        entryPanel.hide();
                    }
                    else
                    {
                        try
                        {
                            double mean = Double.parseDouble(meanTextBox.getValue());
                            double variance = Double.parseDouble(varianceTextBox.getValue());
                            label.setText("Random");
                            parent.onRandom(column, mean, variance);
                            entryPanel.hide();
                        }
                        catch (Exception exp)
                        {
                            errorMsg.setText("Please specify a numeric mean and variance");
                        }
                    }
                }
            }));
            entryPanel.add(container);
            label.addClickHandler(new ClickHandler() {
                public void onClick(ClickEvent e)
                {
                    entryPanel.showRelativeTo(label);
                }
            });
            fixedRb.addClickHandler(new ClickHandler() {
                public void onClick(ClickEvent e)
                {
                    setMeanVarianceEnabled(false);
                }
            });
            randomRb.addClickHandler(new ClickHandler() {
                public void onClick(ClickEvent e)
                {
                    setMeanVarianceEnabled(true);
                }
            });
            label.setStyleName(CELL_STYLE);
            initWidget(label);
        }
        
        private void setMeanVarianceEnabled(boolean enabled)
        {
            meanTextBox.setEnabled(enabled);
            if (!enabled) meanTextBox.setText(null);
            varianceTextBox.setEnabled(enabled);
            if (!enabled) varianceTextBox.setText(null);
            
        }
        
        public void setFixed()
        {
            randomRb.setValue(false);
            fixedRb.setValue(true);
            label.setText("Fixed");
            setMeanVarianceEnabled(false);
        }
        
        public boolean isFixed()
        {
            return fixedRb.getValue();
        }
        
        public String getMean()
        {
            return meanTextBox.getValue();
        }
        
        public String getVariance()
        {
            return varianceTextBox.getValue();
        }
    }
    
    public ColumnMetaDataPanel(int cols)
    {
        metaData = new Grid(1, cols);
        for(int c = 0; c < cols; c++)
        {
            metaData.setWidget(0, c, new ColumnMetaDataEntry(c, this));
        }
        
        //  set style
        metaData.setStyleName(STYLE);
        
        initWidget(metaData);
    }
    
    public void resize(int newCols)
    {
        Window.alert("Got cols: " + newCols);
        int oldCols = metaData.getColumnCount();
        metaData.resize(1, newCols);
        for(int c = oldCols; c < newCols; c++)
        {
            metaData.setWidget(0, c, new ColumnMetaDataEntry(c, this));
        }        
    }
    
    public void onFixed(int col)
    {
        // nothing to do here, but just in case we define the callback
    }
    
    public void onRandom(int col, double mean, double variance)
    {
        // reset the other 
        for(int c = 0; c < metaData.getColumnCount(); c++)
        {
            if (c != col)
            {
                ColumnMetaDataEntry colMD = (ColumnMetaDataEntry) metaData.getWidget(0, c);
                colMD.setFixed();
            }
        }     
    }
    
    public String toXML()
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append("<columnMetaData>");
        for(int c = 0; c < metaData.getColumnCount(); c++) 
        {
            ColumnMetaDataEntry colMD = (ColumnMetaDataEntry) metaData.getWidget(0, c);
            if (!colMD.isFixed())
                buffer.append("<c type='random' mean='" + colMD.getMean() + 
                        "' variance='" + colMD.getVariance() + "' />");
            else
                buffer.append("<c type='fixed' />");
        }
        buffer.append("</columnMetaData>");
        return buffer.toString();
    }
}

