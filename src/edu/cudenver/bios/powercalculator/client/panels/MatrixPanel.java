package edu.cudenver.bios.powercalculator.client.panels;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class MatrixPanel extends Composite
{
	private static final int DEFAULT_COLS = 3;
	private static final int DEFAULT_ROWS = 3;
	
	
	public MatrixPanel()
	{
		Button foo = new Button("Matrix Entry");
		VerticalPanel panel = new VerticalPanel();

		addMatrixEntry(panel, "Beta", DEFAULT_COLS, DEFAULT_ROWS);
		panel.add(new ResizableMatrix("Essence Design", DEFAULT_COLS, DEFAULT_ROWS));
		panel.add(new ResizableMatrix("Beta", DEFAULT_COLS, DEFAULT_ROWS));
		initWidget(panel);
	}
	
	private void addMatrixEntry(VerticalPanel panel, String name, int rows, int cols)
	{

	}
}
