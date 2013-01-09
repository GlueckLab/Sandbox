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

/**
 * Enum of available types of trends which can be tested in a hypothesis.
 */

@interface HypothesisTrendEnum : NSObject

{
    @private
    
    /** The idx. */
    const NSString* idx;
}

typedef enum HypothesisTrendTypeEnum {
    /** The NONE. */
    NONE,
    /** The CHANG e_ fro m_ baseline. */
    CHANGE_FROM_BASELINE,
    /** The AL l_ poynomial. */
    ALL_POLYNOMIAL,
    /** The LINEAR. */
    LINEAR,
    /** The QUADRATIC. */
    QUADRATIC,
    /** The CUBIC. */
    CUBIC,
}HypothesisTrendTypeEnum;

@property (strong, nonatomic) const NSString* idx;
@property (assign, nonatomic) HypothesisTrendTypeEnum *hypothesisTrendTypeEnum;

-(id) initWithIdx:(const NSString*)theIdx;
-(HypothesisTrendTypeEnum *) parseIdx:(const NSString*)theIdx;
    
@end
