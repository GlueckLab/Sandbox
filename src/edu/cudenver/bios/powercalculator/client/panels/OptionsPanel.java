package edu.cudenver.bios.powercalculator.client.panels;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.cudenver.bios.powercalculator.client.PowerCalculatorGUI;
import edu.cudenver.bios.powercalculator.client.listener.MatrixResizeListener;
import edu.cudenver.bios.powercalculator.client.listener.MetaDataListener;
import edu.cudenver.bios.powercalculator.client.listener.ModelSelectListener;
import edu.cudenver.bios.powercalculator.client.listener.OptionsListener;

public class OptionsPanel extends Composite 
implements MatrixResizeListener, MetaDataListener, ModelSelectListener
{
	private static final String DEFAULT_REPS = "2";
	private static final String DEFAULT_RATIO = "1";
	private static final int DEFAULT_N = 3;

	private static final int REPS_COLUMN = 1;
	private static final int RATIO_COLUMN = 2;
	
	private static final String STYLE = "optionsPanel";
	private static final String HEADER_STYLE = "optionsPanelHeader";
	private static final String SOLVE_FOR_GROUP = "solveFor";

	private static final int INDEX_SIMPLE_POWER = 0;
	private static final int INDEX_SIMPLE_SAMPLE_SIZE = 1;
	private static final int INDEX_LINEAR_MODEL = 2;

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
	
	// simple power / sample size options (for basic models such as T-test)
	protected TextBox sampleSizeTextBox = new TextBox();
	protected HTML sampleSizeLabel = new HTML(PowerCalculatorGUI.constants.textLabelSampleSize());
	protected TextBox powerTextBox = new TextBox();
	protected HTML powerLabel = new HTML(PowerCalculatorGUI.constants.textLabelPower());

	// row meta data parameters for GLMM, +1 for table header
	protected Grid rowMetaData = new Grid(DEFAULT_N+1, 3);

	// statistical method lists for GLMM
	protected ListBox testStatisticList = new ListBox();
	protected ListBox powerMethodList = new ListBox();
    protected ListBox unirepCorrectionList = new ListBox();

	protected String modelName = PowerCalculatorGUI.constants.modelGLMM();;

	protected ArrayList<OptionsListener> listeners = new ArrayList<OptionsListener>();

	public OptionsPanel()
	{
		VerticalPanel panel = new VerticalPanel();

		// layout the overall panel
		panel.add(createSolveForPanel());
		panel.add(createDetailsPanel());
		panel.add(createCurveOptionsPanel());

		// set up the panel display based on the model name
		onModelSelect(modelName);
		
		// initialize widget
		initWidget(panel);
	}

	public void onModelSelect(String modelName)
	{
		this.modelName = modelName;
		updateDeck();
	}

	private void updateDeck()
	{
		if (powerRb.getValue())
		{
			if (PowerCalculatorGUI.constants.modelGLMM().equals(modelName))
				deck.showWidget(INDEX_LINEAR_MODEL);
			else
				deck.showWidget(INDEX_SIMPLE_POWER);
			notifyOnSolvingFor(true);
		}
		else
		{
			if (PowerCalculatorGUI.constants.modelGLMM().equals(modelName))
				deck.showWidget(INDEX_LINEAR_MODEL);
			else
				deck.showWidget(INDEX_SIMPLE_SAMPLE_SIZE);
			notifyOnSolvingFor(false);
		}
	}

	private VerticalPanel createDetailsPanel()
	{
		VerticalPanel panel = new VerticalPanel();
		// deck of possible option panels - power/sample size options
		// differ depending on which model is selected
		HTML deckHeader = new HTML(PowerCalculatorGUI.constants.panelLabelOptionsDetails());
		deck = new DeckPanel();
		deck.add(this.createSimplePowerPanel());
		deck.add(this.createSimpleSampleSizePanel());
		deck.add(this.createLinearModelPanel());

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
		powerRb.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent e)
			{
				updateDeck();
			}
		});
		sampleSizeRb.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent e)
			{
				updateDeck();
			}
		});

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
		Grid grid = new Grid(3,2);
		grid.setWidget(0,0, new HTML(PowerCalculatorGUI.constants.textLabelCurveTitle()));
		grid.setWidget(0,1, curveTitleTextBox);
		grid.setWidget(1,0, new HTML(PowerCalculatorGUI.constants.textLabelCurveXAxis()));
		grid.setWidget(1,1, curveXAxisLabel);
		grid.setWidget(2,0, new HTML(PowerCalculatorGUI.constants.textLabelCurveYAxis()));
		grid.setWidget(2,1, curveYAxisLabel);
		
		// layout the panel
		panel.add(header);
		panel.add(showCurveCheckBox);
		panel.add(grid);

		// set style
		panel.setStyleName(STYLE);
		header.setStyleName(HEADER_STYLE);

		return panel;
	}

	private HorizontalPanel createSimplePowerPanel()
	{
		HorizontalPanel panel = new HorizontalPanel();
		
		panel.add(new HTML(PowerCalculatorGUI.constants.textLabelSampleSize()));
		panel.add(sampleSizeTextBox);

		panel.setStyleName(STYLE);
		return panel;
	}

	private HorizontalPanel createSimpleSampleSizePanel()
	{
		HorizontalPanel panel = new HorizontalPanel();
		
		panel.add(new HTML(PowerCalculatorGUI.constants.textLabelPower()));
		panel.add(powerTextBox);

		panel.setStyleName(STYLE);
		return panel;
	}
	
	private VerticalPanel createLinearModelPanel()
	{
		VerticalPanel panel = new VerticalPanel();

		// initialize the row meta data
		rowMetaData.setWidget(0, 0, new HTML("Group"));
		rowMetaData.setWidget(0, 1, new HTML("#Subjects (n)"));
		rowMetaData.setWidget(0, 2, new HTML("Ratio of Group Sizes"));
		for(int r = 1; r < rowMetaData.getRowCount(); r++) initRow(r);
		
		
		panel.add(createStatisticalMethodSelectionPanel());
		panel.add(rowMetaData);
		return panel;
	}

	private Grid createStatisticalMethodSelectionPanel()
	{
		// build test statistic selection list
		testStatisticList.addItem("Hotelling Lawley Trace", "hlt");
		testStatisticList.addItem("Univariate Approach To Repeated Measures", "unirep");
		testStatisticList.addItem("Wilk's Lambda", "wl");
		testStatisticList.addItem("Pillau Bartlett Trace", "pbt");

		// build the covariate adjustment method selection list
		powerMethodList.addItem("Conditional Power", "cond");
		powerMethodList.addItem("Quantile Power", "quantile");
		powerMethodList.addItem("Unconditional Power", "uncond");

		Grid grid = new Grid(2,2);
		grid.setWidget(0, 0, new HTML("Test Statistic: "));
		grid.setWidget(0, 1, testStatisticList);
		grid.setWidget(1, 0, new HTML("Power Approximation Method: "));
		grid.setWidget(1, 1, powerMethodList);

		return grid;
	}

	public String getPower()
	{
		return powerTextBox.getText();
	}

	public String getSampleSize()
	{
		return sampleSizeTextBox.getText();
	}

	public String getRowMetaDataXML()
	{
		StringBuffer buffer = new StringBuffer();
		buffer.append("<rowMetaData>");
		for(int r = 1; r < rowMetaData.getRowCount(); r++)
		{
			TextBox reps = (TextBox) rowMetaData.getWidget(r, REPS_COLUMN);
			buffer.append("<r ");
			if (powerRb.getValue() && !showCurveCheckBox.getValue())
			{
				buffer.append(" reps='" + reps.getValue() + "'");
			}
			else
			{
				TextBox ratio = (TextBox) rowMetaData.getWidget(r, RATIO_COLUMN);
				buffer.append(" reps='" + reps.getValue() + "' ratio='" + ratio.getText() + "'");
			}
			buffer.append("/>");
		}
		buffer.append("</rowMetaData>");
		return buffer.toString();
	}

	public String getStatistic()
	{
		return testStatisticList.getValue(testStatisticList.getSelectedIndex());
	}
	
	public String getPowerMethod()
	{
	    return powerMethodList.getValue(powerMethodList.getSelectedIndex());
	}
	
	public String getUnivariateAdjustment()
	{
	    return unirepCorrectionList.getValue(unirepCorrectionList.getSelectedIndex());
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

	public void onMatrixResize(int rows, int cols)
	{
		int oldRows = rowMetaData.getRowCount()-1;
		rowMetaData.resize(rows+1, rowMetaData.getColumnCount()); //+1 for table header
		if (rows > oldRows) 
		{
			// initialize the new rows
			for(int r = oldRows; r < rows+1; r++) initRow(r);
		}
	}
	
	private void initRow(int row) 
	{
		HTML id = new HTML(Integer.toString(row));
		rowMetaData.setWidget(row, 0, id);
		
		TextBox reps = new TextBox();
		reps.setText(DEFAULT_REPS);				
		rowMetaData.setWidget(row, 1, reps);
		
		TextBox ratio = new TextBox();
		ratio.setText(DEFAULT_RATIO);				
		rowMetaData.setWidget(row, 2, ratio);
	}
    
	public void onFixed(int col) {}
	
	public void onRandom(int col, double mean, double variance) {}
	
    public void onRowName(int row, String name)
    {
    	HTML label = (HTML) rowMetaData.getWidget(row, 0);
    	label.setText(name);
    }
    
    public String getGraphicsAttributes()
    {      
        boolean showCurve = showCurveCheckBox.getValue();
        if (showCurve)
        {
            StringBuffer buffer = new StringBuffer();
            buffer.append("curve='" + showCurveCheckBox.getValue() + "' ");
            buffer.append("curveTitle='" + curveTitleTextBox.getText() + "' ");
            buffer.append("curveXaxis='" + curveXAxisLabel.getText() + "' ");
            buffer.append("curveYAxis='" + curveYAxisLabel.getText() + "'");  
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
        if (PowerCalculatorGUI.constants.modelGLMM().equals(modelName))
        {
            return "statistic='"+ getStatistic() + "' powerMethod='" + getPowerMethod() + "'";
        }
        else
        {
            return "sampleSize='" + getSampleSize() + "'";
        }
    }
    
    public String getSampleSizeAttributes()
    {
        if (PowerCalculatorGUI.constants.modelGLMM().equals(modelName))
        {
            return "statistic='"+ getStatistic() + "' powerMethod='" + getPowerMethod() +"'";
        }
        else
        {
            return "power='" + getPower() + "'";
        }
    }
}
