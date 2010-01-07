package edu.cudenver.bios.powercalculator.client.panels;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;

import edu.cudenver.bios.powercalculator.client.PowerCalculatorGUI;

public class NavigationPanel extends Composite
{
    ArrayList<NavigationListener> listeners = new ArrayList<NavigationListener>();
    protected Button next;
    protected Button previous;
    protected Button cancel;
    
    public NavigationPanel()
    {
        HorizontalPanel panel = new HorizontalPanel();
       
        next = new Button(PowerCalculatorGUI.constants.nextButton(), new ClickHandler() {
            public void onClick(ClickEvent event) {
                notifyOnNext();
            }
        });

        previous = new Button(PowerCalculatorGUI.constants.previousButton(), new ClickHandler() {
            public void onClick(ClickEvent event) {
                notifyOnPrevious();
            }
        });
        
        cancel = new Button(PowerCalculatorGUI.constants.cancelButton(), new ClickHandler() {
            public void onClick(ClickEvent event) {
                notifyOnCancel();
            }
        });
        
        panel.add(previous);
        panel.add(next);
        panel.add(cancel);
        panel.setStyleName("wizardNavigationPanel");
                
        initWidget(panel);
    }
    
    protected void notifyOnNext()
    {
        for(NavigationListener listener: listeners)
            listener.onNext();
    }
    
    protected void notifyOnPrevious()
    {
        for(NavigationListener listener: listeners)
            listener.onPrevious();
    }
    
    protected void notifyOnCancel()
    {
        for(NavigationListener listener: listeners)
            listener.onCancel();
    }
    
    public void setNext(boolean enabled)
    {
        next.setEnabled(enabled);
    }
    
    public void setPrevious(boolean enabled)
    {
        previous.setEnabled(enabled);
    }
    
    public void setCancel(boolean enabled)
    {
        cancel.setEnabled(enabled);
    }
    
    public void addNavigationListener(NavigationListener listener)
    {
        listeners.add(listener);
    }
}
