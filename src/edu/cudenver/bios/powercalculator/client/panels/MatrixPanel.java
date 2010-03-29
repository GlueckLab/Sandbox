package edu.cudenver.bios.powercalculator.client.panels;

import com.google.gwt.user.client.Window;
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

import edu.cudenver.bios.powercalculator.client.PowerCalculatorGUI;
import edu.cudenver.bios.powercalculator.client.listener.MatrixResizeListener;
import edu.cudenver.bios.powercalculator.client.listener.MetaDataListener;
import edu.cudenver.bios.powercalculator.client.listener.ModelSelectListener;

public class MatrixPanel extends Composite implements ModelSelectListener
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
	protected ResizableMatrix sigmaCovariate = new ResizableMatrix("sigmaGaussianRandom", PowerCalculatorGUI.constants.matrixSigmaG(), 
	        PowerCalculatorGUI.constants.matrixSigmaGDetails(), DEFAULT_P, DEFAULT_P, false);
	// variance/covariance of the outcomes
	protected ResizableMatrix sigmaOutcomes = new ResizableMatrix("sigmaOutcome", PowerCalculatorGUI.constants.matrixSigmaY(), 
	        PowerCalculatorGUI.constants.matrixSigmaYDetails(), DEFAULT_P, DEFAULT_P, false);
	// correlation of covariate and outcomes
	protected ResizableMatrix sigmaCovariateOutcome = new ResizableMatrix("sigmaOutcomeGaussianRandom", PowerCalculatorGUI.constants.matrixSigmaYG(), 
	        PowerCalculatorGUI.constants.matrixSigmaYGDetails(), DEFAULT_P, DEFAULT_P, false);

	protected DeckPanel sigmaDeck = new DeckPanel();
	protected FormPanel form = new FormPanel("_blank");
	protected Hidden matrixXML = new Hidden("data");
	
	protected TextBox alphaTextBox = new TextBox();
	protected int covariateColumn = -1;
	
    // build the advanced options panel
    AdvancedLinearModelOptionsPanel advOpts = new AdvancedLinearModelOptionsPanel();
    
	public MatrixPanel()
	{
		VerticalPanel panel = new VerticalPanel();
		
		// build alpha input
		Grid alpha = new Grid(1, 2);
		alpha.setWidget(0, 0, new HTML(PowerCalculatorGUI.constants.textLabelAlpha()));
		alpha.setWidget(0, 1, alphaTextBox);
		
		panel.add(alpha);
		panel.add(advOpts);
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
	    covariateSigma.add(sigmaCovariateOutcome);
	    covariateSigma.add(sigmaCovariate);
	    covariateSigma.add(sigmaOutcomes);
	    sigmaDeck.add(sigma);
	    sigmaDeck.add(covariateSigma);
	    sigmaDeck.showWidget(FIXED_INDEX);
		panel.add(sigmaDeck);
		

		
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
	
	public void onModelSelect(String modelName)
	{
		if (PowerCalculatorGUI.constants.modelOneSampleStudentsT().equals(modelName))
        {
			essence.setDimensions(2, 2);
			withinContrast.setDimensions(1, 1);
			betweenContrast.setDimensions(1, 2);
			beta.setDimensions(2, 1);
			thetaNull.setDimensions(1, 1);
			sigma.setDimensions(1, 1);
			
			advOpts.setVisible(false);
			setResizable(false);
        }   
		else
		{
			advOpts.setVisible(true);
			setResizable(true);
		}
	}
	
	private void setResizable(boolean allowResize)
	{
		essence.setResizable(allowResize);
		withinContrast.setResizable(allowResize);
		betweenContrast.setResizable(allowResize);
		beta.setResizable(allowResize);
		thetaNull.setResizable(allowResize);
		sigma.setResizable(allowResize);
		sigmaCovariate.setResizable(allowResize);
		sigmaOutcomes.setResizable(allowResize);
		sigmaCovariateOutcome.setResizable(allowResize);
	}
	
	private void conformMatrices()
	{
		
	}
	
	public void loadFromXMLDocument(Document doc)
	{
    	Node studyNode = doc.getElementsByTagName("study").item(0);
    	if (studyNode != null)
    	{
    		Node alpha = studyNode.getAttributes().getNamedItem("alpha");
    		if (alpha != null) 	alphaTextBox.setText(alpha.getNodeValue());

    		// parse the essence matrix
    		Node essenceNode = doc.getElementsByTagName("essencematrix").item(0);
    		if (essenceNode != null) loadEssenceMatrixFromNode(essenceNode);
    		
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
    					loadMatrixFromNode(sigma, matrixNode);
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
	
	private void loadEssenceMatrixFromNode(Node matrixNode)
	{
		NodeList children = matrixNode.getChildNodes();
		Node rowMD = null;
		Node colMD = null;
		// locate the row/column meta data
		for(int i = 0; i < children.getLength(); i++)
		{
			Node child = children.item(i);
			String name = child.getNodeName();
			if (name.equals("matrix"))
				loadMatrixFromNode(essence, child);
			else if (name.equals("rowMetaData"))
				rowMD = child;
			else if (name.equals("columnMetaData"))
				colMD = child;
		}
		
		// if we found meta data, fill in the details
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
					if (colItem != null) matrixUI.setData(r, c, colItem.getNodeValue());
				}
			}
		}
	}

}
