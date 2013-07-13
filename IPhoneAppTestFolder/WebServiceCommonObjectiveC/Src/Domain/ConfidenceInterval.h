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

// TODO: Auto-generated Javadoc
/**
 * POJO describing a confidence interval.
 *
 * @author Aarti Munjal
 *
 */

@interface ConfidenceInterval : NSObject
{
    @private
    
    /** lower tail probability. */
    double alphaLower;
    /** upper tail probability. */
    double alphaUpper;
    /** lower limit of the conifidence interval. */
    double lowerLimit;
    /** upper limit of the confidence interval. */
    double upperLimit;
}

//@property (assign, nonatomic) long serialVersionUID;
@property (assign, nonatomic) double alphaLower;
@property (assign, nonatomic) double alphaUpper;
@property (assign, nonatomic) double lowerLimit;
@property (assign, nonatomic) double upperLimit;

-(id) initWithAlphaLower:(double)theAlphaLower andAlphaUpper:(double)theAlphaUpper andLowerLimit:(double)theLowerLimit andUpperLimit:(double)theUpperLimit;
-(double) getConfidenceCoefficient;

@end
