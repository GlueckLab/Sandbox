package edu.cudenver.bios.powercalculator.client.panels;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.xml.client.DOMException;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.XMLParser;

import edu.cudenver.bios.powercalculator.client.PowerCalculatorGUI;
import edu.cudenver.bios.powercalculator.client.listener.StudyUploadListener;

/**
 * Panel which allows the user to upload a previously saved study design file.
 * 
 * @author Sarah Kreidler
 *
 */
public class UploadPanel extends Composite
{
    // CSS names
    private static final String HEADER_STYLE = "uploadPanelHeader";
    private static final String DESCRIPTION_STYLE = "uploadPanelDescription";
    private static final String CONTAINER_STYLE = "uploadPanel";
    // uri of file upload service
    private static final String UPLOAD_URI = "/restcall/file/upload";
    // form tag for file
    private static final String FORM_TAG_FILE = "file";
    // listeners for upload callbacks
    protected ArrayList<StudyUploadListener> listeners = new ArrayList<StudyUploadListener>();

    /**
     * Constructor.
     */
    public UploadPanel()
    {
        VerticalPanel panel = new VerticalPanel();

        // layout the widgets
        // create description / instructions
        HTML header = new HTML(PowerCalculatorGUI.constants.textExistingStudyPanelHeader());
        HTML description = new HTML(PowerCalculatorGUI.constants.textExistingStudyPanelDescription());

        // create the upload form
        final FormPanel formPanel = new FormPanel();
        // for file upload, we need to use the POST method, and multipart MIME encoding.
        formPanel.setEncoding(FormPanel.ENCODING_MULTIPART);
        formPanel.setMethod(FormPanel.METHOD_POST);
        formPanel.setAction(UPLOAD_URI);
        // panel to contain the contents of the submission form
        VerticalPanel formContents = new VerticalPanel();
        // create an upload widget
        final FileUpload uploader = new FileUpload();
        uploader.setName(FORM_TAG_FILE);
        formContents.add(uploader);
        formContents.add(new Button("Upload", new ClickHandler() {
            public void onClick(ClickEvent e)
            {
                String filename = uploader.getFilename();
                if (filename == null || filename.isEmpty())
                {
                    Window.alert("No filename specified.  Please click the 'Browse' button and select a file for upload.");
                }
                else
                {
                    formPanel.submit();
                }
            }
        }));

        // Notify listeners when form submission is complete
        formPanel.addSubmitCompleteHandler(new SubmitCompleteHandler() {
            public void onSubmitComplete(SubmitCompleteEvent event) 
            {
                String results = event.getResults();
                if (results != null)
                {
                	try
                	{
                	    // make sure we at least have a study tag and model name specified
                		Document doc = XMLParser.parse(results);
                    	Node studyNode = doc.getElementsByTagName("study").item(0);
                    	if (studyNode == null) throw new DOMException(DOMException.SYNTAX_ERR, "no study tag specified");
                    	Node modelName = studyNode.getAttributes().getNamedItem("modelname");
                    	if (modelName == null)  throw new DOMException(DOMException.SYNTAX_ERR, "no model name specified");
                		// notify listeners of the file upload
                    	notifyOnStudyUpload(doc, modelName.getNodeValue());
                	}
                	catch (DOMException e)
                	{
                        Window.alert("Uploaded file does not contain a valid study description [error: "+ e.getMessage() +"].  Please try another file.");
                	}
                }
                else
                {
                	Window.alert("Failed to upload file.  Please try again");
                }

            }
        });

        formPanel.add(formContents);

        // layout the widgets
        panel.add(header);
        panel.add(description);
        panel.add(formPanel);

        // add style
        header.setStyleName(HEADER_STYLE);
        description.setStyleName(DESCRIPTION_STYLE);
        panel.setStyleName(CONTAINER_STYLE);

        initWidget(panel);
    }

    public void addStudyUploadListener(StudyUploadListener listener)
    {
        listeners.add(listener);
    }
    
    private void notifyOnStudyUpload(Document doc, String modelName)
    {
        for(StudyUploadListener listener: listeners) listener.onStudyUpload(doc, modelName);

    }
}
