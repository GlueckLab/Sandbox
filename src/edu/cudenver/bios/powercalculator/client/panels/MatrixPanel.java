package edu.cudenver.bios.powercalculator.client.panels;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.cudenver.bios.powercalculator.client.PowerCalculatorGUI;

public class MatrixPanel extends Composite implements ClickHandler
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

	private static final String ECHO_URL = "/restcall/power/saveas"; 
	// matrix inputs
	protected ResizableMatrix essence = new ResizableMatrix("design", PowerCalculatorGUI.constants.matrixDesignEssence(), 
			PowerCalculatorGUI.constants.matrixDesignEssenceDetails(), DEFAULT_N, DEFAULT_Q, true);
	protected ResizableMatrix withinContrast = new ResizableMatrix("withinSubjectContrast", PowerCalculatorGUI.constants.matrixWithinSubjectContrast(), 
			PowerCalculatorGUI.constants.matrixWithinSubjectContrastDetails(), DEFAULT_P, DEFAULT_B, false);
	protected ResizableMatrix betweenContrast = new ResizableMatrix("betweenSubjectContrast", PowerCalculatorGUI.constants.matrixBetweenSubjectContrast(), 
			PowerCalculatorGUI.constants.matrixBetweenSubjectContrastDetails(), DEFAULT_A, DEFAULT_Q, false);
	protected ResizableMatrix beta = new ResizableMatrix("beta", PowerCalculatorGUI.constants.matrixBeta(), 
	        PowerCalculatorGUI.constants.matrixBetaDetails(), DEFAULT_Q, DEFAULT_P, false);
	protected ResizableMatrix thetaNull = new ResizableMatrix("theta", PowerCalculatorGUI.constants.matrixThetaNull(), 
	        PowerCalculatorGUI.constants.matrixThetaNullDetails(), DEFAULT_A, DEFAULT_B, false);
	// variance/covariance matrix for fixed predictors
	protected ResizableMatrix sigma = new ResizableMatrix("sigmaError", PowerCalculatorGUI.constants.matrixSigmaError(), 
	        PowerCalculatorGUI.constants.matrixSigmaErrorDetails(), DEFAULT_P, DEFAULT_P, false);
	/* the following are needed for a baseline covariate */
	// variance of the baseline covariate
	protected ResizableMatrix sigmaCovariate = new ResizableMatrix("sigmaG", PowerCalculatorGUI.constants.matrixSigmaG(), 
	        PowerCalculatorGUI.constants.matrixSigmaGDetails(), DEFAULT_P, DEFAULT_P, false);
	// variance/covariance of the outcomes
	protected ResizableMatrix sigmaOutcomes = new ResizableMatrix("sigmaY", PowerCalculatorGUI.constants.matrixSigmaY(), 
	        PowerCalculatorGUI.constants.matrixSigmaYDetails(), DEFAULT_P, DEFAULT_P, false);
	// correlation of covariate and outcomes
	protected ResizableMatrix rhoCovariateOutcome = new ResizableMatrix("rhoGY", PowerCalculatorGUI.constants.matrixRhoGY(), 
	        PowerCalculatorGUI.constants.matrixRhoGYDetails(), DEFAULT_P, DEFAULT_P, false);

	protected DeckPanel sigmaDeck = new DeckPanel();
	protected FormPanel form = new FormPanel("_blank");
	protected Hidden matrixXML = new Hidden("data");
	
	protected TextBox alphaTextBox = new TextBox();
	protected int covariateColumn = -1;
	
	public MatrixPanel()
	{
		VerticalPanel panel = new VerticalPanel();
		
		// build alpha input
		Grid alpha = new Grid(1, 2);
		alpha.setWidget(0, 0, new HTML(PowerCalculatorGUI.constants.textLabelAlpha()));
		alpha.setWidget(0, 1, alphaTextBox);
		
		panel.add(alpha);
		// add each matrix to the panel
		panel.add(essence);
		panel.add(beta);
		panel.add(sigmaDeck);
		panel.add(thetaNull);
		panel.add(betweenContrast);
		panel.add(withinContrast);
		// deck panel since we have different variance/covariance matrices
		// for all fixed predictors vs. a baseline covariate 
	    VerticalPanel covariateSigma = new VerticalPanel();
	    covariateSigma.add(rhoCovariateOutcome);
	    covariateSigma.add(sigmaCovariate);
	    covariateSigma.add(sigmaOutcomes);
	    sigmaDeck.add(sigma);
	    sigmaDeck.add(covariateSigma);
	    sigmaDeck.showWidget(FIXED_INDEX);
		panel.add(sigmaDeck);
		
		// add the save study link and associated form
		panel.add(new Button(PowerCalculatorGUI.constants.buttonSaveStudy(), this));
		form.setAction(ECHO_URL);
		form.setMethod(FormPanel.METHOD_POST);
		VerticalPanel formContainer = new VerticalPanel();
		formContainer.add(matrixXML);
		formContainer.add(new Hidden("filename", "study.xml"));
		form.add(formContainer);
		panel.add(form);
		
		// set matrix size restrictions
        sigma.setIsSquare(true);
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
				sigma.setDimensions(rows, rows);
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
		sigma.addMatrixResizeListener(new MatrixResizeListener() {
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
	
	public void onClick(ClickEvent e)
	{
	    matrixXML.setValue("<study><params alpha='" + alphaTextBox.getText() + "'>" + getStudyXML(essence.rowNamesToXML()) + "</params></study>");
	    form.submit();
	}
	
	public String getAlpha()
	{
	    return alphaTextBox.getText();
	}
	
	public boolean validate()
	{
		return true;
	}
	
	public String getStudyXML(String rowMetaData)
	{
		StringBuffer buffer = new StringBuffer();
		buffer.append("<essenceMatrix>");
		buffer.append(essence.columnMetaDataToXML());
		buffer.append(rowMetaData);
		buffer.append(essence.matrixDataToXML());
		buffer.append("</essenceMatrix>");
		buffer.append(beta.matrixDataToXML());
		buffer.append(thetaNull.matrixDataToXML());
		buffer.append(withinContrast.matrixDataToXML());
		buffer.append(betweenContrast.matrixDataToXML());
		if (covariateColumn == -1)
		{
		    buffer.append(sigma.matrixDataToXML());
		}
		else
		{
		    buffer.append(sigmaCovariate.matrixDataToXML());
            buffer.append(sigmaOutcomes.matrixDataToXML());
            buffer.append(rhoCovariateOutcome.matrixDataToXML());
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
}
