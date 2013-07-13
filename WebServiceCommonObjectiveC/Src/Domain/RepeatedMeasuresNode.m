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

#import "RepeatedMeasuresNode.h"

/**
 * This is a wrapper for the repeated measures information.
 *
 * @author Aarti Munjal
 *
 */

@implementation RepeatedMeasuresNode

/** The Constant serialVersionUID. */
static const long serialVersionUID = -714204384820498167L;

/*--------------------
 * Getter/Setter Methods
 *--------------------*/

@synthesize idx;
@synthesize dimension;
@synthesize repeatedMeasuresDimensionType;
@synthesize numberOfMeasurements;
@synthesize node;
@synthesize spacingList;

/*--------------------
 * Constructors
 *--------------------*/
/**
 * Instantiates a new repeated measures node.
 */

-(id) init
{
    self = [super init];
    if (self) {
        dimension = NULL;
        repeatedMeasuresDimensionType = NULL;
        numberOfMeasurements = NULL;
        spacingList = NULL;
    }
    return self;
}

/**
 * Instantiates a new repeated measures node.
 *
 * @param name
 *            the name
 */

-(id) initWithName:(NSString const*)theName
{
    self = [super init];
    if (self) {
        dimension = theName;
    }
    return self;
}


/**
 * Instantiates a new repeated measures node.
 *
 * @param name
 *            the name
 * @param type
 *            the type
 * @param count
 *            the count
 * @param node
 *            the node
 * @param parent
 *            the parent
 */

-(id) initWithName:(NSString const*)theName andType:(RepeatedMeasuresDimensionType const*)theType andCount:(NSInteger const*)theCount andNode:(int const)theNode andParent:(NSInteger const*)theParent
{
    self = [super init];
    if (self) {
        dimension = theName;
        repeatedMeasuresDimensionType = theType;
        numberOfMeasurements = theCount;
        node = theNode;
        parent = theParent;
    }
    return self;
}
    

/**
 * Instantiates a new repeated measures node.
 *
 * @param name
 *            the name
 * @param type
 *            the type
 * @param count
 *            the count
 * @param spacingList
 *            the spacing list
 * @param node
 *            the node
 * @param parent
 *            the parent
 */

-(id) initWithName:(NSString const*)theName andType:(RepeatedMeasuresDimensionType const*)theType andCount:(NSInteger *)theCount andList:(NSMutableArray const*)theList andNode:(int const)theNode andParent:(NSInteger const*)theParent
{
    self = [super init];
    if (self) {
        dimension = theName;
        repeatedMeasuresDimensionType = theType;
        numberOfMeasurements = theCount;
        spacingList = theList;
        node = theNode;
        parent = theParent;
    }
    return self;
}




@end
