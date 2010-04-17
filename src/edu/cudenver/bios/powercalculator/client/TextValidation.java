package edu.cudenver.bios.powercalculator.client;

public class TextValidation
{
	public static int parseInteger(String str, int lowerBound, int upperBound)
	throws NumberFormatException
	{
        if (str == null || str.isEmpty()) throw new NumberFormatException();

        int n = Integer.parseInt(str);
        if (n > upperBound && n < lowerBound) throw new NumberFormatException();

        return n;
	}
}
