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
#import "NominalPower.h"
#import "BetaScale.h"
#import "TypeIError.h"
#import "SigmaScale.h"
#import "StatisticalTestEnum.h"
#import "Quantile.h"
#import "ConfidenceInterval.h"
#import "PowerMethod.h"

/**
* Pojo containing a description of the general linear model power result
*
* @author Aarti Munjal
*/

@interface PowerResult : NSObject

{
    @private
    
    NominalPower *nominalPower;
    // calculated power
    double actualPower;
    // total sample size for all groups
    int totalSampleSize;
    // Type I error rate
    TypeIError *alpha;
    // scale factor for beta matrix
    BetaScale *betaScale;
	// scale factor for the sigma error matrix
    SigmaScale *sigmaScale;
	// statistical test performed
    StatisticalTestTypeEnum *test;
	// power method used
    PowerMethod *powerMethod;
	// quantile if using quantile power, null otherwise
    Quantile *quantile;
	// confidence limits for power if requested
	// only available if solving for power in a random design
	ConfidenceInterval *confidenceInterval;
    
}

@property (strong, nonatomic) NominalPower *nominalPower;
@property (assign, nonatomic) double actualPower;
@property (assign, nonatomic) int totalSampleSize;
@property (strong, nonatomic) TypeIError *alpha;
@property (strong, nonatomic) BetaScale *betaScale;
@property (strong, nonatomic) SigmaScale *sigmaScale;
@property (assign, nonatomic) StatisticalTestTypeEnum *test;
@property (strong, nonatomic) PowerMethod *powerMethod;
@property (strong, nonatomic) Quantile *quantile;
@property (strong, nonatomic) ConfidenceInterval *confidenceInterval;


-(id) initWithTest:(StatisticalTestTypeEnum *)theStatisticalTest andTypeI:(TypeIError *)theAlpha andNominalPower:(NominalPower *)theNominalPower andActualPower:(double)theActualPower andSampleSize:(int)theSampleSize andBetaScale:(BetaScale *)theBetaScale andSigmaScale:(SigmaScale *)theSigmaScale andPowerMethod:(PowerMethod *)thePowerMethod;
-(id) initWithTest:(StatisticalTestTypeEnum *)theStatisticalTest andTypeI:(TypeIError *)theAlpha andNominalPower:(NominalPower *)theNominalPower andActualPower:(double)theActualPower andSampleSize:(int)theSampleSize andBetaScale:(BetaScale *)theBetaScale andSigmaScale:(SigmaScale *)theSigmaScale andPowerMethod:(PowerMethod *)thePowerMethod andConfidenceInterval:(ConfidenceInterval *)theConfidenceInterval;
-(id) initWithTest:(StatisticalTestTypeEnum *)theStatisticalTest andTypeI:(TypeIError *)theAlpha andNominalPower:(NominalPower *)theNominalPower andActualPower:(double)theActualPower andSampleSize:(int)theSampleSize andBetaScale:(BetaScale *)theBetaScale andSigmaScale:(SigmaScale *)theSigmaScale andPowerMethod:(PowerMethod *)thePowerMethod andQuantile:(Quantile *)theQuantile;
-(id) initWithTest:(StatisticalTestTypeEnum *)theStatisticalTest andTypeI:(TypeIError *)theAlpha andNominalPower:(NominalPower *)theNominalPower andActualPower:(double)theActualPower andSampleSize:(int)theSampleSize andBetaScale:(BetaScale *)theBetaScale andSigmaScale:(SigmaScale *)theSigmaScale andPowerMethod:(PowerMethod *)thePowerMethod andQuantile:(Quantile *)theQuantile andConfidenceInterval:(ConfidenceInterval *)theConfidenceInterval;

@end
