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

#import "NamedMatrix.h"

/**
 * This is a wrapper for the Matrix information.
 *
 * @author Aarti Munjal
 *
 */

@implementation NamedMatrix

/** The Constant serialVersionUID. */
static const long serialVersionUID = 7392545585998026017L;

/*--------------------
 * Getter/Setter Methods
 *--------------------*/

@synthesize idx;
@synthesize name;
@synthesize rows;
@synthesize columns;
@synthesize data;

/*--------------------
 * Constructors
 *--------------------*/

-(id) init
{
    self = [super init];
    if (self) {
        name = NULL;
    }
    return self;
}

/**
 * Instantiates a new named matrix.
 *
 * @param name
 *            the name
 */

-(id) initWithName:(NSString const*)theName
{
    self = [super init];
    if (self) {
        name = theName;
    }
    return self;
}

/*--------------------
 * Return/Store data[][]
 *--------------------*/
/**
 * Sets the data.
 *
 * @param data
 *            the new data
 */

-(void) setDataFromArray:(NSMutableArray *)theData
{
    if(theData != NULL && theData.count > 0) {
        Blob2DArray *blob = [[Blob2DArray alloc] initWithData:theData];
        data = blob;
    }
}


@end
