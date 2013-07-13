package edu.cudenver.bios.powercalculator.client.panels;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.xml.client.Document;

import edu.cudenver.bios.powercalculator.client.PowerCalculatorConstants;
import edu.cudenver.bios.powercalculator.client.PowerCalculatorGUI;
import edu.cudenver.bios.powercalculator.client.listener.InputWizardStepListener;

public class TemplateDesignPanel extends Composite
{
	// styles for the variable entry tables
	private static final String STYLE_TABLE_PANEL = "variableTablePanel";
	private static final String STYLE_TABLE_COLUMN_HEADER = "variableTableColumnHeader";
	private static final String STYLE_ROW = "variableTableRow";
	private static final String STYLE_ROW_ODD = "variableTableRow-odd";
	private static final String STYLE_ROW_EVEN = "variableTableRow-even";

	private static final int MAX_REPEATED_MEASURES = 10;
    // sub panel headers
    protected SubpanelHeader outcomesHeader = new SubpanelHeader(PowerCalculatorGUI.constants.templateOutcomesHeader(), 
            PowerCalculatorGUI.constants.templateOutcomesDetails());
    protected SubpanelHeader predictorsHeader = new SubpanelHeader(PowerCalculatorGUI.constants.templatePredictorsHeader(), 
            PowerCalculatorGUI.constants.templatePredictorsDetails());
    protected SubpanelHeader hypothesesHeader = new SubpanelHeader(PowerCalculatorGUI.constants.templateHypothesesHeader(), 
            PowerCalculatorGUI.constants.templateHypothesesDetails());
    protected SubpanelHeader meansHeader = new SubpanelHeader(PowerCalculatorGUI.constants.templateMeanHeader(), 
            PowerCalculatorGUI.constants.templateMeanDetails());
    protected SubpanelHeader varianceHeader = new SubpanelHeader(PowerCalculatorGUI.constants.templateVarianceHeader(), 
            PowerCalculatorGUI.constants.templateVarianceDetails());
    protected ListBox numCategories = new ListBox();
    protected ListBox numOutcomesListbox = new ListBox();
    protected ListBox numRepeatedListBox = new ListBox();
    protected ListBox numPredictorsListbox = new ListBox();
    protected CheckBox covariateCheckBox = new CheckBox();
    
    protected FlexTable predictorTable = new FlexTable();
    protected FlexTable outcomesTable = new FlexTable();
    protected PopupPanel categoryPopup = new PopupPanel();
    protected FlexTable categoryTable = new FlexTable();
    protected HTML categoryTarget = null;
    
    protected AlphaPanel alphaPanel = new AlphaPanel();
    
    protected CovariatePanel covariatePanel = new CovariatePanel();
    // panels for hypotheses / means / variance
    protected VerticalPanel hypothesesPanel = new VerticalPanel();
    protected VerticalPanel meansPanel = new VerticalPanel();
    protected Grid meansGrid = new Grid(1,2);
    protected VerticalPanel variancePanel = new VerticalPanel();

    public TemplateDesignPanel(InputWizardStepListener w, int idx)
    {
        VerticalPanel panel = new VerticalPanel();

        panel.add(alphaPanel);
        panel.add(createOutcomesPanel());
        panel.add(createPredictorsPanel());
        panel.add(createHypothesisPanel());
        panel.add(createMeansPanel());
        panel.add(createVariancePanel());

        initWidget(panel);
    }
    
    public VerticalPanel createOutcomesPanel()
    {
        VerticalPanel panel = new VerticalPanel();
                
        // create the dynamic table for entering predictors
        VerticalPanel outcomesTablePanel = new VerticalPanel();
                
        outcomesTable.setWidget(0,0,new HTML("Outcomes"));
        addOutcomesRow();
        outcomesTablePanel.add(outcomesTable);

        // create the repeated measures panel
        HorizontalPanel repeatPanel = new HorizontalPanel();
        repeatPanel.add(new HTML("How many times was this set of outcomes repeated for each subject?"));
        repeatPanel.add(numRepeatedListBox);
        // fill the repeated list box
        for(int i = 1; i < MAX_REPEATED_MEASURES; i++) 
            numRepeatedListBox.addItem(Integer.toString(i));
        numRepeatedListBox.addChangeHandler(new ChangeHandler() {
            public void onChange(ChangeEvent e)
            {
                updateQuestions();
            }
        });
        // set style
        outcomesTablePanel.setStyleName(STYLE_TABLE_PANEL);
        outcomesTable.getRowFormatter().setStylePrimaryName(0, STYLE_TABLE_COLUMN_HEADER);
        panel.setStyleName(PowerCalculatorConstants.STYLE_WIZARD_STEP_PANEL);
        panel.addStyleDependentName(PowerCalculatorConstants.STYLE_WIZARD_STEP_SUBPANEL);
        // layout the overall subpanel
        panel.add(outcomesHeader);
        panel.add(new HTML("Please enter the outcomes (dependent variables) you measured."));
        panel.add(outcomesTablePanel);
        panel.add(repeatPanel);
        return panel;
    }
    
