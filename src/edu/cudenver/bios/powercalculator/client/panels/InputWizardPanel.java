package edu.cudenver.bios.powercalculator.client.panels;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NamedNodeMap;
import com.google.gwt.xml.client.XMLParser;
import com.google.gwt.xml.client.Node;

public class InputWizardPanel extends Composite implements NavigationListener, StartListener
{
	private static final int STATUS_CODE_OK = 200;
	private static final int STATUS_CODE_CREATED = 201;
	private static final String POWER_URL = "/restcall/power/power/model/";
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
	protected StartPanel startPanel = new StartPanel();
    // options panel (always in deck after input panel)
	protected OptionsPanel optionsPanel = new OptionsPanel();
    // results panel (always in deck after options panel)
	protected ResultsPanel resultsPanel = new ResultsPanel();

    // potential input panels.  Added to the deck depending on the input type
    // selected on the start panel
	protected UploadPanel uploadPanel = new UploadPanel();
	protected BasicPanel basicPanel = new BasicPanel();
	protected MatrixPanel matrixPanel = new MatrixPanel();
	protected SimplePanel simplePanel = new SimplePanel();
	
	// wait dialog
	protected DialogBox waitDialog;
	
	// currently selected model
	protected String modelName = TEST_GLMM;
	
    public InputWizardPanel()
    {
        VerticalPanel container = new VerticalPanel();

        // create a wait dialog
        waitDialog = createWaitDialog();
        
        // add the default widgets to the stack of wizard panels - add the upload panel by default
        panelStack.add(startPanel);
        panelStack.add(uploadPanel);
        panelStack.add(optionsPanel);
        panelStack.add(resultsPanel);
        panelStack.showWidget(0);
        // add the wizard panels and navigation buttons
        container.add(panelStack);
        container.add(navPanel);

        // set up the navigation callbacks
        navPanel.addNavigationListener(this);
        
        // listen for start panel events to determine which input panel to display
    	startPanel.addListener(this);
    	
        // enable next/cancel, disable previous button on start page
        navPanel.setPrevious(false);
        navPanel.setNext(true);
        navPanel.setCancel(true);
       
        // add style to container and panel stack
        panelStack.setStyleName(STYLE);
        container.setStyleName(STYLE);
        
        // initialize the composite widget
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
                navPanel.setPrevious(false);
                navPanel.setNext(true);
            }
            else
            {
                navPanel.setNext(true);
                navPanel.setPrevious(true);
            }
        }
    }
    
    public void onNext()
    {    	
        int visibleIndex = panelStack.getVisibleWidget();
        // if we are currently at the options panel, submit the data and
        // fill in the results panel
        if (visibleIndex == OPTIONS_INDEX) retrieveResults();      
        // navigate to the next panel
        if (visibleIndex < RESULTS_INDEX)
        {
            panelStack.showWidget(++visibleIndex);
            if (visibleIndex == RESULTS_INDEX)
            {
                navPanel.setNext(false);
                navPanel.setPrevious(true);
            }
            else
            {
                navPanel.setNext(true);
                navPanel.setPrevious(true);
            }
        }
    }
    
    public void onCancel()
    {
        panelStack.showWidget(0);
        navPanel.setNext(true);
        navPanel.setPrevious(false);
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
    	this.modelName = modelName;
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
    
    private void retrieveResults()
    {    	
        waitDialog.center();
    	RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, POWER_URL + modelName);
    	try 
    	{
    		Window.alert(buildPowerRequestXML());
    		builder.setHeader("Content-Type", "text/xml");
    		builder.sendRequest(buildPowerRequestXML(), new RequestCallback() {

    			public void onError(Request request, Throwable exception) 
    			{
    				waitDialog.hide();
    				Window.alert("Failed to calculate power: " + exception.getMessage());
    				
    			}

    			public void onResponseReceived(Request request, Response response) 
    			{
    				waitDialog.hide();
    				if (STATUS_CODE_OK == response.getStatusCode() ||
    						STATUS_CODE_CREATED == response.getStatusCode()) 
    				{
    					resultsPanel.setResults(response.getText());
    				} 
    				else 
    				{
    					Window.alert("Failed to calculate power: server error [HTTP STATUS " + response.getStatusCode() + "]");
    				}
    			}
    		});
    	} 
    	catch (Exception e) 
    	{
    		Window.alert("Failed to send the request: " + e.getMessage());
    	}
    }

    private String buildPowerRequestXML()
    {
    	return simplePanel.getStudyXML(true);
    }
    
    private DialogBox createWaitDialog()
    {
        DialogBox dialogBox = new DialogBox();
        HTML text = new HTML("Processing, Please Wait...");
        text.setStyleName("waitDialogText");
        dialogBox.setStyleName("waitDialog");
        dialogBox.setWidget(text);
        return dialogBox;
    }
}
