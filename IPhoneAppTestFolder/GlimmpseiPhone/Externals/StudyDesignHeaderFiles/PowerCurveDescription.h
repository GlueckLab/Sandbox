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
#import "HorizontalAxisEnum.h"

/**
 * List of statistical test objects to work around Jackson serializaiton issues.
 *
 * @author Aarti Munjal
 *
 */

@interface PowerCurveDescription : NSObject

{
    @private
    
    /*--------------------
     * Member Variables
     *--------------------*/
    /** The idx. */
    int idx;
    
    /** boolean indicating if the legend should be displayed. */
    BOOL legend;
    
    /** width and height of image. */
    int width;
    int height;
    
    /** The power curve description. */
    NSString *title;
    
    /** The horizontal axis label enum. */
    HorizontalAxisLabelEnum *horizontalAxisLabelEnum;
    
    /** List of data series included in the plot. */
    NSMutableArray *dataSeriesList;
}

@property (assign, nonatomic) int idx;
@property (assign, nonatomic) BOOL legend;
@property (assign, nonatomic) int width;
@property (assign, nonatomic) int height;
@property (strong, nonatomic) NSString *title;
@property (assign, nonatomic) HorizontalAxisLabelEnum *horizontalAxisLabelEnum;
@property (strong, nonatomic) NSMutableArray *dataSeriesList;

@end
