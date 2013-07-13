/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *  GlimmpseRelativeSizeViewController.m
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

#import "GlimmpseRelativeSizeViewController.h"
#import "GlimmpseAppDelegate.h"

@interface GlimmpseRelativeSizeViewController ()

@end

@implementation GlimmpseRelativeSizeViewController
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

@synthesize slider1;
@synthesize slider2;
@synthesize slider3;
@synthesize slider4;
@synthesize slider5;
@synthesize slider6;
@synthesize slider7;
@synthesize slider8;
@synthesize slider9;
@synthesize slider10;

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

@synthesize groupLabels;
@synthesize groupRelativeSizes;
@synthesize groupSliders;

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
    
    [relativeScroll setScrollEnabled:YES];
    [relativeScroll setContentSize:CGSizeMake(320,1000)];
    
    [super viewDidLoad];
    
    groupSliders = [[NSArray alloc] initWithObjects:slider1, slider2,slider3,slider4,slider5,slider6,slider7,slider8,slider9,slider10, nil];
    
    groupRelativeSizes = [[NSArray alloc] initWithObjects:relSizeLabel1,relSizeLabel2,relSizeLabel3,relSizeLabel4,relSizeLabel5,relSizeLabel6,relSizeLabel7,relSizeLabel8,relSizeLabel9,relSizeLabel10, nil];
    
    groupLabels = [[NSArray alloc] initWithObjects:groupLabel1,groupLabel2,groupLabel3,groupLabel4,groupLabel5,groupLabel6,groupLabel7,groupLabel8,groupLabel9,groupLabel10, nil];
    
    
    NSNumberFormatter * f = [[NSNumberFormatter alloc] init];
    [f setLocale:[NSLocale currentLocale]];
    [f setNumberStyle:NSNumberFormatterDecimalStyle];
    NSNumber *tempNum = [f numberFromString:appDelegate.numberOfGroups];
    
    int groups_ = [tempNum intValue];
    
    for (int i=0; i< groups_; i++) {
        
        //NSLog(@"inside the for loop");
        
        NSString *sliderValue = [appDelegate.relSizeSliders objectAtIndex:i];
        
        //NSLog(@"slider value from delegate %@", sliderValue);
        
        tempNum = [f numberFromString:sliderValue];
        
        
        NSString *labelValue = [appDelegate.relSizeLabels objectAtIndex:i];
        
        //NSLog(@"slider value from delegate %@", labelValue);
        
        UISlider *slider = [groupSliders objectAtIndex:i];
        
        slider.value = [tempNum floatValue];
        
        UITextField *label = [groupRelativeSizes objectAtIndex:i];
        
        
        label.text = labelValue;
        
    }
    
    
    for (int i=  groups_; i < 10; i++)
    {
        //NSLog(@"value for i is: %i", i);
        UISlider *currentSlider = [groupSliders objectAtIndex:i];
        currentSlider.hidden = YES;
        
        UITextField *currentTextField = [groupRelativeSizes objectAtIndex:i];
        currentTextField.hidden = YES;
        
        UILabel *currentLabel = [groupLabels objectAtIndex:i];
        currentLabel.hidden = YES;
    }
    _relativeSizeSelection = @"Equal";
    
    //NSLog(@"Just hidden");
    
    
	// Do any additional setup after loading the view.
}

-(void) viewWillDisappear:(BOOL)animated
{
    //NSLog(@"Inside WillDisappear");
    NSNumberFormatter * f = [[NSNumberFormatter alloc] init];
    [f setLocale:[NSLocale currentLocale]];
    [f setNumberStyle:NSNumberFormatterDecimalStyle];
    
    NSNumber *tempNum = [f numberFromString:appDelegate.numberOfGroups];
    
    int groups_ = [tempNum intValue];
    
    BOOL flag = TRUE;
    
    for (int i=0; i < groups_; i++) {
        
        UILabel *textField = [groupRelativeSizes objectAtIndex:i];
        
        UISlider *sliderValue = [groupSliders objectAtIndex:i];
        
        NSString *slider = [NSString stringWithFormat:@"%f", sliderValue.value];
        
        [appDelegate.relSizeSliders replaceObjectAtIndex:i withObject:slider];
        
        NSString *label =  textField.text;
        
        [appDelegate.relSizeLabels replaceObjectAtIndex:i withObject:label];
        
        if (sliderValue.value > 1)
            flag = FALSE;
        
        
    }
    
    if (flag)
        
        appDelegate.relativeGroupSize = _relativeSizeSelection;
    else
        appDelegate.relativeGroupSize = @"Unequal";
    
}

-(void) viewDidAppear:(BOOL)animated
{
    NSNumberFormatter * f = [[NSNumberFormatter alloc] init];
    [f setLocale:[NSLocale currentLocale]];
    [f setNumberStyle:NSNumberFormatterDecimalStyle];
    NSNumber *tempNum = [f numberFromString:appDelegate.numberOfGroups];
    
    int groups_ = [tempNum intValue];
    
    for (int i=0; i< groups_; i++) {
        
        NSString *sliderValue = [appDelegate.relSizeSliders objectAtIndex:i];
        
        tempNum = [f numberFromString:sliderValue];
        
        NSString *labelValue = [appDelegate.relSizeLabels objectAtIndex:i];
        
        UISlider *slider = [groupSliders objectAtIndex:i];
        
        slider.value = [tempNum floatValue];
        
        UITextField *label = [groupRelativeSizes objectAtIndex:i];
        
        
        label.text = labelValue;
        
    }
}

-(void)viewWillAppear:(BOOL)animated
{
    UIButton* fakeButton = (UIButton *) [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"GlimmpseIconNoBG48x48.png"]];
    UIBarButtonItem *fakeButtonItem = [[UIBarButtonItem alloc] initWithCustomView:fakeButton];
    self.navigationItem.rightBarButtonItem = fakeButtonItem;
    
    NSNumberFormatter * f = [[NSNumberFormatter alloc] init];
    [f setLocale:[NSLocale currentLocale]];
    [f setNumberStyle:NSNumberFormatterDecimalStyle];
    
    NSNumber *tempNum = [f numberFromString:appDelegate.numberOfGroups];
    
    int groups_ = [tempNum intValue];
    
    for (int i=0; i < groups_; i++) {
        
        NSString *sliderValue = [appDelegate.relSizeSliders objectAtIndex:i];
        NSString *labelValue = [appDelegate.relSizeLabels objectAtIndex:i];
        
        UISlider *slider = [groupSliders objectAtIndex:i];
        
        tempNum = [f numberFromString:sliderValue];
        
        slider.value = [tempNum floatValue];
        
        UITextField *label = [groupRelativeSizes objectAtIndex:i];
        
        label.text = labelValue;
    }
    
    
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}


#pragma mark -
#pragma mark Actions

- (IBAction)sliderPressed:(UISlider *)sender {
    
    
    int index_ = [groupSliders indexOfObject:sender];
    
    UILabel *textField = [groupRelativeSizes objectAtIndex:index_];
    
    int intValueBySlider = (int) [sender value];
    
    textField.text = [NSString stringWithFormat:@"%d", intValueBySlider];
    
    if (intValueBySlider > 1)
        
        _relativeSizeSelection = @"Unequal";
    else
        _relativeSizeSelection = @"Equal";
}



- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
