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

#import "HorizontalAxisEnum.h"

/**
 * Enum object for covariance matrix types
 *
 * @author Aarti Munjal
 */

@implementation HorizontalAxisEnum

@synthesize idx;
@synthesize horizontalAxisLabelEnum;

/**
 * Instantiates a new horizontal axis label enum.
 *
 * @param id
 *            the id
 */

-(id) initWithIdx:(const NSString*)theIdx
{
    self = [super init];
    if (self) {
        idx = theIdx;
    }
    return self;
}


-(HorizontalAxisLabelEnum *) parseIdx:(const NSString*)theIdx
{
    
    HorizontalAxisLabelEnum *horizontalAxisLabel  = NULL;
    for (int x = VARIABILITY_SCALE_FACTOR; x < REGRESSION_COEEFICIENT_SCALE_FACTOR; x++) {
        HorizontalAxisLabelEnum *b = (HorizontalAxisLabelEnum *) x;
        if ([theIdx.lowercaseString isEqualToString:idx.lowercaseString]) {
            horizontalAxisLabel = b;
        }
    }
    return horizontalAxisLabel;
}

@end
