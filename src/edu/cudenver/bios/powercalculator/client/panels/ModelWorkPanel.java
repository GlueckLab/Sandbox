package edu.cudenver.bios.powercalculator.client.panels;

import org.restlet.gwt.Callback;
import org.restlet.gwt.Client;
import org.restlet.gwt.data.Protocol;
import org.restlet.gwt.data.Request;
import org.restlet.gwt.data.Response;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;

public class ModelWorkPanel extends Composite
{
    private static final String introText = 
        "Welcome to the Power & Sample Size Calculator from\nThe " +
        "University of Colorado Biostatistics & Bioinformatics Department";
    private static final String descText = 
        "This calculator provides a priori power and sample size estimates" + 
        " for a variety of statistical models, including the general multivariate " +
        "linear model. ";
    private static final String instructionText = 
        "To begin, select the statistical model used in your analysis" + 
        "and follow the onscreen instructions.  If you need additional" +
        "help, please consult the Help manual at the top right";

    private int modelType = -1;
    
    public ModelWorkPanel()
    {
        VerticalPanel panel = new VerticalPanel();
        
        if (modelType < 0)
        {            
            panel.setSpacing(10);
            Label intro = new Label(introText, true);
            intro.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
            
            Label descr = new Label(descText, true);
            descr.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
            
            Label instruct = new Label(instructionText, true);
            instruct.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

            panel.add(intro);
            panel.add(descr);
            panel.add(instruct);
        }
        else
        {
            TabPanel tp = new TabPanel();
            tp.add(new HTML("Power"), "Power");
            tp.add(new HTML("Sample Size"), "Sample Size");

            // Show the 'Power' tab initially.
            tp.selectTab(1);
            panel.add(tp);
        }


        final Client client = new Client(Protocol.HTTP);
        client.post("http://sph-bi-glueck4:10080/power/power/test/onesamplestudentt", 
                "<power mu0=\"20\" muA=\"22\" sigma=\"4\" oneTailed=\"true\" alpha=\"0.05\" sampleSize=\"44\" />", 
                new Callback() {
            @Override
            public void onEvent(Request request, Response response) {
                System.out.println(response.getEntity().getText());
            }
        });

        // All composites must call initWidget() in their constructors.
        initWidget(panel);
    }
}
