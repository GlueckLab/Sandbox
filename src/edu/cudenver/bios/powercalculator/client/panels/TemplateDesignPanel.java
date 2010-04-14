package edu.cudenver.bios.powercalculator.client.panels;

import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.xml.client.Document;

import edu.cudenver.bios.powercalculator.client.listener.InputWizardStepListener;

public class TemplateDesignPanel extends Composite
{
    protected ListBox numOutcomesListbox = new ListBox();
    protected ListBox numPredictorsListbox = new ListBox();
    protected CheckBox covariateCheckBox = new CheckBox();
    
    public TemplateDesignPanel(InputWizardStepListener w, int idx)
    {
        VerticalPanel panel = new VerticalPanel();
        
        // 
        
        // 
        Grid grid = new Grid(3,2);
        grid.setWidget(0, 0, new HTML("How many outcomes do you have? <br>(Or how many repeated measurements did you take?)"));
        grid.setWidget(0, 1, numOutcomesListbox);
        grid.setWidget(1, 0, new HTML("Do you have a baseline covariate?"));
        grid.setWidget(1, 1, covariateCheckBox);
        grid.setWidget(2, 0, new HTML("How many predictors do you have? (not including the covariate)"));
        grid.setWidget(2, 1, numPredictorsListbox);
        
        // hypothesis panel
        
        
        
        panel.add(grid);
        panel.add(createHypothesisPanel());
        
        initWidget(panel);
    }
    
    public VerticalPanel createHypothesisPanel()
    {
        VerticalPanel panel = new VerticalPanel();
        Grid grid = new Grid(4,2);
        //grid.setWidget(0, 0, );
        
        panel.add(new HTML("Please select the hypotheses you would like to test"));
        panel.add(grid);
        
        return panel;
    }
    
    public void loadFromXMLDocument(Document doc)
    {
        
    }
    
    public String getStudyXML(int totalN)
    {
        return "<study></study>";
    }
    
    public String getStudyAttributes()
    {
        return "";
    }
}
