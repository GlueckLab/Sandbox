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

#import "HypothesisSet.h"


/**
 * List of Hypothesis objects to work around Jackson serializaiton issues.
 *
 * @author Aarti Munjal
 *
 */

@implementation HypothesisSet

/*--------------------
 * Getter/Setter Methods
 *--------------------*/

@synthesize uuid;
@synthesize hypothesisSet;

/** The Constant serialVersionUID. */
static const long serialVersionUID = 1L;

/*--------------------
 * Constructors
 *--------------------*/
/**
 * Instantiates a new hypothesis set.
 */

-(id) init
{
    self = [super init];
    if (self) {
        uuid = NULL;
        hypothesisSet = NULL;
    }
    return self;
}


/**
 * Instantiates a new hypothesis set.
 *
 * @param uuid the uuid
 */

-(id) initWithUuid:(NSMutableData const*)theUuid
{
    self = [super init];
    if (self) {
        uuid = theUuid;
    }
    return self;
}

/**
 * Instantiates a new hypothesis set.
 *
 * @param uuid the uuid
 * @param set the set
 */

-(id) initWithUuid:(NSMutableData const*)theUuid andSet:(NSSet const*)thehypothesisSet
{
    self = [super init];
    if (self) {
        uuid = theUuid;
        hypothesisSet = thehypothesisSet;
    }
    return self;
}



/**
 * Instantiates a new hypothesis set.
 *
 * @param size
 *            the size
 */

-(id) initWithSize:(int const)theSize
{
    self = [super init];
    if (self) {
        NSMutableArray *array = [[NSMutableArray alloc] initWithCapacity:theSize];
        hypothesisSet = [[NSSet alloc] initWithArray:array];
    }
    return self;
}

/**
 * Instantiates a new hypothesis set.
 *
 * @param set
 *            the set
 */

-(id) initWithSet:(NSSet const*)thehypothesisSet
{
    self = [super init];
    if (self) {
        hypothesisSet = thehypothesisSet;
    }
    return self;
}


@end
