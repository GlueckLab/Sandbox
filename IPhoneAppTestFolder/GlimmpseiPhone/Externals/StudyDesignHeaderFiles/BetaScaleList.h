//
//  BetaScaleList.h
//  StudyDesign
//
//  Created by Aarti Munjal on 8/30/12.
//  Copyright (c) 2012 Deborah Glueck. All rights reserved.
//

#import <Foundation/Foundation.h>


/**
 * List of beta scale objects to work around Jackson serializaiton issues.
 *
 * @author Aarti Munjal
 *
 */

@interface BetaScaleList : NSObject
{
    @private
    
    /** The uuid. */
    NSMutableData const *uuid;
    
    /** The beta scale list. */
    NSMutableArray const *betaScaleList;
}

@property (strong, nonatomic) NSMutableData *uuid;
@property (strong, nonatomic) NSMutableArray const*betaScaleList;

-(id) initWithUuid:(NSMutableData const*)theUuid;
-(id) initWithList:(NSMutableArray const*)theList;
-(id) initWithUuid:(NSMutableData const*)theUuid andList:(NSMutableArray const*)theList;

@end
