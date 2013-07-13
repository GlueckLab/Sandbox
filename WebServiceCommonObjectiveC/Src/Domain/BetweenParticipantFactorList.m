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


#import "BetweenParticipantFactorList.h"

/**
 * List of between participant factor objects to work around Jackson
 * serializaiton issues.
 *
 * @author Aarti Munjal
 *
 */

/** The Constant serialVersionUID. */
static const long serialVersionUID = 1L;

@implementation BetweenParticipantFactorList

/*--------------------
 * Getter/Setter Methods
 *--------------------*/

@synthesize uuid;
@synthesize betweenParticipantFactorList;


/*--------------------
 * Constructors
 *--------------------*/
/**
 * Instantiates a new between participant factor list.
 */


-(id) init
{
    self = [super init];
    if (self) {
        uuid = NULL;
        betweenParticipantFactorList = NULL;
    }
    return self;
}

/**
 * Instantiates a new between participant factor list.
 *
 * @param uuid
 *            the uuid
 */

-(id) initWithUuid:(NSMutableData const *)theUuid
{
    self = [super self];
    if (self) {
        uuid = theUuid;
    }
    return self;
}

/**
 * Instantiates a new between participant factor list.
 *
 * @param list
 *            the list
 */


-(id) initWithList:(NSMutableArray const *)theList
{
    self = [super self];
    if (self) {
        betweenParticipantFactorList = theList;
    }
    return self;
}


/**
 * Instantiates a new between participant factor list.
 *
 * @param size
 *            the size
 */

-(id) initWithListSize:(int const)theSize
{
    self = [super init];
    if (self) {
        betweenParticipantFactorList = [[NSMutableArray alloc] initWithCapacity:theSize];
    }
    return self;
}

/**
 * Instantiates a new between participant factor list.
 *
 * @param uuid
 *            the uuid
 * @param list
 *            the list
 */

-(id) initWithUuid:(NSMutableData const*)theUuid andList:(NSMutableArray *)theList
{
    self = [super init];
    if (self) {
        uuid = theUuid;
        betweenParticipantFactorList = theList;
    }
    return self;
}


@end
