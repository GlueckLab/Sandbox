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


#import "PowerCurveDataSeries.h"

/**
 * Description of an individual data series displayed on
 * a power curve.
 *
 * @author Aarti Munjal
 */

@implementation PowerCurveDataSeries

/*--------------------
 * Getter/Setter Methods
 *--------------------*/

@synthesize  idx;
@synthesize label;
@synthesize confidenceLimits;
@synthesize statisticalTestTypeEnum;
@synthesize betaScale;
@synthesize sigmaScale;
@synthesize typeIError;
@synthesize sampleSize;
@synthesize nominalPower;
@synthesize powerMethod;
@synthesize quantile;

/*--------------------
 * Constructors
 *--------------------*/
/**
 * Instantiates a new power curve data series.
 */

-(id) init
{
    self = [super init];
    if (self) {
        label = @"";
        confidenceLimits = FALSE;
        statisticalTestTypeEnum = NULL;
        betaScale = 0;
        sigmaScale = 0;
        typeIError = -1;
        sampleSize = -1;
        nominalPower = -1;
        powerMethod = NULL;
        quantile = -1;
    }
    return self;
}


@end
