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
 * This is a wrapper for the Confidence Interval information.
 *
 * @author Aarti Munjal
 *
 */

@interface ConfidenceIntervalDescription : NSObject

{
    @private
    
    /*--------------------
     * Member Variables
     *--------------------*/
    /** The idx. */
    
    int idx;
    
    /** The beta fixed. */
    BOOL betaFixed;
    
    /** The sigma fixed. */
    BOOL sigmaFixed;
    
    /** The lower tail probability. */
    float lowerTailProbability;
    
    /** The upper tail probability. */
    float upperTailProbability;
    
    /** The sample size. */
    int sampleSize;
    
    /** The rank of design matrix. */
    int rankOfDesignMatrix;
    
}

@property (assign, nonatomic) int idx;
@property (assign, nonatomic) BOOL betaFixed;
@property (assign, nonatomic) BOOL sigmaFixed;
@property (assign, nonatomic) float lowerTailProbability;
@property (assign, nonatomic) float upperTailProbability;
@property (assign, nonatomic) int sampleSize;
@property (assign, nonatomic) int rankOfDesignMatrix;



-(id) initWithBeta:(BOOL)theBetaFixed andSigma:(BOOL)theSigmaFixed andLowerProb:(float)theLowerTailProbability andUpperProb:(float)theUpperTailProbability andsampleSize:(int)theSampleSize andRank:(int)theRankOfDesignMatrix;
-(id) initWithIdx:(int)theIdx andBeta:(BOOL)theBetaFixed andSigma:(BOOL)theSigmaFixed andLowerProb:(float)theLowerTailProbability andUpperProb:(float)theUpperTailProbability andsampleSize:(int)theSampleSize andRank:(int)theRankOfDesignMatrix;
-(BOOL) isComplete;

@end
