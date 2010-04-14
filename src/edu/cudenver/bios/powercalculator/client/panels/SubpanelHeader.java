package edu.cudenver.bios.powercalculator.client.panels;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.cudenver.bios.powercalculator.client.PowerCalculatorConstants;

public class SubpanelHeader extends Composite
{
    protected PopupPanel detailsPanel = null;
    protected HTML detailsHTML;
    protected HTML headerHTML;
    
    public SubpanelHeader(String title, String details)
    {
        VerticalPanel panel = new VerticalPanel();
        
        // create the header text
        headerHTML = new HTML(title);

        // create the popup text
        if (details != null && !details.isEmpty())
        {
            detailsHTML = new HTML(details);
            detailsHTML.addClickHandler(new ClickHandler() {
                public void onClick(ClickEvent e)
                {
                    detailsPanel.hide();
                }
            });
            detailsPanel = new PopupPanel();
            detailsPanel.add(detailsHTML);
            
            headerHTML.addClickHandler(new ClickHandler() {
                public void onClick(ClickEvent e)
                {
                    detailsPanel.showRelativeTo(headerHTML);
                }
            });
        }
        panel.add(headerHTML);
        
        headerHTML.setStyleName(PowerCalculatorConstants.STYLE_WIZARD_STEP_HEADER);
        headerHTML.addStyleDependentName(PowerCalculatorConstants.STYLE_WIZARD_STEP_SUBPANEL);
        panel.setStyleName(PowerCalculatorConstants.STYLE_WIZARD_STEP_PANEL);
        panel.addStyleDependentName(PowerCalculatorConstants.STYLE_WIZARD_STEP_SUBPANEL);        

        initWidget(panel);
    }
}
