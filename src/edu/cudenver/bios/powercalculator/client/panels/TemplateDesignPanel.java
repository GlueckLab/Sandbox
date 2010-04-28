package edu.cudenver.bios.powercalculator.client.panels;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
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

import edu.cudenver.bios.powercalculator.client.PowerCalculatorConstants;
import edu.cudenver.bios.powercalculator.client.listener.InputWizardStepListener;

public class TemplateDesignPanel extends Composite
{
	// styles for the variable entry tables
	private static final String STYLE_TABLE_PANEL = "variableTablePanel";
	private static final String STYLE_TABLE_COLUMN_HEADER = "variableTableColumnHeader";
	private static final String STYLE_TABLE_ADDBAR = "variableTableAddBar";
	private static final String STYLE_ROW = "variableTableRow";
	private static final String STYLE_ROW_ODD = "variableTableRow-odd";
	private static final String STYLE_ROW_EVEN = "variableTableRow-even";

	private static final int MAX_CATEGORIES = 10;
	private static final int MAX_REPEATED_MEASURES = 30;
    // sub panel headers
    protected SubpanelHeader outcomesHeader = new SubpanelHeader("Dependent Variables / Outcomes", 
            "stuff you measued");
    protected SubpanelHeader predictorsHeader = new SubpanelHeader("Independent Variables / Predictors", 
    "things that predict the stuff you measured");
    protected SubpanelHeader hypothesesHeader = new SubpanelHeader("Comparisons / Hypotheses", 
    "stuff you want to know");
    
    protected ListBox numCategories = new ListBox();
    protected ListBox numOutcomesListbox = new ListBox();
    protected ListBox numRepeatedListBox = new ListBox();
    protected ListBox numPredictorsListbox = new ListBox();
    protected CheckBox covariateCheckBox = new CheckBox();
    
    protected TextBox predictorInputTextBox = new TextBox();
    protected TextBox outcomesInputTextBox = new TextBox();
    protected FlexTable predictorTable = new FlexTable();
    protected FlexTable outcomesTable = new FlexTable();
    
    protected Button predictorAddButton = new Button("Add");
    protected Button outcomesAddButton = new Button("Add");
    
    protected TextBox alphaTextBox = new TextBox();
    protected HTML alphaErrorHTML = new HTML();
    
    public TemplateDesignPanel(InputWizardStepListener w, int idx)
    {
        VerticalPanel panel = new VerticalPanel();

        panel.add(createOutcomesPanel());
        panel.add(createPredictorsPanel());
        panel.add(createHypothesisPanel());

        initWidget(panel);
    }
    
    public VerticalPanel createOutcomesPanel()
    {
        VerticalPanel panel = new VerticalPanel();
                
        // create the dynamic table for entering predictors
        VerticalPanel outcomesTablePanel = new VerticalPanel();
        
        // create the input bar
        HorizontalPanel addBar = new HorizontalPanel();
        
        // layout the input bar       
        addBar.add(new HTML("Outcome: "));
        addBar.add(outcomesInputTextBox);
        // button to add outcome information to the display table
        addBar.add(new Button("Add", new ClickHandler() {
            public void onClick(ClickEvent e)
            {
                int rows = outcomesTable.getRowCount();
                outcomesTable.setWidget(rows, 0, new HTML(outcomesInputTextBox.getText()));
                outcomesTable.setWidget(rows, 1, new Button("x", new ClickHandler() {
                    public void onClick(ClickEvent ce)
                    {
                        for(int i = 2; i < outcomesTable.getRowCount(); i++)
                        {
                            if (ce.getSource() == outcomesTable.getWidget(i, 1))
                            {
                            	outcomesTable.removeRow(i);
                            	break;
                            }
                        }
                   }
                }));
                // style the newly added row
                outcomesTable.getRowFormatter().setStylePrimaryName(rows, (rows % 2 == 0 ? STYLE_ROW_EVEN : STYLE_ROW_ODD));
            }
        }));
        // layout the table panel
        outcomesTable.setWidget(0, 0, addBar);
        outcomesTable.getFlexCellFormatter().setColSpan(0, 0, 2);   
        // add column headers
        outcomesTable.setWidget(1,0,new HTML("Name"));
        outcomesTable.setWidget(1,1,new HTML(""));
        // add to the table panel
        outcomesTablePanel.add(outcomesTable);

        // create the repeated measures panel
        HorizontalPanel repeatPanel = new HorizontalPanel();
        repeatPanel.add(new HTML("How many times was the above set of outcomes repeated for each subject?"));
        repeatPanel.add(numRepeatedListBox);
        // fill the repeated list box
        for(int i = 0; i < MAX_REPEATED_MEASURES; i++) 
            numRepeatedListBox.addItem(Integer.toString(i));
        
        // set style
        outcomesTablePanel.setStyleName(STYLE_TABLE_PANEL);
        addBar.setStyleName(STYLE_TABLE_ADDBAR);
        outcomesTable.getRowFormatter().setStylePrimaryName(1, STYLE_TABLE_COLUMN_HEADER);
        panel.setStyleName(PowerCalculatorConstants.STYLE_WIZARD_STEP_PANEL);
        panel.addStyleDependentName(PowerCalculatorConstants.STYLE_WIZARD_STEP_SUBPANEL);
        // layout the overall subpanel
        panel.add(outcomesHeader);
        panel.add(new HTML("Please enter the outcomes (dependent variables) you measured."));
        panel.add(outcomesTablePanel);
        panel.add(repeatPanel);
        return panel;
    }
    
