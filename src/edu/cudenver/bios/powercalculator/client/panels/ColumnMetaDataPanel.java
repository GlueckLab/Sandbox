package edu.cudenver.bios.powercalculator.client.panels;

import java.util.ArrayList;

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

import edu.cudenver.bios.powercalculator.client.listener.MetaDataListener;

public class ColumnMetaDataPanel extends Composite implements MetaDataListener
{
    private static final String STYLE = "columnMetaData";
    private static final String CELL_STYLE = "columnMetaDataCell";   
    // single row of column meta data entry objects
    private Grid metaData = null;    
    
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
        // notify matrix panel that a random
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
    
    public void addListener(MetaDataListener listener)
    {
        for(int c = 0; c < metaData.getColumnCount(); c++) 
        {
            ColumnMetaDataEntry colMD = (ColumnMetaDataEntry) metaData.getWidget(0, c);
            colMD.addListener(listener);
        }
    }
    
    public void onRowName(int row, String name)
    {
    	
    }
}

