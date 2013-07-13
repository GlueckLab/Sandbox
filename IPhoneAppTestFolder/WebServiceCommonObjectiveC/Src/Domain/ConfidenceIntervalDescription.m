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

#import "ConfidenceIntervalDescription.h"

/**
 * This is a wrapper for the Confidence Interval information.
 *
 * @author Aarti Munjal
 *
 */

@implementation ConfidenceIntervalDescription

/*--------------------
 * Getter/Setter Methods
 *--------------------*/

@synthesize idx;
@synthesize betaFixed;
@synthesize sigmaFixed;
@synthesize lowerTailProbability;
@synthesize upperTailProbability;
@synthesize sampleSize;
@synthesize rankOfDesignMatrix;

/*--------------------
 * Constructors
 *--------------------*/
/**
 * Instantiates a new confidence interval description.
 */


-(id) init
{
    self = [super init];
    if (self) {
        betaFixed = FALSE;
        sigmaFixed = FALSE;
        lowerTailProbability = -1;
        upperTailProbability = -1;
        sampleSize = -1;
        rankOfDesignMatrix = -1;
    }
    return self;
}

/**
 * Instantiates a new confidence interval description.
 *
 * @param isBetaFixed
 *            the is beta fixed
 * @param isSigmaFixed
 *            the is sigma fixed
 * @param lowerTailProbability
 *            the lower tail probability
 * @param upperTailProbability
 *            the upper tail probability
 * @param sampleSize
 *            the sample size
 * @param rankOfDesignMatrix
 *            the rank of design matrix
 */


-(id) initWithBeta:(BOOL)theBetaFixed andSigma:(BOOL)theSigmaFixed andLowerProb:(float)theLowerTailProbability andUpperProb:(float)theUpperTailProbability andsampleSize:(int)theSampleSize andRank:(int)theRankOfDesignMatrix
{
    self = [super init];
    if (self) {
        betaFixed = theBetaFixed;
        sigmaFixed = theSigmaFixed;
        lowerTailProbability = theLowerTailProbability;
        upperTailProbability = theUpperTailProbability;
        sampleSize = theSampleSize;
        rankOfDesignMatrix = theRankOfDesignMatrix;
    }
    return self;
}


/**
 * Instantiates a new confidence interval description.
 *
 * @param id
 *            the id
 * @param isBetaFixed
 *            the is beta fixed
 * @param isSigmaFixed
 *            the is sigma fixed
 * @param lowerTailProbability
 *            the lower tail probability
 * @param upperTailProbability
 *            the upper tail probability
 * @param sampleSize
 *            the sample size
 * @param rankOfDesignMatrix
 *            the rank of design matrix
 */

-(id) initWithIdx:(int)theIdx andBeta:(BOOL)theBetaFixed andSigma:(BOOL)theSigmaFixed andLowerProb:(float)theLowerTailProbability andUpperProb:(float)theUpperTailProbability andsampleSize:(int)theSampleSize andRank:(int)theRankOfDesignMatrix
{
    self = [super init];
    if (self) {
        idx = theIdx;
        betaFixed = theBetaFixed;
        sigmaFixed = theSigmaFixed;
        lowerTailProbability = theLowerTailProbability;
        upperTailProbability = theUpperTailProbability;
        sampleSize = theSampleSize;
        rankOfDesignMatrix = theRankOfDesignMatrix;
    }
    return self;
}

-(BOOL) isComplete
{
    return ((betaFixed == TRUE || sigmaFixed == TRUE) && lowerTailProbability >= 0 &&
            upperTailProbability >= 0 &&
            sampleSize > 1 &&
            rankOfDesignMatrix > 0);
}

@end
