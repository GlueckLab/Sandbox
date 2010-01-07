package edu.cudenver.bios.powercalculator.client.panels;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.cudenver.bios.powercalculator.client.PowerCalculatorGUI;

public class MatrixPanel extends Composite implements ClickHandler
{
	// these default names derived from linear model theory.
	// ensures that default matrix dimensions conform properly
	private static final int DEFAULT_N = 3;
	private static final int DEFAULT_Q = 3;
	private static final int DEFAULT_P = 2;
	private static final int DEFAULT_A = 2;
	private static final int DEFAULT_B = 1;

	//private static final String ECHO_URL = "/restcall/power/echo"; 
	private static final String ECHO_URL = "/restcall/power/saveas"; 
	// matrix inputs
	ResizableMatrix essence = new ResizableMatrix(PowerCalculatorGUI.constants.matrixDesignEssence(), 
			PowerCalculatorGUI.constants.matrixDesignEssenceDetails(), DEFAULT_N, DEFAULT_Q, true);
	ResizableMatrix withinContrast = new ResizableMatrix(PowerCalculatorGUI.constants.matrixWithinSubjectContrast(), 
			PowerCalculatorGUI.constants.matrixWithinSubjectContrastDetails(), DEFAULT_P, DEFAULT_B, false);
	ResizableMatrix betweenContrast = new ResizableMatrix(PowerCalculatorGUI.constants.matrixBetweenSubjectContrast(), 
			PowerCalculatorGUI.constants.matrixBetweenSubjectContrastDetails(), DEFAULT_A, DEFAULT_Q, false);
	ResizableMatrix beta = new ResizableMatrix(PowerCalculatorGUI.constants.matrixBeta(), 
			PowerCalculatorGUI.constants.matrixBetaDetails(), DEFAULT_Q, DEFAULT_P, false);
	ResizableMatrix sigma = new ResizableMatrix(PowerCalculatorGUI.constants.matrixSigma(), 
			PowerCalculatorGUI.constants.matrixSigmaDetails(), DEFAULT_P, DEFAULT_P, false);
	ResizableMatrix thetaNull = new ResizableMatrix(PowerCalculatorGUI.constants.matrixThetaNull(), 
			PowerCalculatorGUI.constants.matrixThetaNullDetails(), DEFAULT_A, DEFAULT_B, false);

	FormPanel form = new FormPanel("_blank");
	Hidden matrixXML = new Hidden("data");
	
	public MatrixPanel()
	{
		VerticalPanel panel = new VerticalPanel();
		
		panel.add(essence);
		essence.addListener(new ResizableMatrixListener() {
			public void onMatrixResize(int rows, int cols)
			{
				beta.setDimensions(cols, beta.getColumnDimension());
				betweenContrast.setDimensions(betweenContrast.getRowDimension(), cols);
			}
		});
		panel.add(betweenContrast);
		betweenContrast.addListener(new ResizableMatrixListener() {
			public void onMatrixResize(int rows, int cols)
			{
				thetaNull.setDimensions(rows, thetaNull.getColumnDimension());
				beta.setDimensions(cols, beta.getColumnDimension());
			}
		});
		panel.add(withinContrast);
		withinContrast.addListener(new ResizableMatrixListener() {
			public void onMatrixResize(int rows, int cols)
			{
				sigma.setDimensions(rows, rows);
				beta.setDimensions(beta.getRowDimension(), rows);
				thetaNull.setDimensions(thetaNull.getRowDimension(), cols);
			}
		});
		panel.add(beta);
		beta.addListener(new ResizableMatrixListener() {
			public void onMatrixResize(int rows, int cols)
			{
				essence.setDimensions(essence.getRowDimension(), rows);
				betweenContrast.setDimensions(betweenContrast.getRowDimension(), rows);
				withinContrast.setDimensions(cols, withinContrast.getColumnDimension());
			}
		});
		panel.add(sigma);
		// make sure sigma and within subject contrast (U) conform
		sigma.addListener(new ResizableMatrixListener() {
			public void onMatrixResize(int rows, int cols)
			{
				withinContrast.setDimensions(rows, withinContrast.getColumnDimension());
				beta.setDimensions(beta.getRowDimension(), rows);
			}
		});
		sigma.setIsSquare(true);
		panel.add(thetaNull);
		thetaNull.addListener(new ResizableMatrixListener() {
			public void onMatrixResize(int rows, int cols)
			{
				withinContrast.setDimensions(withinContrast.getRowDimension(), cols);
				betweenContrast.setDimensions(rows, betweenContrast.getColumnDimension());
			}
		});
		panel.add(new Button(PowerCalculatorGUI.constants.saveStudyButton(), this));

		form.setAction(ECHO_URL);
		form.setMethod(FormPanel.METHOD_POST);
		VerticalPanel formContainer = new VerticalPanel();
		formContainer.add(matrixXML);
		formContainer.add(new Hidden("filename", "study.xml"));
		form.add(formContainer);
		panel.add(form);
		initWidget(panel);
	}
	
	public void onClick(ClickEvent e)
	{
	    matrixXML.setValue(getStudyXML("<study>", "</study>"));
	    form.submit();
	}

	
	public String getStudyXML(String openTag, String closeTag)
	{
		StringBuffer buffer = new StringBuffer();
		buffer.append(openTag);
		buffer.append("<params>");
		buffer.append(essence.toXML());
		buffer.append(withinContrast.toXML());
		buffer.append(betweenContrast.toXML());
		buffer.append(beta.toXML());
		buffer.append(sigma.toXML());
		buffer.append(thetaNull.toXML());
		buffer.append("</params>");
		buffer.append(closeTag);
		return buffer.toString();
	}
	
}