    public VerticalPanel createPredictorsPanel()
    {
        VerticalPanel panel = new VerticalPanel();
        
        // create the dynamic table for entering predictors
        VerticalPanel predictorTablePanel = new VerticalPanel();

        // create the input bar
        HorizontalPanel addBar = new HorizontalPanel();
        
        // create the categories drop down, and only allow adding of variables when 
        // an appropriate number of categories is selected
        numCategories.addItem("# Categories");
        for(int i = 1; i <= MAX_CATEGORIES; i++) numCategories.addItem(Integer.toString(i));
        numCategories.addChangeHandler(new ChangeHandler() {
        	public void onChange(ChangeEvent e)
        	{
        		predictorAddButton.setEnabled(numCategories.getSelectedIndex() != 0);
        	}
        });
        
        // layout the input bar
        addBar.add(new HTML("Predictor: "));
        addBar.add(predictorInputTextBox);
        addBar.add(numCategories);
        addBar.add(predictorAddButton);

        // disable the add button to start
        predictorAddButton.setEnabled(false);
        // add predictor information to the display table when add button is clicked
        predictorAddButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent e)
            {
                int rows = predictorTable.getRowCount();
                predictorTable.setWidget(rows, 0, new HTML(predictorInputTextBox.getText()));
                predictorTable.setWidget(rows, 1, new HTML(numCategories.getItemText(numCategories.getSelectedIndex())));
                predictorTable.setWidget(rows, 2, new Button("x", new ClickHandler() {
                    public void onClick(ClickEvent ce)
                    {
                        for(int i = 2; i < predictorTable.getRowCount(); i++)
                        {
                            if (ce.getSource() == predictorTable.getWidget(i, 2))
                            {
                                predictorTable.removeRow(i);
                                break;
                                // TODO: reset the coloring?
                            }
                        }
                    }
                }));
                // style the newly added row
                predictorTable.getRowFormatter().setStylePrimaryName(rows, (rows % 2 == 0 ? STYLE_ROW_EVEN : STYLE_ROW_ODD));
            }
        });
        
        // layout the predictor input table 
        predictorTable.setWidget(0, 0, addBar);
        predictorTable.getFlexCellFormatter().setColSpan(0, 0, 3);   
        // add column headers
        predictorTable.setWidget(1,0,new HTML("Name"));
        predictorTable.setWidget(1,1,new HTML("# Categories"));
        predictorTable.setWidget(1,2,new HTML(""));
        // add table to its container
        predictorTablePanel.add(predictorTable);
        
        // set style
        predictorTablePanel.setStyleName(STYLE_TABLE_PANEL);
        addBar.setStyleName(STYLE_TABLE_ADDBAR);
        predictorTable.getRowFormatter().setStylePrimaryName(1, STYLE_TABLE_COLUMN_HEADER);

        // layout the predictor subpanel
        panel.add(predictorsHeader);
        panel.add(new HTML("Please enter any fixed predictors and the number of possible values for each predictor."));
        panel.add(predictorTablePanel);
        
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
        StringBuffer buffer = new StringBuffer();
        buffer.append("alpha='" + alphaTextBox.getText() + "' ");
        return buffer.toString();
    }
    
    public String getAlpha()
    {
        return alphaTextBox.getText();
    }
    
    public void reset()
    {
        alphaTextBox.setText("");
        
        // clear variable tables
    }
}
