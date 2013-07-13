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
#import "UuidCovarianceName.h"

/**
 * This is a wrapper for the Covariance object.
 *
 * When user requests remove/retrieve a Covariance, this wrapper class serves
 * the purpose. User can pass Uuid and Name of Covariance through this wrapper.
 *
 * @author Aarti Munjal
 */

@implementation UuidCovarianceName

/** The Constant serialVersionUID. */
static const long serialVersionUID = 1L;

/*--------------------
 * Getter/Setter Methods
 *--------------------*/

@synthesize uuid;
@synthesize covarianceName;

/*--------------------
 * Constructors
 *--------------------*/
/**
 * Instantiates a new uuid covariance name.
 *
 * @param uuid
 *            the uuid
 */

-(id) initWithUuid:(NSMutableData *)theUuid
{
    self = [super init];
    if (self) {
        uuid = theUuid;
    }
    return self;
}

/**
 * Instantiates a new uuid covariance name.
 *
 * @param covarianceName
 *            the covariance name
 */

-(id) initWithCovariance:(NSString *)theCovarianceName
{
    self = [super init];
    if (self) {
        covarianceName = theCovarianceName;
    }
    return self;
}


/**
 * Instantiates a new uuid covariance name.
 *
 * @param uuid
 *            the uuid
 * @param covarianceName
 *            the covariance name
 */

-(id) initWithUuid:(NSMutableData *)theUuid andCovarianceName:(NSString *)theCovarianceName
{
    self = [super init];
    if (self) {
        uuid = theUuid;
        covarianceName = theCovarianceName;
    }
    return self;
}



@end
