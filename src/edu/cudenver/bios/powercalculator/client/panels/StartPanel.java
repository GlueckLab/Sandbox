package edu.cudenver.bios.powercalculator.client.panels;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import edu.cudenver.bios.powercalculator.client.PowerCalculatorGUI;

/**
 * Welcome / start panel in the input wizard for the power calculator 
 * site.  Allows the user to select the method for study design input
 * and the statistical model
 * 
 * @author Owner
 *
 */
public class StartPanel extends Composite implements ClickHandler, ChangeHandler
{
	public enum InputType {
		BASIC,
		MATRIX,
		UPLOAD
	};
	
	protected ArrayList<StartListener> listeners = new ArrayList<StartListener>();
	protected VerticalPanel panel = new VerticalPanel();
	RadioButton basicRb = new RadioButton("myRadioGroup", PowerCalculatorGUI.constants.basicInputRadioButton());
    RadioButton matrixRb = new RadioButton("myRadioGroup", PowerCalculatorGUI.constants.matrixInputRadioButton());
    RadioButton uploadRb = new RadioButton("myRadioGroup", PowerCalculatorGUI.constants.uploadInputRadioButton());
    ListBox modelList = new ListBox();
    
	public StartPanel()
	{
		panel.setStyleName("inputPanel");
		panel.add(new Label(PowerCalculatorGUI.constants.startPanelWelcomeText()));
		panel.add(new Label(PowerCalculatorGUI.constants.startPanelDescriptionText()));
		panel.add(new Label(PowerCalculatorGUI.constants.startPanelInstructionsText()));
		
		panel.add(new Label(PowerCalculatorGUI.constants.startPanelModelText()));
		modelList.addItem(PowerCalculatorGUI.constants.oneSampleStudentsT(), "onesamplestudentt");
		modelList.addItem(PowerCalculatorGUI.constants.glmm(), "glmm");
		panel.add(modelList);
		panel.add(new Label(PowerCalculatorGUI.constants.startPanelStudyInputText()));
		
	    basicRb.addClickHandler(this);
	    matrixRb.addClickHandler(this);
	    uploadRb.addClickHandler(this);
	    
		panel.add(basicRb);
		panel.add(matrixRb);
		panel.add(uploadRb);
		initWidget(panel);
	}
	
	public void addListener(StartListener listener)
	{
		listeners.add(listener);
	}
	
	public void onClick(ClickEvent e)
	{
		Widget sender = (Widget) e.getSource();
		if (sender == basicRb)
		{
			notifyInputType(InputType.BASIC);
		}
		else if (sender == matrixRb)
		{
			notifyInputType(InputType.MATRIX);
		}
		else if (sender == uploadRb)
		{
			notifyInputType(InputType.UPLOAD);
		}
	}
	
	public void onChange(ChangeEvent e)
	{
		Widget sender = (Widget) e.getSource();
		if (sender == modelList)
		{
			notifyModel(modelList.getValue(modelList.getSelectedIndex()));
		}
	}
	
	private void notifyInputType(InputType type)
	{
		for(StartListener listener: listeners) listener.onInputTypeSelect(type);
	}
	
	private void notifyModel(String modelName)
	{
		for(StartListener listener: listeners) listener.onModelSelect(modelName);
	}
}
