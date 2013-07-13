/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *  GlimmpseSolvingForViewController.m
 *
 * A simple view controller that allows user to choose if they are solving for power or sample size.
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


#import "GlimmpseSolvingForViewController.h"
#import "GlimmpseAppDelegate.h"

@interface GlimmpseSolvingForViewController ()

@end

@implementation GlimmpseSolvingForViewController
{
    GlimmpseAppDelegate *appDelegate;
}
@synthesize segment;
@synthesize selectedSegment;


- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

#pragma mark -
#pragma mark View lifecycle


- (void)viewDidLoad
{
    appDelegate = (GlimmpseAppDelegate*)[[UIApplication sharedApplication]delegate];
    [super viewDidLoad];
	// Do any additional setup after loading the view.
}

-(void)viewWillAppear:(BOOL)animated
{
    
    UIButton* fakeButton = (UIButton *) [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"GlimmpseIconNoBG48x48.png"]];
    UIBarButtonItem *fakeButtonItem = [[UIBarButtonItem alloc] initWithCustomView:fakeButton];
    self.navigationItem.rightBarButtonItem = fakeButtonItem;
    
    
    if(appDelegate.solvingFor == @"Sample Size")
    {
        self.segment.selectedSegmentIndex = 1;
    }
    else
    {
        self.segment.selectedSegmentIndex = 0;
    }
}

- (IBAction)segmentChange:(id)sender
{
    
    selectedSegment = [segment selectedSegmentIndex];
}

-(void)viewWillDisappear:(BOOL)animated
{
    selectedSegment = segment.selectedSegmentIndex;
    if(selectedSegment == 0 && [appDelegate.solvingFor isEqualToString:@""])
    {
        appDelegate.solvingFor = @"Power";
        appDelegate.mode = 1;
        
        
    }
    else if (selectedSegment == 1 && [appDelegate.solvingFor isEqualToString:@""])
    {
        appDelegate.solvingFor = @"Sample Size";
        appDelegate.mode = 2;
    }
    
    else if (selectedSegment == 0 && [appDelegate.solvingFor isEqualToString:@"Sample Size"])
    {
        //NSLog(@"switch from sample size to power");
        for (int i=0; i< 4; i++) {
            [appDelegate.powerResults replaceObjectAtIndex:i withObject:@""];
        }
        
        //NSLog(@"sample size values are: %@, %@, %@, %@", appDelegate.smallestGroupSize, appDelegate.sampleSizeResult2, appDelegate.sampleSizeResult3,appDelegate.sampleSizeResult4);
        
        appDelegate.solvingFor = @"Power";
        appDelegate.mode = 1;
    }
    
    else if (selectedSegment == 1 && [appDelegate.solvingFor isEqualToString:@"Power"])
    {
        
        if ([[appDelegate.powerButtonsStatus objectAtIndex:0] isEqualToString:@"Selected"])
            [appDelegate.powerResults replaceObjectAtIndex:0 withObject:@"0.8"];
        if ([[appDelegate.powerButtonsStatus objectAtIndex:1] isEqualToString:@"Selected"])
            [appDelegate.powerResults replaceObjectAtIndex:1 withObject:@"0.9"];
        if ([[appDelegate.powerButtonsStatus objectAtIndex:2] isEqualToString:@"Selected"])
            [appDelegate.powerResults replaceObjectAtIndex:2 withObject:@"0.95"];
        if ([[appDelegate.powerButtonsStatus objectAtIndex:3] isEqualToString:@"Selected"])
            [appDelegate.powerResults replaceObjectAtIndex:3 withObject:@"0.975"];
        
        appDelegate.solvingFor = @"Sample Size";
        appDelegate.mode = 2;
        
    }
    
    else
    {
        //appDelegate.solvingFor = @"";
        //appDelegate.mode = 0;
    }
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void)dealloc {
    [segment release];
    [super dealloc];
}
- (void)viewDidUnload {
    [self setSegment:nil];
    [super viewDidUnload];
}
@end
