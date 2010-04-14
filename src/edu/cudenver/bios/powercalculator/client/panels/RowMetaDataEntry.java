package edu.cudenver.bios.powercalculator.client.panels;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;

import edu.cudenver.bios.powercalculator.client.listener.MetaDataListener;

public class RowMetaDataEntry extends Composite
{
	private static final String STYLE = "rowMetaData";
	
	protected TextBox rowMD = new TextBox();
	protected ArrayList<MetaDataListener> listeners = new ArrayList<MetaDataListener>();
	protected int row;
	
	public RowMetaDataEntry(int rowIndex, MetaDataListener listener)
	{
		row = rowIndex;
		listeners.add(listener);

		rowMD.setValue("1");
		rowMD.setStyleName(STYLE);
		rowMD.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent e)
			{
			     
				//for(MetaDataListener listener: listeners) listener.onRowName(row, rowMD.getValue());
			}
		});
		
		initWidget(rowMD);
	}
	
	public void addListener(MetaDataListener listener) 
	{
		listeners.add(listener);
	}
	
	public String getRowName()
	{
	    return rowMD.getText();
	}
}
