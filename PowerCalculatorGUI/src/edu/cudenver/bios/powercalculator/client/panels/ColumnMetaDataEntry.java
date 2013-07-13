package edu.cudenver.bios.powercalculator.client.panels;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.cudenver.bios.powercalculator.client.listener.MetaDataListener;

public class ColumnMetaDataEntry extends Composite
{
    private static final String STYLE = "columnMetaData";
    private static final String POPUP_STYLE = "columnMetaDataPopup";
    
	private PopupPanel entryPanel = new PopupPanel();
	RadioButton fixedRb = new RadioButton("ColumnMetaData", "Fixed");
	RadioButton randomRb = new RadioButton("ColumnMetaData", "Random");
	HTML meanLabel = new HTML("Mean");
	TextBox meanTextBox = new TextBox();
	HTML varianceLabel = new HTML("Variance");
	TextBox varianceTextBox = new TextBox();
	HTML label = new HTML("Fixed");
	HTML errorMsg = new HTML(null);

	private ArrayList<MetaDataListener> listeners = new ArrayList<MetaDataListener>();;
	private int column;

	public ColumnMetaDataEntry(int columnIndex, MetaDataListener listener)
	{
//		this.column = columnIndex;
//		listeners.add(listener);
//		// build the popup panel for selecting fixed/random
//		VerticalPanel container = new VerticalPanel();
//		container.add(fixedRb);
//		fixedRb.setValue(true);
//		setMeanVarianceEnabled(false);
//		container.add(randomRb);
//		Grid meanVarPanel = new Grid(2,2);
//
//		meanVarPanel.setWidget(0,0,meanLabel);
//		meanVarPanel.setWidget(0,1,meanTextBox);
//		meanVarPanel.setWidget(1,0,varianceLabel);
//		meanVarPanel.setWidget(1,1,varianceTextBox);
//		container.add(meanVarPanel);
//		container.add(errorMsg);
//		container.add(new Button("Done", new ClickHandler() {
//			public void onClick(ClickEvent e)
//			{
//				// validate
//				if (fixedRb.getValue())
//				{
//					label.setText("Fixed");
//					for(MetaDataListener listener: listeners) listener.onFixed(column);
//					entryPanel.hide();
//				}
//				else
//				{
//					try
//					{
//						double mean = Double.parseDouble(meanTextBox.getValue());
//						double variance = Double.parseDouble(varianceTextBox.getValue());
//						label.setText("Random");
//						for(MetaDataListener listener: listeners) listener.onRandom(column, mean, variance);
//						entryPanel.hide();
//					}
//					catch (Exception exp)
//					{
//						errorMsg.setText("Please specify a numeric mean and variance");
//					}
//				}
//			}
//		}));
//		entryPanel.add(container);
//		label.addClickHandler(new ClickHandler() {
//			public void onClick(ClickEvent e)
//			{
//				entryPanel.showRelativeTo(label);
//			}
//		});
//		fixedRb.addClickHandler(new ClickHandler() {
//			public void onClick(ClickEvent e)
//			{
//				setMeanVarianceEnabled(false);
//			}
//		});
//		randomRb.addClickHandler(new ClickHandler() {
//			public void onClick(ClickEvent e)
//			{
//				setMeanVarianceEnabled(true);
//			}
//		});
//		label.setStyleName(STYLE);
//		entryPanel.setStyleName(POPUP_STYLE);
		initWidget(label);
	}

	public void addListener(MetaDataListener listener) 
	{
		listeners.add(listener);
	}
	
	private void setMeanVarianceEnabled(boolean enabled)
	{
		meanTextBox.setEnabled(enabled);
		if (!enabled) meanTextBox.setText(null);
		varianceTextBox.setEnabled(enabled);
		if (!enabled) varianceTextBox.setText(null);

	}

	public void setFixed()
	{
		randomRb.setValue(false);
		fixedRb.setValue(true);
		label.setText("Fixed");
		setMeanVarianceEnabled(false);
	}

	public boolean isFixed()
	{
		return fixedRb.getValue();
	}

	public String getMean()
	{
		return meanTextBox.getValue();
	}

	public String getVariance()
	{
		return varianceTextBox.getValue();
	}
	
	public String toXML()
	{
		if (fixedRb.getValue())
			return "<c type='fixed'></c>";
		else
			return "<c type='random' mean='" + getMean() + "' variance='" + getVariance() + "'></c>";
	}
}
