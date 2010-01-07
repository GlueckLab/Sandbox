package edu.cudenver.bios.powercalculator.client.panels;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.cudenver.bios.powercalculator.client.PowerCalculatorGUI;

public class ResizableMatrix extends Composite implements ChangeHandler
{
	private static final String DIMENSION_STYLE = "matrixDimension";
	private static final String MATRIX_STYLE = "matrix";
	private static final String MATRIX_CELL_STYLE = "matrixCell";
	private static final String HEADER_STYLE = "matrixHeader";
	private static final String DEFAULT_VALUE = "0";
	private static final int MAX_ROWS = 50;
	private static final int MAX_COLS = 50;
	private Grid matrixData;
	private TextBox rowTextBox;
	private TextBox columnTextBox;
	
	public ResizableMatrix(String name, int rows, int cols, boolean hasMetaData)
	{
		VerticalPanel matrixPanel = new VerticalPanel();
		HorizontalPanel matrixHeader = new HorizontalPanel();
		matrixHeader.setStyleName(HEADER_STYLE);

		matrixHeader .add(new HTML(name));
		matrixHeader.add(new Button("(details)"));
		rowTextBox = new TextBox();
		rowTextBox.addChangeHandler(this);
		rowTextBox.setValue(Integer.toString(rows), false);
		rowTextBox.setStyleName(DIMENSION_STYLE);
		columnTextBox = new TextBox();
		columnTextBox.addChangeHandler(this);
		columnTextBox.setValue(Integer.toString(cols), false);
		columnTextBox.setStyleName(DIMENSION_STYLE);
		matrixHeader.add(rowTextBox);
		matrixHeader.add(new HTML(PowerCalculatorGUI.constants.matrixDimensionSeparator()));
		matrixHeader.add(columnTextBox);
		
		if (hasMetaData)
		{
			// TODO: add row/column meta data entry panels
		}
		matrixData = new Grid(rows, cols);
		matrixData.setStyleName(MATRIX_STYLE);
		initMatrixData();
		matrixPanel.add(matrixHeader);
		matrixPanel.add(matrixData);
		initWidget(matrixPanel);
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
			newCols = Integer.parseInt(columnTextBox.getValue());
		}
		catch (Exception e)
		{
			Window.alert("Row and column dimensions must be integer values");
		}
		if (newRows > 0 && newRows <= MAX_ROWS && newCols > 0 && newCols <= MAX_COLS)
		{
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
	}
	
	private void initMatrixData()
	{
		int rows = matrixData.getRowCount();
		int cols = matrixData.getColumnCount();
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
}
