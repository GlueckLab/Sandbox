package edu.cudenver.bios.powercalculator.client.panels;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.cudenver.bios.powercalculator.client.PowerCalculatorGUI;

public class MatrixPanel extends Composite implements ClickHandler
{
	private static final int DEFAULT_COLS = 3;
	private static final int DEFAULT_ROWS = 3;
	private static final String ECHO_URL = "http://localhost:8080/power/echo"; // TODO: properties file?
	protected FormPanel form = new FormPanel("_blank");;
	protected Hidden matrixXML = new Hidden("data");
	
	// matrix inputs
	ResizableMatrix essence = new ResizableMatrix(PowerCalculatorGUI.constants.matrixDesignEssence(), DEFAULT_COLS, DEFAULT_ROWS, true);
	ResizableMatrix withinContrast = new ResizableMatrix(PowerCalculatorGUI.constants.matrixWithinSubjectContrast(), DEFAULT_COLS, DEFAULT_ROWS, false);
	ResizableMatrix betweenContrast = new ResizableMatrix(PowerCalculatorGUI.constants.matrixBetweenSubjectContrast(), DEFAULT_COLS, DEFAULT_ROWS, false);
	ResizableMatrix beta = new ResizableMatrix(PowerCalculatorGUI.constants.matrixBeta(), DEFAULT_COLS, DEFAULT_ROWS, false);
	ResizableMatrix sigma = new ResizableMatrix(PowerCalculatorGUI.constants.matrixSigma(), DEFAULT_COLS, DEFAULT_ROWS, false);
	ResizableMatrix thetaNull = new ResizableMatrix(PowerCalculatorGUI.constants.matrixThetaNull(), DEFAULT_COLS, DEFAULT_ROWS, false);

	public MatrixPanel()
	{
		VerticalPanel panel = new VerticalPanel();
		
		panel.add(essence);
		panel.add(betweenContrast);
		panel.add(withinContrast);
		panel.add(beta);
		panel.add(sigma);
		panel.add(thetaNull);
		panel.add(new Button(PowerCalculatorGUI.constants.saveStudyButton(), this));
		form.add(matrixXML);
		form.setMethod(FormPanel.METHOD_POST);
		form.setAction(ECHO_URL);
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