    private void addOutcomesRow()
    {
        int row = outcomesTable.getRowCount();
        TextBox tb = new TextBox();
        tb.addChangeHandler(new ChangeHandler() {
            public void onChange(ChangeEvent e)
            {
                TextBox source = (TextBox) e.getSource();
                // get current row index
                int focusRow = 1;
                for(; focusRow < outcomesTable.getRowCount(); focusRow++)
                {
                    if (source == outcomesTable.getWidget(focusRow, 0)) break;
                }
                if (source.getText().isEmpty() && outcomesTable.getRowCount() > 2)
                    outcomesTable.removeRow(focusRow);
                else
                    if (focusRow == outcomesTable.getRowCount()-1) addOutcomesRow();
                updateQuestions();
            }
        });
        outcomesTable.setWidget(row, 0, tb);
        tb.setFocus(true);
    }
    
    public VerticalPanel createPredictorsPanel()
    {
        VerticalPanel panel = new VerticalPanel();
        
        // create the dynamic table for entering predictors
        VerticalPanel predictorTablePanel = new VerticalPanel();

        // build the category editting popup
        VerticalPanel categoryPanel = new VerticalPanel();
        categoryTable.setWidget(0, 0, new HTML("Categories:"));
        addCategoryRow(null);
        categoryPanel.add(categoryTable);
        categoryPanel.add(new Button("Done", new ClickHandler() {
            public void onClick(ClickEvent e)
            {
                // populate the category list
                StringBuffer buffer = new StringBuffer();
                for(int r = 1; r < categoryTable.getRowCount(); r++)
                {
                    TextBox tb = (TextBox) categoryTable.getWidget(r, 0);
                    String text = tb.getText();
                    if (!text.isEmpty()) 
                    {
                        if (r != 1) buffer.append(",");
                        buffer.append(text);
                    }
                    if (categoryTarget != null) categoryTarget.setText(buffer.toString());
                }
                // clear the table
                for(int r = categoryTable.getRowCount()-1; r >= 1; r--) 
                    categoryTable.removeRow(r);
                addCategoryRow(null);
                // close the popup
                categoryPopup.hide();
                categoryTarget = null;
            }
        }));
        categoryPopup.add(categoryPanel);
        // add column headers to the predictor input table 
        predictorTable.setWidget(0,0,new HTML("Predictor:"));
        predictorTable.setWidget(0,1,new HTML("Categories:"));
        addPredictorsRow();
        predictorTablePanel.add(predictorTable);

        // set style
        predictorTablePanel.setStyleName(STYLE_TABLE_PANEL);
        predictorTable.getRowFormatter().setStylePrimaryName(0, STYLE_TABLE_COLUMN_HEADER);

        // layout the predictor subpanel
        panel.add(predictorsHeader);
        panel.add(new HTML("Please enter any fixed predictors and the number of possible values for each predictor."));
        panel.add(predictorTablePanel);
        panel.add(covariatePanel);
        return panel;
    }
    
    private void addCategoryRow(String value)
    {
        int row = categoryTable.getRowCount();
        TextBox tb = new TextBox();
        tb.addChangeHandler(new ChangeHandler() {
            public void onChange(ChangeEvent e)
            {
                TextBox source = (TextBox) e.getSource();
                // get current row index
                int focusRow = 1;
                for(; focusRow < categoryTable.getRowCount(); focusRow++)
                {
                    if (source == categoryTable.getWidget(focusRow, 0)) break;
                }
                if (source.getText().isEmpty())
                    categoryTable.removeRow(focusRow);
                else
                    if (focusRow == categoryTable.getRowCount()-1) addCategoryRow(null);
            }
        });
        categoryTable.setWidget(row, 0, tb);
        if (value != null) tb.setText(value); 
        tb.setFocus(true);
    }
    
