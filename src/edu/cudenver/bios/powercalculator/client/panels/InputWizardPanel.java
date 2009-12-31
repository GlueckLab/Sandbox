package edu.cudenver.bios.powercalculator.client.panels;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckPanel;

import edu.cudenver.bios.powercalculator.client.PowerCalculatorConstants;

public class InputWizardPanel extends Composite 
implements NavigationListener, StartListener
{
		
	// not using an enum here to allow easier comparison with visible 
	// widget index from the DeckPanel
	private static final int START_INDEX = 0;
	private static final int INPUT_INDEX = 1;
	private static final int OPTIONS_INDEX = 2;
	private static final int RESULTS_INDEX = 3;
	private static final int MAX_STEPS = 4;
	
	protected static final String STYLE = "inputPanel";
	
	// stack of panels for the wizard
    DeckPanel panel = new DeckPanel();
    // start panel
    StartPanel startPanel;
    // potential input panels.  Added to the deck depending on the input type
    // selected on the start panel
    UploadPanel uploadPanel;
    BasicPanel basicPanel;
    MatrixPanel matrixPanel;
    OptionsPanel optionsPanel;
    ResultsPanel resultsPanel;

    public InputWizardPanel()
    {
    	startPanel = new StartPanel();
    	startPanel.addListener(this);
    	uploadPanel = new UploadPanel();
    	basicPanel = new BasicPanel();
    	matrixPanel = new MatrixPanel();
    	optionsPanel = new OptionsPanel();
    	resultsPanel = new ResultsPanel();
    	
        panel.add(startPanel);
        panel.add(uploadPanel);
        panel.add(new Label("Power / Sample Size Options"));
        panel.add(new Label("View Results"));
        panel.setStyleName(STYLE);
        panel.showWidget(0);
        initWidget(panel);
    }
    
    public void onPrevious()
    {
        int visibleIndex = panel.getVisibleWidget();
        if (visibleIndex > 0)
        {
        	panel.showWidget(visibleIndex-1);
        }
    }
    
    public void onNext()
    {    	
        int visibleIndex = panel.getVisibleWidget();
        if (visibleIndex < MAX_STEPS-1)
        {
            panel.showWidget(visibleIndex+1);
        }
    }
    
    public void onCancel()
    {
        panel.showWidget(0);
    }
    
    public boolean canContinue()
    {
    	return true;
    }
    
    public void onInputTypeSelect(StartPanel.InputType type)
    {
		panel.remove(INPUT_INDEX);
    	switch (type)
    	{
    	case BASIC:
    		panel.insert(basicPanel, INPUT_INDEX);
    		break;
    	case MATRIX:
    		panel.insert(matrixPanel, INPUT_INDEX);
    		break;
    	case UPLOAD:
    		panel.insert(uploadPanel, INPUT_INDEX);
    		break;
    	default:
    		break;    			
    	}
    }
    
    public void onModelSelect(String modelName)
    {
    	
    }
}
