/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *  GlimmpseSmallestGroupSizeViewController.m
 *
 * A simple view controller that allows user to enter the desired size of the smallest group, if solving for
 * power. A user can enter up to 4 smallest group sizes.
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

#import "GlimmpseSmallestGroupSizeViewController.h"
#import "GlimmpseAppDelegate.h"

@interface GlimmpseSmallestGroupSizeViewController ()

@end

@implementation GlimmpseSmallestGroupSizeViewController
{
    GlimmpseAppDelegate *appDelegate;
}

@synthesize smallestGroupSizeTextField1;
@synthesize smallestGroupSizeTextField2;
@synthesize smallestGroupSizeTextField3;
@synthesize smallestGroupSizeTextField4;
@synthesize smallestGroupSizeValue;
@synthesize status;


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
    //NSLog(@"Inside smallest");
    appDelegate = (GlimmpseAppDelegate*)[[UIApplication sharedApplication]delegate];
    
    [scrollerSmallestGroupSize setScrollEnabled:YES];
    [scrollerSmallestGroupSize setContentSize:CGSizeMake(320,850)];
    
    [super viewDidLoad];
	// Do any additional setup after loading the view.
    
    //Display UIKeyboard NumberPad
    smallestGroupSizeTextField1.keyboardType = UIKeyboardTypeNumberPad;
    smallestGroupSizeTextField2.keyboardType = UIKeyboardTypeNumberPad;
    smallestGroupSizeTextField3.keyboardType = UIKeyboardTypeNumberPad;
    smallestGroupSizeTextField4.keyboardType = UIKeyboardTypeNumberPad;
    
    smallestGroupSizeTextField1.delegate = self;
    smallestGroupSizeTextField2.delegate = self;
    smallestGroupSizeTextField3.delegate = self;
    smallestGroupSizeTextField4.delegate = self;
    
}


-(void)viewWillAppear:(BOOL)animated
{
    //NSLog(@"inside will appear for smallest");
    UIButton* fakeButton = (UIButton *) [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"GlimmpseIconNoBG48x48.png"]];
    UIBarButtonItem *fakeButtonItem = [[UIBarButtonItem alloc] initWithCustomView:fakeButton];
    self.navigationItem.rightBarButtonItem = fakeButtonItem;
    
    smallestGroupSizeTextField1.text = appDelegate.smallestGroupSize;
    smallestGroupSizeTextField2.text = appDelegate.sampleSizeResult2;
    smallestGroupSizeTextField3.text = appDelegate.sampleSizeResult3;
    smallestGroupSizeTextField4.text = appDelegate.sampleSizeResult4;
    
    status = appDelegate.smallestGroupSizeStatus;
}

-(void)viewWillDisappear:(BOOL)animated
{
    
    
    appDelegate.smallestGroupSize = smallestGroupSizeTextField1.text;
    appDelegate.sampleSizeResult2 = smallestGroupSizeTextField2.text;
    appDelegate.sampleSizeResult3 = smallestGroupSizeTextField3.text;
    appDelegate.sampleSizeResult4 = smallestGroupSizeTextField4.text;
    
    [appDelegate.sampleSizeResults replaceObjectAtIndex:0 withObject:appDelegate.smallestGroupSize];
    
    [appDelegate.sampleSizeResults replaceObjectAtIndex:1 withObject:appDelegate.sampleSizeResult2];
    [appDelegate.sampleSizeResults replaceObjectAtIndex:2 withObject:appDelegate.sampleSizeResult3];
    [appDelegate.sampleSizeResults replaceObjectAtIndex:3 withObject:appDelegate.sampleSizeResult4];
    
    //NSLog(@"The value selected is %@", _smallestGroupSizeTextField.text);
    
    appDelegate.smallestGroupSize = smallestGroupSizeTextField1.text;
    
    BOOL flag = TRUE;
    
    if ([appDelegate.smallestGroupSize isEqualToString:@""] && [appDelegate.sampleSizeResult2 isEqualToString:@""] && [appDelegate.sampleSizeResult3 isEqualToString:@""] && [appDelegate.sampleSizeResult4 isEqualToString:@""]) {
        flag = FALSE;
        status = @"Incomplete";
    }
    else {
        flag = TRUE;
        status = @"Complete";
    }
    NSNumberFormatter * f = [[NSNumberFormatter alloc] init];
    [f setLocale:[NSLocale currentLocale]];
    [f setNumberStyle:NSNumberFormatterDecimalStyle];
    
    NSNumber *tempNum = [f numberFromString:appDelegate.smallestGroupSize];
    
    smallestGroupSizeValue = [tempNum intValue];
    
    
    
    if (flag == TRUE && ([appDelegate.smallestGroupSizeStatus isEqualToString: @"Incomplete"] || [appDelegate.smallestGroupSizeStatus isEqualToString:@""])) {
        //NSLog(@"Just tested for increment");
        status = @"Complete";
        appDelegate.progressValue +=0.17;
    }
    else if (flag == FALSE && [appDelegate.smallestGroupSizeStatus isEqualToString:@"Complete"]) {
        //NSLog(@"Just tested for decrement");
        status = @"Incomplete";
        appDelegate.progressValue -=0.17;
    }
    
    else if (flag == TRUE && [appDelegate.smallestGroupSizeStatus isEqualToString:@"Complete"]) {
        //NSLog(@"Just tested for NO change");
        status  = @"Complete";
    }
    else if (flag == FALSE && [appDelegate.smallestGroupSizeStatus isEqualToString:@"Incomplete"]) {
        //NSLog(@"Just tested for NO Change");
        status = @"Incomplete";
    }
    
    appDelegate.smallestGroupSizeStatus = status;
}

- (BOOL)textField:(UITextField *)textField shouldChangeCharactersInRange:(NSRange)range replacementString:(NSString *)string {
    NSString *newString = [textField.text stringByReplacingCharactersInRange:range withString:string];
    return !([newString length] > 9);
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}

- (IBAction)dismissKeyboard:(id)sender
{
    [smallestGroupSizeTextField1 resignFirstResponder];
    [smallestGroupSizeTextField2 resignFirstResponder];
    [smallestGroupSizeTextField3 resignFirstResponder];
    [smallestGroupSizeTextField4 resignFirstResponder];
}



- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
