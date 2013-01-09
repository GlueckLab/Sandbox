/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *  GlimmpseDesignMeansAndVarianceViewController.h
 *
 * A simple view controller that allows user to input enter means and variance for each group for the ANOVA
 * study design.
 *
 * Version: 1.0.0
 * @author Aarti Munjal on 11/15/12.
 *
 * GLIMMPSE Lite to calculate power and sample size for ANOVA study
 * designs, which interacts with Glimmpse Software Subsystems.
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
 *
 *
 *
 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

#import <UIKit/UIKit.h>

@interface GlimmpseMeansAndVarianceViewController : UIViewController <UITextFieldDelegate>

{
    IBOutlet UIScrollView *meansVarianceScroll;
    
}

//- (IBAction) dismissTheKeyboard:(id)sender;

- (IBAction)keyBoardDismiss:(id)sender;


@property (strong, nonatomic) IBOutlet UILabel *groupLabel1;
@property (strong, nonatomic) IBOutlet UILabel *groupLabel2;
@property (strong, nonatomic) IBOutlet UILabel *groupLabel3;
@property (strong, nonatomic) IBOutlet UILabel *groupLabel4;
@property (strong, nonatomic) IBOutlet UILabel *groupLabel5;
@property (strong, nonatomic) IBOutlet UILabel *groupLabel6;
@property (strong, nonatomic) IBOutlet UILabel *groupLabel7;
@property (strong, nonatomic) IBOutlet UILabel *groupLabel8;
@property (strong, nonatomic) IBOutlet UILabel *groupLabel9;
@property (strong, nonatomic) IBOutlet UILabel *groupLabel10;


@property (strong, nonatomic) IBOutlet UITextField *meanGroup1;
@property (strong, nonatomic) IBOutlet UITextField *meanGroup2;
@property (strong, nonatomic) IBOutlet UITextField *meanGroup3;
@property (strong, nonatomic) IBOutlet UITextField *meanGroup4;
@property (strong, nonatomic) IBOutlet UITextField *meanGroup5;
@property (strong, nonatomic) IBOutlet UITextField *meanGroup6;
@property (strong, nonatomic) IBOutlet UITextField *meanGroup7;
@property (strong, nonatomic) IBOutlet UITextField *meanGroup8;
@property (strong, nonatomic) IBOutlet UITextField *meanGroup9;
@property (strong, nonatomic) IBOutlet UITextField *meanGroup10;


@property (strong, nonatomic) IBOutlet UITextField *varianceGroup1;
@property (strong, nonatomic) IBOutlet UITextField *varianceGroup2;
@property (strong, nonatomic) IBOutlet UITextField *varianceGroup3;
@property (strong, nonatomic) IBOutlet UITextField *varianceGroup4;
@property (strong, nonatomic) IBOutlet UITextField *varianceGroup5;
@property (strong, nonatomic) IBOutlet UITextField *varianceGroup6;
@property (strong, nonatomic) IBOutlet UITextField *varianceGroup7;
@property (strong, nonatomic) IBOutlet UITextField *varianceGroup8;
@property (strong, nonatomic) IBOutlet UITextField *varianceGroup9;
@property (strong, nonatomic) IBOutlet UITextField *varianceGroup10;




@property (strong, nonatomic) NSArray *meansArray;
@property (strong, nonatomic) NSArray *varianceArray;
@property (strong, nonatomic) NSArray *groupsArray;

@property (assign, nonatomic) NSString *meansAndVariance;



@end
