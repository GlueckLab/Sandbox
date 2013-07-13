/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *  GlimmpseAppDelegate.m
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
 * This program is free software; you can redistribute it 
 * and/or
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


#import "GlimmpseAppDelegate.h"
#import "GlimmpseTutorialViewController.h"
#import "GlimmpseAboutUsViewController.h"
#import "GlimmpseDesignViewController.h"

@implementation GlimmpseAppDelegate

@synthesize tabBarController;

@synthesize solvingFor;
@synthesize typeIError;
@synthesize numberOfGroups;
@synthesize smallestGroupSize;
@synthesize relativeGroupSize;
@synthesize meansAndVariance;
@synthesize solvingForPower;
@synthesize solvingForSampleSize;

@synthesize nominalPower1;
@synthesize nominalPower2;
@synthesize nominalPower3;
@synthesize nominalPower4;

@synthesize powerResult1;
@synthesize powerResult2;
@synthesize powerResult3;
@synthesize powerResult4;

@synthesize sampleSizeResult1;
@synthesize sampleSizeResult2;
@synthesize sampleSizeResult3;
@synthesize sampleSizeResult4;

@synthesize powerButtonStatus1;
@synthesize powerButtonStatus2;
@synthesize powerButtonStatus3;
@synthesize powerButtonStatus4;

@synthesize relSizeSlider1;
@synthesize relSizeSlider2;
@synthesize relSizeSlider3;
@synthesize relSizeSlider4;
@synthesize relSizeSlider5;
@synthesize relSizeSlider6;
@synthesize relSizeSlider7;
@synthesize relSizeSlider8;
@synthesize relSizeSlider9;
@synthesize relSizeSlider10;

@synthesize relSizeLabel1;
@synthesize relSizeLabel2;
@synthesize relSizeLabel3;
@synthesize relSizeLabel4;
@synthesize relSizeLabel5;
@synthesize relSizeLabel6;
@synthesize relSizeLabel7;
@synthesize relSizeLabel8;
@synthesize relSizeLabel9;
@synthesize relSizeLabel10;

@synthesize meanGroup1;
@synthesize meanGroup2;
@synthesize meanGroup3;
@synthesize meanGroup4;
@synthesize meanGroup5;
@synthesize meanGroup6;
@synthesize meanGroup7;
@synthesize meanGroup8;
@synthesize meanGroup9;
@synthesize meanGroup10;

@synthesize varianceGroup1;
@synthesize varianceGroup2;
@synthesize varianceGroup3;
@synthesize varianceGroup4;
@synthesize varianceGroup5;
@synthesize varianceGroup6;
@synthesize varianceGroup7;
@synthesize varianceGroup8;
@synthesize varianceGroup9;
@synthesize varianceGroup10;

@synthesize jSONFileContents;
@synthesize jsonFileContents;
@synthesize mode;
@synthesize smallestGroupSizeStatus;

@synthesize design;
@synthesize component0,component1,component2;
@synthesize numberOfGroupsSelected;
@synthesize progressValue;
@synthesize resultsResponse;

@synthesize nominalPowers;
@synthesize powerResults;
@synthesize sampleSizeResults;
@synthesize relSizeSliders;
@synthesize relSizeLabels;
@synthesize meanOfGroups;
@synthesize varianceOfGroups;
@synthesize powerButtonsStatus;

- (void)dealloc
{
    [_window release];
    [super dealloc];
}

#pragma mark -
#pragma mark Application lifecycle


- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
    // Override point for customization after application launch.
    
    [self initAllControllers];

    sleep(2);
    solvingFor = @"";
    numberOfGroups = @"";
    smallestGroupSize = @"";
    smallestGroupSizeStatus = @"";
    relativeGroupSize = @"";
    typeIError = @"";
    meansAndVariance = @"";
    mode = 0;
    solvingForPower = @"";
    solvingForSampleSize = @"";
    resultsResponse = @"";
    
    nominalPower1 = @"";
    nominalPower2 = @"";
    nominalPower3 = @"";
    nominalPower4 = @"";
    
    powerResult1 = @"";
    powerResult2 = @"";
    powerResult3 = @"";
    powerResult4 = @"";
    
    
    
    powerButtonStatus1 = @"NotSelected";
    powerButtonStatus2 = @"NotSelected";
    powerButtonStatus3 = @"NotSelected";
    powerButtonStatus4 = @"NotSelected";
    
    sampleSizeResult1 = @"";
    sampleSizeResult2 = @"";
    sampleSizeResult3 = @"";
    sampleSizeResult4 = @"";
    
    relSizeSlider1 = @"1";
    relSizeSlider2 = @"1";
    relSizeSlider3 = @"1";
    relSizeSlider4 = @"1";
    relSizeSlider5 = @"1";
    relSizeSlider6 = @"1";
    relSizeSlider7 = @"1";
    relSizeSlider8 = @"1";
    relSizeSlider9 = @"1";
    relSizeSlider10 = @"1";
    
    relSizeLabel1 = @"1";
    relSizeLabel2 = @"1";
    relSizeLabel3 = @"1";
    relSizeLabel4 = @"1";
    relSizeLabel5 = @"1";
    relSizeLabel6 = @"1";
    relSizeLabel7 = @"1";
    relSizeLabel8 = @"1";
    relSizeLabel9 = @"1";
    relSizeLabel10 = @"1";
    
    meanGroup1 = @"";
    meanGroup2 = @"";
    meanGroup3 = @"";
    meanGroup4 = @"";
    meanGroup5 = @"";
    meanGroup6 = @"";
    meanGroup7 = @"";
    meanGroup8 = @"";
    meanGroup9 = @"";
    meanGroup10 = @"";
    
    varianceGroup1 = @"";
    varianceGroup2 = @"";
    varianceGroup3 = @"";
    varianceGroup4 = @"";
    varianceGroup5 = @"";
    varianceGroup6 = @"";
    varianceGroup7 = @"";
    varianceGroup8 = @"";
    varianceGroup9 = @"";
    varianceGroup10 = @"";
    
    jSONFileContents = @"";
    
    nominalPowers = [[NSMutableArray alloc] initWithObjects:nominalPower1, nominalPower2, nominalPower3, nominalPower4, nil];
    powerResults = [[NSMutableArray alloc] initWithObjects:powerResult1, powerResult2, powerResult3, powerResult4, nil];
    
    powerButtonsStatus = [[NSMutableArray alloc] initWithObjects:powerButtonStatus1,powerButtonStatus2,powerButtonStatus3,powerButtonStatus4, nil];
    
    sampleSizeResults = [[NSMutableArray alloc] initWithObjects:sampleSizeResult1,sampleSizeResult2,sampleSizeResult3,sampleSizeResult4, nil];
    
    relSizeSliders = [[NSMutableArray alloc] initWithObjects:relSizeSlider1,relSizeSlider2,relSizeSlider3,relSizeSlider4,relSizeSlider5,relSizeSlider6,relSizeSlider7,relSizeSlider8,relSizeSlider9,relSizeSlider10,nil];
    
    relSizeLabels = [[NSMutableArray alloc] initWithObjects:relSizeLabel1,relSizeLabel2,relSizeLabel3,relSizeLabel4,relSizeLabel5,relSizeLabel6,relSizeLabel7,relSizeLabel8,relSizeLabel9,relSizeLabel10,nil];
    
    meanOfGroups = [[NSMutableArray alloc] initWithObjects:meanGroup1,meanGroup2,meanGroup3,meanGroup4,meanGroup5,meanGroup6,meanGroup7,meanGroup8,meanGroup9,meanGroup10, nil];
    
    varianceOfGroups = [[NSMutableArray alloc] initWithObjects:varianceGroup1,varianceGroup2,varianceGroup3,varianceGroup4,varianceGroup5,varianceGroup6,varianceGroup7,varianceGroup8,varianceGroup9,varianceGroup10,nil];
    
    progressValue = 0;
    
        
    return YES;
}

-(void)initAllControllers
{
    tabBarController = [[UITabBarController alloc] init];
    
    UIStoryboard *mainStoryboard = [UIStoryboard storyboardWithName:@"MainStoryboard"
                                                             bundle: nil];
    
    UINavigationController *navigationController = [mainStoryboard instantiateViewControllerWithIdentifier:@"NavigationController"];
    
    navigationController.tabBarItem.image = [UIImage imageWithContentsOfFile:[[NSBundle mainBundle] pathForResource:@"165-glasses-3" ofType:@"png"]];
    
    UIImageView *iv = [[UIImageView alloc] initWithFrame:CGRectMake(0,0,48,48)];
    [iv setImage:[UIImage imageNamed:@"GlimmpseIconNoBG48x48.png"]];
    navigationController.navigationItem.rightBarButtonItem = [[UIBarButtonItem alloc] initWithCustomView:iv];
    [iv release];
    
    UINavigationController *designController = [mainStoryboard instantiateViewControllerWithIdentifier:@"GlimmpseDesignView"];
    
    designController.tabBarItem.image = [UIImage imageWithContentsOfFile:[[NSBundle mainBundle] pathForResource:@"20-gear2" ofType:@"png"]];
    
    //GlimmpseDesignViewController *designViewController = (GlimmpseDesignViewController*)[mainStoryboard instantiateViewControllerWithIdentifier:@"GlimmpseDesignViewController"];
    
    GlimmpseAboutUsViewController *aboutUsViewController = (GlimmpseAboutUsViewController*)[mainStoryboard instantiateViewControllerWithIdentifier:@"GlimmpseAboutUsViewController"];
    
    aboutUsViewController.tabBarItem.image = [UIImage imageWithContentsOfFile:[[NSBundle mainBundle] pathForResource:@"aboutus" ofType:@"png"]];
    
    //tabBarController.viewControllers = [NSArray arrayWithObjects:tutorialViewController, designViewController, aboutUsViewController, nil];
    
    tabBarController.viewControllers = [NSArray arrayWithObjects:navigationController, designController, aboutUsViewController, nil];
    
    
    
    self.tabBarController.selectedViewController
    = [self.tabBarController.viewControllers objectAtIndex:0];
    
}
							
- (void)applicationWillResignActive:(UIApplication *)application
{
    // Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state.
    // Use this method to pause ongoing tasks, disable timers, and throttle down OpenGL ES frame rates. Games should use this method to pause the game.
}

- (void)applicationDidEnterBackground:(UIApplication *)application
{
    // Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later. 
    // If your application supports background execution, this method is called instead of applicationWillTerminate: when the user quits.
}

- (void)applicationWillEnterForeground:(UIApplication *)application
{
    // Called as part of the transition from the background to the inactive state; here you can undo many of the changes made on entering the background.
}

- (void)applicationDidBecomeActive:(UIApplication *)application
{
    // Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
}

- (void)applicationWillTerminate:(UIApplication *)application
{
    // Called when the application is about to terminate. Save data if appropriate. See also applicationDidEnterBackground:.
}

@end
