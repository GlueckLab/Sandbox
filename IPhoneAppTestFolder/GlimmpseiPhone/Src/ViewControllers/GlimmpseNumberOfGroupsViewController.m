/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *  GlimmpseNumberOfGroupsViewController.m
 *
 * A simple view controller that allows user to enter the number of groups involved in the ANOVA study design.
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

#import "GlimmpseNumberOfGroupsViewController.h"
#import "GlimmpseAppDelegate.h"

@interface GlimmpseNumberOfGroupsViewController ()

@end

@implementation GlimmpseNumberOfGroupsViewController
{
    GlimmpseAppDelegate *appDelegate;
}

@synthesize numberOfGroupsArray;
@synthesize numberOfGroupsPicker;
@synthesize numberOfGroupsSelected;

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

#pragma mark -
#pragma mark View lifecycle

- (void)viewDidLoad
{
    appDelegate = (GlimmpseAppDelegate*)[[UIApplication sharedApplication]delegate];
    
    [super viewDidLoad];
    
    //Load NSArray object
    numberOfGroupsArray = [[NSArray alloc]initWithObjects:@"2",@"3",@"4",@"5",@"6",@"7",@"8",@"9",@"10", nil];
}

-(void)viewWillAppear:(BOOL)animated
{
    UIButton* fakeButton = (UIButton *) [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"GlimmpseIconNoBG48x48.png"]];
    UIBarButtonItem *fakeButtonItem = [[UIBarButtonItem alloc] initWithCustomView:fakeButton];
    self.navigationItem.rightBarButtonItem = fakeButtonItem;
    
    
    [numberOfGroupsPicker selectRow:appDelegate.numberOfGroupsSelected inComponent:0 animated:NO];
}

- (void)viewDidUnload
{
    
    [self setNumberOfGroupsPicker:nil];
    [super viewDidUnload];
    // Release any retained subviews of the main view.
}

-(void)viewWillDisappear:(BOOL)animated
{
    [self valueSelected:numberOfGroupsPicker];
    appDelegate.numberOfGroups = numberOfGroupsSelected;
    //NSLog(@"The value selected is %@", _numberOfGroupsSelected);
    appDelegate.numberOfGroupsSelected = [numberOfGroupsPicker selectedRowInComponent:0];
}


- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}


#pragma mark UIPickerView Methods


- (NSInteger)numberOfComponentsInPickerView:(UIPickerView *)pickerView
{
    return 1;
}
- (NSInteger)pickerView:(UIPickerView *)pickerView numberOfRowsInComponent:(NSInteger)component
{
    return [numberOfGroupsArray count];
}

- (NSString *)pickerView:(UIPickerView *)pickerView titleForRow:(NSInteger)row forComponent:(NSInteger)component
{
    return [numberOfGroupsArray objectAtIndex:row];
}


//Method to capture the value selected in the PickerView


- (void)valueSelected:(id)sender
{
    NSUInteger numComponents = [[numberOfGroupsPicker dataSource] numberOfComponentsInPickerView:numberOfGroupsPicker];
    NSMutableString *text = [NSMutableString string];
    for(NSUInteger i = 0; i <numComponents; i++)
    {
        NSUInteger selectedRow = [numberOfGroupsPicker selectedRowInComponent:i];
        NSString *title = [[numberOfGroupsPicker delegate] pickerView:numberOfGroupsPicker titleForRow:selectedRow forComponent:i];
        [text appendString:title];
    }
    numberOfGroupsSelected = text;
}




- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
