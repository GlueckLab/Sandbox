package edu.cudenver.bios.powercalculator.client.panels;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.xml.client.NamedNodeMap;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;

import edu.cudenver.bios.powercalculator.client.PowerCalculatorGUI;
import edu.cudenver.bios.powercalculator.client.listener.MatrixResizeListener;
import edu.cudenver.bios.powercalculator.client.listener.MetaDataListener;

public class ResizableMatrix extends Composite 
implements ChangeHandler
{
    protected static final String RANDOM = "random";
    protected static final String MATRIX_STYLE = "matrix";
	protected static final String DIMENSION_STYLE = "matrixDimensions";
	protected static final String MATRIX_DATA_STYLE = "matrixData";
	protected static final String MATRIX_CELL_STYLE = "matrixCell";
	protected static final String DEFAULT_VALUE = "0";
	protected static final int MAX_ROWS = 50;
	protected static final int MAX_COLS = 50;
	protected Grid matrixData;
	protected TextBox rowTextBox;
	protected TextBox columnTextBox;
	protected boolean isSquare;
	protected boolean isSymmetric;
	protected String name;
	protected ArrayList<MatrixResizeListener> resizeListeners = new ArrayList<MatrixResizeListener>();
	protected ArrayList<MetaDataListener> metaDataListeners = new ArrayList<MetaDataListener>();
	
	public ResizableMatrix(String name, int rows, int cols) 
	{
	    this.name = name;
		// overall layout panel    
	    VerticalPanel matrixPanel = new VerticalPanel();

		// matrix dimensions
		rowTextBox = new TextBox();
		rowTextBox.addChangeHandler(this);
		rowTextBox.setValue(Integer.toString(rows), false);
		columnTextBox = new TextBox();
		columnTextBox.addChangeHandler(this);
		columnTextBox.setValue(Integer.toString(cols), false);
		
		// layout the matrix dimensions
		HorizontalPanel matrixDimensions = new HorizontalPanel();
		matrixDimensions.add(rowTextBox);
		matrixDimensions.add(new HTML(PowerCalculatorGUI.constants.matrixDimensionSeparator()));
		matrixDimensions.add(columnTextBox);
	    
		// build matrix itself
		matrixData = new Grid(rows, cols);
	    
		// initialize cells to 0
		initMatrixData();
		
		// add the widgets to the vertical panel
		matrixPanel.add(matrixDimensions);
		matrixPanel.add(matrixData);
		
		// set up styles
		matrixPanel.setStyleName(MATRIX_STYLE);
		matrixDimensions.setStyleName(DIMENSION_STYLE);
		matrixData.setStyleName(MATRIX_DATA_STYLE);
		
		// initialize the widget
		initWidget(matrixPanel);
	}
	
	public void setIsSquare(boolean isSquare)
	{
		this.isSquare = isSquare;
		if (isSquare)
		{
			int rows = Integer.parseInt(rowTextBox.getValue());
			int cols = Integer.parseInt(columnTextBox.getValue());
			if (rows != cols)
			{
				columnTextBox.setText(rowTextBox.getValue());
			}
		}
		columnTextBox.setEnabled(!isSquare);
	}
	
	public void setIsSymmetric(boolean isSymmetric)
	{
		this.isSymmetric = isSymmetric;

	}
	
	public void onChange(ChangeEvent event)
	{
		int oldRows = matrixData.getRowCount();
		int oldCols = matrixData.getColumnCount();
		int newRows = 0;
		int newCols = 0;
		try
		{
			newRows = Integer.parseInt(rowTextBox.getValue());
			if (isSquare) columnTextBox.setText(rowTextBox.getValue());
			newCols = Integer.parseInt(columnTextBox.getValue());
			
			if (!resizeMatrix(newRows, newCols)) throw new IllegalArgumentException("failed to set dimension");
			notifyOnMatrixResize(newRows, newCols);
		}
		catch (Exception e)
		{ 
			rowTextBox.setText(Integer.toString(oldRows));
			columnTextBox.setText(Integer.toString(oldCols));
			Window.alert(PowerCalculatorGUI.constants.errorMatrixDimensionInvalid());
		}
	}
	
	public void setDimensions(int newRows, int newCols)
	{
		resizeMatrix(newRows, newCols);
	}
	
	private boolean resizeMatrix(int rows, int cols)
	{		
		int newRows = rows;
		int newCols = cols;
		int maxRows = MAX_ROWS;
		int maxCols = MAX_COLS;
		if (newRows > 0 && newRows <= maxRows && newCols > 0 && newCols <= maxCols)
		{	
			if (isSquare && newRows != newCols) return false;
			
			int oldRows = matrixData.getRowCount();
			int oldCols = matrixData.getColumnCount();
			if (newCols != oldCols || newRows != oldRows) 
			{
				rowTextBox.setText(Integer.toString(rows));
				columnTextBox.setText(Integer.toString(cols));
				matrixData.resize(newRows, newCols);
				if (newCols != oldCols)
				{
					for(int c = oldCols; c < newCols; c++) initColumn(c);
				}
				if (newRows != oldRows)
				{
					for(int r = oldRows; r < newRows; r++) initRow(r);
				}
			}
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public int getRowDimension()
	{
		return matrixData.getRowCount();
	}
	
	public int getColumnDimension()
	{
		return matrixData.getColumnCount();
	}
	
	private void initMatrixData()
	{
		for(int r = 0; r < matrixData.getRowCount(); r++)
		{
			initRow(r);
		}
	}
	
	private void initRow(int row)
	{
	    for(int c = 0; c < matrixData.getColumnCount(); c++)
	    {
	        TextBox textBox = new TextBox();
	        textBox.setValue(DEFAULT_VALUE);
	        textBox.setStyleName(MATRIX_CELL_STYLE);
	        matrixData.setWidget(row, c, textBox);
	    }
	}
	
	private void initColumn(int col)
	{
	    for(int r = 0; r < matrixData.getRowCount(); r++)
	    {
	        TextBox textBox = new TextBox();
	        textBox.setValue(DEFAULT_VALUE);
	        textBox.setStyleName(MATRIX_CELL_STYLE);
	        matrixData.setWidget(r, col, textBox);
	    }
	}
	
	public String matrixDataToXML()
	{
		StringBuffer buffer = new StringBuffer();
		
		int start = 0;
		int rows = matrixData.getRowCount();
		int cols = matrixData.getColumnCount();

		buffer.append("<matrix name='" + name + "' rows='" +  rows + "' columns='" + 
		        cols + "'>");

		for(int r = start; r < matrixData.getRowCount(); r++)
		{
			buffer.append("<r>");
			for(int c = start; c < matrixData.getColumnCount(); c++)
			{
				TextBox txt = (TextBox) matrixData.getWidget(r, c);
				buffer.append("<c>" + txt.getValue() + "</c>");
			}
			buffer.append("</r>");
		}
		buffer.append("</matrix>");

		return buffer.toString();
	}
	
	public void setResizable(boolean allowResize)
	{
		rowTextBox.setEnabled(allowResize);
		columnTextBox.setEnabled(allowResize);
	}
	
	private void notifyOnMatrixResize(int rows, int cols)
	{
		for(MatrixResizeListener listener: resizeListeners) listener.onMatrixResize(rows, cols);
	}
		
	public void addMatrixResizeListener(MatrixResizeListener listener)
	{
		resizeListeners.add(listener);
	}
    
    public void setData(int row, int col, String value)
    {
        if (row < matrixData.getRowCount() && col < matrixData.getColumnCount())
        {
            TextBox tb = (TextBox) matrixData.getWidget(row, col);
            tb.setText(value);
        }
    }
    
    public void loadFromDomNode(Node matrixNode)
    {
        NamedNodeMap attrs = matrixNode.getAttributes();
        Node rowNode = attrs.getNamedItem("rows");
        Node colNode = attrs.getNamedItem("columns");
        if (rowNode != null && colNode != null)
        {
            int rows = Integer.parseInt(rowNode.getNodeValue());
            int cols = Integer.parseInt(colNode.getNodeValue());
            setDimensions(rows, cols);
            
            NodeList rowNodeList = matrixNode.getChildNodes();
            for(int r = 0; r < rowNodeList.getLength(); r++)
            {
                NodeList colNodeList = rowNodeList.item(r).getChildNodes();
                for(int c = 0; c < colNodeList.getLength(); c++)
                {
                    Node colItem = colNodeList.item(c).getFirstChild();
                    if (colItem != null) 
                    {
                        setData(r, c, colItem.getNodeValue());
                    }
                }
            }
        }
    }
}
