package edu.cudenver.bios.powercalculator.client.panels;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Grid;

import edu.cudenver.bios.powercalculator.client.PowerCalculatorGUI;

public class NavigationPanel extends Composite
{
    ArrayList<NavigationListener> listeners = new ArrayList<NavigationListener>();

    public NavigationPanel()
    {
        Grid grid = new Grid(1,2);
       
        Button next = new Button(PowerCalculatorGUI.constants.nextButton(), new ClickHandler() {
            public void onClick(ClickEvent event) {
                notifyOnNext();
            }
        });

        Button previous = new Button(PowerCalculatorGUI.constants.previousButton(), new ClickHandler() {
            public void onClick(ClickEvent event) {
                notifyOnPrevious();
            }
        });
        
        grid.setWidget(0, 0, previous);
        grid.setWidget(0, 1, next);
        grid.setStyleName("wizardNavigationPanel");
        initWidget(grid);
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
    public void addNavigationListener(NavigationListener listener)
    {
        listeners.add(listener);
    }
}
