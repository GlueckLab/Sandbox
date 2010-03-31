package edu.cudenver.bios.powercalculator.client.panels;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Node;

import edu.cudenver.bios.powercalculator.client.PowerCalculatorGUI;
import edu.cudenver.bios.powercalculator.client.listener.MatrixResizeListener;
import edu.cudenver.bios.powercalculator.client.listener.MetaDataListener;
import edu.cudenver.bios.powercalculator.client.listener.ModelSelectListener;
import edu.cudenver.bios.powercalculator.client.listener.OptionsListener;
import edu.cudenver.bios.powercalculator.client.listener.StudyUploadListener;

public class OptionsPanel extends Composite 
implements MatrixResizeListener, MetaDataListener, ModelSelectListener,
StudyUploadListener, ClickHandler, ChangeHandler
{
	private static final String STYLE = "optionsPanel";
	private static final String HEADER_STYLE = "optionsPanelHeader";
	private static final String SOLVE_FOR_GROUP = "solveFor";

	private static final int INDEX_TWO_GROUP = 0;
	private static final int INDEX_LINEAR_MODEL = 1;

	// solve for options
	protected RadioButton powerRb = new RadioButton(SOLVE_FOR_GROUP, PowerCalculatorGUI.constants.radioButtonPower());
	protected RadioButton sampleSizeRb = new RadioButton(SOLVE_FOR_GROUP, PowerCalculatorGUI.constants.radioButtonSampleSize());

	// deck panel containing all possible detail views
	protected DeckPanel deck;
	
	// curve options
	protected CheckBox showCurveCheckBox = new CheckBox(PowerCalculatorGUI.constants.checkBoxShowCurve());
	protected TextBox curveTitleTextBox = new TextBox();
	protected TextBox curveXAxisLabel = new TextBox();
	protected TextBox curveYAxisLabel = new TextBox();
    protected TextBox curveWidth = new TextBox();
    protected TextBox curveHeight = new TextBox();
    
	protected LinearModelDetailsPanel glmmDetailsPanel;
    protected TwoGroupDetailsPanel twoGroupDetailsPanel;
    
	protected String modelName = PowerCalculatorGUI.constants.modelGLMM();;
	protected ArrayList<OptionsListener> listeners = new ArrayList<OptionsListener>();

	public OptionsPanel()
	{
		VerticalPanel panel = new VerticalPanel();

		// layout the overall panel
		panel.add(createSolveForPanel());
		panel.add(createDetailsPanel());
		panel.add(createCurveOptionsPanel());

		// the details panels listen for options events
		this.addListener(glmmDetailsPanel);
		this.addListener(twoGroupDetailsPanel);
		
		// set up the panel display based on the model name
		onModelSelect(modelName);
		
		// initialize widget
		initWidget(panel);
	}

	public void onModelSelect(String modelName)
	{
		this.modelName = modelName;
        if (PowerCalculatorGUI.constants.modelGLMM().equals(modelName))
            deck.showWidget(INDEX_LINEAR_MODEL);
        else
            deck.showWidget(INDEX_TWO_GROUP);
	}

	private VerticalPanel createDetailsPanel()
	{
		VerticalPanel panel = new VerticalPanel();
		
		// create the sub panels
		glmmDetailsPanel = new LinearModelDetailsPanel(powerRb.getValue());
		twoGroupDetailsPanel = new TwoGroupDetailsPanel(powerRb.getValue());
		// deck of possible option panels - power/sample size options
		// differ depending on which model is selected
		HTML deckHeader = new HTML(PowerCalculatorGUI.constants.panelLabelOptionsDetails());
		deck = new DeckPanel();
		deck.add(twoGroupDetailsPanel);
		deck.add(glmmDetailsPanel);

		panel.add(deckHeader);
		panel.add(deck);

		deckHeader.setStyleName(HEADER_STYLE);
		panel.setStyleName(STYLE);

		return panel;
	}

	private VerticalPanel createSolveForPanel()
	{
		// radio buttons indicating which calculation is desired
		VerticalPanel selectPanel = new VerticalPanel();

		// create the header
		HTML header = new HTML(PowerCalculatorGUI.constants.panelLabelOptionsSolveFor());

		// add click handlers on the selection buttons
		powerRb.addClickHandler(this);
		sampleSizeRb.addClickHandler(this);

		// layout the panel
		selectPanel.add(header);
		selectPanel.add(powerRb);
		selectPanel.add(sampleSizeRb);

		// set style
		selectPanel.setStyleName(STYLE);
		header.setStyleName(HEADER_STYLE);

		// select power by default
		powerRb.setValue(true);

		return selectPanel;
	}

	private VerticalPanel createCurveOptionsPanel()
	{
		VerticalPanel panel = new VerticalPanel();

		// create the header
		HTML header = new HTML(PowerCalculatorGUI.constants.panelLabelOptionsGraphics());

		// add handler for show curve checkbox
		showCurveCheckBox.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent e)
			{
				notifyOnShowCurve(showCurveCheckBox.getValue());
			}
		});

		// title, label options
		Grid grid = new Grid(5,2);
		grid.setWidget(0,0, new HTML(PowerCalculatorGUI.constants.textLabelCurveTitle()));
		grid.setWidget(0,1, curveTitleTextBox);
		grid.setWidget(1,0, new HTML(PowerCalculatorGUI.constants.textLabelCurveXAxis()));
		grid.setWidget(1,1, curveXAxisLabel);
		grid.setWidget(2,0, new HTML(PowerCalculatorGUI.constants.textLabelCurveYAxis()));
		grid.setWidget(2,1, curveYAxisLabel);
		grid.setWidget(3, 0, new HTML("Width"));
		grid.setWidget(3, 1, curveWidth);
		grid.setWidget(4, 0, new HTML("Height"));
		grid.setWidget(4, 1, curveHeight);
	        
		
		// layout the panel
		panel.add(header);
		panel.add(showCurveCheckBox);
		panel.add(grid);

		// set style
		panel.setStyleName(STYLE);
		header.setStyleName(HEADER_STYLE);

		return panel;
	}
	
	public void addListener(OptionsListener listener)
	{
		listeners.add(listener);
	}

	private void notifyOnSolvingFor(boolean solveForPower)
	{
		for(OptionsListener listener: listeners) listener.onSolveFor(solveForPower);
	}

	private void notifyOnShowCurve(boolean showCurve)
	{
	    CurveOptions curveOpts = null;
	    if (showCurve)
	    {
	        curveOpts = new CurveOptions();
	        curveOpts.title = curveTitleTextBox.getText();
	        curveOpts.xAxisLabel = this.curveXAxisLabel.getText();
	        curveOpts.yAxisLabel = this.curveYAxisLabel.getText();
	    }
		for(OptionsListener listener: listeners) listener.onShowCurve(showCurve, curveOpts);
	}

    public String getGraphicsOptions()
    {      
        boolean showCurve = showCurveCheckBox.getValue();
        if (showCurve)
        {
            StringBuffer buffer = new StringBuffer();
            buffer.append("<curve ");
            buffer.append("title='" + curveTitleTextBox.getText() + "' ");
            buffer.append("xlabel='" + curveXAxisLabel.getText() + "' ");
            buffer.append("ylabel='" + curveYAxisLabel.getText() + "' ");
            if (!curveWidth.getText().isEmpty())
                buffer.append("width='" + curveWidth.getText() + "' ");
            if (!curveHeight.getText().isEmpty())
                buffer.append("height='" + curveHeight.getText() + "' ");
            buffer.append("/>");  
            return buffer.toString();
        }
        else
        {
            // no graphics options set
            return ""; 
        }
    }
    
    public String getPowerAttributes()
    {
        if (PowerCalculatorGUI.constants.modelOneSampleStudentsT().equals(modelName))
            return "sampleSize='" + twoGroupDetailsPanel.getSampleSize() + "'";
        else
            return "";
            
    }
    
    public String getSampleSizeAttributes()
    {
        if (PowerCalculatorGUI.constants.modelGLMM().equals(modelName))
        {
            return "power='" + glmmDetailsPanel.getPower() + "'";
        }
        else
        {
            return "power='" + twoGroupDetailsPanel.getPower() + "'";
        }
    }
        
    public String getRowMetaDataXML()
    {
        return glmmDetailsPanel.getRowMetaDataXML();
    }
    
    public void onMatrixResize(int rows, int cols)
    {
        LinearModelDetailsPanel panel = 
            (LinearModelDetailsPanel) deck.getWidget(INDEX_LINEAR_MODEL);
        panel.onMatrixResize(rows, cols);
    }
    
    public void onRowName(int row, String name)
    {
        LinearModelDetailsPanel panel = 
            (LinearModelDetailsPanel) deck.getWidget(INDEX_LINEAR_MODEL);
        panel.onRowName(row, name);
    }
    
    public void onFixed(int col) 
    {
        LinearModelDetailsPanel panel = 
            (LinearModelDetailsPanel) deck.getWidget(INDEX_LINEAR_MODEL);
        panel.onFixed(col);
    }
    
    public void onRandom(int col, double mean, double variance) 
    {
        LinearModelDetailsPanel panel = 
            (LinearModelDetailsPanel) deck.getWidget(INDEX_LINEAR_MODEL);
        panel.onRandom(col, mean, variance);
    }
    
    public void onClick(ClickEvent e)
    {
        notifyOnSolvingFor(powerRb.getValue());
    }
    
    public void onStudyUpload(Document doc, String modelName)
    {
        onModelSelect(modelName);
    }
    
    public void onChange(ChangeEvent e)
    {
        
    }
}
