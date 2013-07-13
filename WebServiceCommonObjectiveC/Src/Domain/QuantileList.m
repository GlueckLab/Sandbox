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


#import "QuantileList.h"

/**
 * List of quantile objects to work around Jackson serializaiton issues.
 *
 * @author Aarti Munjal
 *
 */

/** The Constant serialVersionUID. */
static const long serialVersionUID = 1L;

@implementation QuantileList

/*--------------------
 * Getter/Setter Methods
 *--------------------*/

@synthesize uuid;
@synthesize quatileList;

/*--------------------
 * Constructors
 *--------------------*/
/**
 * Instantiates a new quantile list.
 *
 * @param uuid
 *            the uuid
 */

-(id) init
{
    self = [super self];
    if (self) {
        uuid = NULL;
        quantileList = NULL;
    }
    return self;
}


-(id) initWithUuid:(NSMutableData const*)theUuid
{
    self = [super self];
    if (self) {
        uuid = theUuid;
    }
    return self;
}

/**
 * Instantiates a new quantile list.
 *
 * @param uuid
 *            the uuid
 * @param list
 *            the list
 */

-(id) initWithUuid:(NSMutableData const*)theUuid andList:(NSMutableArray const*)theQuantileList
{
    self = [super self];
    if (self) {
        uuid = theUuid;
        quatileList = theQuantileList;
    }
    return self;
}


/**
 * Instantiates a new quantile list.
 *
 * @param size
 *            the size
 */

-(id) initWithListSize:(int const)theSize
{
    self = [super init];
    if (self) {
        quatileList = [[NSMutableArray alloc] initWithCapacity:theSize];
    }
    return self;
}



/**
 * Instantiates a new quantile list.
 *
 * @param list
 *            the list
 */

-(id) initWithList:(NSMutableArray const*)theList
{
    self = [super init];
    if (self) {
        quatileList = theList;
    }
    return self;
}









@end
