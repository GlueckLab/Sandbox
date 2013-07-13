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

#import "UuidMatrixName.h"

/**
 * This is a wrapper for the NamedMatrix object.
 *
 * When user requests remove/retrieve a NamedMatrix, this wrapper class serves
 * the purpose. User can pass Uuid and Name of NamedMatrix through this wrapper.
 *
 * @author Aarti Munjal
 */

@implementation UuidMatrixName

/** The Constant serialVersionUID. */
static const long serialVersionUID = 1L;

/*--------------------
 * Getter/Setter Methods
 *--------------------*/

@synthesize uuid;
@synthesize matrixName;


/*--------------------
 * Constructors
 *--------------------*/

/**
 * Instantiates a new uuid matrix name.
 *
 * @param uuid
 *            the uuid
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
 * Instantiates a new uuid matrix name.
 *
 * @param matrixName
 *            the matrix name
 */

-(id) initWithName:(NSString const*)theMatrixName
{
    self = [super init];
    if (self) {
        matrixName = theMatrixName;
    }
    return self;
}



/**
 * Instantiates a new uuid matrix name.
 *
 * @param uuid
 *            the uuid
 * @param matrixName
 *            the matrix name
 */


-(id) initWithUuid:(NSMutableData const*)theUuid andMatrixName:(NSString const*)theMatrixName
{
    self = [super init];
    if (self) {
        uuid = theUuid;
        matrixName = theMatrixName;
    }
    return self;
}

@end
