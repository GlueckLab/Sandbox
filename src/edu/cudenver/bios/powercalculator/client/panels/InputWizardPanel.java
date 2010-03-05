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
import edu.cudenver.bios.powercalculator.client.listener.NavigationListener;
import edu.cudenver.bios.powercalculator.client.listener.OptionsListener;
import edu.cudenver.bios.powercalculator.client.listener.StartListener;
import edu.cudenver.bios.powercalculator.client.listener.StudyUploadListener;

public class InputWizardPanel extends Composite 
implements NavigationListener, StartListener, OptionsListener, StudyUploadListener
{
    private static final int PANEL_STACK_START = 0;
    private static final int PANEL_STACK_NEW_STUDY = 1;
    private static final int PANEL_STACK_EXISTING_STUDY = 2;
    private static final int PANEL_STACK_STUDY_DESIGN = 3;
    private static final int PANEL_STACK_OPTIONS = 4;
    private static final int PANEL_STACK_RESULTS = 5;

	private static final int STATUS_CODE_OK = 200;
	private static final int STATUS_CODE_CREATED = 201;
	private static final String POWER_URL = "/restcall/power/power/model/";
    private static final String SAMPLE_SIZE_URL = "/restcall/power/samplesize/model/";
	
	protected static final String STYLE = "inputPanel";
	
	// top steps left panel
	protected StepsLeftPanel stepsLeftPanel = new StepsLeftPanel();
	// bottom navigation buttons
	protected NavigationPanel navPanel = new NavigationPanel();
	// stack of panels for the wizard
	protected DeckPanel panelStack = new DeckPanel();
    // start panel
	protected StartPanel startPanel = new StartPanel();
	// create study panel
	protected CreateNewStudyPanel newStudyPanel = new CreateNewStudyPanel();
	// upload existing study panel
	protected UploadPanel existingStudyPanel = new UploadPanel();
	
    // options panel (always in deck after input panel)
	protected OptionsPanel optionsPanel = new OptionsPanel(PowerCalculatorGUI.constants.modelGLMM());
    // results panel (always in deck after options panel)
	protected ResultsPanel resultsPanel = new ResultsPanel();

    // potential input panels.  Added to the deck depending on the input type
    // selected on the start panel
	protected UploadPanel uploadPanel = new UploadPanel();
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
        panelStack.add(startPanel);
        panelStack.add(newStudyPanel);
        panelStack.add(existingStudyPanel);
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
        startOver(true);
        
        // setup callbacks between the panels
        // upload panel notifies study design panel when study is uploaded
        uploadPanel.addStudyUploadListener(this);
        uploadPanel.addStudyUploadListener(studyDesignPanel);
        // listen for start panel to determine if we are creating a new study
        // or using an existing study
    	startPanel.addListener(this);
    	
    	// listener for resize events on the essence matrix to allow 
    	// updating power/sample size options 
    	studyDesignPanel.addEssenceMatrixResizeListener(optionsPanel);
    	studyDesignPanel.addEssenceMatrixMetaDataListener(optionsPanel);
    	
    	// listener for options panel events to determine if we are solving for sample size or power
    	optionsPanel.addListener(this);
       
        // add style to container and panel stack
        panelStack.setStyleName(STYLE);
        container.setStyleName(STYLE);
        
        // initialize the composite widget
        initWidget(container);
    }
    
    public void onPrevious()
    {
        int visibleIndex = panelStack.getVisibleWidget();
        switch(visibleIndex)
        {
        case PANEL_STACK_NEW_STUDY:
        case PANEL_STACK_EXISTING_STUDY:
            startOver(false);
            break;
        case PANEL_STACK_STUDY_DESIGN:
            if (newStudy)
                panelStack.showWidget(PANEL_STACK_NEW_STUDY);
            else
                panelStack.showWidget(PANEL_STACK_EXISTING_STUDY);
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
    
    private void startOver(boolean clear)
    {
        panelStack.showWidget(PANEL_STACK_START);
        // hide the nav panel and steps left on the start screen
        navPanel.setVisible(false);
        stepsLeftPanel.setVisible(false);
        stepsLeftPanel.onCancel();
    }
    
    public void onNext()
    {    	
        int visibleIndex = panelStack.getVisibleWidget();
        switch(visibleIndex)
        {
        case PANEL_STACK_NEW_STUDY:
            panelStack.showWidget(PANEL_STACK_STUDY_DESIGN);
            break;
        case PANEL_STACK_EXISTING_STUDY:
            panelStack.showWidget(PANEL_STACK_STUDY_DESIGN);
            break;
        case PANEL_STACK_STUDY_DESIGN:
            panelStack.showWidget(PANEL_STACK_OPTIONS);
            break;
        case PANEL_STACK_OPTIONS:
            panelStack.showWidget(PANEL_STACK_RESULTS);
            break;
        default:
            break;
        };
        
        if (visibleIndex != PANEL_STACK_START)
            stepsLeftPanel.onNext();
    }
    
    public void onCancel()
    {
        startOver(true);
    }
    
    public void onModelSelect(String modelName)
    {
//    	this.modelName = modelName;
//    	if (!PowerCalculatorGUI.constants.modelGLMM().equals(modelName))
//    	{
//    	    panelStack.remove(INPUT_INDEX);
//    	    panelStack.insert(simplePanel, INPUT_INDEX);
//    	}
//    	// update the power/sample size options panel
//    	optionsPanel.setModel(modelName);
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

//    	try 
//    	{
//    		builder.setHeader("Content-Type", "text/xml");
//    		builder.sendRequest((solveForPower ? buildPowerRequestXML() : buildSampleSizeRequestXML()),
//    		        new RequestCallback() {
//
//    			public void onError(Request request, Throwable exception) 
//    			{
//    				waitDialog.hide();
//    				Window.alert("Calculation failed: " + exception.getMessage());	
//    			}
//
//    			public void onResponseReceived(Request request, Response response) 
//    			{
//    				waitDialog.hide();
//    				if (STATUS_CODE_OK == response.getStatusCode() ||
//    						STATUS_CODE_CREATED == response.getStatusCode()) 
//    				{
//    				    if (solveForPower)
//    				        resultsPanel.setPowerResults(response.getText());
//    				    else
//    				        resultsPanel.setSampleSizeResults(response.getText());
//    				} 
//    				else 
//    				{
//    					Window.alert("Calculation failed: [HTTP STATUS " + 
//    					        response.getStatusCode() + "] " + response.getText());
//    				}
//    			}
//    		});
//    	} 
//    	catch (Exception e) 
//    	{
//			waitDialog.hide();
//    		Window.alert("Failed to send the request: " + e.getMessage());
//    	}
    }

//    private String buildPowerRequestXML()
//    {
//    	StringBuffer buffer = new StringBuffer();
//		buffer.append("<power curve='" + showCurve + "' ");
//		if (curveOpts != null)
//		    buffer.append("curveTitle='" + curveOpts.title + 
//		        "' curveXaxis='" + curveOpts.xAxisLabel + "' curveYAxis='" + curveOpts.yAxisLabel + "'");
//		buffer.append(">");
//
//    	if (PowerCalculatorGUI.constants.modelGLMM().equals(modelName))
//    	{
//    	    buffer.append("<params alpha='" + matrixPanel.getAlpha() + "' statistic='"+ optionsPanel.getStatistic() +
//    	            "' powerMethod='" + optionsPanel.getPowerMethod() +"'>");
//    		buffer.append(matrixPanel.getStudyXML(optionsPanel.getRowMetaDataXML()));
//    		buffer.append("</params>");
//    	}
//    	else if (PowerCalculatorGUI.constants.modelOneSampleStudentsT().equals(modelName))
//    	{
//    		buffer.append("<params alpha='" + simplePanel.getAlpha() + "' ");
//    		buffer.append(" mu0='" + simplePanel.getNullMean() + "' ");
//    		buffer.append(" muA='" + simplePanel.getAlternativeMean() + "' ");
//    		buffer.append(" sigma='" + simplePanel.getSigma() + "' ");
//    		buffer.append(" sampleSize='" + optionsPanel.getSampleSize() + "' />");
//    	}
//
// 		buffer.append("</power>");
//        Window.alert(buffer.toString());
//    	return buffer.toString();
//    }
//    
//    private String buildSampleSizeRequestXML()
//    {
//    	StringBuffer buffer = new StringBuffer();
//    	
//		buffer.append("<sampleSize " + " curve='" + showCurve + "'>");
//    	if (PowerCalculatorGUI.constants.modelGLMM().equals(modelName))
//    	{
//    		
//    	}
//    	else if (PowerCalculatorGUI.constants.modelOneSampleStudentsT().equals(modelName))
//    	{
//            buffer.append("<params alpha='" + simplePanel.getAlpha() + "' ");
//            buffer.append(" mu0='" + simplePanel.getNullMean() + "' ");
//            buffer.append(" muA='" + simplePanel.getAlternativeMean() + "' ");
//            buffer.append(" sigma='" + simplePanel.getSigma() + "' ");
//            buffer.append(" power='" + optionsPanel.getPower() + "' />");
//    	}
// 		buffer.append("</sampleSize>");
//        //Window.alert(buffer.toString());
//    	return buffer.toString();
//    }
    
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
   
    public void onNewStudy()
    {
        panelStack.showWidget(PANEL_STACK_NEW_STUDY);
        stepsLeftPanel.setVisible(true);
        navPanel.setVisible(true);

    }
    
    public void onExistingStudy()
    {
        panelStack.showWidget(PANEL_STACK_EXISTING_STUDY);
        stepsLeftPanel.setVisible(true);
        navPanel.setVisible(true);
    }
    
    public void onStudyUpload(Document doc)
    {
        Window.alert("hello?");
        this.onNext();
    }
}