    private void addPredictorsRow()
    {
        int row = predictorTable.getRowCount();
        
        // name entry text box
        TextBox tb = new TextBox();
        tb.addChangeHandler(new ChangeHandler() {
            public void onChange(ChangeEvent e)
            {
                TextBox source = (TextBox) e.getSource();
                // get current row index
                int focusRow = 1;
                for(; focusRow < predictorTable.getRowCount(); focusRow++)
                {
                    if (source == predictorTable.getWidget(focusRow, 0)) break;
                }
                if (source.getText().isEmpty())
                    predictorTable.removeRow(focusRow);
                else
                {
                    HTML catEdit = (HTML) predictorTable.getWidget(focusRow, 1);
                    if (catEdit.getHTML().isEmpty()) catEdit.setHTML(PowerCalculatorGUI.constants.templateLabelEditCategories());
                    if (focusRow == predictorTable.getRowCount()-1) addPredictorsRow();
                }
                updateQuestions();
            }
        });
        
        // category editting popup
        HTML categories = new HTML();
        categories.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent e)
            {
                HTML h = (HTML) e.getSource();
                if (PowerCalculatorGUI.constants.templateLabelEditCategories().equals(h.getHTML()))
                    showCategoryPopup(null, h);
                else
                    showCategoryPopup(h.getHTML(), h);
            }
        });

        // add the widgets and set focus to the new row
        predictorTable.setWidget(row, 0, tb);
        predictorTable.setWidget(row, 1, categories);
        tb.setFocus(true);        
    }
    
    private void showCategoryPopup(String list, HTML target)
    {
        if (list != null)
        {
            String[] categories = list.split(",");
            for(int i = 0; i < categories.length; i++)
            {
                if (!categories[i].isEmpty())
                    addCategoryRow(categories[i]);
            }
        }
        categoryTarget = target;
        categoryPopup.showRelativeTo(target);
        
    }
    
    public VerticalPanel createHypothesisPanel()
    {
        hypothesesPanel.add(hypothesesHeader);
        hypothesesPanel.setVisible(false);
        return hypothesesPanel;
    }
    
    public VerticalPanel createMeansPanel()
    {
        meansPanel.add(meansHeader);
        meansPanel.setVisible(false);
        return meansPanel;
    }
    
    public VerticalPanel createVariancePanel()
    {
        variancePanel.add(varianceHeader);
        variancePanel.setVisible(false);
        return variancePanel;
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
        buffer.append("alpha='" + alphaPanel.getAlpha() + "' ");
        return buffer.toString();
    }
    
    public String getAlpha()
    {
        return alphaPanel.getAlpha();
    }
    
    public void reset()
    {
        alphaPanel.reset();
        
        // clear variable tables (starts at 2 to avoid removing header rows)
        for(int i =2; i < predictorTable.getRowCount(); i++) 
            predictorTable.removeRow(i); 
        for(int i =2; i < outcomesTable.getRowCount(); i++) 
            outcomesTable.removeRow(i); 
        
        updateQuestions();
    }
    
    private void updateQuestions()
    {
        updateHypotheses();
        updateMeans();
        updateVariability();
    }
    
    private void updateHypotheses()
    {
        
    }
    
    private void updateMeans()
    {
        int numPredictors = predictorTable.getRowCount()-1;
        int numOutcomes = outcomesTable.getRowCount()-1;
        
//        if (numOutcomes > 0 && numPredictors > 0)
//        {
//            meansGrid.clear();
//            meansGrid.resizeRows(numOutcomes * totalCategories);
//            for(int r = 0; r < meansGrid.getRowCount(); r++)
//            {
//                TextBox tb = new TextBox();
//                meansGrid.setWidget(r, 0, new HTML(""));
//                meansGrid.setWidget(r, 1, tb);
//            }
//            meansPanel.setVisible(true);
//        }
//        else
//        {
//            meansPanel.setVisible(false);
//        }
    }
    
    private void updateVariability()
    {
        
    }
}
