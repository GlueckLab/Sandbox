package edu.cudenver.bios.powercalculator.client.panels;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckPanel;

public class InputWizardPanel extends Composite implements NavigationListener
{
    protected static final int MAX_INDEX = 5;
    DeckPanel panel = new DeckPanel();
    
    public InputWizardPanel()
    {
        panel.add(new Label("Step 1"));
        panel.add(new Label("Step 2"));
        panel.add(new Label("Step 3"));
        panel.add(new Label("Step 4"));
        panel.add(new Label("Step 5"));
        panel.add(new Label("Step 6"));

        initWidget(panel);
    }
    
    public void onPrevious()
    {
        int visibleIndex = panel.getVisibleWidget();
        visibleIndex--;
        if (visibleIndex < 0)
            panel.showWidget(5);
        else
            panel.showWidget(visibleIndex);
    }
    
    public void onNext()
    {
        int visibleIndex = panel.getVisibleWidget();
        visibleIndex++;
        if (visibleIndex >= MAX_INDEX)
            panel.showWidget(0);
        else
            panel.showWidget(visibleIndex);
    }
    
    public void onCancel()
    {
        panel.showWidget(0);
    }
}
