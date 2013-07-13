/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *  GlimmpseTypeIErrorViewController.m
 *
 * A simple view controller that allows user to select the type I error rate for the ANOVA study design.
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

#import "GlimmpseTypeIErrorViewController.h"
#import "GlimmpseAppDelegate.h"

@interface GlimmpseTypeIErrorViewController ()

@end

@implementation GlimmpseTypeIErrorViewController
{
    GlimmpseAppDelegate *appDelegate;
}

@synthesize typeIErrorPicker;
@synthesize firstArray;
@synthesize secondArray;
@synthesize thirdArray;
@synthesize selectedvalue;
@synthesize pickerViewDefaults;

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
    //UIButton* fakeButton = (UIButton *) [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"glimmpse.png"]];
    //UIBarButtonItem *fakeButtonItem = [[UIBarButtonItem alloc] initWithCustomView:fakeButton];
    //self.navigationItem.rightBarButtonItem = fakeButtonItem;
    
    NSLog(@"Inside view load for type error");
    
    appDelegate = (GlimmpseAppDelegate*)[[UIApplication sharedApplication]delegate];
    [super viewDidLoad];
    
    firstArray = [[NSArray alloc]initWithObjects:@"0.", nil];
    secondArray = [[NSArray alloc] initWithObjects:@"0",@"1",@"2",@"3",@"4",@"5",@"6",@"7",@"8",@"9", nil];
    
    thirdArray = [[NSArray alloc] initWithObjects:@"1",@"2",@"3",@"4",@"5",@"6",@"7",@"8",@"9", nil];
    
}

-(void)viewWillAppear:(BOOL)animated
{
    
    UIButton* fakeButton = (UIButton *) [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"GlimmpseIconNoBG48x48.png"]];
    UIBarButtonItem *fakeButtonItem = [[UIBarButtonItem alloc] initWithCustomView:fakeButton];
    self.navigationItem.rightBarButtonItem = fakeButtonItem;
    
    NSLog(@"Inside view will appear for type error");
    
}

-(void)viewWillDisappear:(BOOL)animated
{
    [self valueSelected:typeIErrorPicker];
    
    appDelegate.typeIError = selectedvalue;
    //NSLog(@"The value selected is %@", _selectedvalue);
    
    appDelegate.component0 = [typeIErrorPicker selectedRowInComponent:0];
    appDelegate.component1 = [typeIErrorPicker selectedRowInComponent:1];
    appDelegate.component2 = [typeIErrorPicker selectedRowInComponent:2];
    
    
}


-(void)viewDidAppear:(BOOL)animated
{
    NSLog(@"Inside view did appear for type error");
    
    [typeIErrorPicker reloadAllComponents];
    [typeIErrorPicker selectRow:appDelegate.component0 inComponent:0 animated:NO];
    [typeIErrorPicker selectRow:appDelegate.component1 inComponent:1 animated:NO];
    [typeIErrorPicker selectRow:appDelegate.component2 inComponent:2 animated:NO];
}

#pragma mark - picker view

- (NSInteger)numberOfComponentsInPickerView:(UIPickerView *)pickerView
{
    return 3;
}



- (NSInteger)pickerView:(UIPickerView *)pickerView numberOfRowsInComponent:(NSInteger)component
{
    if(component == 0)
    {
        return [firstArray count];
    }
    else if(component == 1)
    {
        return [secondArray count];
    }
    else if(component == 2)
    {
        return [thirdArray count];
    }
    else
    {
        return [thirdArray count];
    }
    
}


- (NSString *)pickerView:(UIPickerView *)pickerView titleForRow:(NSInteger)row forComponent:(NSInteger)component
{
    if(component == 0)
    {
        return [firstArray objectAtIndex:row];
    }
    else if(component == 1)
    {
        return [secondArray objectAtIndex:row];
    }
    else if(component == 2)
    {
        return [thirdArray objectAtIndex:row];
    }
    else
    {
        return [thirdArray objectAtIndex:row];
    }
}

#pragma mark - Private Methods


- (void)valueSelected:(id)sender
{
    NSUInteger numComponents = [[typeIErrorPicker dataSource] numberOfComponentsInPickerView:typeIErrorPicker];
    NSMutableString *text = [NSMutableString string];
    for(NSUInteger i = 0; i <numComponents; i++)
    {
        NSUInteger selectedRow = [typeIErrorPicker selectedRowInComponent:i];
        NSString *title = [[typeIErrorPicker delegate] pickerView:typeIErrorPicker titleForRow:selectedRow forComponent:i];
        [text appendString:title];
    }
    selectedvalue = text;
    
}



- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void)dealloc {
    [typeIErrorPicker release];
    [super dealloc];
}
- (void)viewDidUnload {
    [self setTypeIErrorPicker:nil];
    [super viewDidUnload];
}
@end
