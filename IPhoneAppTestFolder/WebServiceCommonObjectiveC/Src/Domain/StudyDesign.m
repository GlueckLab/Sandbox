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


/**
 * This is a wrapper for the Study Design object information.
 *
 * @author Aarti Munjal
 *
 */

#import "StudyDesign.h"

#import "TypeIError.h"

/**
 * Main Study Design object which holds all lists, matrices etc.
 *
 * @author Aarti Munjal
 */

/** The Constant serialVersionUID. */


static const long serialVersionUID = -2761124691778704875L;

@implementation StudyDesign

{
    TypeIError *typeIError;
}

@synthesize uuid;
@synthesize name;
@synthesize gaussianCovariate;
@synthesize solutionTypeEnum;
@synthesize participantLabel;
@synthesize viewTypeEnum;
@synthesize confidenceIntervalDescriptions;
@synthesize powerCurveDescriptions;
@synthesize alphaList;
@synthesize betaScaleList;
@synthesize sigmaScaleList;
@synthesize relativeGroupSizeList;
@synthesize sampleSizeList;
@synthesize statisticalTestList;
@synthesize powerMethodList;
@synthesize quantileList;
@synthesize nominalPowerList;
@synthesize responseList;
@synthesize betweenParticipantFactorList;
@synthesize repeatedMeasuresTree;
@synthesize clusteringTree;
@synthesize hypothesis;
@synthesize covariance;
@synthesize matrixSet;
@synthesize objectsArray;
@synthesize keysArray;
@synthesize initDictionary;
@synthesize mutableDictionary;


/*--------------------
 * Constructors
 *--------------------*/
/**
 * Create an empty study design without a UUID assigned.
 */

-(id) init
{
    self = [super init];
    if (self) {
        uuid = NULL;
        name = NULL;
        gaussianCovariate = FALSE;
        solutionTypeEnum = NULL;
        participantLabel = NULL;
        viewTypeEnum = NULL;
        confidenceIntervalDescriptions = NULL;
        powerCurveDescriptions = NULL;
        alphaList = NULL;
        betaScaleList = NULL;
        sigmaScaleList = NULL;
        relativeGroupSizeList = NULL;
        sampleSizeList = NULL;
        statisticalTestList = NULL;
        powerMethodList = NULL;
        quantileList = NULL;
        nominalPowerList = NULL;
        responseList = NULL;
        betweenParticipantFactorList = NULL;;
        repeatedMeasuresTree = NULL;;
        clusteringTree = NULL;
        hypothesis = NULL;
        covariance = NULL;
        matrixSet = NULL;;
        objectsArray = NULL;
        keysArray = NULL;
        
        keysArray = [[NSMutableArray alloc] initWithObjects:@"uuid",@"name",@"gaussianCovariate",@"solutionTypeEnum",@"participantLabel",@"viewTypeEnum",@"confidenceIntervalDescriptions",@"powerCurveDescriptions",@"alphaList",@"betaScaleList",@"sigmaScaleList",@"relativeGroupSizeList",@"sampleSizeList",@"statisticalTestList",@"powerMethodList",@"quantileList",@"nominalPowerList",@"responseList",@"betweenParticipantFactorList",@"repeatedMeasuresTree",@"clusteringTree",@"hypothesis",@"covariance",@"matrixSet",nil];
        
        objectsArray = [[NSMutableArray alloc] initWithObjects:@"null",@"null",@"FALSE",@"null",@"null",@"null",@"null",@"null",@"null",@"null",@"null",@"null",@"null",@"null",@"null",@"null",@"null",@"null",@"null",@"null",@"null",@"null",@"null",@"null", nil];
        
        initDictionary = [[NSDictionary alloc] initWithObjectsAndKeys:NULL,@"uuid",NULL,@"name",FALSE,@"gaussianCovariate",NULL,@"solutionTypeEnum",NULL,@"participantLabel",NULL,@"viewTypeEnum",NULL,@"confidenceIntervalDescriptions",NULL,@"powerCurveDescriptions",NULL,@"alphaList",NULL,@"betaScaleList",NULL,@"sigmaScaleList",NULL,@"relativeGroupSizeList",NULL,@"sampleSizeList",NULL,@"statisticalTestList",NULL,@"powerMethodList",NULL,@"quantileList",NULL,@"nominalPowerList",NULL,@"responseList",NULL,@"betweenParticipantFactorList",NULL,@"repeatedMeasuresTree",NULL,@"clusteringTree",NULL,@"hypothesis",NULL,@"covariance",NULL,@"matrixSet",nil];
        
       // *newDictionary = [[NSDictionary alloc] initWithObjectsAndKeys:NULL,@"uuid",NULL,@"name",FALSE,@"gaussianCovariate",NULL,@"solutionTypeEnum",NULL,@"participantLabel",NULL,@"viewTypeEnum",NULL,@"confidenceIntervalDescriptions",NULL,@"powerCurveDescriptions",NULL,@"alphaList",NULL,@"betaScaleList",NULL,@"sigmaScaleList",NULL,@"relativeGroupSizeList",NULL,@"sampleSizeList",NULL,@"statisticalTestList",NULL,@"powerMethodList",NULL,@"quantileList",NULL,@"nominalPowerList",NULL,@"responseList",NULL,@"betweenParticipantFactorList",NULL,@"repeatedMeasuresTree",NULL,@"clusteringTree",NULL,@"hypothesis",NULL,@"covariance",NULL,@"matrixSet",nil];
        
        //objectsArray = [[NSMutableArray alloc] initWithObjects:NULL,NULL,gaussianCovariate,@"null",@"null",@"null",confidenceIntervalDescriptions,powerCurveDescriptions,@"null",@"null",@"null",@"null",@"null",@"null",powerMethodList,quantileList,nominalPowerList,@"null",@"null",repeatedMeasuresTree,clusteringTree,@"null",@"null",@"null", nil];
        
       // initDictionary = [[NSDictionary alloc] initWithObjects:objectsArray forKeys:keysArray];
        
        mutableDictionary = [initDictionary mutableCopy];
        
    }
    return self;
}

