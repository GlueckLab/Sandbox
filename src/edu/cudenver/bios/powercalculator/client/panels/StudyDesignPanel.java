package edu.cudenver.bios.powercalculator.client.panels;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.xml.client.Document;

import edu.cudenver.bios.powercalculator.client.listener.MatrixResizeListener;
import edu.cudenver.bios.powercalculator.client.listener.MetaDataListener;
import edu.cudenver.bios.powercalculator.client.listener.StudyUploadListener;

public class StudyDesignPanel extends Composite implements StudyUploadListener
{
    protected BasicPanel basicPanel = new BasicPanel();
    protected MatrixPanel matrixPanel = new MatrixPanel();
    
    public StudyDesignPanel()
    {
        TabPanel panel = new TabPanel();
        
        panel.add(basicPanel, "Study Design View");
        panel.add(matrixPanel, "Matrix View");
        
        initWidget(panel);
    }
    
    public void addEssenceMatrixResizeListener(MatrixResizeListener listener)
    {
        matrixPanel.addEssenceMatrixResizeListener(listener);
    }
    
    public void addEssenceMatrixMetaDataListener(MetaDataListener listener)
    {
        matrixPanel.addEssenceMatrixMetaDataListener(listener);
    }
    
    public void onStudyUpload(Document studyDoc)
    {
        
    }
}
