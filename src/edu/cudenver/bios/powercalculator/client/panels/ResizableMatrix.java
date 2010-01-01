package edu.cudenver.bios.powercalculator.client.panels;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;

public class ResizableMatrix extends Composite implements ChangeHandler
{
	private static final int MAX_ROWS = 50;
	private static final int MAX_COLS = 50;
	private Grid matrixData;
	private TextBox rowTextBox;
	private TextBox columnTextBox;
	
	public ResizableMatrix(String name, int rows, int cols)
	{
		HorizontalPanel matrixHeader = new HorizontalPanel();

		matrixHeader .add(new Label(name + ": "));
		rowTextBox = new TextBox();
		rowTextBox.addChangeHandler(this);
		rowTextBox.setValue(Integer.toString(rows), false);
		columnTextBox = new TextBox();
		columnTextBox.addChangeHandler(this);
		columnTextBox.setValue(Integer.toString(cols), false);

		matrixHeader.add(rowTextBox);
		matrixHeader.add(columnTextBox);
		
		matrixData = new Grid(rows, cols);
		matrixData.setBorderWidth(1);
		matrixHeader.add(matrixData);
		initWidget(matrixHeader);
	}
	
	public void onChange(ChangeEvent event)
	{
		int rows = matrixData.getRowCount();
		int cols = matrixData.getColumnCount();
		try
		{
			rows = Integer.parseInt(rowTextBox.getValue());
			cols = Integer.parseInt(columnTextBox.getValue());
		}
		catch (Exception e)
		{
			
		}
		if (rows > 0 && rows <= MAX_ROWS && cols > 0 && cols <= MAX_COLS)
			matrixData.resize(rows, cols);
	}
}
