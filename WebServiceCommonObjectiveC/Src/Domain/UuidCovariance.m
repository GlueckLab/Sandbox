//
//  UuidCovariance.m
//  StudyDesign
//
//  Created by Aarti Munjal on 8/30/12.
//  Copyright (c) 2012 Deborah Glueck. All rights reserved.
//

#import "UuidCovariance.h"

/** The Constant serialVersionUID. */
static const long serialVersionUID = 1L;

@implementation UuidCovariance

/*--------------------
 * Getter/Setter Methods
 *--------------------*/

@synthesize uuid;
@synthesize covariance;

/*--------------------
 * Constructors
 *--------------------*/

/**
 * Instantiates a new uuid covariance.
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
 * Instantiates a new uuid covariance.
 *
 * @param uuid
 *            the uuid
 * @param covariance
 *            the covariance
 */

-(id) initWithUuid:(NSMutableData *)theUuid andCovariance:(Covariance *)theCovariance
{
    self = [super init];
    if (self) {
        uuid = theUuid;
        covariance = theCovariance;
    }
    return self;
}


@end
