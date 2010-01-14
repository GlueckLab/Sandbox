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
    // model names supported by power REST service
    public String modelGLMM();
    public String modelOneSampleStudentsT();
    // human-readable labels for the models
	public String labelGLMM();
	public String labelOneSampleStudentsT();

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
	public String textStartPanelDescription();
	public String listBoxModel();
	public String radioGroupLabelStudyInput();
	public String radioButtonBasicInput();
	public String radioButtonMatrixInput();
	public String radioButtonUploadInput();

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
	public String matrixRhoGY();
	public String matrixRhoGYDetails();
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
}
