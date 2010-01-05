package edu.cudenver.bios.powercalculator.client.panels;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
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

	private static final String TEST_GLMM = "glmm";
	private static final String TEST_ONESAMPLESTUDENTST = "onesamplestudentt";
	
	protected ArrayList<StartListener> listeners = new ArrayList<StartListener>();
	protected VerticalPanel panel = new VerticalPanel();
	protected VerticalPanel inputSelectPanel = new VerticalPanel();
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
		modelList.setItemSelected(1, true); // select glmm as default
		modelList.addChangeHandler(this);
		panel.add(modelList);
		
	    basicRb.addClickHandler(this);
	    matrixRb.addClickHandler(this);
	    uploadRb.addClickHandler(this);
	    
	    inputSelectPanel.add(new Label(PowerCalculatorGUI.constants.startPanelStudyInputText()));
		inputSelectPanel.add(basicRb);
		inputSelectPanel.add(matrixRb);
		inputSelectPanel.add(uploadRb);
		panel.add(inputSelectPanel);
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
		    String value = modelList.getValue(modelList.getSelectedIndex());
		    if (value != null && !value.isEmpty())
		    {
		        if (TEST_GLMM.equals(value))
		        {
		            inputSelectPanel.setVisible(true);
		        }
		        else
		        {
		            inputSelectPanel.setVisible(false);
		        }
		    }
			notifyModel(value);
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
