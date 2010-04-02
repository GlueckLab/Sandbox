package edu.cudenver.bios.powercalculator.client;

import com.google.gwt.i18n.client.Constants;

/**
 * Constants for internationalization.  String calls below will be mapped 
 * to the properties file for the current locale.
 * 
 * @author Sarah Kreidler
 *
 */
public interface PowerCalculatorConstants extends Constants
{
    // some shared style names
    public static final String STYLE_WIZARD_STEP_PANEL = "wizardStepPanel";
    public static final String STYLE_WIZARD_STEP_INPUT_CONTAINER = "wizardStepInputContainer";
    public static final String STYLE_WIZARD_STEP_HEADER = "wizardStepHeader";
    public static final String STYLE_WIZARD_STEP_DESCRIPTION = "wizardStepDescription";
    public static final String STYLE_WIZARD_STEP_SUBPANEL = "subpanel";
    
	// wizard navigation button labels
	public String buttonNext();
	public String buttonPrevious();
	public String buttonCancel();	
	
	// save study button
	public String buttonSaveStudy();
	
	// labels for "steps left" navigation bar	
	public String stepSpacer();
	public String stepStart();
	public String stepStudy();
	public String stepOptions();
	public String stepResults();
	
	// Start panel constants	
	public String textStartPanelHeader();
	public String textStartPanelDescription();
    public String buttonNewStudy();
    public String buttonExistingStudy();    
    
    // create new study panel constants
    public String textCreateNewStudyPanelHeader();
    public String textCreateNewStudyPanelDescription();
	public String listBoxModel();
    // model names supported by power REST service
    public String modelGLMM();
    public String modelOneSampleStudentsT();
    // human-readable labels for the models
    public String labelGLMM();
    public String labelOneSampleStudentsT();
    
    // upload existing study panel constants
    public String textExistingStudyPanelHeader();
    public String textExistingStudyPanelDescription();
    
	// text inputs
	public String textLabelAlpha();
	public String textLabelPower();
	public String textLabelSampleSize();
	public String textLabelMu0();
	public String textLabelMuA();
	public String textLabelSigma();
	
	// matrix input view names
	public String matrixDimensionSeparator();
	public String matrixBeta();
	public String matrixBetaDetails();
	public String matrixDesignEssence();
	public String matrixDesignEssenceDetails();
	public String matrixThetaNull();
	public String matrixThetaNullDetails();
	public String matrixSigmaError();
	public String matrixSigmaErrorDetails();
	public String matrixSigmaG();
	public String matrixSigmaGDetails();
	public String matrixSigmaY();
	public String matrixSigmaYDetails();
	public String matrixSigmaYG();
	public String matrixSigmaYGDetails();
	public String matrixBetweenSubjectContrast();
	public String matrixBetweenSubjectContrastDetails();
	public String matrixWithinSubjectContrast();
	public String matrixWithinSubjectContrastDetails();

	// power / sample size options
	public String radioButtonPower();
	public String radioButtonSampleSize();
	public String checkBoxShowCurve();
	public String textLabelCurveTitle();
	public String textLabelCurveXAxis();
	public String textLabelCurveYAxis();
	public String panelLabelOptionsDetails();
	public String panelLabelOptionsGraphics();
	public String panelLabelOptionsSolveFor();
	
	// error messages
	public String errorMatrixDimensionInvalid();
	public String errorAlphaInvalid();
	public String errorMeanInvalid();
	public String errorVarianceInvalid();
	
	// ok messages
	public String okay();
}
