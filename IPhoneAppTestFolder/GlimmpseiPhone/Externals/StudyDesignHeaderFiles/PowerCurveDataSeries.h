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
#import "StatisticalTestEnum.h"
#import "PowerEnum.h"

/**
 * Description of an individual data series displayed on
 * a power curve.
 *
 * @author Aarti Munjal
 */

@interface PowerCurveDataSeries : NSObject

{
    @private
    
    /*--------------------
     * Member Variables
     *--------------------*/
    /** The idx. */
    
    int idx;
    
    /** label for this data series. */
    NSString *label;
    
    /** indicates if confidence limits should be included on plot. */
    BOOL confidenceLimits;
    
    /** The statistical test. */
    StatisticalTestTypeEnum *statisticalTestTypeEnum;
    
    /** The beta scale. */
    double betaScale;
    
    /** The sigma scale. */
    double sigmaScale;
    
    /** The type i error. */
    double typeIError;
    
    /** The sample size. */
    int sampleSize;
    
    /** The nominal power. */
    double nominalPower;
    
    /** power method. */
    PowerMethodEnum *powerMethod;
    
    /** The quantile. */
    double quantile;
    
}

@property (assign, nonatomic) int idx;
@property (strong, nonatomic) NSString *label;
@property (assign, nonatomic) BOOL confidenceLimits;
@property (assign, nonatomic) StatisticalTestTypeEnum *statisticalTestTypeEnum;
@property (assign, nonatomic) double betaScale;
@property (assign, nonatomic) double sigmaScale;
@property (assign, nonatomic) double typeIError;
@property (assign, nonatomic) int sampleSize;
@property (assign, nonatomic) double nominalPower;
@property (assign, nonatomic) PowerMethodEnum *powerMethod;
@property (assign, nonatomic) double quantile;



@end
