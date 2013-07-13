/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *  GlimmpseAppDelegate.h
 *
 * A simple delegate to store input values and communicate with all view controllers.
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

@interface GlimmpseAppDelegate : UIResponder <UIApplicationDelegate, UITabBarControllerDelegate>
{
    
    // create tab bar controller and array to hold the view controllers
    UITabBarController *tabBarController;
    
    
    NSString *solvingFor;
    NSString *typeIError;
    NSString *numberOfGroups;
    NSString *smallestGroupSize;
    NSString *smallestGroupSizeStatus;
    NSString *relativeGroupSize;
    NSString *meansAndVariance;
    NSString *solvingForPower;
    NSString *solvingForSampleSize;
    
    NSString *nominalPower1;
    NSString *nominalPower2;
    NSString *nominalPower3;
    NSString *nominalPower4;
    
    NSString *powerResult1;
    NSString *powerResult2;
    NSString *powerResult3;
    NSString *powerResult4;
    
    NSString *sampleSizeResult1;
    NSString *sampleSizeResult2;
    NSString *sampleSizeResult3;
    NSString *sampleSizeResult4;
    
    NSString *powerButtonStatus1;
    NSString * powerButtonStatus2;
    NSString * powerButtonStatus3;
    NSString * powerButtonStatus4;
    
    NSString *relSizeSlider1;
    NSString *relSizeSlider2;
    NSString *relSizeSlider3;
    NSString *relSizeSlider4;
    NSString *relSizeSlider5;
    NSString *relSizeSlider6;
    NSString *relSizeSlider7;
    NSString *relSizeSlider8;
    NSString *relSizeSlider9;
    NSString *relSizeSlider10;
    
    NSString *relSizeLabel1;
    NSString *relSizeLabel2;
    NSString *relSizeLabel3;
    NSString *relSizeLabel4;
    NSString *relSizeLabel5;
    NSString *relSizeLabel6;
    NSString *relSizeLabel7;
    NSString *relSizeLabel8;
    NSString *relSizeLabel9;
    NSString *relSizeLabel10;
    
    NSString *meanGroup1;
    NSString *meanGroup2;
    NSString *meanGroup3;
    NSString *meanGroup4;
    NSString *meanGroup5;
    NSString *meanGroup6;
    NSString *meanGroup7;
    NSString *meanGroup8;
    NSString *meanGroup9;
    NSString *meanGroup10;
    
    NSString *varianceGroup1;
    NSString *varianceGroup2;
    NSString *varianceGroup3;
    NSString *varianceGroup4;
    NSString *varianceGroup5;
    NSString *varianceGroup6;
    NSString *varianceGroup7;
    NSString *varianceGroup8;
    NSString *varianceGroup9;
    NSString *varianceGroup10;
    
    NSString *jSONFileContents;
    NSMutableDictionary *jsonFileContents;
    
    int design;
    int component1;
    int component2;
    int component0;
    int numberOfGroupsSelected;
    int mode;
    
    float progressValue;
    NSString *resultsResponse;
    
}

-(void)initAllControllers;
@property (strong, nonatomic) UIWindow *window;
@property (strong, nonatomic) UITabBarController *tabBarController;

@property (strong, nonatomic) NSString *solvingFor;
@property (strong, nonatomic) NSString *typeIError;
@property (strong, nonatomic) NSString *numberOfGroups;
@property (strong, nonatomic) NSString *relativeGroupSize;
@property (strong, nonatomic) NSString *smallestGroupSize;
@property (strong, nonatomic) NSString *smallestGroupSizeStatus;
@property (strong, nonatomic) NSString *meansAndVariance;
@property (strong, nonatomic) NSString *solvingForPower;
@property (strong, nonatomic) NSString *solvingForSampleSize;

@property (strong, nonatomic) NSString *powerButtonStatus1;
@property (strong, nonatomic) NSString *powerButtonStatus2;
@property (strong, nonatomic) NSString *powerButtonStatus3;
@property (strong, nonatomic) NSString *powerButtonStatus4;

@property (strong, nonatomic) NSString *nominalPower1;
@property (strong, nonatomic) NSString *nominalPower2;
@property (strong, nonatomic) NSString *nominalPower3;
@property (strong, nonatomic) NSString *nominalPower4;