/**
 * Create a study design object with the specified UUID.
 *
 * @param uuid
 *            the uuid
 */


-(id) initWithUuid:(NSMutableData *)theUuid
{
    self = [super init];
    if (self) {
        uuid = theUuid;
    }
    return self;
}

-(BOOL) isGaussianCovariate
{
    return gaussianCovariate;
}

/*--------------------
 * Return/Store AlphaList values
 *--------------------*/
/**
 * Gets the alpha list values.
 *
 * @return the alpha list values
 */

-(NSMutableArray const*) getAlphaListValues
{
    
    NSMutableArray const*list = alphaList;
    NSMutableArray *values = NULL;
    if (list != NULL && list.count != 0) {
        values = [[NSMutableArray alloc] initWithCapacity:list.count];
         for (TypeIError *node in list) {
             id newValue = [NSNumber numberWithDouble:node.alphaValue];
             [values addObject:newValue];
        }
    }
    return values;
}


-(void) setAlphaListValues:(NSMutableArray *)values
{
    if (values != NULL && values.count !=0) {
        NSMutableArray *list = [[NSMutableArray alloc] initWithCapacity:values.count];
        for (id x in values){
            double value = [x doubleValue];
            typeIError = [[TypeIError alloc] initWithAlphaValue:value];
            [list addObject:typeIError];
        }
        alphaList = list;
        /*for (int i=0; i<values.count; i++) {
            double value = [[values objectAtIndex:i] doubleValue];
            typeIError = [[TypeIError alloc] initWithAlphaValue:value];
            [list addObject:typeIError];
            
        }*/
    }
    
}

-(void) createRequestDictionary
{
    mutableDictionary = [initDictionary mutableCopy];
    //return mutableDictionary;
}


@end
