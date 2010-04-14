package edu.cudenver.bios.powercalculator.client.panels;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.NamedNodeMap;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;

import edu.cudenver.bios.powercalculator.client.PowerCalculatorConstants;
import edu.cudenver.bios.powercalculator.client.PowerCalculatorGUI;
import edu.cudenver.bios.powercalculator.client.listener.InputWizardStepListener;
import edu.cudenver.bios.powercalculator.client.listener.MatrixResizeListener;
import edu.cudenver.bios.powercalculator.client.listener.MetaDataListener;
import edu.cudenver.bios.powercalculator.client.listener.StudyDesignChangeListener;

public class MatrixPanel extends Composite
{
    private static final String STYLE = "matrixPanel";
	// these default names derived from linear model theory.
	// ensures that default matrix dimensions conform properly
	private static final int DEFAULT_N = 3;
	private static final int DEFAULT_Q = 3;
	private static final int DEFAULT_P = 2;
	private static final int DEFAULT_A = 2;
	private static final int DEFAULT_B = 1;

	// indices for the sigma panels for fixed vs. random predictors
	private static final int FIXED_INDEX = 0;
	private static final int COVARIATE_INDEX = 1;

	// matrix headers
	protected SubpanelHeader essenceHeader = new SubpanelHeader(PowerCalculatorGUI.constants.matrixDesignEssence(), 
	        PowerCalculatorGUI.constants.matrixDesignEssenceDetails());
	protected SubpanelHeader withinContrastHeader = new SubpanelHeader(PowerCalculatorGUI.constants.matrixWithinSubjectContrast(),
	        PowerCalculatorGUI.constants.matrixWithinSubjectContrastDetails());
	protected SubpanelHeader betweenContrastHeader = new SubpanelHeader(PowerCalculatorGUI.constants.matrixBetweenSubjectContrast(), 
            PowerCalculatorGUI.constants.matrixBetweenSubjectContrastDetails());
	protected SubpanelHeader betaHeader = new SubpanelHeader(PowerCalculatorGUI.constants.matrixBeta(), 
            PowerCalculatorGUI.constants.matrixBetaDetails());
	protected SubpanelHeader thetaNullHeader = new SubpanelHeader(PowerCalculatorGUI.constants.matrixThetaNull(), 
            PowerCalculatorGUI.constants.matrixThetaNullDetails());
	protected SubpanelHeader sigmaErrorHeader = new SubpanelHeader(PowerCalculatorGUI.constants.matrixSigmaError(), 
            PowerCalculatorGUI.constants.matrixSigmaErrorDetails());
	protected SubpanelHeader sigmaCovariateHeader = new SubpanelHeader(PowerCalculatorGUI.constants.matrixSigmaG(), 
            PowerCalculatorGUI.constants.matrixSigmaGDetails());
	protected SubpanelHeader sigmaOutcomesHeader = new SubpanelHeader(PowerCalculatorGUI.constants.matrixSigmaY(), 
            PowerCalculatorGUI.constants.matrixSigmaYDetails());
	protected SubpanelHeader sigmaCovariateOutcomeHeader = new SubpanelHeader(PowerCalculatorGUI.constants.matrixSigmaYG(), 
            PowerCalculatorGUI.constants.matrixSigmaYGDetails());
	
	// matrix inputs
	protected EssenceMatrix essence = new EssenceMatrix("design", DEFAULT_N, DEFAULT_Q);
	protected ResizableMatrix withinContrast = new ResizableMatrix("withinSubjectContrast", DEFAULT_P, DEFAULT_B);
	protected ResizableMatrix betweenContrast = new ResizableMatrix("betweenSubjectContrast", DEFAULT_A, DEFAULT_Q);
	protected ResizableMatrix beta = new ResizableMatrix("beta", DEFAULT_Q, DEFAULT_P);
	protected ResizableMatrix thetaNull = new ResizableMatrix("theta", DEFAULT_A, DEFAULT_B);
	// variance/covariance matrix for fixed predictors
	protected ResizableMatrix sigmaError = new ResizableMatrix("sigmaError", DEFAULT_P, DEFAULT_P);
	/* the following are needed for a baseline covariate */
	// variance of the baseline covariate
	protected ResizableMatrix sigmaCovariate = new ResizableMatrix("sigmaGaussianRandom", DEFAULT_P, DEFAULT_P);
	// variance/covariance of the outcomes
	protected ResizableMatrix sigmaOutcomes = new ResizableMatrix("sigmaOutcome", DEFAULT_P, DEFAULT_P);
	// correlation of covariate and outcomes
	protected ResizableMatrix sigmaCovariateOutcome = new ResizableMatrix("sigmaOutcomeGaussianRandom", DEFAULT_P, DEFAULT_P);

