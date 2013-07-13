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


#import "ClusterNode.h"

/**
 * This is a wrapper for the clustering information.
 *
 * @author Aarti Munjal
 *
 */


/** The Constant serialVersionUID. */
static const long _serialVersionUID = -8859192442862759392L;

@implementation ClusterNode

/*--------------------
 * Getter/Setter Methods
 *--------------------*/

@synthesize idx;
@synthesize groupName;
@synthesize groupSize;
@synthesize intraClusterCorrelation;
@synthesize node;
@synthesize parent;

/*--------------------
 * Constructors
 *--------------------*/
/**
 * Instantiates a new cluster node.
 */

-(id) init
{
    self = [super init];
    if (self)
    {
        groupName = NULL;
        groupSize = 0;
        intraClusterCorrelation = 0;
    }
    return self;
    
}


/**
 * Instantiates a new cluster node.
 *
 * @param groupNameValue
 *            the group name
 */

-(id) initWithGroupName:(NSString const*)theGroupName
{
    self = [super init];
    if (self) {
        groupName = theGroupName;
    }
    return self;
}


/**
 * Instantiates a new cluster node.
 *
 * @param theGroupName
 *            the group name
 * @param theGroupSize
 *            the group size
 * @param theNode
 *            the node
 * @param theParent
 *            the parent
 */

-(id) initWithGroupName:(NSString const*)theGroupName andGroupSize:(NSInteger const)theGroupSize andNodeValue:(NSInteger const)theNode andParentValue:(NSInteger const)theParent
{
    self = [super init];
    if (self) {
        groupName = theGroupName;
        groupSize = theGroupSize;
        node = theNode;
        parent = theParent;
    }
    return self;
}

@end
