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



#import "PowerResult.h"

/**
 * Pojo containing a description of the general linear model power result
 *
 * @author Aarti Munjal
 */

static const long serialVersionUID = -6289570391909037726L;

// error/warning message associated with this result
// TODO: private String errMsg = null;
// nominal power value - if solving for sample size, this is the target power
// desired.  Otherwise, equals the actual power

@implementation PowerResult


@synthesize nominalPower;
@synthesize actualPower;
@synthesize totalSampleSize;
@synthesize alpha;
@synthesize betaScale;
@synthesize sigmaScale;
@synthesize test;
@synthesize powerMethod;
@synthesize quantile;
@synthesize confidenceInterval;

/**
 * Create an empty Power result object.
 */

-(id) init
{
    self = [super init];
    if (self) {
        confidenceInterval = NULL;
    }
    return self;
}


/**
 * Create a new Power result object.
 *
 * @param test the statistical test performed
 * @param alpha the type I error rate
 * @param nominalPower requested power (for sample size, detectable difference requests)
 * @param actualPower calculated power
 * @param sampleSize total sample size
 * @param betaScale scale factor for beta matrix (roughly interpreted as detectable difference)
 * @param sigmaScale scale factor for error covariance matrix
 * @param method method used for power calculation
 */

-(id) initWithTest:(StatisticalTestTypeEnum *)theStatisticalTest andTypeI:(TypeIError *)theAlpha andNominalPower:(NominalPower *)theNominalPower andActualPower:(double)theActualPower andSampleSize:(int)theSampleSize andBetaScale:(BetaScale *)theBetaScale andSigmaScale:(SigmaScale *)theSigmaScale andPowerMethod:(PowerMethod *)thePowerMethod
{
    self = [super init];
    if (self) {
        test = theStatisticalTest;
        alpha = theAlpha;
        nominalPower = theNominalPower;
        actualPower = theActualPower;
        totalSampleSize = theSampleSize;
        betaScale = theBetaScale;
        sigmaScale = theSigmaScale;
        powerMethod = thePowerMethod;
    }
    return self;
}



/**
 * Create a new GLMMPower object.
 *
 * @param test the statistical test performed
 * @param alpha the type I error rate
 * @param nominalPower requested power (for sample size, detectable difference requests)
 * @param actualPower calculated power
 * @param sampleSize total sample size
 * @param betaScale scale factor for beta matrix (roughly interpreted as detectable difference)
 * @param sigmaScale scale factor for error covariance matrix
 * @param method method used for power calculation
 * @param confidenceInterval confidence interval if requested
 */

-(id) initWithTest:(StatisticalTestTypeEnum *)theStatisticalTest andTypeI:(TypeIError *)theAlpha andNominalPower:(NominalPower *)theNominalPower andActualPower:(double)theActualPower andSampleSize:(int)theSampleSize andBetaScale:(BetaScale *)theBetaScale andSigmaScale:(SigmaScale *)theSigmaScale andPowerMethod:(PowerMethod *)thePowerMethod andConfidenceInterval:(ConfidenceInterval *)theConfidenceInterval
{
    self = [super init];
    if (self) {
        //test = theStatisticalTest;
        alpha = theAlpha;
        nominalPower = theNominalPower;
        actualPower = theActualPower;
        totalSampleSize = theSampleSize;
        betaScale = theBetaScale;
        sigmaScale = theSigmaScale;
        powerMethod = thePowerMethod;
        confidenceInterval = theConfidenceInterval;
    }
    return self;
}




/**
 * Create a new GLMMPower object.
 *
 * @param test the statistical test performed
 * @param alpha the type I error rate
 * @param nominalPower requested power (for sample size, detectable difference requests)
 * @param actualPower calculated power
 * @param sampleSize total sample size
 * @param betaScale scale factor for beta matrix (roughly interpreted as detectable difference)
 * @param sigmaScale scale factor for error covariance matrix
 * @param method method used for power calculation
 * @param quantile optional quantile value (for quantile power only)
 */

-(id) initWithTest:(StatisticalTestTypeEnum *)theStatisticalTest andTypeI:(TypeIError *)theAlpha andNominalPower:(NominalPower *)theNominalPower andActualPower:(double)theActualPower andSampleSize:(int)theSampleSize andBetaScale:(BetaScale *)theBetaScale andSigmaScale:(SigmaScale *)theSigmaScale andPowerMethod:(PowerMethod *)thePowerMethod andQuantile:(Quantile *)theQuantile
{
    self = [super init];
    if (self) {
        //test = theStatisticalTest;
        alpha = theAlpha;
        nominalPower = theNominalPower;
        actualPower = theActualPower;
        totalSampleSize = theSampleSize;
        betaScale = theBetaScale;
        sigmaScale = theSigmaScale;
        powerMethod = thePowerMethod;
        quantile = theQuantile;
    }
    return self;
}




/**
 * Create a new GLMMPower object.
 *
 * @param test the statistical test performed
 * @param alpha the type I error rate
 * @param nominalPower requested power (for sample size, detectable difference requests)
 * @param actualPower calculated power
 * @param sampleSize total sample size
 * @param betaScale scale factor for beta matrix (roughly interpreted as detectable difference)
 * @param sigmaScale scale factor for error covariance matrix
 * @param method method used for power calculation
 * @param quantile optional quantile value (for quantile power only)
 * @param confidenceInterval confidence interval if requested
 */

-(id) initWithTest:(StatisticalTestTypeEnum *)theStatisticalTest andTypeI:(TypeIError *)theAlpha andNominalPower:(NominalPower *)theNominalPower andActualPower:(double)theActualPower andSampleSize:(int)theSampleSize andBetaScale:(BetaScale *)theBetaScale andSigmaScale:(SigmaScale *)theSigmaScale andPowerMethod:(PowerMethod *)thePowerMethod andQuantile:(Quantile *)theQuantile andConfidenceInterval:(ConfidenceInterval *)theConfidenceInterval
{
    self = [super init];
    if (self) {
        //test = theStatisticalTest;
        alpha = theAlpha;
        nominalPower = theNominalPower;
        actualPower = theActualPower;
        totalSampleSize = theSampleSize;
        betaScale = theBetaScale;
        sigmaScale = theSigmaScale;
        powerMethod = thePowerMethod;
        quantile = theQuantile;
        confidenceInterval = theConfidenceInterval;
    }
    return self;
}


@end
