/* Bug Report Service is for GLIMMPSE Software System.
 * Processes the incomming HTTP requests to report the bug.
 * 
 * Copyright (C) 2010 Regents of the University of Colorado.
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
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301, USA.
 */
package edu.ucdenver.bios.bugreportsvc.resource;

import org.restlet.resource.ServerResource;

/**
 * 
 * @author VIJAY AKULA
 *
 */
public class DefaultResource extends ServerResource
{
    public String represent()
    {
        return "Bug Report Service";        
    }
}
