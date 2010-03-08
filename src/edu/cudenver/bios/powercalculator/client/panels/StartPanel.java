/*
 * .NAME SOFTWARE, one line about what it does
 * 
 * Copyright (C) 2010 Regents of the University of Colorado.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 * Please contact Sarah Kreidler (sarah.kreidler@ucdenver.edu) for more information
 * about this software.  Or visit the website at <>
 */

package edu.cudenver.bios.powercalculator.client.panels;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import edu.cudenver.bios.powercalculator.client.PowerCalculatorGUI;
import edu.cudenver.bios.powercalculator.client.listener.StartListener;

/**
 * Welcome / start panel in the input wizard for the power calculator 
 * site.  Allows the user to select the method for study design input
 * and the statistical model
 * 
 * @author Owner
 *
 */
public class StartPanel extends Composite implements ClickHandler
{
    private static final String CONTAINER_STYLE = "startPanel";
    private static final String BUTTON_CONTAINER_STYLE = "startPanelButtonContainer";
    private static final String HEADER_STYLE = "startPanelHeader";
    private static final String DESCRIPTION_STYLE = "startPanelDescription";

    protected ArrayList<StartListener> listeners = new ArrayList<StartListener>();

    protected Button newStudyButton = new Button(PowerCalculatorGUI.constants.buttonNewStudy(), this);
    protected Button existingStudyButton = new Button(PowerCalculatorGUI.constants.buttonExistingStudy(), this);
    
    public StartPanel()
    {
        VerticalPanel panel = new VerticalPanel();

        // layout the widgets        
        // add introductory text
        HTML header = new HTML(PowerCalculatorGUI.constants.textStartPanelHeader());
        HTML description = new HTML(PowerCalculatorGUI.constants.textStartPanelDescription());
        panel.add(header);
        panel.add(description);
        // add buttons
        VerticalPanel buttonContainer = new VerticalPanel();
        buttonContainer.add(newStudyButton);
        buttonContainer.add(existingStudyButton);
        panel.add(buttonContainer);
        
        // add style
        panel.setStyleName(CONTAINER_STYLE);
        buttonContainer.setStyleName(BUTTON_CONTAINER_STYLE);
        header.setStyleName(HEADER_STYLE);
        description.setStyleName(DESCRIPTION_STYLE);
                
        initWidget(panel);
    }
    
    /**
     * Notify the input wizard as to whether the user is
     * creating a new study or uploading an existing design
     */
    public void onClick(ClickEvent e)
    {
        Widget sender = (Widget) e.getSource();
        if (sender == newStudyButton)
        {
            notifyOnNewStudy();
        }
        else
        {
            notifyOnExistingStudy();
        }
    }
    
    /**
     * Add a listener for "start events" which indicate if the user
     * wants to create a new study, or upload an existing design
     * 
     * @param listener 
     */
    public void addListener(StartListener listener)
    {
        listeners.add(listener);
    }
    
    /**
     * Notify listeners about new study creation request
     */
    private void notifyOnNewStudy()
    {
        for(StartListener listener: listeners) listener.onNewStudy();
    }
    
    /**
     * Notify listeners about existing study request
     */
    private void notifyOnExistingStudy()
    {
        for(StartListener listener: listeners) listener.onExistingStudy();
    }
}
