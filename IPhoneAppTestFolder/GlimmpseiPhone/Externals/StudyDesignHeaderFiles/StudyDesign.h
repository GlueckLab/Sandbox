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
#import "SolutionEnum.h"
#import "StudyDesignViewEnum.h"
#import "ConfidenceIntervalDescription.h"
#import "PowerCurveDescription.h"


/**
 * Main Study Design object which holds all lists, matrices etc.
 *
 * @author Aarti Munjal
 */



@interface StudyDesign : NSObject

{
    
    @private
    
    /*--------------------
     * Member Variables
     *--------------------*/
    
    // UUID for the study design. Main unique identifier for the design
    /** The uuid. */
    NSMutableData const *uuid;
    
    /** The name. */
    NSString const* name;
    
    /** The gaussian covariate. */
    BOOL gaussianCovariate;
    
    /** The solution type enum. */
    SolutionTypeEnum const* solutionTypeEnum;
    
    /** The participant label. */
    NSString const* participantLabel;

    /** The view type enum. */
    StudyDesignViewTypeEnum const* viewTypeEnum;
    
    /** The confidence interval descriptions. */
    ConfidenceIntervalDescription const* confidenceIntervalDescriptions;
    
    /** The power curve descriptions. */
    PowerCurveDescription const* powerCurveDescriptions;
    
    /* separate sets for list objects */
    /** The alpha list. */
    NSMutableArray const* alphaList;
    
    /** The beta scale list. */
    NSMutableArray const* betaScaleList;
    
    /** The sigma scale list. */
    NSMutableArray const* sigmaScaleList;
    
    /** The relative group size list. */
    NSMutableArray const* relativeGroupSizeList;
    
    /** The sample size list. */
    NSMutableArray const* sampleSizeList;
    
    /** The statistical test list. */
    NSMutableArray const* statisticalTestList;
    
    /** The power method list. */
    NSMutableArray const* powerMethodList;
    
    /** The quantile list. */
    NSMutableArray const* quantileList;
    
    /** The nominal power list. */
    NSMutableArray const* nominalPowerList;
    
    /** The response list. */
    NSMutableArray const* responseList;
    
    /** The between participant factor list. */
    NSMutableArray const* betweenParticipantFactorList;
    
    /** The repeated measures tree. */
    NSMutableArray const* repeatedMeasuresTree;
    
    /** The clustering tree. */
    NSMutableArray const* clusteringTree;
    
    /** The hypothesis. */
    NSMutableArray const* hypothesis;
    
    /** The covariance. */
    NSMutableArray const* covariance;
    
    /** The matrix set. */
    NSMutableArray const* matrixSet;
    
}


@property (strong, nonatomic) NSMutableData const* uuid;
@property (strong, nonatomic) NSString const* name;
@property (assign, nonatomic) BOOL gaussianCovariate;
@property (assign, nonatomic) SolutionTypeEnum const* solutionTypeEnum;
@property (strong, nonatomic) NSString const* participantLabel;
@property (assign, nonatomic) StudyDesignViewTypeEnum const*viewTypeEnum;
@property (strong, nonatomic) ConfidenceIntervalDescription *confidenceIntervalDescriptions;
@property (strong, nonatomic) PowerCurveDescription *powerCurveDescriptions;
@property (strong, nonatomic) NSMutableArray const* alphaList;
@property (strong, nonatomic) NSMutableArray const* betaScaleList;
@property (strong, nonatomic) NSMutableArray const* sigmaScaleList;
@property (strong, nonatomic) NSMutableArray const* relativeGroupSizeList;
@property (strong, nonatomic) NSMutableArray const* sampleSizeList;
@property (strong, nonatomic) NSMutableArray const* statisticalTestList;
@property (strong, nonatomic) NSMutableArray const* powerMethodList;
@property (strong, nonatomic) NSMutableArray const* quantileList;
@property (strong, nonatomic) NSMutableArray const* nominalPowerList;
@property (strong, nonatomic) NSMutableArray const* responseList;
@property (strong, nonatomic) NSMutableArray const* betweenParticipantFactorList;
@property (strong, nonatomic) NSMutableArray const* repeatedMeasuresTree;
@property (strong, nonatomic) NSMutableArray const*clusteringTree;
@property (strong, nonatomic) NSMutableArray const* hypothesis;
@property (strong, nonatomic) NSMutableArray const* covariance;
@property (strong, nonatomic) NSMutableArray const* matrixSet;

@property (strong, nonatomic) NSMutableArray *objectsArray;
@property (strong, nonatomic) NSMutableArray *keysArray;
@property (strong, nonatomic) NSDictionary *initDictionary;
@property (strong, nonatomic) NSMutableDictionary *mutableDictionary;


-(NSMutableArray *) getAlphaListValues;

-(void) createRequestDictionary;

@end