	protected DeckPanel sigmaDeck = new DeckPanel();
	protected FormPanel form = new FormPanel("_blank");
	protected Hidden matrixXML = new Hidden("data");
	
	protected TextBox alphaTextBox = new TextBox();
	protected HTML alphaErrorHTML = new HTML();
	protected int covariateColumn = -1;
	
    // build the advanced options panel
    AdvancedLinearModelOptionsPanel advOpts = new AdvancedLinearModelOptionsPanel();
    
    // synchronization listener with deisgn view
    ArrayList<StudyDesignChangeListener> listeners = new ArrayList<StudyDesignChangeListener>();
    
    // tell wizard this step is complete
    InputWizardStepListener wizard;
    int stepIndex;
    
	public MatrixPanel(InputWizardStepListener w, int idx)
	{
	    this.wizard = w;
	    this.stepIndex = idx;
	    
		VerticalPanel panel = new VerticalPanel();
		
		// build alpha input
		Grid alpha = new Grid(1, 3);
		alpha.setWidget(0, 0, new HTML(PowerCalculatorGUI.constants.textLabelAlpha()));
		alpha.setWidget(0, 1, alphaTextBox);
		alpha.setWidget(0, 2, alphaErrorHTML);
		alphaErrorHTML.setStyleName(PowerCalculatorConstants.STYLE_MESSAGE);
		alphaErrorHTML.setStyleName(PowerCalculatorConstants.STYLE_MESSAGE_ERROR);
		
		panel.add(alpha);
		panel.add(advOpts);
		// add each header / matrix to the panel
		panel.add(essenceHeader);
		panel.add(essence);
		panel.add(betaHeader);
		panel.add(beta);
		panel.add(sigmaDeck);
		panel.add(thetaNullHeader);
		panel.add(thetaNull);
		panel.add(betweenContrastHeader);
		panel.add(betweenContrast);
		panel.add(withinContrastHeader);
		panel.add(withinContrast);
		// deck panel since we have different variance/covariance matrices
		// for all fixed predictors vs. a baseline covariate 
	    VerticalPanel covariateSigma = new VerticalPanel();
	    covariateSigma.add(sigmaCovariateOutcomeHeader);
	    covariateSigma.add(sigmaCovariateOutcome);
	    covariateSigma.add(sigmaCovariateHeader);
	    covariateSigma.add(sigmaCovariate);
	    covariateSigma.add(sigmaOutcomesHeader);
	    covariateSigma.add(sigmaOutcomes);
	    VerticalPanel fixedOnlySigma = new VerticalPanel();
	    fixedOnlySigma.add(sigmaErrorHeader);
	    fixedOnlySigma.add(sigmaError);
	    sigmaDeck.add(fixedOnlySigma);
	    sigmaDeck.add(covariateSigma);
	    sigmaDeck.showWidget(FIXED_INDEX);
		panel.add(sigmaDeck);
		
        alphaTextBox.addChangeHandler(new ChangeHandler() {
            // 
            public void onChange(ChangeEvent e)
            {
                String alpha = alphaTextBox.getText();
                if (validAlpha(alphaTextBox.getText()))
                {
                    displayOkay(alphaErrorHTML, PowerCalculatorGUI.constants.okay());
                    wizard.onStepComplete(stepIndex);
                }
                else
                {
                    alpha = "";
                    displayError(alphaErrorHTML, PowerCalculatorGUI.constants.errorAlphaInvalid());
                    alphaTextBox.setText(alpha);
                    wizard.onStepInProgress(stepIndex);
                }
                for(StudyDesignChangeListener listener: listeners) listener.onAlpha(alpha);
            }
        });
		
		// set matrix size restrictions
        sigmaError.setIsSquare(true);
        sigmaCovariate.setIsSquare(true);
        sigmaOutcomes.setIsSquare(true);
		
		// add listeners to ensure matrix conformance when each 
		// matrix changes
		essence.addMatrixResizeListener(new MatrixResizeListener() {
			public void onMatrixResize(int rows, int cols)
			{
				beta.setDimensions(cols, beta.getColumnDimension());
				betweenContrast.setDimensions(betweenContrast.getRowDimension(), cols);
			}
		});
		essence.addMetaDataListener(new MetaDataListener() {
		    public void onRandom(int col, double mean, double variance)
		    {
		        sigmaDeck.showWidget(COVARIATE_INDEX);
		        covariateColumn = col;
		    }
		    
		    public void onFixed(int col)
		    {
		        if (col == covariateColumn)
		        {
		            covariateColumn = -1;
		            sigmaDeck.showWidget(FIXED_INDEX);
		        }
		    }
		    
		    public void onRowName(int row, String name)
		    {
		    }
		});
		betweenContrast.addMatrixResizeListener(new MatrixResizeListener() {
			public void onMatrixResize(int rows, int cols)
			{
				thetaNull.setDimensions(rows, thetaNull.getColumnDimension());
				beta.setDimensions(cols, beta.getColumnDimension());
			}
		});
		withinContrast.addMatrixResizeListener(new MatrixResizeListener() {
			public void onMatrixResize(int rows, int cols)
			{
				sigmaError.setDimensions(rows, rows);
				beta.setDimensions(beta.getRowDimension(), rows);
				thetaNull.setDimensions(thetaNull.getRowDimension(), cols);
			}
		});
		beta.addMatrixResizeListener(new MatrixResizeListener() {
			public void onMatrixResize(int rows, int cols)
			{
				essence.setDimensions(essence.getRowDimension(), rows);
				betweenContrast.setDimensions(betweenContrast.getRowDimension(), rows);
				withinContrast.setDimensions(cols, withinContrast.getColumnDimension());
			}
		});
		// make sure sigma and within subject contrast (U) conform
		sigmaError.addMatrixResizeListener(new MatrixResizeListener() {
			public void onMatrixResize(int rows, int cols)
			{
				withinContrast.setDimensions(rows, withinContrast.getColumnDimension());
				beta.setDimensions(beta.getRowDimension(), rows);
			}
		});
		thetaNull.addMatrixResizeListener(new MatrixResizeListener() {
			public void onMatrixResize(int rows, int cols)
			{
				withinContrast.setDimensions(withinContrast.getRowDimension(), cols);
				betweenContrast.setDimensions(rows, betweenContrast.getColumnDimension());
			}
		});

		// set style
		panel.setStyleName(STYLE);
		
		// initialize the widget
		initWidget(panel);
	}
	
