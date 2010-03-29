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
import com.google.gwt.xml.client.Document;

import edu.cudenver.bios.powercalculator.client.PowerCalculatorGUI;
import edu.cudenver.bios.powercalculator.client.listener.ModelSelectListener;
import edu.cudenver.bios.powercalculator.client.listener.NavigationListener;
import edu.cudenver.bios.powercalculator.client.listener.OptionsListener;
import edu.cudenver.bios.powercalculator.client.listener.StudyUploadListener;

public class InputWizardPanel extends Composite 
implements NavigationListener, OptionsListener, StudyUploadListener, ModelSelectListener
{
	private static final String UPLOAD_PARAM = "u";
	
    private static final int PANEL_STACK_START = 0;
    private static final int PANEL_STACK_STUDY_DESIGN = 1;
    private static final int PANEL_STACK_OPTIONS = 2;
    private static final int PANEL_STACK_RESULTS = 3;

	private static final int STATUS_CODE_OK = 200;
	private static final int STATUS_CODE_CREATED = 201;
	private static final String POWER_URL = "/restcall/power/power/model/";
    private static final String SAMPLE_SIZE_URL = "/restcall/power/samplesize/model/";
	
	protected static final String STYLE = "inputPanel";
	protected static final String DECK_STYLE = "inputPanelDeck";
	// top steps left panel
	protected StepsLeftPanel stepsLeftPanel = new StepsLeftPanel();
	// bottom navigation buttons
	protected NavigationPanel navPanel = new NavigationPanel();
	// stack of panels for the wizard
	protected DeckPanel panelStack = new DeckPanel();
	// create study panel
	protected CreateNewStudyPanel newStudyPanel;
	// upload existing study panel
	protected UploadPanel existingStudyPanel;
	
    // options panel (always in deck after input panel)
	protected OptionsPanel optionsPanel = new OptionsPanel();
    // results panel (always in deck after options panel)
	protected ResultsPanel resultsPanel = new ResultsPanel();

    // potential input panels.  Added to the deck depending on the input type
    // selected on the start panel
	protected StudyDesignPanel studyDesignPanel = new StudyDesignPanel();
	
	// wait dialog
	protected DialogBox waitDialog;
	
	// currently selected model
	protected boolean newStudy = true;
	protected String modelName = PowerCalculatorGUI.constants.modelGLMM();
	protected boolean showCurve = false;
	protected CurveOptions curveOpts = null;
	protected boolean solveForPower = true;
	
    public InputWizardPanel() 
    {
        VerticalPanel container = new VerticalPanel();

        // create a wait dialog
        waitDialog = createWaitDialog();
        
        // add the widgets to the stack of wizard panels
        // NOTE: order matters here - must match stack index constants
        String uploadStudy = Window.Location.getParameter(UPLOAD_PARAM);
        if (uploadStudy != null && uploadStudy.equals("1"))
        {
        	existingStudyPanel = new UploadPanel();
            panelStack.add(existingStudyPanel);
            
            // upload panel notifies study design panel when study is uploaded
            existingStudyPanel.addStudyUploadListener(this);
            existingStudyPanel.addStudyUploadListener(studyDesignPanel);
        }
        else
        {
        	newStudyPanel = new CreateNewStudyPanel();
            panelStack.add(newStudyPanel);
            
            // listen for model name changes from the create study panel
            newStudyPanel.addModelSelectListener(this);
            newStudyPanel.addModelSelectListener(studyDesignPanel);
            newStudyPanel.addModelSelectListener(optionsPanel);
        }
        panelStack.add(studyDesignPanel);
        panelStack.add(optionsPanel);
        panelStack.add(resultsPanel);
        // add the wizard panels and navigation buttons
        container.add(stepsLeftPanel);
        container.add(panelStack);
        container.add(navPanel);

        // set up the navigation callbacks
        navPanel.addNavigationListener(this);

        // intialize the wizard
        initWizard();
    	
    	// listener for resize events on the essence matrix to allow 
    	// updating power/sample size options 
    	studyDesignPanel.addEssenceMatrixResizeListener(optionsPanel);
    	studyDesignPanel.addEssenceMatrixMetaDataListener(optionsPanel);
    	
    	// listener for options panel events to determine if we are solving for sample size or power
    	optionsPanel.addListener(this);
       
        // add style to container and panel stack
        panelStack.setStyleName(DECK_STYLE);
        container.setStyleName(STYLE);
        
        // initialize the composite widget
        initWidget(container);
    }
    
    public void onPrevious()
    {
        int visibleIndex = panelStack.getVisibleWidget();
        switch(visibleIndex)
        {
        case PANEL_STACK_STUDY_DESIGN:
                panelStack.showWidget(PANEL_STACK_START);
                initWizard();
            break;
        case PANEL_STACK_OPTIONS:
            panelStack.showWidget(PANEL_STACK_STUDY_DESIGN);
            break;
        case PANEL_STACK_RESULTS:
            panelStack.showWidget(PANEL_STACK_OPTIONS);
            break;
        default:
            break;
        };

        stepsLeftPanel.onPrevious();
    }
    
    private void initWizard()
    {
        panelStack.showWidget(PANEL_STACK_START);
    }
    
    public void onNext()
    {    	
        int visibleIndex = panelStack.getVisibleWidget();
        switch(visibleIndex)
        {
        case PANEL_STACK_START:
            panelStack.showWidget(PANEL_STACK_STUDY_DESIGN);
            break;
        case PANEL_STACK_STUDY_DESIGN:
            panelStack.showWidget(PANEL_STACK_OPTIONS);
            break;
        case PANEL_STACK_OPTIONS:
            retrieveResults();
            panelStack.showWidget(PANEL_STACK_RESULTS);
            break;
        default:
            break;
        };
        
        stepsLeftPanel.onNext();
    }
    
    public void onCancel()
    {
    	panelStack.showWidget(PANEL_STACK_START);
    	stepsLeftPanel.onCancel();
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
    	buffer.append("<power>");
    	buffer.append(optionsPanel.getGraphicsOptions());
		buffer.append("<params " + studyDesignPanel.getStudyAttributes() + " " +
		        optionsPanel.getPowerAttributes() + ">");
		buffer.append(studyDesignPanel.getStudyXML(optionsPanel.getRowMetaDataXML()));
		buffer.append("</params></power>");
        Window.alert(buffer.toString());
    	return buffer.toString();
    }
    
    private String buildSampleSizeRequestXML()
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append("<sampleSize>");
        buffer.append(optionsPanel.getGraphicsOptions());
        buffer.append("<params " + studyDesignPanel.getStudyAttributes() + " " +
                optionsPanel.getSampleSizeAttributes() + ">");
        buffer.append(studyDesignPanel.getStudyXML(optionsPanel.getRowMetaDataXML()));
        buffer.append("</params></sampleSize>");
        Window.alert(buffer.toString());
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
    
    public void onShowCurve(boolean show, CurveOptions opts)
    {
        showCurve = show;
        curveOpts = opts;
    }
    
    public void onSolveFor(boolean power)
    {
        solveForPower = power;
    }
   

    
    public void onStudyUpload(Document doc)
    {
        this.onNext();
    }
    
    public void onModelSelect(String modelName)
    {
        this.modelName = modelName;
    }
}
