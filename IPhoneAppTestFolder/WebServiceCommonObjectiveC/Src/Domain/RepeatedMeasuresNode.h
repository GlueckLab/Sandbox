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
#import "RepeatedMeasuresDimension.h"

/**
 * This is a wrapper for the repeated measures information.
 *
 * @author Aarti Munjal
 *
 */

@interface RepeatedMeasuresNode : NSObject

{
    @private
    
    /*--------------------
     * Member Variables
     *--------------------*/
    /** The id. */
    int idx;
    
    /** The dimension. */
    NSString const*dimension;
    
    /** The repeated measures dimension type. */
    RepeatedMeasuresDimensionType const*repeatedMeasuresDimensionType;
    
    /** The number of measurements. */
    NSInteger const*numberOfMeasurements;
    
    /** The node. */
    int node;
    
    /** The parent. */
    NSInteger const*parent;
    
    /** The spacing list. */
    NSMutableArray const*spacingList;
}

@property (assign, nonatomic) int idx;
@property (strong, nonatomic) NSString const*dimension;
@property (assign, nonatomic) RepeatedMeasuresDimensionType const*repeatedMeasuresDimensionType;
@property (assign, nonatomic) NSInteger const*numberOfMeasurements;
@property (assign, nonatomic) int node;
@property (strong, nonatomic) NSMutableArray const*spacingList;

-(id) initWithName:(NSString const*)theName;
-(id) initWithName:(NSString const*)theName andType:(RepeatedMeasuresDimensionType const*)theType andCount:(NSInteger const*)theCount andNode:(int const)theNode andParent:(NSInteger const*)theParent;
-(id) initWithName:(NSString const*)theName andType:(RepeatedMeasuresDimensionType const*)theType andCount:(NSInteger *)theCount andList:(NSMutableArray const*)theList andNode:(int const)theNode andParent:(NSInteger const*)theParent;

@end
