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

#import "UuidHypothesisType.h"

/**
 * This is a wrapper for the Hypothesis object.
 *
 * When user requests remove/retrieve a Hypothesis, this wrapper class serves
 * the purpose. User can pass Uuid and Type of Hypothesis through this wrapper.
 *
 * @author Aarti Munjal
 */

@implementation UuidHypothesisType

/** The Constant serialVersionUID. */
static const long serialVersionUID = 1L;


/*--------------------
 * Getter/Setter Methods
 *--------------------*/

@synthesize uuid;
@synthesize type;

/**
 * Instantiates a new uuid hypothesis type.
 *
 * @param uuid
 *            the uuid
 */

-(id) initWithUuid:(NSMutableData *)theUuid
{
    self = [super init];
    if (self) {
        uuid = theUuid;
    }
    return self;
}

/**
 * Instantiates a new uuid hypothesis type.
 *
 * @param type
 *            the type
 */

-(id) initWithType:(HypothesisTypeEnum *)theType
{
    self = [super init];
    if (self) {
        type = theType;
    }
    return self;
}

/**
 * Instantiates a new uuid hypothesis type.
 *
 * @param uuid
 *            the uuid
 * @param type
 *            the type
 */

-(id) initWithUuid:(NSMutableData *)theUuid andType:(HypothesisTypeEnum *)theType
{
    self = [super init];
    if (self) {
        uuid = theUuid;
        type = theType;
    }
    return self;
}

@end
