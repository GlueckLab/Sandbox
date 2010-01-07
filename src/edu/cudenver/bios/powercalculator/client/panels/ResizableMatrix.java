package edu.cudenver.bios.powercalculator.client.panels;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.cudenver.bios.powercalculator.client.PowerCalculatorGUI;

public class ResizableMatrix extends Composite implements ChangeHandler
{
	protected static final String DIMENSION_STYLE = "matrixDimension";
	protected static final String MATRIX_STYLE = "matrix";
	protected static final String MATRIX_CELL_STYLE = "matrixCell";
	protected static final String HEADER_STYLE = "matrixHeader";
	protected static final String DEFAULT_VALUE = "0";
	protected static final int MAX_ROWS = 50;
	protected static final int MAX_COLS = 50;
	protected Grid matrixData;
	protected TextBox rowTextBox;
	protected TextBox columnTextBox;
	protected HTML nameHTML;
	protected boolean isSquare;
	protected boolean isSymmetric;
	protected ArrayList<ResizableMatrixListener> listeners = new ArrayList<ResizableMatrixListener>();
	
	public ResizableMatrix(String name, String details, int rows, int cols, boolean hasMetaData)
	{
		// overall layout panel
		VerticalPanel matrixPanel = new VerticalPanel();

		// pop-up with matrix description
		final PopupPanel detailsPanel = new PopupPanel();
		detailsPanel.add(new HTML(details));
		
		// matrix name
		nameHTML = new HTML(name);
		nameHTML.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent e)
			{
				detailsPanel.showRelativeTo(nameHTML);
			}
		});
		nameHTML.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent e)
			{
				detailsPanel.hide();
			}
		});
		// matrix dimensions
		rowTextBox = new TextBox();
		rowTextBox.addChangeHandler(this);
		rowTextBox.setValue(Integer.toString(rows), false);
		columnTextBox = new TextBox();
		columnTextBox.addChangeHandler(this);
		columnTextBox.setValue(Integer.toString(cols), false);
		
		// add name, dimensions to the matrix header
		HorizontalPanel matrixHeader = new HorizontalPanel();
		matrixHeader.add(nameHTML);
		matrixHeader.add(rowTextBox);
		matrixHeader.add(new HTML(PowerCalculatorGUI.constants.matrixDimensionSeparator()));
		matrixHeader.add(columnTextBox);
		
		// build matrix itself
		if (hasMetaData)
		{
			// TODO: add row/column meta data entry panels
		}
		matrixData = new Grid(rows, cols);
		// initialize cells to 0
		initMatrixData();
		
		// add the widgets to the vertical panel
		matrixPanel.add(nameHTML);
		matrixPanel.add(matrixHeader);
		matrixPanel.add(matrixData);
		
		// set up styles
		matrixHeader.setStyleName(HEADER_STYLE);
		matrixData.setStyleName(MATRIX_STYLE);
		rowTextBox.setStyleName(DIMENSION_STYLE);
		columnTextBox.setStyleName(DIMENSION_STYLE);
		
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
			Window.alert(PowerCalculatorGUI.constants.matrixDimensionInvalidMessage());
		}
	}
	
	public void setDimensions(int newRows, int newCols)
	{
		resizeMatrix(newRows, newCols);
	}
	
	private boolean resizeMatrix(int newRows, int newCols)
	{		
		if (newRows > 0 && newRows <= MAX_ROWS && newCols > 0 && newCols <= MAX_COLS)
		{	
			if (isSquare && newRows != newCols) return false;
			
			int oldRows = matrixData.getRowCount();
			int oldCols = matrixData.getColumnCount();
			if (newCols != oldCols || newRows != oldRows) 
			{
				rowTextBox.setText(Integer.toString(newRows));
				columnTextBox.setText(Integer.toString(newCols));
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
		int rows = matrixData.getRowCount();
		for(int r = 0; r < rows; r++)
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
	
	public String toXML()
	{
		StringBuffer buffer = new StringBuffer();
		
		buffer.append("<matrix rows=" + matrixData.getRowCount() + " columns='" + matrixData.getColumnCount() + "'>");
		for(int r = 0; r < matrixData.getRowCount(); r++)
		{
			buffer.append("<r>");
			for(int c = 0; c < matrixData.getColumnCount(); c++)
			{
				TextBox txt = (TextBox) matrixData.getWidget(r, c);
				buffer.append("<c>" + txt.getValue() + "</c>");
			}
			buffer.append("</r>");
		}
		buffer.append("</matrix>");
		return buffer.toString();
	}
	
	private void notifyOnMatrixResize(int rows, int cols)
	{
		for(ResizableMatrixListener listener: listeners) listener.onMatrixResize(rows, cols);
	}
	
	public void addListener(ResizableMatrixListener listener)
	{
		listeners.add(listener);
	}
}
