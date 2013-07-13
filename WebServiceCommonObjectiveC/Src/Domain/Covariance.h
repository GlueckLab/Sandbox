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
#import "CovarianceEnum.h"
#import "Blob2DArray.h"
#import "StandardDeviation.h"

// TODO: Auto-generated Javadoc
/**
 * This is a wrapper for the Covariance information.
 *
 * @author Aarti Munjal
 *
 */

@interface Covariance : NSObject
{
    /*--------------------
     * Member Variables
     *--------------------*/
    /** The idx. */
    
    @private
    
    int idx;
    
    /** The type. */
    CovarianceTypeEnum *type;
    
    NSString *name;
    
    /** The standard deviation list. */
    NSMutableArray *standardDeviationList;
    
    
    /** The rho. */
    double rho;
    
    /** The delta. */
    double delta;
    
    /** The rows. */
    int rows;
    
    /** The columns. */
    int columns;
    
    /** The blob. */
    Blob2DArray *blob;
    
}

@property (assign, nonatomic) int idx;
@property (assign, nonatomic) CovarianceTypeEnum *type;
@property (strong, nonatomic) NSString *name;
@property (strong, nonatomic) NSMutableArray *standardDeviationList;
@property (assign, nonatomic) double rho;
@property (assign, nonatomic) double delta;
@property (assign, nonatomic) int rows;
@property (assign, nonatomic) int columns;
@property (strong, nonatomic) Blob2DArray *blob;

-(void) setStandardDeviationListFromArray:(NSArray *)data;
-(void) setBlobFromArray:(NSMutableArray *)theData;


@end
