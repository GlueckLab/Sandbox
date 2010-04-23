package edu.cudenver.bios.powercalculator.client.panels;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.xml.client.Document;

import edu.cudenver.bios.powercalculator.client.listener.InputWizardStepListener;

public class TemplateDesignPanel extends Composite
{
    private static final int MAX_OUTCOMES = 50;
    private static final String OUTCOME_RADIO_GROUP = "outcomeRb";
    // sub panel headers
    protected SubpanelHeader outcomesHeader = new SubpanelHeader("Dependent Variables / Outcomes", 
            "stuff you measued");
    protected SubpanelHeader predictorsHeader = new SubpanelHeader("Independent Variables / Predictors", 
    "things that predict the stuff you measured");
    protected SubpanelHeader hypothesesHeader = new SubpanelHeader("Comparisons / Hypotheses", 
    "stuff you want to know");
    
    protected ListBox numOutcomesListbox = new ListBox();
    protected ListBox numRepeatedListBox = new ListBox();
    protected ListBox numPredictorsListbox = new ListBox();
    protected CheckBox covariateCheckBox = new CheckBox();
    
    protected TextBox predictorInputTextBox = new TextBox();
    
    protected FlexTable predictorTable = new FlexTable();
    
    public TemplateDesignPanel(InputWizardStepListener w, int idx)
    {
        VerticalPanel panel = new VerticalPanel();
        
        // 
        panel.add(createOutcomesPanel());
        panel.add(createPredictorsPanel());
        panel.add(createHypothesisPanel());
        
        
        // hypothesis panel
        
        
        
        panel.add(createHypothesisPanel());
        
        initWidget(panel);
    }
    
    public VerticalPanel createOutcomesPanel()
    {
        VerticalPanel panel = new VerticalPanel();
        panel.add(outcomesHeader);

        Grid grid = new Grid(2,2);
        grid.setWidget(0,0,new HTML("How many outcomes did you measure?"));
        grid.setWidget(0,1,numOutcomesListbox);
        for(int i = 1; i <= MAX_OUTCOMES; i++)
        {
            numOutcomesListbox.addItem(Integer.toString(i));
        }
        grid.setWidget(1, 0, new HTML("How many times was this set of measurements repeated for each subject?"));
        grid.setWidget(1, 1, numRepeatedListBox);
        for(int i = 1; i <= MAX_OUTCOMES; i++)
        {
            numRepeatedListBox.addItem(Integer.toString(i));
        }
        
        panel.add(grid);
        return panel;
    }
    
    public VerticalPanel createPredictorsPanel()
    {
        VerticalPanel panel = new VerticalPanel();

        final ListBox numCategories = new ListBox();
        for(int i = 1; i <= 10; i++) numCategories.addItem(Integer.toString(i));
        
        HorizontalPanel addBar = new HorizontalPanel();
        addBar.add(new HTML("Predictor Name: "));
        addBar.add(predictorInputTextBox);
        addBar.add(new HTML("Number of categories: "));
        addBar.add(numCategories);
        addBar.add(new Button("Add", new ClickHandler() {
            public void onClick(ClickEvent e)
            {
                int rows = predictorTable.getRowCount();
                predictorTable.setWidget(rows, 0, new HTML(predictorInputTextBox.getText()));
                predictorTable.setWidget(rows, 1, new HTML(numCategories.getItemText(numCategories.getSelectedIndex())));
                predictorTable.setWidget(rows, 2, new Button("remove", new ClickHandler() {
                    public void onClick(ClickEvent ce)
                    {
                        for(int i = 1; i < predictorTable.getRowCount(); i++)
                        {
                            if (ce.getSource() == predictorTable.getWidget(i, 2))
                            {
                                predictorTable.removeRow(i);
                            }
                        }
                    }
                }));
            }
        }));
        predictorTable.setWidget(0, 0, addBar);
        predictorTable.getFlexCellFormatter().setColSpan(0, 0, 3);    
        panel.add(predictorsHeader);
        panel.add(predictorTable);
        
        return panel;
    }
    
    public VerticalPanel createHypothesisPanel()
    {
        VerticalPanel panel = new VerticalPanel();

        panel.add(hypothesesHeader);
        
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
