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


#import "RepeatedMeasuresDimension.h"

/**
 * Enum of types of repeated measures.
 */

@implementation RepeatedMeasuresDimension

@synthesize idx;
@synthesize repeatedMeasuresType;

/**
 * Instantiates a new power method enum.
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
 * Parses the idx.
 *
 * @param idx
 *            the idx
 * @return the repeated measures dimension type
 */


-(RepeatedMeasuresDimensionType *) parseIdx:(const NSString*)theIdx
{
    
    RepeatedMeasuresDimensionType *dimensionType  = NULL;
    for (int x = NUMERICAL; x < CATEGORICAL; x++) {
        RepeatedMeasuresDimensionType *type = (RepeatedMeasuresDimensionType *) x;
        if ([theIdx.lowercaseString isEqualToString:idx.lowercaseString]) {
            dimensionType = type;
        }
    }
    return dimensionType;
}


@end
