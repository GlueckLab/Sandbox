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
	// wizard navigation buttons
	public String nextButton();
	public String previousButton();
	public String cancelButton();	
	
	// constants for "steps left" navigation bar	
	public String stepSpacer();
	
	public String startStep();
	
	public String studyStep();
	
	public String optionsStep();
	
	public String resultsStep();
	
	// Start panel constants	
	public String startPanelDescriptionText();
	
	public String startPanelModelText();
	
	public String startPanelStudyInputText();
	
	public String basicInputRadioButton();
	
	public String matrixInputRadioButton();
	
	public String uploadInputRadioButton();
	
	// Statistical Model names
	public String oneSampleStudentsT();
	
	public String glmm();
	
	// matrix input view names
	public String matrixDimensionInvalidMessage();
	public String matrixDimensionSeparator();
	public String matrixBeta();
	public String matrixBetaDetails();
	public String matrixDesignEssence();
	public String matrixDesignEssenceDetails();
	public String matrixThetaNull();
	public String matrixThetaNullDetails();
	public String matrixSigma();
	public String matrixSigmaDetails();
	public String matrixBetweenSubjectContrast();
	public String matrixBetweenSubjectContrastDetails();
	public String matrixWithinSubjectContrast();
	public String matrixWithinSubjectContrastDetails();
	public String saveStudyButton();
	
}
