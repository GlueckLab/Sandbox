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


#import "StudyDesignList.h"

/**
 * List of study design objects to work around Jackson serializaiton issues.
 *
 * @author Aarti Munjal
 *
 */

/** The Constant serialVersionUID. */
static const long serialVersionUID = 1L;

@implementation StudyDesignList

/*--------------------
 * Getter/Setter Methods
 *--------------------*/

@synthesize studyDesignList;

/*--------------------
 * Constructors
 *--------------------*/

/**
 * Instantiates a new study design list.
 */

-(id) init
{
    self = [super init];
    if (self) {
        studyDesignList = NULL;
    }
    return self;
}

/**
 * Instantiates a new study design list.
 *
 * @param size
 *            the size
 */

-(id) initWithListSize:(int const)theSize
{
    self = [super init];
    if (self) {
        studyDesignList = [[NSMutableArray alloc] initWithCapacity:theSize];
    }
    return self;
}

/**
 * Instantiates a new study design list.
 *
 * @param list
 *            the list
 */

-(id) initWithList:(NSMutableArray const*)theList
{
    self = [super init];
    if (self) {
        studyDesignList = theList;
    }
    return self;
}



@end
