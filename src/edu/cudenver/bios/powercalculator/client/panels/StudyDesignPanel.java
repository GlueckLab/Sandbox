package edu.cudenver.bios.powercalculator.client.panels;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.xml.client.Document;

import edu.cudenver.bios.powercalculator.client.PowerCalculatorConstants;
import edu.cudenver.bios.powercalculator.client.PowerCalculatorGUI;
import edu.cudenver.bios.powercalculator.client.listener.InputWizardStepListener;
import edu.cudenver.bios.powercalculator.client.listener.MatrixResizeListener;
import edu.cudenver.bios.powercalculator.client.listener.MetaDataListener;
import edu.cudenver.bios.powercalculator.client.listener.ModelSelectListener;
import edu.cudenver.bios.powercalculator.client.listener.StartListener;
import edu.cudenver.bios.powercalculator.client.listener.StudyUploadListener;

public class StudyDesignPanel extends Composite
implements StartListener, ClickHandler
{
	private static final String SAVEAS_URL = "/webapps/file/saveas"; 
	private static final int MATRIX_MODE = 0;
	private static final int TEMPLATE_MODE = 1;
	
	// matrix and template mode panels
    protected MatrixPanel matrixPanel;
    protected TemplateDesignPanel templatePanel;
    // deck panel to hold the two mode panels
    protected DeckPanel modeDeck = new DeckPanel();
    // form for saving the study design
	protected FormPanel form = new FormPanel("_blank");
	protected Hidden matrixXML = new Hidden("data");
	
	// current input mode
    protected boolean isTemplateMode = true;

    public StudyDesignPanel(InputWizardStepListener wizard, int stepIndex)
    {
        VerticalPanel designContainer = new VerticalPanel();

        // create the subpanels for matrix mode and template mode
        matrixPanel = new MatrixPanel(wizard, stepIndex);
        templatePanel = new TemplateDesignPanel(wizard, stepIndex);
        
        // header, description
        HTML header = new HTML("Design study");
        HTML description = new HTML("Fill in the details of your study design using either the matrix view (for users familier with linear models) or the design view (beginning users)");
        
    	// note, order must match indices listed above
        modeDeck.add(matrixPanel);
        modeDeck.add(templatePanel);
        modeDeck.showWidget(TEMPLATE_MODE);
        
		// add the save study link and associated form
		form.setAction(SAVEAS_URL);
		form.setMethod(FormPanel.METHOD_POST);
		VerticalPanel formContainer = new VerticalPanel();
		formContainer.add(matrixXML);
		formContainer.add(new Hidden("filename", "study.xml"));
		form.add(formContainer);
		// save button
		Button saveButton = new Button(PowerCalculatorGUI.constants.buttonSaveStudy(), this);
  
        // build the user input section
        VerticalPanel inputContainer = new VerticalPanel();
        inputContainer.add(modeDeck);
        inputContainer.add(saveButton);
        inputContainer.add(form);
        
		// build the panel layout
		designContainer.add(header);
		designContainer.add(description);        
		designContainer.add(inputContainer);
		
        // TODO: add style
		saveButton.setStyleName(PowerCalculatorConstants.STYLE_WIZARD_STEP_BUTTON);
        designContainer.setStyleName(PowerCalculatorConstants.STYLE_WIZARD_STEP_PANEL);
        header.setStyleName(PowerCalculatorConstants.STYLE_WIZARD_STEP_HEADER);
        description.setStyleName(PowerCalculatorConstants.STYLE_WIZARD_STEP_DESCRIPTION);
        inputContainer.setStyleName(PowerCalculatorConstants.STYLE_WIZARD_STEP_INPUT_CONTAINER);

        initWidget(designContainer);
    }
    
    public void addEssenceMatrixResizeListener(MatrixResizeListener listener)
    {
        matrixPanel.addEssenceMatrixResizeListener(listener);
    }
    
    public void addEssenceMatrixMetaDataListener(MetaDataListener listener)
    {
        matrixPanel.addEssenceMatrixMetaDataListener(listener);
    }
    
    public void onTemplateMode()
    {
        isTemplateMode = true;
        modeDeck.showWidget(TEMPLATE_MODE);
    }
    
    public void onMatrixMode()
    {
        isTemplateMode = false;
        modeDeck.showWidget(MATRIX_MODE);
    }
    
    public void onStudyUpload(Document studyDoc, String modelName)
    {
        if (isTemplateMode)
            templatePanel.loadFromXMLDocument(studyDoc);
        else
            matrixPanel.loadFromXMLDocument(studyDoc);
    }
    
    public String getStudyAttributes()
    {
        if (isTemplateMode)
            return templatePanel.getStudyAttributes();
        else
            return matrixPanel.getStudyAttributes();
    }
    
    public String getStudyXML(int totalN)
    {
        if (isTemplateMode)
            return templatePanel.getStudyXML(totalN);
        else
            return matrixPanel.getStudyXML(totalN);
    }
    
    public void onClick(ClickEvent e)
    {
    	matrixXML.setValue("<study mode='" + (isTemplateMode ? "template" : "matrix") + "'>" + 
    			matrixPanel.getStudyXML(0) + "</study>");
    	form.submit();    	
    }

}
