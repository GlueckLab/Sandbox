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

#import "CovarianceSet.h"

/**
 * List of covariance objects to work around Jackson serializaiton issues.
 *
 * @author Aarti Munjal
 *
 */

@implementation CovarianceSet

/** The Constant serialVersionUID. */
static const long serialVersionUID = 1L;

/*--------------------
 * Getter/Setter Methods
 *--------------------*/

@synthesize uuid;
@synthesize covarianceSet;

/*--------------------
 * Constructors
 *--------------------*/

/**
 * Instantiates a new covariance set.
 */

-(id) init
{
    self = [super init];
    if (self) {
        uuid = NULL;
        covarianceSet = NULL;
    }
    return self;
}

/**
 * Instantiates a new covariance set.
 *
 * @param uuid the uuid
 */

-(id) initWithUuid:(NSMutableData const*)theUuid
{
    self = [super self];
    if (self) {
        uuid = theUuid;
    }
    return self;
}

/**
 * Instantiates a new covariance set.
 *
 * @param uuid the uuid
 * @param set the set
 */

-(id) initWithUuid:(NSMutableData const*)theUuid andSet:(NSSet *)theSet
{
    self = [super self];
    if (self) {
        uuid = theUuid;
        covarianceSet = theSet;
    }
    return self;
}


/**
 * Instantiates a new covariance set.
 *
 * @param size
 *            the size
 */

-(id) initWithSize:(int)theSize
{
    self = [super self];
    if (self) {
        NSMutableArray *array = [[NSMutableArray alloc] initWithCapacity:theSize];
        covarianceSet = [[NSSet alloc] initWithArray:array];
    }
    return self;
}

/**
 * Instantiates a new covariance set.
 *
 * @param set
 *            the set
 */

-(id) initWithSet:(NSSet *)theSet
{
    self = [super self];
    if (self) {
        covarianceSet = theSet;
    }
    return self;
}


@end
