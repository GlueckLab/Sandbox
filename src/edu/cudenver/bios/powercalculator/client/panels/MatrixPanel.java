package edu.cudenver.bios.powercalculator.client.panels;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

public class MatrixPanel extends Composite
{
	public MatrixPanel()
	{
		Button foo = new Button("Matrix Entry");
		VerticalPanel panel = new VerticalPanel();

		panel.add(foo);
		initWidget(panel);
	}
}
