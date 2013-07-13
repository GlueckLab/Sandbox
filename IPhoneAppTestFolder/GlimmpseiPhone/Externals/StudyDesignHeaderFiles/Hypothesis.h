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
#import "HypothesisEnum.h"
#import "HypothesisRepeatedMeasuresMapping.h"
#import "HypothesisBetweenParticipantMapping.h"


/**
 * This is a wrapper for the Hypothesis information.
 *
 * @author Aarti Munjal
 *
 */

@interface Hypothesis : NSObject

{
    @private
    
    /*--------------------
     * Member Variables
     *--------------------*/
    /** The id. */
    int idx;
    
    /** The type. */
    HypothesisTypeEnum const*type;
    
    /** The between participant factor map list. */
    NSMutableArray const* betweenParticipantFactorMapList;
    
    /** The repeated measures map tree. */
    NSMutableArray const* repeatedMeasuresMapTree;
}

@property (assign, nonatomic) int idx;
@property (assign, nonatomic) HypothesisTypeEnum const*type;
@property (strong, nonatomic) NSMutableArray const*betweenParticipantFactorMapList;
@property (strong, nonatomic) NSMutableArray const*repeatedMeasuresMapTree;

@end
