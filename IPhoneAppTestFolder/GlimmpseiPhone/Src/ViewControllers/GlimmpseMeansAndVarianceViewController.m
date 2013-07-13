/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *  GlimmpseDesignMeansAndVarianceViewController.m
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

#import "GlimmpseMeansAndVarianceViewController.h"
#import "GlimmpseAppDelegate.h"

@interface GlimmpseMeansAndVarianceViewController ()

@end

@implementation GlimmpseMeansAndVarianceViewController
{
    GlimmpseAppDelegate *appDelegate;
}

@synthesize groupLabel1;
@synthesize groupLabel2;
@synthesize groupLabel3;
@synthesize groupLabel4;
@synthesize groupLabel5;
@synthesize groupLabel6;
@synthesize groupLabel7;
@synthesize groupLabel8;
@synthesize groupLabel9;
@synthesize groupLabel10;

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

@synthesize varianceArray;
@synthesize meansArray;
@synthesize groupsArray;
@synthesize meansAndVariance;

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
    
    /**
     enable and set the scroll size.
     */
    
    [meansVarianceScroll setScrollEnabled:YES];
    [meansVarianceScroll setContentSize:CGSizeMake(320,1200)];
    
    
    [super viewDidLoad];
    
    
    meanGroup1.delegate = self;
    meanGroup2.delegate = self;
    meanGroup3.delegate = self;
    meanGroup4.delegate = self;
    meanGroup5.delegate = self;
    meanGroup6.delegate = self;
    meanGroup7.delegate = self;
    meanGroup8.delegate = self;
    meanGroup9.delegate = self;
    meanGroup10.delegate = self;
    
    varianceGroup1.delegate = self;
    
    /**
     NSArrays to hold group lablels, means, and variance.
     */
    
    groupsArray = [[NSArray alloc] initWithObjects:groupLabel1, groupLabel2,groupLabel3,groupLabel4,groupLabel5,groupLabel6,groupLabel7,groupLabel8,groupLabel9,groupLabel10,nil];
    
    meansArray = [[NSArray alloc] initWithObjects:meanGroup1,meanGroup2,meanGroup3,meanGroup4,meanGroup5,meanGroup6,meanGroup7,meanGroup8,meanGroup9,meanGroup10,nil];
    
    varianceArray = [[NSArray alloc] initWithObjects:varianceGroup1,varianceGroup2,varianceGroup3,varianceGroup4,varianceGroup5,varianceGroup6,varianceGroup7,varianceGroup8,varianceGroup9,varianceGroup10,nil];
    
    for (int i=0; i<meansArray.count; i++) {
        
        UITextField *currentField = [meansArray objectAtIndex:i];
        currentField.keyboardType = UIKeyboardTypeDecimalPad;
        
    }
    
    for (int i=0; i<varianceArray.count; i++) {
        
        UITextField *currentField = [varianceArray objectAtIndex:i];
        currentField.keyboardType = UIKeyboardTypeDecimalPad;
    }
    
    
    /**
     convert NSString to integer value.
     */
    
    NSNumberFormatter * f = [[NSNumberFormatter alloc] init];
    [f setLocale:[NSLocale currentLocale]];
    [f setNumberStyle:NSNumberFormatterDecimalStyle];
    
    NSNumber *tempNum = [f numberFromString:appDelegate.numberOfGroups];
    
    int groups_ = [tempNum intValue];
    
    
    /**
     Based on number of groups selected, show/hide the labels, means, and variance text fields.
     Initialize the _meansAndVariance to Incomplete.
     */
    
    for (int i=  1; i < 10; i++)
    {
        UISlider *currentVarianceField = [varianceArray objectAtIndex:i];
        currentVarianceField.hidden = YES;
    }
    
    for (int i=  groups_; i < 10; i++)
    {
        //NSLog(@"value for i is: %i", i);
        
        
        UITextField *currentMeanField = [meansArray objectAtIndex:i];
        currentMeanField.hidden = YES;
        
        UILabel *currentLabel = [groupsArray objectAtIndex:i];
        currentLabel.hidden = YES;
    }
    
    meansAndVariance = @"Incomplete";
    
	
}

