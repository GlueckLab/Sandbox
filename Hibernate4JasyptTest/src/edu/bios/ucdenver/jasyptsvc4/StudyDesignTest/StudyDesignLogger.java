/*
 * Study Design Service for the GLIMMPSE Software System.  
 * This service stores study design definitions for users of the GLIMMSE interface.
 * Service contain all information related to a power or sample size calculation.  
 * The Study Design Service simplifies communication between different screens in the user interface.
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
 */
package edu.bios.ucdenver.jasyptsvc4.StudyDesignTest;

import org.apache.log4j.Logger;

/**
 * Singleton Log4J wrapper class.
 *
 * @author Uttara Sakhadeo
 */
public final class StudyDesignLogger {

    /** The instance. */
    private static Logger instance = null;

    /**
     * Create a new logging object.
     */
    private StudyDesignLogger() {
    }

    /**
     * Create a single instance of a logging class.
     *
     * @return Logger object
     */
    public static Logger getInstance() {
        if (instance == null) {
            instance = Logger
                    .getLogger("edu.cudenver.bios.studydesignsvc.Study");
        }

        return instance;
    }

}
