package edu.cudenver.bios.powercalculator.client.panels;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;

public class NavigationPanel extends Composite
{
    ArrayList<NavigationListener> listeners = new ArrayList<NavigationListener>();
    DockPanel panel = new DockPanel();

    public NavigationPanel()
    {
        
        Button next = new Button("Next", new ClickHandler() {
            public void onClick(ClickEvent event) {
                notifyOnNext();
            }
        });

        Button previous = new Button("Previous", new ClickHandler() {
            public void onClick(ClickEvent event) {
                notifyOnPrevious();
            }
        });
        
        panel.add(next, DockPanel.EAST);
        panel.add(previous, DockPanel.WEST);
        
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
    public void addNavigationListener(NavigationListener listener)
    {
        listeners.add(listener);
    }
}
