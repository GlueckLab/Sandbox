package edu.cudenver.bios.powercalculator.client.panels;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.xml.client.Document;

import edu.cudenver.bios.powercalculator.client.PowerCalculatorGUI;
import edu.cudenver.bios.powercalculator.client.listener.MatrixResizeListener;
import edu.cudenver.bios.powercalculator.client.listener.MetaDataListener;
import edu.cudenver.bios.powercalculator.client.listener.ModelSelectListener;
import edu.cudenver.bios.powercalculator.client.listener.StudyUploadListener;

public class StudyDesignPanel extends Composite 
implements StudyUploadListener, ModelSelectListener
{
    private static final int BASIC_GLMM = 0;
    private static final int SIMPLE = 1;
    protected BasicPanel basicPanel = new BasicPanel();
    protected SimplePanel simplePanel = new SimplePanel();
    protected MatrixPanel matrixPanel = new MatrixPanel();
    protected DeckPanel designPanel = new DeckPanel();
    
    protected String modelName = PowerCalculatorGUI.constants.modelGLMM();
    
    public StudyDesignPanel()
    {
        TabPanel panel = new TabPanel();
        
        designPanel.add(basicPanel);
        designPanel.add(simplePanel);
        designPanel.showWidget(BASIC_GLMM);
        panel.add(designPanel, "Study Design View");
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
    
    public void onModelSelect(String modelName)
    {
        Window.alert("Study panel got model " + modelName);
        this.modelName = modelName;
        if (PowerCalculatorGUI.constants.modelGLMM().equals(modelName))
        {
            designPanel.showWidget(BASIC_GLMM);
        }   
        else
        {
            designPanel.showWidget(SIMPLE);
        }
    }
    
    public String getStudyAttributes()
    {
        if (PowerCalculatorGUI.constants.modelGLMM().equals(modelName))
        {
            return "alpha='" + matrixPanel.getAlpha() + "'";
        }
        else
        {
            StringBuffer buffer = new StringBuffer();
            buffer.append("alpha='" + simplePanel.getAlpha() + "' ");
            buffer.append("mu0='" + simplePanel.getNullMean() + "' ");
            buffer.append("muA='" + simplePanel.getAlternativeMean() + "' ");
            buffer.append("sigmaError='" + simplePanel.getSigma() + "'");
            return buffer.toString();
        }
    }
    
    public String getStudyXML(String rowMetaDataXML)
    {
        if (PowerCalculatorGUI.constants.modelGLMM().equals(modelName))
        {
            return matrixPanel.getStudyXML(rowMetaDataXML);
        }
        else
        {
            return "";
        }
    }
}
