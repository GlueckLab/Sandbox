package edu.cudenver.bios.powercalculator.client.panels;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;

import edu.cudenver.bios.powercalculator.client.PowerCalculatorGUI;
import edu.cudenver.bios.powercalculator.client.listener.MatrixResizeListener;
import edu.cudenver.bios.powercalculator.client.listener.MetaDataListener;

public class EssenceMatrix extends Composite implements MatrixResizeListener
{
	private static final int MAX_RATIO = 10;
    protected CheckBox covariateCheckBox = new CheckBox();
    protected Grid meanVarPanel = new Grid(2,2);
    protected TextBox meanTextBox = new TextBox();
    protected TextBox varianceTextBox = new TextBox();
    protected ResizableMatrix essence;
   	protected Grid rowMDGrid;
    protected ArrayList<MatrixResizeListener> resizeListeners = new ArrayList<MatrixResizeListener>();
    protected ArrayList<MetaDataListener> metaDataListeners = new ArrayList<MetaDataListener>();

    protected int minimumN;
    
    public EssenceMatrix(String name, int rows, int cols) 
    {
        essence = new ResizableMatrix(name, rows, cols);
        essence.addMatrixResizeListener(this);

        // start with 1:1 ratio amongst groups
        minimumN = rows;
        
        VerticalPanel panel = new VerticalPanel();
                
        // build the row meta data panel
        VerticalPanel rowMDPanel = new VerticalPanel();
        rowMDPanel.add(new HTML("Ratio of group sizes"));
        rowMDGrid = new Grid(rows, 1);
        for(int r = 0; r < rows; r++)
        {
        	rowMDGrid.setWidget(r, 0, createRowMDTextBox());
        }
        rowMDPanel.add(rowMDGrid);
        // layout the essence matrix and row meta data
        Grid essenceGrid = new Grid(1,2);
        essenceGrid.setWidget(0, 0, essence);
        essenceGrid.setWidget(0, 1, rowMDPanel);

        
        // build covariate panel
        VerticalPanel covariatePanel = new VerticalPanel();
        HorizontalPanel includeCovariatePanel = new HorizontalPanel();
        includeCovariatePanel.add(covariateCheckBox);
        includeCovariatePanel.add(new HTML("Include a baseline covariate"));
        covariateCheckBox.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent e)
            {
            	meanVarPanel.setVisible(covariateCheckBox.getValue());
            	meanTextBox.setText("");
            	varianceTextBox.setText("");
            	
            	essence.setCovariateColumn(covariateCheckBox.getValue());
            	
            	for(MetaDataListener listener: metaDataListeners) listener.onCovariate(covariateCheckBox.getValue());
            }
        });
        covariatePanel.add(includeCovariatePanel);
        
        // subpanel for mean / variance
        meanVarPanel.setWidget(0, 0, new HTML("Mean: "));
        meanVarPanel.setWidget(0, 1, meanTextBox);
        meanVarPanel.setWidget(1, 0, new HTML("Variance: "));
        meanVarPanel.setWidget(1, 1, varianceTextBox);
        meanVarPanel.setVisible(false);
        covariatePanel.add(meanVarPanel);

        // layout the overall panel
        panel.add(essenceGrid);
        panel.add(covariatePanel);
        
        
        // add style
        
        
        initWidget(panel);
    }
    
    private ListBox createRowMDTextBox()
    {
    	ListBox list = new ListBox();
    	for(int i = 1; i <= MAX_RATIO; i++)
    	{
    		list.addItem(Integer.toString(i));
    		list.addChangeHandler(new ChangeHandler() {
    			public void onChange(ChangeEvent e)
    			{
    				minimumN = calculateMinimumN();
    				for(MetaDataListener listener: metaDataListeners) listener.onMinimumSampleSize(minimumN);
    			}
    		});
    	}
    	return list;
    }
    
    public void addMatrixResizeListener(MatrixResizeListener listener)
    {
        essence.addMatrixResizeListener(listener);
        resizeListeners.add(listener);
    }
    
    public void addMetaDataListener(MetaDataListener listener)
    {
        metaDataListeners.add(listener);
        // notify of current sample size in case user does not change anything.
        listener.onMinimumSampleSize(minimumN);
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
        
        // parse the column meta data if there is any
        if (colMD != null)
        {
        	
        }
        
        // parse the row meta data if there is any
        if (rowMD != null)
        {
        	
        }
        
    }
    
    public void setRowDimension(int newRows)
    {
        essence.setRowDimension(newRows);
    }
    
    public void setColumnDimension(int newCols)
    {
        essence.setColumnDimension(newCols);
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
        buffer.append(columnMetaDataToXML());
        buffer.append(rowMetaDataToXML(totalN));
        buffer.append(essence.toXML());
        buffer.append("</essenceMatrix>");
        Window.alert(buffer.toString());
        return buffer.toString();
    }
        
    private String rowMetaDataToXML(int totalN)
    {    	
    	// totalN must be multiple of the minimum sample size
    	int nMultiplier = totalN / minimumN;
    	
        StringBuffer buffer = new StringBuffer();
        buffer.append("<rowMetaData>");
        for(int r = 0; r < rowMDGrid.getRowCount(); r++)
        {
            ListBox ratioList = (ListBox) rowMDGrid.getWidget(r, 0);
            int ratio = Integer.parseInt(ratioList.getItemText(ratioList.getSelectedIndex()));
            buffer.append("<r ratio='" + ratio + "' ");
            if (nMultiplier > 0) buffer.append(" reps='" + ratio*nMultiplier + "' ");
            buffer.append("></r>");
        }
        buffer.append("</rowMetaData>");
        return buffer.toString();
    }
    
    private String columnMetaDataToXML()
    {        
        StringBuffer buffer = new StringBuffer();
        buffer.append("<columnMetaData>");
        for(int c = 0; c < essence.getColumnDimension(); c++) 
        {
            buffer.append("<c type='fixed'></c>");            
        }
        if (covariateCheckBox.getValue())
        {
        	buffer.append("<c type='random' mean='" + meanTextBox.getText() + "' variance='" + 
        			varianceTextBox.getText()+ "' ></c>");
        }
        buffer.append("</columnMetaData>");
        return buffer.toString();
    }
    
    public void onRows(int newRows)
    {
    	int currentRows = rowMDGrid.getRowCount();
    	if (newRows != currentRows)
    	{
        	rowMDGrid.resizeRows(newRows);
        	for(int r = currentRows; r < newRows; r++)
        	{
        		rowMDGrid.setWidget(r, 0, createRowMDTextBox());
        	}
        	
        	// calculate the minimum sample size based on ratio of group sizes
        	minimumN = 0;
        	for(int r = 0; r < rowMDGrid.getRowCount(); r++) 
        	{
        	    ListBox tb = (ListBox) rowMDGrid.getWidget(r, 0);
        	    minimumN += Integer.parseInt(tb.getItemText(tb.getSelectedIndex()));
        	}
        	for(MetaDataListener listener: metaDataListeners) listener.onMinimumSampleSize(minimumN);
    	}
    }
    
    public void onColumns(int newCols) {};
    
    public void onMatrixResize(int newRows, int newCols)
    {
    	int currentRows = rowMDGrid.getRowCount();
    	if (newRows != currentRows)
    	{
        	rowMDGrid.resizeRows(newRows);
        	for(int r = currentRows; r < newRows; r++)
        	{
        		rowMDGrid.setWidget(r, 0, createRowMDTextBox());
        	}
    	}
    }
    
    private int calculateMinimumN()
    {
        int minN = 0;
        for(int r = 0; r < rowMDGrid.getRowCount(); r++)
        {
            ListBox ratioList = (ListBox) rowMDGrid.getWidget(r, 0);
            int ratio = Integer.parseInt(ratioList.getItemText(ratioList.getSelectedIndex()));
            minN += ratio;
        }
        return minN;
    }
}
