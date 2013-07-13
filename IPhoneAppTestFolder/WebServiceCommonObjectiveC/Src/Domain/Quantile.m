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

#import "Quantile.h"

/**
 * This is a wrapper for the Quantile a list object information.
 *
 * @author Aarti Munjal
 *
 */

/** The Constant serialVersionUID. */
static const long serialVersionUID = 7498358054750918337L;

@implementation Quantile

@synthesize idx;
@synthesize value;

/*--------------------
 * Constructors
 *--------------------*/
/**
 * Instantiates a new quantile.
 *
 * @param value
 *            the value
 */

-(id) initWithValue:(const double)theValue
{
    self = [super init];
    if (self) {
        value = theValue;
    }
    return self;
}

/**
 * Instantiates a new quantile.
 *
 * @param idx
 *            the idx
 * @param value
 *            the value
 */

-(id) initWithIdx:(const int)theIdx andValue:(const double)theValue
{
    self = [super init];
    if (self) {
        idx = theIdx;
        value = theValue;
    }
    return self;
}

@end
