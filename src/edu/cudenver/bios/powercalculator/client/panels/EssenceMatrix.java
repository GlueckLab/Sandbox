package edu.cudenver.bios.powercalculator.client.panels;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;

import edu.cudenver.bios.powercalculator.client.listener.MatrixResizeListener;
import edu.cudenver.bios.powercalculator.client.listener.MetaDataListener;

public class EssenceMatrix extends Composite
{
    protected CheckBox covariateCheckBox = new CheckBox();
    protected TextBox meanTextBox = new TextBox();
    protected TextBox varianceTextBox = new TextBox();
    protected ResizableMatrix essence;
    
    protected ArrayList<MatrixResizeListener> resizeListeners = new ArrayList<MatrixResizeListener>();
    protected ArrayList<MetaDataListener> metaDataListeners = new ArrayList<MetaDataListener>();

    public EssenceMatrix(String name, int rows, int cols) 
    {
        essence = new ResizableMatrix(name, rows, cols);
        
        VerticalPanel panel = new VerticalPanel();
        
        // build grid
        Grid grid = new Grid(1,2);
        grid.setWidget(0, 1, essence);
        // build covariate panel
        HorizontalPanel covariatePanel = new HorizontalPanel();
        covariatePanel.add(new HTML("Include a baseline covariate?"));
        covariatePanel.add(covariateCheckBox);
        covariateCheckBox.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent e)
            {
                meanTextBox.setEnabled(covariateCheckBox.getValue());
                varianceTextBox.setEnabled(covariateCheckBox.getValue());                    
            }
        });
        panel.add(grid);
        
        initWidget(panel);
    }
    
    public void addMatrixResizeListener(MatrixResizeListener listener)
    {
        essence.addMatrixResizeListener(listener);
        resizeListeners.add(listener);
    }
    
    public void addMetaDataListener(MetaDataListener listener)
    {
        metaDataListeners.add(listener);
    }
    
    public void loadFromDomNode(Node node)
    {
        NodeList children = node.getChildNodes();
        Node rowMD = null;
        Node colMD = null;
        // locate the row/column meta data
        for(int i = 0; i < children.getLength(); i++)
        {
            Node child = children.item(i);
            String name = child.getNodeName();
            if (name.equals("matrix"))
                essence.loadFromDomNode(child);
            else if (name.equals("rowMetaData"))
                rowMD = child;
            else if (name.equals("columnMetaData"))
                colMD = child;
        }
        
        // if we found meta data, fill in the details        
    }
    
    public void setDimensions(int newRows, int newCols)
    {
        essence.setDimensions(newRows, newCols);
    }

    public int getRowDimension()
    {
        return essence.getRowDimension();
    }
    
    public int getColumnDimension()
    {
        return essence.getColumnDimension();
    }
    
    public String toXML(int totalN)
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append("<essenceMatrix>");
        //        buffer.append(essence.columnMetaDataToXML());
        //        buffer.append(rowMetaData);
        //        buffer.append(essence.matrixDataToXML());
        buffer.append("</essenceMatrix>");
        return buffer.toString();
    }
    
    public String columnMetaDataToXML()
    {
//        StringBuffer buffer = new StringBuffer();
//        buffer.append("<columnMetaData>");
//        for(int c = 1; c < matrixData.getColumnCount(); c++)
//        {
//            ColumnMetaDataEntry colMD = (ColumnMetaDataEntry) matrixData.getWidget(0, c);
//            buffer.append(colMD.toXML());
//        }
//        buffer.append("</columnMetaData>");
//        return buffer.toString();
        return "";
    }
}
