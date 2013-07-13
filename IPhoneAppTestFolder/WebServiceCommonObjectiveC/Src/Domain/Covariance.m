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


#import "Covariance.h"

// TODO: Auto-generated Javadoc
/**
 * This is a wrapper for the Covariance information.
 *
 * @author Aarti Munjal
 *
 */

static const long serialVersionUID = 1L;

@implementation Covariance


@synthesize idx;
@synthesize name;
@synthesize standardDeviationList;
@synthesize rho;
@synthesize delta;
@synthesize rows;
@synthesize columns;
@synthesize type;
@synthesize blob;

/*--------------------
 * Constructors
 *--------------------*/
/**
 * Instantiates a new covariance.
 */
-(id) init
{
    self = [super init];
    if (self) {
        name = NULL;
        type = NULL;
        rho = -2;
        delta = -1;
        rows = -1;
        columns = -1;
        blob = NULL;
    }
    return self;
}



/*--------------------
 * Return/Store data[]
 *--------------------*/
/**
 * Sets the standard deviation list from array.
 *
 * @param data the new standard deviation list from array
 */

-(void) setStandardDeviationListFromArray:(NSArray *)data
{
    if (data != NULL) {
        
        NSMutableArray *list = [self standardDeviationList];
        if(list != NULL && list.count > 0)
        {
            for (int i=0; i < data.count; i++) {
                double value = [[data objectAtIndex:i] doubleValue];
                
                StandardDeviation *std = [[StandardDeviation alloc] initWithValue:value];
                [list addObject:std];
            }
        }
        self.standardDeviationList = list;
    }
}

/*--------------------
 * Return/Store data[][]
 *--------------------*/
/**
 * Sets the blob.
 *
 * @param data
 *            the new blob
 */

-(void) setBlobFromArray:(NSMutableArray *)theData
{
    if (theData != NULL) {
        Blob2DArray *theblob = [[Blob2DArray alloc] initWithData:theData];
        blob = theblob;
    }
}

/*public void setBlobFromArray(double[][] data) {
    if(data != null)
    {
        Blob2DArray blob = new Blob2DArray(data);
        this.setBlob(blob);
    }
}*/


@end
