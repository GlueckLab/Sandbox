package edu.cudenver.bios.powercalculator.client.panels;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import edu.cudenver.bios.powercalculator.client.PowerCalculatorGUI;
import edu.cudenver.bios.powercalculator.client.listener.ModelSelectListener;

public class CreateNewStudyPanel extends Composite implements ChangeHandler
{
    protected ArrayList<ModelSelectListener> listeners = new ArrayList<ModelSelectListener>();
    protected ListBox modelList = new ListBox();
    
    public CreateNewStudyPanel()
    {
        VerticalPanel panel = new VerticalPanel();
        // add study create instructions
        panel.add(new HTML(PowerCalculatorGUI.constants.textCreateNewStudyPanelHeader()));
        panel.add(new HTML(PowerCalculatorGUI.constants.textCreateNewStudyPanelDescription()));

        // add model selection list
        HorizontalPanel modelPanel = new HorizontalPanel();
        modelPanel.add(new HTML(PowerCalculatorGUI.constants.listBoxModel()));
        modelList.addItem(PowerCalculatorGUI.constants.labelOneSampleStudentsT(), 
                PowerCalculatorGUI.constants.modelOneSampleStudentsT());
        modelList.addItem(PowerCalculatorGUI.constants.labelGLMM(), PowerCalculatorGUI.constants.modelGLMM());
        modelList.setItemSelected(1, true); // select glmm as default
        modelList.addChangeHandler(this);
        modelPanel.add(modelList);
        panel.add(modelPanel);
        initWidget(panel);
    }
    
    public void onChange(ChangeEvent e)
    {
        Widget sender = (Widget) e.getSource();
        if (sender == modelList)
        {
            String value = modelList.getValue(modelList.getSelectedIndex());
            if (value != null && !value.isEmpty())
            {
                notifyModel(value);
            }
        }
    }
    
    private void notifyModel(String modelName)
    {
        for(ModelSelectListener listener: listeners) listener.onModelSelect(modelName);
    }
    
    /**
     * Add a listener for model change events
     * @param listener 
     */
    public void addModelSelectListener(ModelSelectListener listener)
    {
        listeners.add(listener);
    }
}
