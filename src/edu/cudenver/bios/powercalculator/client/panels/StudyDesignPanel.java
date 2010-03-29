package edu.cudenver.bios.powercalculator.client.panels;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Node;

import edu.cudenver.bios.powercalculator.client.PowerCalculatorGUI;
import edu.cudenver.bios.powercalculator.client.listener.MatrixResizeListener;
import edu.cudenver.bios.powercalculator.client.listener.MetaDataListener;
import edu.cudenver.bios.powercalculator.client.listener.ModelSelectListener;
import edu.cudenver.bios.powercalculator.client.listener.StudyUploadListener;

public class StudyDesignPanel extends Composite
implements StudyUploadListener, ModelSelectListener, ClickHandler
{
	private static final String SAVEAS_URL = "/restcall/file/saveas"; 

	private static final String STYLE = "studyDesignPanel";
    private static final int BASIC_GLMM = 0;
    private static final int TWO_GROUP = 1;
    protected BasicPanel linearModelPanel = new BasicPanel();
    protected TwoGroupDesignPanel twoGroupPanel = new TwoGroupDesignPanel();
    protected MatrixPanel matrixPanel = new MatrixPanel();
    protected DeckPanel designPanel = new DeckPanel();
	protected FormPanel form = new FormPanel("_blank");
	protected Hidden matrixXML = new Hidden("data");
	
    protected String modelName = PowerCalculatorGUI.constants.modelGLMM();
    TabPanel tabs = new TabPanel();

    public StudyDesignPanel()
    {
    	VerticalPanel designContainer = new VerticalPanel();
        
    	// note, order must match indices listed above
        designPanel.add(linearModelPanel);
        designPanel.add(twoGroupPanel);
        designPanel.showWidget(BASIC_GLMM);
        tabs.add(designPanel, "Study Design View");
        tabs.add(matrixPanel, "Matrix View");
        tabs.selectTab(0);
        
		// add the save study link and associated form
		form.setAction(SAVEAS_URL);
		form.setMethod(FormPanel.METHOD_POST);
		VerticalPanel formContainer = new VerticalPanel();
		formContainer.add(matrixXML);
		formContainer.add(new Hidden("filename", "study.xml"));
		form.add(formContainer);
		
		// build the panel layout
		designContainer.add(tabs);
        designContainer.add(new Button(PowerCalculatorGUI.constants.buttonSaveStudy(), this));
		designContainer.add(form);
        
        // TODO: add style
        designContainer.setStyleName(STYLE);
        initWidget(designContainer);
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
    	Node studyNode = studyDoc.getElementsByTagName("study").item(0);
    	if (studyNode != null)
    	{
    		Window.alert("wtf");
    		Node modelName = studyNode.getAttributes().getNamedItem("modelname");
    		if (modelName != null)
    		{
        		Window.alert("wtf: " + modelName.getNodeValue());

    	    	matrixPanel.loadFromXMLDocument(studyDoc);
    			if (PowerCalculatorGUI.constants.modelOneSampleStudentsT().equals(modelName.getNodeValue()))
    			{
    				twoGroupPanel.loadFromXMLDocument(studyDoc);
    				designPanel.showWidget(TWO_GROUP);
    			}
    			else
    			{
    				linearModelPanel.loadFromXMLDocument(studyDoc);
    				designPanel.showWidget(BASIC_GLMM);
    			}
    			tabs.selectTab(0);
    		}
    	}
    }
    
    public void onModelSelect(String modelName)
    {
        this.modelName = modelName;
        if (PowerCalculatorGUI.constants.modelGLMM().equals(modelName))
        {
            designPanel.showWidget(BASIC_GLMM);
        }   
        else
        {
            designPanel.showWidget(TWO_GROUP);
        }
        matrixPanel.onModelSelect(modelName);
    }
    
    public String getStudyAttributes()
    {
        if (PowerCalculatorGUI.constants.modelGLMM().equals(modelName))
        {
            return matrixPanel.getStudyAttributes();
        }
        else
        {
            StringBuffer buffer = new StringBuffer();
            buffer.append("alpha='" + twoGroupPanel.getAlpha() + "' ");
            buffer.append("mu0='" + twoGroupPanel.getNullMean() + "' ");
            buffer.append("muA='" + twoGroupPanel.getAlternativeMean() + "' ");
            buffer.append("sigmaError='" + twoGroupPanel.getSigma() + "'");
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
    
    public void onClick(ClickEvent e)
    {
    	matrixXML.setValue("<study alpha='" + matrixPanel.getAlpha() + "' modelName='" + modelName + "'>" + 
    			matrixPanel.getStudyXML("") + "</study>");
    	Window.alert(matrixXML.getValue());
    	form.submit();    	
    }
}
