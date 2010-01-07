package edu.cudenver.bios.powercalculator.client.panels;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
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

	private static final String INPUT_RADIO_GOUP = "inputRadioGroup";
	private static final String TEST_GLMM = "glmm";
	private static final String TEST_ONESAMPLESTUDENTST = "onesamplestudentt";
	
	protected ArrayList<StartListener> listeners = new ArrayList<StartListener>();
	protected VerticalPanel inputSelectPanel = new VerticalPanel();
	protected RadioButton basicRb = new RadioButton(INPUT_RADIO_GOUP, PowerCalculatorGUI.constants.basicInputRadioButton());
	protected RadioButton matrixRb = new RadioButton(INPUT_RADIO_GOUP, PowerCalculatorGUI.constants.matrixInputRadioButton());
	protected RadioButton uploadRb = new RadioButton(INPUT_RADIO_GOUP, PowerCalculatorGUI.constants.uploadInputRadioButton());
	protected ListBox modelList = new ListBox();
    
	public StartPanel()
	{
		VerticalPanel panel = new VerticalPanel();
		// add introductory text
		panel.add(new HTML(PowerCalculatorGUI.constants.startPanelDescriptionText()));
		
		// add model selection list
		HorizontalPanel modelPanel = new HorizontalPanel();
		modelPanel.add(new HTML(PowerCalculatorGUI.constants.startPanelModelText()));
		modelList.addItem(PowerCalculatorGUI.constants.oneSampleStudentsT(), "onesamplestudentt");
		modelList.addItem(PowerCalculatorGUI.constants.glmm(), "glmm");
		modelList.setItemSelected(1, true); // select glmm as default
		modelList.addChangeHandler(this);
		modelPanel.add(modelList);
		panel.add(modelPanel);
	    
		// build input selection panel - only visible if GLMM is selected as model
	    inputSelectPanel.add(new HTML(PowerCalculatorGUI.constants.startPanelStudyInputText()));
		inputSelectPanel.add(basicRb);
		inputSelectPanel.add(matrixRb);
		inputSelectPanel.add(uploadRb);
		panel.add(inputSelectPanel);
		
		// add radio button callbacks for input type selection
	    basicRb.addClickHandler(this);
	    matrixRb.addClickHandler(this);
	    uploadRb.addClickHandler(this);
	    basicRb.setValue(true);
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
		            if (basicRb.getValue())
		            	notifyInputType(InputType.BASIC);
		            else if (uploadRb.getValue())
		            	notifyInputType(InputType.UPLOAD);
		            else if (matrixRb.getValue())
		            	notifyInputType(InputType.UPLOAD);
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
