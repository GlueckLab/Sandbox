/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *  GlimmpseRelativeSizeViewController.h
 *
 * A simple view controller that allows user to enter relative group sizes between groups involved in the ANOVA
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

@interface GlimmpseRelativeSizeViewController : UIViewController <UITextFieldDelegate>

{
    IBOutlet UIScrollView *relativeScroll;
}


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


@property (strong, nonatomic) IBOutlet UISlider *slider1;
@property (strong, nonatomic) IBOutlet UISlider *slider2;
@property (strong, nonatomic) IBOutlet UISlider *slider3;
@property (strong, nonatomic) IBOutlet UISlider *slider4;
@property (strong, nonatomic) IBOutlet UISlider *slider5;
@property (strong, nonatomic) IBOutlet UISlider *slider6;
@property (strong, nonatomic) IBOutlet UISlider *slider7;
@property (strong, nonatomic) IBOutlet UISlider *slider8;
@property (strong, nonatomic) IBOutlet UISlider *slider9;
@property (strong, nonatomic) IBOutlet UISlider *slider10;

@property (strong, nonatomic) IBOutlet UILabel *relSizeLabel1;
@property (strong, nonatomic) IBOutlet UILabel *relSizeLabel2;
@property (strong, nonatomic) IBOutlet UILabel *relSizeLabel3;
@property (strong, nonatomic) IBOutlet UILabel *relSizeLabel4;
@property (strong, nonatomic) IBOutlet UILabel *relSizeLabel5;
@property (strong, nonatomic) IBOutlet UILabel *relSizeLabel6;
@property (strong, nonatomic) IBOutlet UILabel *relSizeLabel7;
@property (strong, nonatomic) IBOutlet UILabel *relSizeLabel8;
@property (strong, nonatomic) IBOutlet UILabel *relSizeLabel9;
@property (strong, nonatomic) IBOutlet UILabel *relSizeLabel10;


@property (strong, nonatomic) NSArray *groupLabels;
@property (strong, nonatomic) NSArray *groupSliders;
@property (strong, nonatomic) NSArray *groupRelativeSizes;

@property (assign, nonatomic) NSString *relativeSizeSelection;

- (IBAction)sliderPressed:(UISlider *)sender;


@end
