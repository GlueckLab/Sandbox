/*
 * Communication Domain layer for iPhone applications which
 * interacts with Glimmpse Software Subsystems.
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
 * Foundation, Inc., 51 Franklin Street, Fifth Floor,
 * Boston, MA  02110-1301, USA.
 */

#import <Foundation/Foundation.h>
#import "PowerCurveDescription.h"

/**
 * This is a wrapper for the PowerCurveDescription object.
 *
 * When user requests create/update a PowerCurveDescription, this wrapper class
 * serves the purpose. User can pass Uuid and PowerCurveDescription object
 * through this wrapper.
 *
 * @author Aarti Munjal
 */

@interface UuidPowerCurveDescription : NSObject

{
    @private
    
    /*--------------------
     * Member Variables
     *--------------------*/
    /** The uuid. */
    NSMutableData const* uuid;
    
    /** The power curve description. */
    PowerCurveDescription const*powerCurveDescription;
}

@property (strong, nonatomic) NSMutableData const* uuid;
@property (strong, nonatomic) PowerCurveDescription const*powerCurveDescription;


-(id) initWithUuid:(NSMutableData const*)theUuid;
-(id) initWithPower:(PowerCurveDescription const*)thePowerCurveDescription;
-(id) initWithUuid:(NSMutableData const*)theUuid  andPower:(PowerCurveDescription const*)thePowerCurveDescription;

@end