@property (strong, nonatomic) NSString *powerResult1;
@property (strong, nonatomic) NSString *powerResult2;
@property (strong, nonatomic) NSString *powerResult3;
@property (strong, nonatomic) NSString *powerResult4;

@property (strong, nonatomic) NSMutableArray *powerButtonsStatus;

@property (strong, nonatomic) NSString *sampleSizeResult1;
@property (strong, nonatomic) NSString *sampleSizeResult2;
@property (strong, nonatomic) NSString *sampleSizeResult3;
@property (strong, nonatomic) NSString *sampleSizeResult4;

@property (strong, nonatomic) NSString *relSizeSlider1;
@property (strong, nonatomic) NSString *relSizeSlider2;
@property (strong, nonatomic) NSString *relSizeSlider3;
@property (strong, nonatomic) NSString *relSizeSlider4;
@property (strong, nonatomic) NSString *relSizeSlider5;
@property (strong, nonatomic) NSString *relSizeSlider6;
@property (strong, nonatomic) NSString *relSizeSlider7;
@property (strong, nonatomic) NSString *relSizeSlider8;
@property (strong, nonatomic) NSString *relSizeSlider9;
@property (strong, nonatomic) NSString *relSizeSlider10;

@property (strong, nonatomic) NSString *relSizeLabel1;
@property (strong, nonatomic) NSString *relSizeLabel2;
@property (strong, nonatomic) NSString *relSizeLabel3;
@property (strong, nonatomic) NSString *relSizeLabel4;
@property (strong, nonatomic) NSString *relSizeLabel5;
@property (strong, nonatomic) NSString *relSizeLabel6;
@property (strong, nonatomic) NSString *relSizeLabel7;
@property (strong, nonatomic) NSString *relSizeLabel8;
@property (strong, nonatomic) NSString *relSizeLabel9;
@property (strong, nonatomic) NSString *relSizeLabel10;

@property (strong, nonatomic) NSString *meanGroup1;
@property (strong, nonatomic) NSString *meanGroup2;
@property (strong, nonatomic) NSString *meanGroup3;
@property (strong, nonatomic) NSString *meanGroup4;
@property (strong, nonatomic) NSString *meanGroup5;
@property (strong, nonatomic) NSString *meanGroup6;
@property (strong, nonatomic) NSString *meanGroup7;
@property (strong, nonatomic) NSString *meanGroup8;
@property (strong, nonatomic) NSString *meanGroup9;
@property (strong, nonatomic) NSString *meanGroup10;


@property (strong, nonatomic) NSString *varianceGroup1;
@property (strong, nonatomic) NSString *varianceGroup2;
@property (strong, nonatomic) NSString *varianceGroup3;
@property (strong, nonatomic) NSString *varianceGroup4;
@property (strong, nonatomic) NSString *varianceGroup5;
@property (strong, nonatomic) NSString *varianceGroup6;
@property (strong, nonatomic) NSString *varianceGroup7;
@property (strong, nonatomic) NSString *varianceGroup8;
@property (strong, nonatomic) NSString *varianceGroup9;
@property (strong, nonatomic) NSString *varianceGroup10;

@property (strong, nonatomic) NSMutableArray *nominalPowers;
@property (strong, nonatomic) NSMutableArray *powerResults;
@property (strong, nonatomic) NSMutableArray *sampleSizeResults;
@property (strong, nonatomic) NSMutableArray *relSizeSliders;
@property (strong, nonatomic) NSMutableArray *relSizeLabels;
@property (strong, nonatomic) NSMutableArray *meanOfGroups;
@property (strong, nonatomic) NSMutableArray *varianceOfGroups;

@property (strong, nonatomic) NSString *jSONFileContents;
@property (strong, nonatomic) NSMutableDictionary *jsonFileContents;

@property (assign, nonatomic) int design;
@property (assign, nonatomic) int component0;
@property (assign, nonatomic) int component1;
@property (assign, nonatomic) int component2;
@property (assign, nonatomic) int numberOfGroupsSelected;
@property (assign, nonatomic) int mode;
@property (assign, nonatomic) NSString *resultsResponse;

@property (assign, nonatomic) float progressValue;

@end
