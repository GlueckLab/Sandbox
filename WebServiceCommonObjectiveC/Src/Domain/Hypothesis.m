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

#import "Hypothesis.h"


/**
 * This is a wrapper for the Hypothesis information.
 *
 * @author Aarti Munjal
 *
 */

@implementation Hypothesis

/** The Constant serialVersionUID. */
static const long serialVersionUID = 570635778188633231L;

/*--------------------
 * Getter/Setter Methods
 *--------------------*/

@synthesize idx;
@synthesize type;
@synthesize betweenParticipantFactorMapList;
@synthesize repeatedMeasuresMapTree;

/*--------------------
 * Constructors
 *--------------------*/
/**
 * Instantiates a new hypothesis.
 */

-(id) init
{
    self = [super init];
    if (self) {
        betweenParticipantFactorMapList = NULL;
        repeatedMeasuresMapTree = NULL;
    }
    return self;
}

/**
 * Instantiates a new hypothesis.
 *
 * @param type
 *            the type
 * @param betweenParticipantFactorMapList
 *            the between participant factor map list
 * @param repeatedMeasuresMapTree
 *            the repeated measures map tree
 */

-(id) initWithHypothesis:(HypothesisTypeEnum const*)theType andList:(NSMutableArray const*)thebetweenParticipantFactorMapList andMapTree:(NSMutableArray const*)therepeatedMeasuresMapTree
{
    self = [super init];
    if (self) {
        type = theType;
        betweenParticipantFactorMapList = thebetweenParticipantFactorMapList;
        repeatedMeasuresMapTree = therepeatedMeasuresMapTree;
    }
    return self;
    
}


/*--------------------
 * Return BetweenParticipantFactor list
 *--------------------*/
/**
 * Gets the between participant factor list.
 *
 * @return the between participant factor list
 */

-(NSMutableArray const*) getBetweenParticipantFactorList
{
    NSMutableArray const* list =  [self betweenParticipantFactorMapList];
    NSMutableArray const* betweenParticipantFactorList = NULL;
    if (list != NULL && list.count > 0) {
        betweenParticipantFactorList = [[NSMutableArray alloc] init];
        for (HypothesisBetweenParticipantMapping *m in list) {
            [betweenParticipantFactorList addObject:[m betweenParticipantFactor]];
        }
    }
    return betweenParticipantFactorList;
}




/*--------------------
 * Return RepeatedMeasures list
 *--------------------*/
/**
 * Gets the repeated measures list.
 *
 * @return the repeated measures list
 */

-(NSMutableArray const*) getRepeatedMeasuresList
{
    NSMutableArray const*list =  [self repeatedMeasuresMapTree];
    NSMutableArray const*betweenParticipantFactorList = NULL;
    if (list != NULL && list.count > 0) {
        betweenParticipantFactorList = [[NSMutableArray alloc] init];
        for (HypothesisRepeatedMeasuresMapping* m in list) {
            [betweenParticipantFactorList addObject:[m repeatedMeasuresNode]];
        }
    }
    return betweenParticipantFactorList;
}



@end
