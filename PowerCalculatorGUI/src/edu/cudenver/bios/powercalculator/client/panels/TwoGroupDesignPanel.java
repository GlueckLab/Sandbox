package edu.cudenver.bios.powercalculator.client.panels;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.NamedNodeMap;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;

import edu.cudenver.bios.powercalculator.client.PowerCalculatorGUI;
import edu.cudenver.bios.powercalculator.client.listener.InputWizardStepListener;

public class TwoGroupDesignPanel extends Composite 
implements ChangeHandler
{
	private static final String STYLE = "twoGroupDesignPanel";
	private static final String MESSAGE_STYLE = "message";
	private static final String ERROR_STYLE = "error";
	private static final String OKAY_STYLE = "okay";
    protected TextBox alphaTextBox = new TextBox();
    protected HTML alphaErrorHTML = new HTML("");
    protected TextBox mu0TextBox = new TextBox();
    protected HTML mu0ErrorHTML = new HTML("");
    protected TextBox muATextBox = new TextBox();
    protected HTML muAErrorHTML = new HTML("");
    protected TextBox sigmaTextBox = new TextBox();
    protected HTML sigmaErrorHTML = new HTML("");
    protected InputWizardStepListener wizard;
    protected int stepIndex = -1;

    public TwoGroupDesignPanel(InputWizardStepListener wizard, int stepIndex)
    {
        this.wizard = wizard;
        this.stepIndex = stepIndex;
        // TODO: string constants!!!!
        VerticalPanel panel = new VerticalPanel();
        Grid grid = new Grid(4,3);
        grid.setWidget(0, 0, new HTML(PowerCalculatorGUI.constants.textLabelAlpha()));
        grid.setWidget(0, 1, alphaTextBox);
        grid.setWidget(0, 2, alphaErrorHTML);
        grid.setWidget(1, 0, new HTML(PowerCalculatorGUI.constants.textLabelMu0()));
        grid.setWidget(1, 1, mu0TextBox);
        grid.setWidget(1, 2, mu0ErrorHTML);
        grid.setWidget(2, 0, new HTML(PowerCalculatorGUI.constants.textLabelMuA()));
        grid.setWidget(2, 1, muATextBox);
        grid.setWidget(2, 2, muAErrorHTML);
        grid.setWidget(3, 0, new HTML(PowerCalculatorGUI.constants.textLabelSigma()));
        grid.setWidget(3, 1, sigmaTextBox);
        grid.setWidget(3, 2, sigmaErrorHTML);
        panel.add(grid);
              
        // add validation callbacks
        
        // make sure alpha between 0, 1
        alphaTextBox.addChangeHandler(new ChangeHandler() {
                // 
                public void onChange(ChangeEvent e)
                {
                    String alpha = alphaTextBox.getText();
                    if (validAlpha(alphaTextBox.getText()))
                    {
                        displayOkay(alphaErrorHTML, PowerCalculatorGUI.constants.okay());
                    }
                    else
                    {
                        alpha = "";
                        displayError(alphaErrorHTML, PowerCalculatorGUI.constants.errorAlphaInvalid());
                        alphaTextBox.setText(alpha);
                    }
                }
        });
        
        // make sure mu0 is a valid mean
        mu0TextBox.addChangeHandler(new ChangeHandler() {
                // 
                public void onChange(ChangeEvent e)
                {
                    String mu0 = mu0TextBox.getText();
                    if (validMean(mu0))
                    {
                        displayOkay(mu0ErrorHTML, PowerCalculatorGUI.constants.okay());
                    }
                    else
                    {
                        mu0 = "";
                        displayError(mu0ErrorHTML, PowerCalculatorGUI.constants.errorMeanInvalid());
                        mu0TextBox.setText(mu0);
                    }      
                }
        });
        
        // make sure muA is a valid mean
        muATextBox.addChangeHandler(new ChangeHandler() {
                // 
                public void onChange(ChangeEvent e)
                {
                    String muA = muATextBox.getText();
                    if (validMean(muA))
                    {
                        displayOkay(muAErrorHTML,PowerCalculatorGUI.constants.okay());
                    }
                    else
                    {
                        muA = "";
                        displayError(muAErrorHTML, PowerCalculatorGUI.constants.errorMeanInvalid());
                        muATextBox.setText(muA);
                    }    
                }
        });
        
        // make sure sigma is a valid variance
        sigmaTextBox.addChangeHandler(new ChangeHandler() {
                // 
                public void onChange(ChangeEvent e)
                {
                    String sigma = sigmaTextBox.getText();
                    if (validVariance(sigma))
                    {
                        displayOkay(sigmaErrorHTML,PowerCalculatorGUI.constants.okay());
                    }
                    else
                    {
                        sigma = "";
                        displayError(sigmaErrorHTML,PowerCalculatorGUI.constants.errorVarianceInvalid());
                        sigmaTextBox.setText("");
                    }      

                }
        });
        // add shared change handler for all text boxes - determines if 
        // continue is allowed
        alphaTextBox.addChangeHandler(this);
        mu0TextBox.addChangeHandler(this);
        muATextBox.addChangeHandler(this);
        sigmaTextBox.addChangeHandler(this);
        
        // add style
        alphaErrorHTML.setStyleName(MESSAGE_STYLE);
        alphaErrorHTML.addStyleDependentName(OKAY_STYLE);
        mu0ErrorHTML.setStyleName(MESSAGE_STYLE);
        mu0ErrorHTML.addStyleDependentName(OKAY_STYLE);
        muAErrorHTML.setStyleName(MESSAGE_STYLE);
        muAErrorHTML.addStyleDependentName(OKAY_STYLE);
        sigmaErrorHTML.setStyleName(MESSAGE_STYLE);
        sigmaErrorHTML.addStyleDependentName(OKAY_STYLE);

        initWidget(panel);
    }
    
    public String getAlpha()
    {
    	return alphaTextBox.getText();
    }
    
    public String getNullMean()
    {
    	return mu0TextBox.getText();
    }
    
    public String getAlternativeMean()
    {
    	return muATextBox.getText();
    }
    
    public String getSigma()
    {
    	return sigmaTextBox.getText();
    }        
    
    public void loadFromXMLDocument(Document doc)
    {
    	Node studyNode = doc.getElementsByTagName("study").item(0);
    	if (studyNode != null)
    	{
    		Node alpha = studyNode.getAttributes().getNamedItem("alpha");
    		if (alpha != null) 
    		{
    		    alphaTextBox.setValue(alpha.getNodeValue());
    		    alphaTextBox.fireEvent(new ChangeEvent() {});
    		}

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
    				{
    					// retrieve the mu0 (entry 0,0), muA (entry 1,0) from the beta matrix				
    					NodeList rowNodeList = matrixNode.getChildNodes();
    					for(int r = 0; r < rowNodeList.getLength(); r++)
    					{
    						Node firstColNode = rowNodeList.item(r).getFirstChild();
    						if (firstColNode != null)
    						{
    							Node colItem = firstColNode.getFirstChild();
    							if (r == 0)
    							{
    								if (colItem != null) 
    								{
    								    mu0TextBox.setText(colItem.getNodeValue());
    								    mu0TextBox.fireEvent(new ChangeEvent() {});
    								}
    							}
    							else if (r == 1)
    							{
    								if (colItem != null) 
    								{
    								    muATextBox.setText(colItem.getNodeValue());
    								    muATextBox.fireEvent(new ChangeEvent() {});
    								}
    							}
    						}
    					}
    				}
    				else if (name.equals("sigmaError"))
    				{
    					// retrieve the variance from the sigma matrix (entry 0,0)		
    					Node firstRowNode = matrixNode.getFirstChild();
    					if (firstRowNode != null)
    					{
    						Node firstColNode = firstRowNode.getFirstChild();
    						if (firstColNode != null)
    						{
    							Node colItem = firstColNode.getFirstChild();
    							if (colItem != null) 
    							{
    							    sigmaTextBox.setText(colItem.getNodeValue());
    							    sigmaTextBox.fireEvent(new ChangeEvent() {});
    							}
    						}
    					}
    				}
    			}
    		}
    	}
    }
    
    public void onChange(ChangeEvent e)
    {
        if (!alphaTextBox.getText().isEmpty() &&
                !mu0TextBox.getText().isEmpty() &&
                !muATextBox.getText().isEmpty() &&
                !sigmaTextBox.getText().isEmpty())
            wizard.onStepComplete(stepIndex);
        else
            wizard.onStepInProgress(stepIndex);
        
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
    
    private boolean validMean(String mean)
    {
        if (mean == null || mean.isEmpty())
            return false;
        
        try
        {
            double m = Double.parseDouble(mean);
            if (Double.isNaN(m)) 
                return false;
            else
                return true;
        }
        catch (NumberFormatException nfe)
        {
            return false;
        }
    }
    
    private boolean validVariance(String variance)
    {
        if (variance == null || variance.isEmpty())
            return false;
        
        try
        {
            double v = Double.parseDouble(variance);
            if (Double.isNaN(v) || v < 0) 
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
        widget.removeStyleDependentName(OKAY_STYLE);
        widget.removeStyleDependentName(ERROR_STYLE);

        widget.addStyleDependentName(ERROR_STYLE);
        widget.setHTML(msg);
    }
    
    private void displayOkay(HTML widget, String msg)
    {
        widget.removeStyleDependentName(ERROR_STYLE);
        widget.removeStyleDependentName(OKAY_STYLE);

        widget.addStyleDependentName(OKAY_STYLE);
        widget.setHTML(msg);
    }
    
    
    public void onAlpha(String alpha)
    {
        alphaTextBox.setText(alpha);
    }
    
    public void onDesign(int row, int col, String value) {}
    
    public void onDesignRowMetaData(int row, String name) {}
    
    public void onDesignColumnMetaData(int col, boolean isRandom, String mean, String variance) {}
        
    public void onBeta(int row, int col, String value) 
    {
        if (col == 0)
        {
            if (row == 0)
                mu0TextBox.setText(value);
            else if (row == 1)
                muATextBox.setText(value);
        }
    }
    
    public void onTheta(int row, int col, String value) {}
    
    public void onBetweenSubjectContrast(int row, int col, String value) {}

    public void onWithinSubjectContrast(int row, int col, String value) {}

    public void onSigmaError(int row, int col, String value) 
    {
        if (row == 0 && col == 0) sigmaTextBox.setText(value);
    }  
    
    public void onSigmaOutcome(int row, int col, String value) {}
    
    public void onSigmaCovariateOutcome(int row, int col, String value) {}
    
    public void onSigmaCovariate(int row, int col, String value) {}
}