	public String getAlpha()
	{
	    return alphaTextBox.getText();
	}
	
	public boolean validate()
	{
		return true;
	}
	
	public String getStudyAttributes()
	{
        StringBuffer buffer = new StringBuffer();
        buffer.append("alpha='" + alphaTextBox.getText() + "' ");
        buffer.append(advOpts.getStudyAttributes());
        return buffer.toString();
	}
	
	public String getStudyXML(int totalN)
	{
		StringBuffer buffer = new StringBuffer();
		buffer.append(essence.toXML(totalN));
		buffer.append(beta.matrixDataToXML());
		buffer.append(thetaNull.matrixDataToXML());
		buffer.append(withinContrast.matrixDataToXML());
		buffer.append(betweenContrast.matrixDataToXML());
		if (covariateColumn == -1)
		{
		    buffer.append(sigmaError.matrixDataToXML());
		}
		else
		{
		    buffer.append(sigmaCovariate.matrixDataToXML());
            buffer.append(sigmaOutcomes.matrixDataToXML());
            buffer.append(sigmaCovariateOutcome.matrixDataToXML());
		}
		return buffer.toString();
	}
	
	public void addEssenceMatrixResizeListener(MatrixResizeListener listener)
	{
	    essence.addMatrixResizeListener(listener);
	}
	
	public void addEssenceMatrixMetaDataListener(MetaDataListener listener)
	{
		essence.addMetaDataListener(listener);
	}
	
