package edu.cudenver.bios.powercalculator.client.panels;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratedStackPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.StackPanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ModelSelectPanel extends Composite
{
    // display text for each "stacked" panel.  Grouped by type of model
    private static final String STACK_ONE_SAMPLE = "One Sample Models";
    private static final String STACK_TWO_SAMPLE = "Two Sample Models";
    private static final String STACK_MULTIFACTORIAL = "Multifactorial Models";
    
    public ModelSelectPanel()
    {
        VerticalPanel panel = new VerticalPanel();
        panel.setWidth("200");
        // Add label
        Label modelHeader = new Label("Statistical Models");
        modelHeader.setStyleName("modelSelect-Header");
        panel.add(modelHeader);
        
        // TODO: request list of available tests from server?
        // build selection stack
        Tree modelTree = new Tree();
        TreeItem oneSample = modelTree.addItem(STACK_ONE_SAMPLE);
        oneSample.addItem("Student's T");
        oneSample.addItem("F");
        
        TreeItem twoSample = modelTree.addItem(STACK_TWO_SAMPLE);
        twoSample.addItem("Student's T");
        twoSample.addItem("F");
        
        TreeItem multi = modelTree.addItem(STACK_MULTIFACTORIAL);
        TreeItem glum = multi.addItem(new Label("General Linear Multivariate Model", true));
        glum.setWidth("50");
        TreeItem glmm = multi.addItem(new Label("General Linear Univariate Model", true));
        glmm.setWidth("100");
        panel.add(modelTree);
        panel.setStyleName("modelSelect");
        
        // All composites must call initWidget() in their constructors.
        initWidget(panel);
    }
}
