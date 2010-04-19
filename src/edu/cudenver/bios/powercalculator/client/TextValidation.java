package edu.cudenver.bios.powercalculator.client;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;

public class TextValidation
{
	public static int parseInteger(String str, int lowerBound, int upperBound)
	throws NumberFormatException
	{
        if (str == null || str.isEmpty()) throw new NumberFormatException();

        int n = Integer.parseInt(str);
        if (n >= upperBound || n <= lowerBound) throw new NumberFormatException();

        return n;
	}
	
	public static double parseDouble(String str, double lowerBound, double upperBound)
	throws NumberFormatException
	{
	    if (str == null || str.isEmpty()) throw new NumberFormatException();

	    double n = Double.parseDouble(str);
	    if (n > upperBound || n < lowerBound) throw new NumberFormatException();

	    return n;
	}
	
    public static void displayError(HTML widget, String msg)
    {
        widget.removeStyleDependentName(PowerCalculatorConstants.OKAY_STYLE);
        widget.removeStyleDependentName(PowerCalculatorConstants.ERROR_STYLE);

        widget.addStyleDependentName(PowerCalculatorConstants.ERROR_STYLE);
        widget.setHTML(msg);
    }
    
    public static void displayOkay(HTML widget, String msg)
    {
        widget.removeStyleDependentName(PowerCalculatorConstants.ERROR_STYLE);
        widget.removeStyleDependentName(PowerCalculatorConstants.OKAY_STYLE);

        widget.addStyleDependentName(PowerCalculatorConstants.OKAY_STYLE);
        widget.setHTML(msg);
    }
}
