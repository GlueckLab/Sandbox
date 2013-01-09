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


#import "ConfidenceInterval.h"

// TODO: Auto-generated Javadoc
/**
 * POJO describing a confidence interval.
 *
 * @author Aarti Munjal
 *
 */

static long serialVersionUID;

@implementation ConfidenceInterval

/*--------------------
 * Getter/Setter Methods
 *--------------------*/


//@synthesize serialVersionUID = _serialVersionUID;
@synthesize alphaLower;
@synthesize alphaUpper;
@synthesize lowerLimit;
@synthesize upperLimit;

-(id) init
{
    self = [super init];
    if (self)
    {
        serialVersionUID = 5023627485583542502L;
    }
    return self;
}

-(id) initWithAlphaLower: (double) theAlphaLower andAlphaUpper: (double) theAlphaUpper andLowerLimit: (double) theLowerLimit andUpperLimit: (double) theUpperLimit
{
    self = [super init];
    if (self)
    {
    
        if (theLowerLimit > theUpperLimit)
        {
            NSLog(@"lowerLimit > upperLimit");
        }
    
        if (theAlphaLower < 0 || theAlphaLower > 1 || theAlphaUpper < 0 || theAlphaUpper > 1 || theAlphaUpper + theAlphaLower > 1)
        {
            NSLog(@"lowerLimit > upperLimit");
        }
        
        alphaLower = theAlphaLower;
        alphaUpper = theAlphaUpper;
        lowerLimit = theLowerLimit;
        upperLimit = theUpperLimit;
    }
        return self;
}

-(double) getConfidenceCoefficient {
 return 1 - alphaUpper - alphaLower;
}

@end
