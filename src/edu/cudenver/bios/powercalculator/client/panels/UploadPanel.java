package edu.cudenver.bios.powercalculator.client.panels;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class UploadPanel extends Composite
{
	private ArrayList<FileUploadListener> listeners;
	
	public UploadPanel()
	{
		listeners = new ArrayList<FileUploadListener>();
		
		HorizontalPanel panel = new HorizontalPanel();
		
		FileUpload uploader = new FileUpload();
		
		panel.add(uploader);
		
		initWidget(panel);
	}
	
	public void addListener(FileUploadListener listener)
	{
		listeners.add(listener);
	}
}
