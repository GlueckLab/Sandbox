package edu.cudenver.bios.powercalculator.client.listener;

import com.google.gwt.xml.client.Document;

public interface StartListener
{
	public void onTemplateMode();
	
	public void onMatrixMode();
	
    public void onStudyUpload(Document doc, String mode);
}
