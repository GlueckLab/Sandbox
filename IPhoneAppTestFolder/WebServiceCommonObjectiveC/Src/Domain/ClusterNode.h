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


#import <Foundation/Foundation.h>

/**
 * This is a wrapper for the clustering information.
 *
 * @author Aarti Munjal
 *
 */

@interface ClusterNode : NSObject
{
    @private
    
    /*--------------------
     * Member Variables
     *--------------------*/
    /** The id. */
    int idx;
    
    /** The group name. */
    NSString const* groupName;
    
    /** The group size. */
    NSInteger groupSize;
    
    /** The intra cluster correlation. */
    double intraClusterCorrelation;
    
    /** The node. */
    NSInteger node;
    
    /** The parent. */
    NSInteger parent;
}


@property (assign, nonatomic) int idx;
@property (strong, nonatomic) NSString const* groupName;
@property (assign, nonatomic) NSInteger groupSize;
@property (assign, nonatomic) double intraClusterCorrelation;
@property (assign, nonatomic) NSInteger node;
@property (assign, nonatomic) NSInteger parent;


-(id) initWithGroupName:(NSString const*)theGroupName;
-(id) initWithGroupName:(NSString const*)theGroupName andGroupSize:(NSInteger const)theGroupSize andNodeValue:(NSInteger const)theNode andParentValue:(NSInteger const)theParent;


@end
