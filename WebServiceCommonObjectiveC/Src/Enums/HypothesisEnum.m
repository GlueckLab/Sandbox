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

#import "HypothesisEnum.h"

/**
 * Enum object referred in 'Hypothesis' class.
 *
 * @author Aarti Munjal
 */

@implementation HypothesisEnum

@synthesize idx;
@synthesize hypothesisTypeEnum;

/**
 * Instantiates a new horizontal axis label enum.
 *
 * @param idx
 *            the idx
 */

-(id) initWithIdx:(const NSString*)theIdx
{
    self = [super init];
    if (self) {
        idx = theIdx;
    }
    return self;
}

/**
 * Parses the id.
 *
 * @param idx
 *            the idx
 * @return the hypothesis type enum
 */

-(HypothesisTypeEnum *) parseIdx:(const NSString*)theIdx
{
    
    HypothesisTypeEnum *hypothesisType  = NULL;
    for (int x = GRAND_MEAN; x < TREND; x++) {
        HypothesisTypeEnum *type = (HypothesisTypeEnum *) x;
        if ([theIdx.lowercaseString isEqualToString:idx.lowercaseString]) {
            hypothesisType = type;
        }
    }
    return hypothesisType;
}

@end
