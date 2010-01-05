package edu.cudenver.bios.powercalculator.client.panels;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class InputWizardPanel extends Composite 
implements NavigationListener, StartListener
{
    private static final String TEST_GLMM = "glmm";
    private static final String TEST_ONESAMPLESTUDENTST = "onesamplestudentt";
    
	// not using an enum here to allow easier comparison with visible 
	// widget index from the DeckPanel
    private static final int START_INDEX = 0;
    private static final int INPUT_INDEX = 1;
    private static final int OPTIONS_INDEX = 2;
    private static final int RESULTS_INDEX = 3;
	
	protected static final String STYLE = "inputPanel";
	
	protected NavigationPanel navPanel = new NavigationPanel();
	// stack of panels for the wizard
	protected DeckPanel panelStack = new DeckPanel();
    // start panel
	protected StartPanel startPanel = new StartPanel();;
    // potential input panels.  Added to the deck depending on the input type
    // selected on the start panel
	protected UploadPanel uploadPanel = new UploadPanel();
	protected BasicPanel basicPanel = new BasicPanel();
	protected MatrixPanel matrixPanel = new MatrixPanel();
	protected SimplePanel simplePanel = new SimplePanel();
    // options panel (always in deck after input panel)
	protected OptionsPanel optionsPanel = new OptionsPanel();
    // results panel (always in deck after options panel)
	protected ResultsPanel resultsPanel = new ResultsPanel();

    public InputWizardPanel()
    {
        VerticalPanel container = new VerticalPanel();

    	startPanel.addListener(this);
        panelStack.add(startPanel);
        panelStack.add(uploadPanel);
        panelStack.add(optionsPanel);
        panelStack.add(resultsPanel);
        panelStack.setStyleName(STYLE);
        panelStack.showWidget(0);
        
        container.add(panelStack);
        container.add(navPanel);
        
        // set up the navigation callbacks
        navPanel.addNavigationListener(this);
        navPanel.setPrevious(false, false);
        navPanel.setNext(true, true);
        initWidget(container);
    }
    
    public void onPrevious()
    {
        int visibleIndex = panelStack.getVisibleWidget();
        if (visibleIndex > START_INDEX)
        {
            panelStack.showWidget(--visibleIndex);
            if (visibleIndex == START_INDEX)
            {
                navPanel.setPrevious(false, false);
                navPanel.setNext(true, true);
            }
            else
            {
                navPanel.setNext(true, true);
                navPanel.setPrevious(true, true);
            }
        }
    }
    
    public void onNext()
    {    	
        int visibleIndex = panelStack.getVisibleWidget();
        if (visibleIndex < RESULTS_INDEX)
        {
            panelStack.showWidget(++visibleIndex);
            if (visibleIndex == RESULTS_INDEX)
            {
                navPanel.setNext(false, false);
                navPanel.setPrevious(true, true);
            }
            else
            {
                navPanel.setNext(true, true);
                navPanel.setPrevious(true, true);
            }
        }
    }
    
    public void onCancel()
    {
        panelStack.showWidget(0);
        navPanel.setNext(true, true);
        navPanel.setPrevious(false, false);
    }
    
    public void onInputTypeSelect(StartPanel.InputType type)
    {
        panelStack.remove(INPUT_INDEX);
    	switch (type)
    	{
    	case BASIC:
    	    panelStack.insert(basicPanel, INPUT_INDEX);
    		break;
    	case MATRIX:
    	    panelStack.insert(matrixPanel, INPUT_INDEX);
    		break;
    	case UPLOAD:
    	    panelStack.insert(uploadPanel, INPUT_INDEX);
    		break;
    	default:
    		break;    			
    	}
    }
    
    public void onModelSelect(String modelName)
    {
    	if (!TEST_GLMM.equals(modelName))
    	{
    	    panelStack.remove(INPUT_INDEX);
    	    panelStack.insert(simplePanel, INPUT_INDEX);
    	}
    }
    
    public void addNavigationListener(NavigationListener listener)
    {
        navPanel.addNavigationListener(listener);
    }
}