	public void loadFromXMLDocument(Document doc)
	{
    	Node studyNode = doc.getElementsByTagName("study").item(0);
    	if (studyNode != null)
    	{
    		Node alpha = studyNode.getAttributes().getNamedItem("alpha");
    		if (alpha != null) 	
    		{
    		    alphaTextBox.setText(alpha.getNodeValue());
    		}

    		// parse the essence matrix
    		Node essenceNode = doc.getElementsByTagName("essencematrix").item(0);
    		if (essenceNode != null) essence.loadFromDomNode(essenceNode);
    		
    		// parse the remaining matrices
    		NodeList matrixNodes = doc.getElementsByTagName("matrix");
    		for(int i = 0; i < matrixNodes.getLength(); i++)
    		{
    			Node matrixNode = matrixNodes.item(i);
    			NamedNodeMap attrs = matrixNode.getAttributes();
    			Node nameNode = attrs.getNamedItem("name");
    			if (nameNode != null)
    			{
    				String name = nameNode.getNodeValue();
    				if (name.equals("beta"))
    					loadMatrixFromNode(beta, matrixNode);
    				else if (name.equals("withinSubjectContrast"))
    					loadMatrixFromNode(betweenContrast, matrixNode);
    				else if (name.equals("betweenSubjectContrast"))
    					loadMatrixFromNode(withinContrast, matrixNode);
    				else if (name.equals("theta"))
    					loadMatrixFromNode(thetaNull, matrixNode);
    				else if (name.equals("sigmaError"))
    					loadMatrixFromNode(sigmaError, matrixNode);
    				else if (name.equals("sigmaGaussianRandom"))
    					loadMatrixFromNode(sigmaCovariate, matrixNode);
    				else if (name.equals("sigmaOutcome"))
    					loadMatrixFromNode(sigmaOutcomes, matrixNode);
    				else if (name.equals("sigmaOutcomeGaussianRandom"))
    					loadMatrixFromNode(sigmaCovariateOutcome, matrixNode);
    			}
    		}
    	}
	}
		
	private void loadMatrixFromNode(ResizableMatrix matrixUI, Node matrixNode)
	{

		NamedNodeMap attrs = matrixNode.getAttributes();
		Node rowNode = attrs.getNamedItem("rows");
		Node colNode = attrs.getNamedItem("columns");
		if (rowNode != null && colNode != null)
		{
			int rows = Integer.parseInt(rowNode.getNodeValue());
			int cols = Integer.parseInt(colNode.getNodeValue());
			matrixUI.setDimensions(rows, cols);
			
			NodeList rowNodeList = matrixNode.getChildNodes();
			for(int r = 0; r < rowNodeList.getLength(); r++)
			{
				NodeList colNodeList = rowNodeList.item(r).getChildNodes();
				for(int c = 0; c < colNodeList.getLength(); c++)
				{
					Node colItem = colNodeList.item(c).getFirstChild();
					if (colItem != null) 
					{
					    matrixUI.setData(r, c, colItem.getNodeValue());
					}
				}
			}
		}
	}
    
    private boolean validAlpha(String alpha)
    {
        if (alpha == null || alpha.isEmpty())
            return false;
        
        try
        {
            double a = Double.parseDouble(alpha);
            if (Double.isNaN(a) || (a <= 0) || a >= 1) 
                return false;
            else
                return true;
        }
        catch (NumberFormatException nfe)
        {
            return false;
        }
    }
    
    private void displayError(HTML widget, String msg)
    {
        widget.removeStyleDependentName(PowerCalculatorConstants.STYLE_MESSAGE_OKAY);
        widget.removeStyleDependentName(PowerCalculatorConstants.STYLE_MESSAGE_ERROR);

        widget.addStyleDependentName(PowerCalculatorConstants.STYLE_MESSAGE_ERROR);
        widget.setHTML(msg);
    }
    
    private void displayOkay(HTML widget, String msg)
    {
        widget.removeStyleDependentName(PowerCalculatorConstants.STYLE_MESSAGE_ERROR);
        widget.removeStyleDependentName(PowerCalculatorConstants.STYLE_MESSAGE_OKAY);

        widget.addStyleDependentName(PowerCalculatorConstants.STYLE_MESSAGE_OKAY);
        widget.setHTML(msg);
    }
}
