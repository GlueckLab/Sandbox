/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *  GlimmpsePowerViewController.m
 *
 * A simple view controller that allows user to select the desired power level, if solving for sample size. A
 * user can select up to 4 power levels.
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
#import "GlimmpsePowerViewController.h"
#import "GlimmpseAppDelegate.h"

@interface GlimmpsePowerViewController ()

@end

@implementation GlimmpsePowerViewController
{
    GlimmpseAppDelegate *appDelegate;
}
@synthesize checkBox1;
@synthesize checkBox2;
@synthesize checkBox3;
@synthesize checkBox4;

@synthesize powerButtons;
@synthesize powerLabels;
@synthesize powerArray;

#pragma mark -
#pragma mark Initialization

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    appDelegate = (GlimmpseAppDelegate*)[[UIApplication sharedApplication]delegate];
    
    powerArray = [[NSArray alloc] initWithObjects:@"0.80",@"0.90",@"0.95",@"0.975", nil];
    
    powerLabels = [[NSMutableArray alloc] init];
    
    [checkBox1 setSelected:NO];
    [checkBox2 setSelected:NO];
    [checkBox3 setSelected:NO];
    [checkBox4 setSelected:NO];
    
    powerButtons = [[NSArray alloc] initWithObjects:checkBox1,checkBox2,checkBox3,checkBox4, nil];
    [super viewDidLoad];
	// Do any additional setup after loading the view.
}

-(void) viewWillAppear:(BOOL)animated
{
    UIButton* fakeButton = (UIButton *) [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"GlimmpseIconNoBG48x48.png"]];
    UIBarButtonItem *fakeButtonItem = [[UIBarButtonItem alloc] initWithCustomView:fakeButton];
    self.navigationItem.rightBarButtonItem = fakeButtonItem;
    
    int number = appDelegate.powerResults.count;
    
    for (int i=0; i < number; i++) {
        UIButton *button = [powerButtons objectAtIndex:i];
        NSString *power = [appDelegate.powerResults objectAtIndex:i];
        if ([appDelegate.powerButtonsStatus objectAtIndex:i] == @"Selected") {
            [powerLabels addObject:power];
            [button setSelected:YES];
            [button setImage:[UIImage imageNamed:@"117-todo.png"] forState:UIControlStateSelected];
        }
        else {
            [button setSelected:NO];
        }
        
    }
    
}


-(void)viewWillDisappear:(BOOL)animated
{
    
    BOOL flag = NO;
    for (int i=0; i < 4; i++) {
        UIButton *button = [powerButtons objectAtIndex:i];
        if (button.selected == YES) {
            flag = YES;
            //NSLog(@"button selected %i", i);
            NSString *power = [powerArray objectAtIndex:i];
            [appDelegate.powerResults replaceObjectAtIndex:i withObject:power];
            [appDelegate.powerButtonsStatus replaceObjectAtIndex:i withObject:@"Selected"];
        }
        else {
            [appDelegate.powerResults replaceObjectAtIndex:i withObject:@""];
            [appDelegate.powerButtonsStatus replaceObjectAtIndex:i withObject:@"NotSelected"];
        }
        
    }
    
    if (flag == NO && appDelegate.solvingForPower == @"Incomplete") {
        appDelegate.solvingForPower = @"Incomplete";
    }
    else if (flag == NO && appDelegate.solvingForPower == @"Complete") {
        appDelegate.solvingForPower = @"Incomplete";
        appDelegate.progressValue -= 0.17;
    }
    else if (flag == YES && (appDelegate.solvingForPower == @"Incomplete" || appDelegate.solvingForPower == @"")) {
        appDelegate.solvingForPower = @"Complete";
        appDelegate.progressValue +=0.17;
    }
    else if (flag ==YES && appDelegate.solvingForPower ==@"Complete") {
        appDelegate.solvingForPower = @"Complete";
    }
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

# pragma mark Actions

- (IBAction)checkPressed:(UIButton *)sender {
    
    if (sender.selected == YES) {
        [sender setSelected:NO];
    }
    else if (sender.selected == NO) {
        [sender setSelected:YES];
        [sender setImage:[UIImage imageNamed:@"117-todo.png"] forState:UIControlStateSelected];
    }
    
}

@end