-(void) viewWillAppear:(BOOL)animated
{
    
    /**
     Create a UIButton to place the GLIMMPSE logo.
     */
    
    UIButton* fakeButton = (UIButton *) [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"GlimmpseIconNoBG48x48.png"]];
    UIBarButtonItem *fakeButtonItem = [[UIBarButtonItem alloc] initWithCustomView:fakeButton];
    self.navigationItem.rightBarButtonItem = fakeButtonItem;
    
    /**
     Convert NSString to integer value.
     */
    
    NSNumberFormatter * f = [[NSNumberFormatter alloc] init];
    [f setLocale:[NSLocale currentLocale]];
    [f setNumberStyle:NSNumberFormatterDecimalStyle];
    
    NSNumber *tempNum = [f numberFromString:appDelegate.numberOfGroups];
    
    int groups_ = [tempNum intValue];
    
    
    /**
     Update the means and variance values for each group.
     */
    
    NSString *variance = [appDelegate.varianceOfGroups objectAtIndex:0];
    UITextField *varianceValue = [varianceArray objectAtIndex:0];
    varianceValue.text = variance;
    
    for (int i=0; i< groups_; i++)
    {
        NSString *mean = [appDelegate.meanOfGroups objectAtIndex:i];
        UITextField *meanValue = [meansArray objectAtIndex:i];
        meanValue.text = mean;
    }
    
    /**
     Update the design table detail text label if all the user inputs for this view are complete.
     */
    
    
    BOOL flag = TRUE;
    for (int i=0; i< groups_; i++) {
        NSString *value = [appDelegate.meanOfGroups objectAtIndex:i];
        
        if (value.length == 0)
            flag = FALSE;
        
        value = [appDelegate.varianceOfGroups objectAtIndex:0];
        if (value.length == 0)
            flag = FALSE;
    }
    
    if (flag == TRUE && appDelegate.meansAndVariance == @"Complete")
    {
        appDelegate.meansAndVariance = @"Complete";
    }
    
    else if (flag == TRUE && appDelegate.meansAndVariance != @"InComplete"){
        appDelegate.meansAndVariance = @"Complete";
        appDelegate.progressValue += 0.17;
        
        
    }
    else if (flag != TRUE)
        appDelegate.meansAndVariance = @"Incomplete";
}


-(void) viewWillDisappear:(BOOL)animated
{
    
    /**
     Convert NSString to integer value.
     */
    
    NSNumberFormatter * f = [[NSNumberFormatter alloc] init];
    [f setLocale:[NSLocale currentLocale]];
    [f setNumberStyle:NSNumberFormatterDecimalStyle];
    
    NSNumber *tempNum = [f numberFromString:appDelegate.numberOfGroups];
    
    int groups_ = [tempNum intValue];
    
    /**
     Update appDelegate for the means and variance values for each group.
     */
    
    UITextField *varianceValue = [varianceArray objectAtIndex:0];
    [appDelegate.varianceOfGroups replaceObjectAtIndex:0 withObject:varianceValue.text];
    for (int i=0; i< groups_; i++)
    {
        
        UITextField *meanValue = [meansArray objectAtIndex:i];
        [appDelegate.meanOfGroups replaceObjectAtIndex:i withObject:meanValue.text];
        
        
        
    }
    
    /**
     Update the design table detail text label if all the user inputs for this view are complete.
     */
    
    
    BOOL flag = TRUE;
    for (int i=0; i< groups_; i++) {
        //NSLog(@"value at mean of group: %i is: %@", i, [appDelegate.meanOfGroups objectAtIndex:i]);
        NSString *value = [appDelegate.meanOfGroups objectAtIndex:i];
        
        if (value.length == 0)
            flag = FALSE;
        
        value = [appDelegate.varianceOfGroups objectAtIndex:0];
        if (value.length == 0)
            flag = FALSE;
    }
    
    if (flag == TRUE && appDelegate.meansAndVariance != @"Complete")
    {
        //NSLog(@"In all True");
        meansAndVariance = @"Complete";
        //appDelegate.meansAndVariance = _meansAndVariance;
        appDelegate.progressValue += 0.17;
        
    }
    
    else if (flag == TRUE && appDelegate.meansAndVariance == @"Complete"){
        //NSLog(@"In half True");
        meansAndVariance = @"Complete";
        //appDelegate.meansAndVariance = @"Complete";
    }
    
    
    
    else if (flag == FALSE && appDelegate.meansAndVariance == @"Complete") {
        meansAndVariance = @"Incomplete";
        appDelegate.progressValue -= 0.17;
    }
    
    appDelegate.meansAndVariance = meansAndVariance;
}

- (BOOL)textField:(UITextField *)textField shouldChangeCharactersInRange:(NSRange)range replacementString:(NSString *)string {
    NSString *newString = [textField.text stringByReplacingCharactersInRange:range withString:string];
    return !([newString length] > 9);
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}


- (IBAction)keyBoardDismiss:(id)sender {
    [meanGroup1 resignFirstResponder];
    [meanGroup2 resignFirstResponder];
    [meanGroup3 resignFirstResponder];
    [meanGroup4 resignFirstResponder];
    [meanGroup5 resignFirstResponder];
    [meanGroup6 resignFirstResponder];
    [meanGroup7 resignFirstResponder];
    [meanGroup8 resignFirstResponder];
    [meanGroup9 resignFirstResponder];
    [meanGroup10 resignFirstResponder];
    
    [varianceGroup1 resignFirstResponder];
}


- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
