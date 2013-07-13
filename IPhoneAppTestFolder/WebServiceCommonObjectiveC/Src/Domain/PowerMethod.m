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


#import "PowerMethod.h"


/**
 * This is a wrapper for the power method object information.
 *
 * @author Aarti Munjal
 *
 */

static const long serialVersionUID = 2312611754070134629L;

@implementation PowerMethod

/*--------------------
 * Getter/Setter Methods
 *--------------------*/
/**
 * Gets the id.
 *
 * @return the id
 */

@synthesize idx;
@synthesize powerMethodEnum;


/*--------------------
 * Constructors
 *--------------------*/

/**
 * Instantiates a new power method.
 *
 * @param powerMethodEnum
 *            the power method enum
 */

-(id) initWithPower:(const PowerMethodEnum *)thePowerMethod
{
    self = [super init];
    if (self) {
        powerMethodEnum = thePowerMethod;
    }
    return self;
    
}



@end
