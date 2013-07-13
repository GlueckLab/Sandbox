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

#import "HypothesisRepeatedMeasuresMapping.h"

/**
 * This is a wrapper for the Hypothesis information.
 *
 * @author Aarti Munjal
 *
 */

@implementation HypothesisRepeatedMeasuresMapping

/** The Constant serialVersionUID. */
static const long serialVersionUID = 1L;

/*--------------------
 * Getter/Setter Methods
 *--------------------*/

@synthesize type;
@synthesize repeatedMeasuresNode;


/*--------------------
 * Constructors
 *--------------------*/
/**
 * Instantiates a new hypothesis repeated measures mapping.
 */

-(id) init
{
    self = [super init];
    if (self) {
        type = NULL;
        repeatedMeasuresNode = NULL;
    }
    return self;
}


/**
 * Instantiates a new hypothesis repeated measures mapping.
 *
 * @param type
 *            the type
 * @param repeatedMeasuresNode
 *            the repeated measures node
 */

-(id) initWithType:(HypothesisTrendTypeEnum const*)theType andRepeatedMeasures:(RepeatedMeasuresNode const*)therepeatedMeasuresNode
{
    self = [super init];
    if (self) {
        type = theType;
        repeatedMeasuresNode = therepeatedMeasuresNode;
    }
    return self;
}


@end
