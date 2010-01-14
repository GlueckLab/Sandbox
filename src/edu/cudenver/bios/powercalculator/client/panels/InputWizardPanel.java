package edu.cudenver.bios.powercalculator.client.panels;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.cudenver.bios.powercalculator.client.PowerCalculatorGUI;

public class InputWizardPanel extends Composite 
implements NavigationListener, StartListener, OptionsListener
{
	private static final int STATUS_CODE_OK = 200;
	private static final int STATUS_CODE_CREATED = 201;
	private static final String POWER_URL = "/restcall/power/power/model/";
    private static final String SAMPLE_SIZE_URL = "/restcall/power/samplesize/model/";
    
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
	protected OptionsPanel optionsPanel = new OptionsPanel(PowerCalculatorGUI.constants.modelGLMM());
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
	protected String modelName = PowerCalculatorGUI.constants.modelGLMM();
	protected boolean showCurve = false;
	protected boolean solveForPower = true;
	
    public InputWizardPanel()
    {
        VerticalPanel container = new VerticalPanel();

        // create a wait dialog
        waitDialog = createWaitDialog();
        
        // add the default widgets to the stack of wizard panels - add the upload panel by default
        panelStack.add(startPanel);
        panelStack.add(basicPanel);
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
    	
    	// listener for resize events on the essence matrix to allow 
    	// updating power/sample size options 
    	matrixPanel.addEssenceMatrixResizeListener(optionsPanel);
    	matrixPanel.addEssenceMatrixMetaDataListener(optionsPanel);
    	
    	// listener for options panel events to determine if we are solving for sample size or power
    	optionsPanel.addListener(this);
    	
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
    	if (!PowerCalculatorGUI.constants.modelGLMM().equals(modelName))
    	{
    	    panelStack.remove(INPUT_INDEX);
    	    panelStack.insert(simplePanel, INPUT_INDEX);
    	}
    	// update the power/sample size options panel
    	optionsPanel.setModel(modelName);
    }
    
    public void addNavigationListener(NavigationListener listener)
    {
        navPanel.addNavigationListener(listener);
    }
    
    private void retrieveResults()
    {    	
        waitDialog.center();
    	RequestBuilder builder = null;
    	if (solveForPower)
    	    builder = new RequestBuilder(RequestBuilder.POST, POWER_URL + modelName);
    	else
            builder = new RequestBuilder(RequestBuilder.POST, SAMPLE_SIZE_URL + modelName);

    	try 
    	{
    		builder.setHeader("Content-Type", "text/xml");
    		builder.sendRequest((solveForPower ? buildPowerRequestXML() : buildSampleSizeRequestXML()),
    		        new RequestCallback() {

    			public void onError(Request request, Throwable exception) 
    			{
    				waitDialog.hide();
    				Window.alert("Calculation failed: " + exception.getMessage());	
    			}

    			public void onResponseReceived(Request request, Response response) 
    			{
    				waitDialog.hide();
    				if (STATUS_CODE_OK == response.getStatusCode() ||
    						STATUS_CODE_CREATED == response.getStatusCode()) 
    				{
    				    if (solveForPower)
    				        resultsPanel.setPowerResults(response.getText());
    				    else
    				        resultsPanel.setSampleSizeResults(response.getText());
    				} 
    				else 
    				{
    					Window.alert("Calculation failed: [HTTP STATUS " + 
    					        response.getStatusCode() + "] " + response.getText());
    				}
    			}
    		});
    	} 
    	catch (Exception e) 
    	{
			waitDialog.hide();
    		Window.alert("Failed to send the request: " + e.getMessage());
    	}
    }

    private String buildPowerRequestXML()
    {
    	StringBuffer buffer = new StringBuffer();
    	
		buffer.append("<power curve='" + showCurve + "' curveTitle='' curveXaxis='' curveYAxis='' >");
		
    	if (PowerCalculatorGUI.constants.modelGLMM().equals(modelName))
    	{
    	    buffer.append("<params alpha='" + matrixPanel.getAlpha() + "' statistic='"+ optionsPanel.getStatistic() +"'>");
    		buffer.append(matrixPanel.getStudyXML(optionsPanel.getRowMetaDataXML()));
    		buffer.append("</params>");
    	}
    	else if (PowerCalculatorGUI.constants.modelOneSampleStudentsT().equals(modelName))
    	{
    		buffer.append("<params alpha='" + simplePanel.getAlpha() + "' ");
    		buffer.append(" mu0='" + simplePanel.getNullMean() + "' ");
    		buffer.append(" muA='" + simplePanel.getAlternativeMean() + "' ");
    		buffer.append(" sigma='" + simplePanel.getSigma() + "' ");
    		buffer.append(" sampleSize='" + optionsPanel.getSampleSize() + "' />");
    	}

 		buffer.append("</power>");
        Window.alert(buffer.toString());
    	return buffer.toString();
    }
    
    private String buildSampleSizeRequestXML()
    {
    	StringBuffer buffer = new StringBuffer();
    	
		buffer.append("<sampleSize " + " curve='" + showCurve + "'>");
    	if (PowerCalculatorGUI.constants.modelGLMM().equals(modelName))
    	{
    		
    	}
    	else if (PowerCalculatorGUI.constants.modelOneSampleStudentsT().equals(modelName))
    	{
            buffer.append("<params alpha='" + simplePanel.getAlpha() + "' ");
            buffer.append(" mu0='" + simplePanel.getNullMean() + "' ");
            buffer.append(" muA='" + simplePanel.getAlternativeMean() + "' ");
            buffer.append(" sigma='" + simplePanel.getSigma() + "' ");
            buffer.append(" power='" + optionsPanel.getPower() + "' />");
    	}
 		buffer.append("</sampleSize>");
        //Window.alert(buffer.toString());
    	return buffer.toString();
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
    
    public void onShowCurve(boolean show)
    {
        showCurve = show;
    }
    
    public void onSolveFor(boolean power)
    {
        solveForPower = power;
    }
    
}
