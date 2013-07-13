package edu.cudenver.bios.powercalculator.client.panels;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ListBox;

import edu.cudenver.bios.powercalculator.client.listener.CovariateListener;

public class AdvancedLinearModelOptionsPanel extends Composite
implements CovariateListener
{
    protected ListBox testStatisticList = new ListBox();
    protected ListBox powerMethodList = new ListBox();
    protected ListBox unirepCorrectionList = new ListBox();
    protected ListBox momentMethodList = new ListBox();
    protected ListBox unirepCdfList = new ListBox();
    
    public AdvancedLinearModelOptionsPanel()
    {
        DisclosurePanel panel = new DisclosurePanel("Advanced Options");

        // build test statistic selection list
        testStatisticList.addItem("Hotelling Lawley Trace", "hlt");
        testStatisticList.addItem("Univariate Approach To Repeated Measures", "unirep");
        testStatisticList.addItem("Wilk's Lambda", "wl");
        testStatisticList.addItem("Pillau Bartlett Trace", "pbt");
        testStatisticList.addChangeHandler(new ChangeHandler() {
            public void onChange(ChangeEvent e)
            {
                String stat = testStatisticList.getValue(testStatisticList.getSelectedIndex());
                boolean unirep = stat.equals("unirep");
                unirepCorrectionList.setEnabled(unirep);
                unirepCdfList.setEnabled(unirep);
            }
        });
        
        // build the covariate adjustment method selection list
        powerMethodList.addItem("Conditional Power", "cond");
        powerMethodList.addItem("Quantile Power", "quantile");
        powerMethodList.addItem("Unconditional Power", "uncond");
        powerMethodList.setEnabled(false);
        
        // build the correction list for univariate approach to repeated measures
        unirepCorrectionList.addItem("Box", "box");
        unirepCorrectionList.addItem("Geisser-Greenhouse", "gg");
        unirepCorrectionList.addItem("Hyuhn-Feldt", "hf");
        unirepCorrectionList.addItem("Uncorrected", "un");
        unirepCorrectionList.setEnabled(false);
        
        // build the moment method list
        momentMethodList.addItem("Pillai 1 Moment", "pillai1");
        momentMethodList.addItem("Pillai 1 Moment with Non-centrality Scaling", "pillai1mult");
        momentMethodList.addItem("McKeon 2 Moment", "mckeon2");
        momentMethodList.addItem("McKeon 2 Moment with Non-centrality Scaling", "mckeon2mult");
        momentMethodList.addItem("Muller 2 Moment", "muller2");
        momentMethodList.addItem("Muller 2 Moment", "muller2mult");
        momentMethodList.addItem("Rao 2 Moment", "rao2");
        momentMethodList.addItem("Rao 2 Moment with Non-centrality Scaling", "rao2mult");

        
        // build the unirep CDF options
        unirepCdfList.addItem("Muller-Barton Approximation", "mba");
        unirepCdfList.addItem("Muller-Edwards-Taylor Appoximation", "meta");
        unirepCdfList.addItem("Muller-Edwards-Taylor Exact (Davies')", "mete");
        unirepCdfList.addItem("Muller-Edwards-Taylor Exact + failover to Approximation", "metea");
        unirepCdfList.setEnabled(false);
        
        // layout the options
        Grid grid = new Grid(5,2);
        grid.setWidget(0, 0, new HTML("Test Statistic: "));
        grid.setWidget(0, 1, testStatisticList);
        grid.setWidget(1, 0, new HTML("Moment Approximation Method: "));
        grid.setWidget(1, 1, momentMethodList);
        grid.setWidget(2, 0, new HTML("Power Approximation Method: "));
        grid.setWidget(2, 1, powerMethodList);
        grid.setWidget(3, 0, new HTML("Univariate Correction Method: "));
        grid.setWidget(3, 1, unirepCorrectionList);
        grid.setWidget(4, 0, new HTML("Univariate CDF Calculation Method"));
        grid.setWidget(4, 1, unirepCdfList);

        // synchronize the drop down lists
        
        
        panel.add(grid);
        initWidget(panel);
        
    }
    
    public String getStudyAttributes()
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append("statistic='" + testStatisticList.getValue(testStatisticList.getSelectedIndex()) + "' ");
        buffer.append("momentMethod='" + momentMethodList.getValue(momentMethodList.getSelectedIndex()) + "' ");
        if (powerMethodList.isEnabled()) 
            buffer.append("powerMethod='" + powerMethodList.getValue(powerMethodList.getSelectedIndex()) + "' ");
        if (unirepCorrectionList.isEnabled())
            buffer.append("unirepCorrection='" + unirepCorrectionList.getValue(unirepCorrectionList.getSelectedIndex()) + "' ");
        if (unirepCdfList.isEnabled())
            buffer.append("unirepCdf='" + unirepCdfList.getValue(unirepCdfList.getSelectedIndex()) + "' ");
        return buffer.toString();
    }
    
    public void onHasCovariate(boolean hasCovariate)
    {
        powerMethodList.setEnabled(hasCovariate);
    }
    
    public void onMean(double mean) {}
    
    public void onVariance(double variance) {}
}
