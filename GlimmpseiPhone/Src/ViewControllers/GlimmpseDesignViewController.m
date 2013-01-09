/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *  GlimmpseDesignViewController.m
 *
 * A simple table view controller that allows user to enter inputs to create the ANOVA study design.
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



#import "GlimmpseDesignViewController.h"
#import "GlimmpseAppDelegate.h"
#import "AFNetworking.h"
#import "StudyDesign.h"

@interface GlimmpseDesignViewController ()

@end

@implementation GlimmpseDesignViewController
{
    GlimmpseAppDelegate *appDelegate;
}


@synthesize baseAlert;
@synthesize baseAlert2;
@synthesize progressView;
@synthesize progressIndicator;
@synthesize items;
@synthesize designTable;
@synthesize data;
@synthesize calculateButton;
@synthesize reset2Button;
@synthesize resetButton;

#pragma mark -
#pragma mark View lifecycle

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    
    self.designTable.delegate = self;
    self.designTable.dataSource = self;
    
    return self;
}

#pragma marks- TableView Data Source

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    NSLog(@"inside the sections method");
    return 1;
}

-(NSInteger) tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    NSLog(@"inside the rows method");
    return [self.items count];
    //NSLog(@"number of sections %@", );
    //items.count;
}

-(UITableViewCell *) tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    
    NSLog(@"inside the cell setter method");
    
    
    UITableViewCell *cell;
    
    cell = [tableView dequeueReusableCellWithIdentifier:@"MyCell"];
    
    if(cell == nil)
    {
        cell = [[UITableViewCell alloc]initWithStyle:UITableViewCellStyleValue1 reuseIdentifier:@"MyCell"];
    }
    
    cell.accessoryType = UITableViewCellAccessoryDisclosureIndicator;
    
    cell.textLabel.text = [items objectAtIndex:indexPath.row];
    
    cell.detailTextLabel.text = [data objectAtIndex:indexPath.row];
 
    
    if ([cell.detailTextLabel.text isEqualToString:@""] || [cell.detailTextLabel.text isEqualToString:@"Incomplete"]) {
        
        cell.imageView.image = [UIImage imageNamed:@"incomplete_icon.png"];
        
    }
    else {
        cell.imageView.image = [UIImage imageNamed:@"complete_icon.png"];
    }
    
    return cell;
}


- (void)viewDidLoad
{
    
    NSLog(@"Inside view did load");
    appDelegate = (GlimmpseAppDelegate*)[[UIApplication sharedApplication] delegate];
    
    self.title = @"Design";
    [self.view setUserInteractionEnabled:YES];
    [super viewDidLoad];
	// Do any additional setup after loading the view.
    
    /**
     Preload the table cells with static data.
     */
    
    if (appDelegate.mode == 0)
    {
        
        NSLog(@"Inside mode =0");
        items = [[NSArray alloc] initWithObjects:@"Solving For",@"Type I Error", @"Number of Groups",@"Relative Group Size", @"Smallest Group Size", @"Means and Variances", nil];
        
        data = [[NSArray alloc]initWithObjects:appDelegate.solvingFor, appDelegate.typeIError, appDelegate.numberOfGroups, appDelegate.relativeGroupSize, appDelegate.smallestGroupSizeStatus, appDelegate.meansAndVariance, nil];
        
        
    }
    else if (appDelegate.mode == 1)
    {
        NSLog(@"Inside mode =1");
        items = [[NSArray alloc] initWithObjects:@"Solving For",@"Type I Error", @"Number of Groups",@"Relative Group Size", @"Smallest Group Size", @"Means and Variances", nil];
        data = [[NSArray alloc]initWithObjects:appDelegate.solvingFor, appDelegate.typeIError, appDelegate.numberOfGroups, appDelegate.relativeGroupSize, appDelegate.smallestGroupSizeStatus, appDelegate.meansAndVariance, nil];
        
        
    }
    else if (appDelegate.mode == 2)
    {
        NSLog(@"Inside mode = 2");
        items = [[NSArray alloc] initWithObjects:@"Solving For",@"Power",@"Type I Error", @"Number of Groups",@"Relative Group Size", @"Means and Variances", nil];
        data = [[NSArray alloc]initWithObjects:appDelegate.solvingFor, appDelegate.solvingForPower, appDelegate.typeIError, appDelegate.numberOfGroups, appDelegate.relativeGroupSize, appDelegate.smallestGroupSizeStatus, appDelegate.meansAndVariance, nil];
        
    }
    
    /**
     Update progress bar with value from appDelegate. Hide or show the Calculate/reset buttons based on the progress made in entering user inputs.
     */
    
    progressView.progress = appDelegate.progressValue;
    
    if (progressView.progress == 1.0) {
        
        calculateButton.userInteractionEnabled = YES;
        reset2Button.userInteractionEnabled = YES;
        calculateButton.highlighted = YES;
        calculateButton.hidden = NO;
        reset2Button.hidden = NO;
        resetButton.hidden = YES;
        resetButton.highlighted = YES;
        
    }
    else {
        calculateButton.userInteractionEnabled = NO;
        calculateButton.hidden = YES;
        reset2Button.hidden = YES;
        resetButton.hidden = NO;
        resetButton.userInteractionEnabled = YES;
        resetButton.highlighted = YES;
    }

    
}

-(void)viewWillAppear:(BOOL)animated
{
    
    /**
     Load GLIMMPSE logo.
     */
    
    UIButton* fakeButton = (UIButton *) [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"GlimmpseIconNoBG48x48.png"]];//glimmpse50x50.png
    UIBarButtonItem *fakeButtonItem = [[UIBarButtonItem alloc] initWithCustomView:fakeButton];
    self.navigationItem.rightBarButtonItem = fakeButtonItem;

    
    /**
     Preload the table cells with static data.
     */
    
    NSLog(@"Inside view will appear");
    
    [self.view setUserInteractionEnabled:YES];
    
    if(appDelegate.mode == 0) {
        items = [[NSArray alloc] initWithObjects:@"Solving For",@"Type I Error", @"Number of Groups",@"Relative Group Size", @"Smallest Group Size", @"Means and Variances", nil];
        data = [[NSArray alloc]initWithObjects:appDelegate.solvingFor, appDelegate.typeIError, appDelegate.numberOfGroups, appDelegate.relativeGroupSize, appDelegate.smallestGroupSizeStatus, appDelegate.meansAndVariance, nil];
        progressView.progress = appDelegate.progressValue;
        int progressValue = progressView.progress*100;
        progressIndicator.text = [NSString stringWithFormat:@"%i%%  Complete", progressValue];
    }
    else if (appDelegate.mode == 1) {
        
        items = [[NSArray alloc] initWithObjects:@"Solving For",@"Type I Error", @"Number of Groups",@"Relative Group Size", @"Smallest Group Size", @"Means and Variances", nil];
        data = [[NSArray alloc]initWithObjects:appDelegate.solvingFor, appDelegate.typeIError, appDelegate.numberOfGroups, appDelegate.relativeGroupSize, appDelegate.smallestGroupSizeStatus, appDelegate.meansAndVariance, nil];
        progressView.progress = appDelegate.progressValue;
        int progressValue = progressView.progress*100;
        progressIndicator.text = [NSString stringWithFormat:@"%i%%  Complete", progressValue];
    }
    else if (appDelegate.mode == 2) {
        items = [[NSArray alloc] initWithObjects:@"Solving For",@"Power",@"Type I Error", @"Number of Groups",@"Relative Group Size", @"Means and Variances", nil];
        data = [[NSArray alloc]initWithObjects:appDelegate.solvingFor, appDelegate.solvingForPower, appDelegate.typeIError, appDelegate.numberOfGroups, appDelegate.relativeGroupSize, appDelegate.meansAndVariance, nil];
        progressView.progress = appDelegate.progressValue;
        int progressValue = progressView.progress*100;
        progressIndicator.text = [NSString stringWithFormat:@"%i%%  Complete", progressValue];
    }
    
    /**
     Reload the table to reflect the cell values.
     */
    
    [self.view addSubview:designTable];
    
    [self.designTable reloadData];
    
    NSLog(@"%i", [self.designTable numberOfSections]);
    NSLog(@"%i", [self.designTable numberOfRowsInSection:1]);
    
    NSLog(@"table reloaded");
    
    [self.designTable setNeedsDisplay];
    
    progressView.progress = appDelegate.progressValue;
    
    if (progressView.progress == 1.0) {
        
        calculateButton.userInteractionEnabled = YES;
        reset2Button.userInteractionEnabled = YES;
        calculateButton.highlighted = YES;
        reset2Button.highlighted = YES;
        calculateButton.hidden = NO;
        reset2Button.hidden = NO;
        resetButton.highlighted = YES;
        resetButton.hidden = YES;
    }
    else {
        calculateButton.userInteractionEnabled = NO;
        calculateButton.highlighted = NO;
        calculateButton.hidden = YES;
        reset2Button.hidden = YES;
        resetButton.highlighted = YES;
        resetButton.hidden = NO;
    }

}

-(void) tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    
    if (appDelegate.mode == 0) {
        
        /**
         Table reload with cell contents if solving for sample size.
         */
        
        if(indexPath.row == 0)
        {
            
            UIViewController *uiViewController = [self.storyboard instantiateViewControllerWithIdentifier:@"designSolvingForViewController"];
            [self.navigationController pushViewController:uiViewController animated:YES];
            UITableViewCell *tb = [tableView cellForRowAtIndexPath:indexPath];
            tb.detailTextLabel.text = appDelegate.solvingFor;
            
            tb.imageView.image = [UIImage imageNamed:@"complete_icon.png"];
            
            if (appDelegate.solvingFor == @"") {
                
                progressView.progress += 0.17;
                int progressValue = progressView.progress*100;
                progressIndicator.text = [NSString stringWithFormat:@"%i%%  Complete", progressValue];
                appDelegate.progressValue = progressView.progress;
                if (progressView.progress == 1.0) {
                    
                    calculateButton.userInteractionEnabled = YES;
                    reset2Button.userInteractionEnabled = YES;
                    calculateButton.highlighted = YES;
                    reset2Button.highlighted = YES;
                    calculateButton.hidden = NO;
                    reset2Button.hidden = NO;
                    resetButton.hidden = YES;
                }
            }
            
        }// end if indexPath = 0
        
        if(indexPath.row == 1)
        {
            UIViewController *uiViewController = [self.storyboard instantiateViewControllerWithIdentifier:@"designTypeIErrorRate"];
            [self.navigationController pushViewController:uiViewController animated:YES];
            UITableViewCell *tb = [tableView cellForRowAtIndexPath:indexPath];
            tb.detailTextLabel.text = appDelegate.typeIError;
            tb.imageView.image = [UIImage imageNamed:@"complete_icon.png"];
            
            if (appDelegate.typeIError == @"") {
                progressView.progress += 0.17;
                int progressValue = progressView.progress*100;
                progressIndicator.text = [NSString stringWithFormat:@"%i%%  Complete", progressValue];
                appDelegate.progressValue = progressView.progress;
                if (progressView.progress == 1.0) {
                    
                    calculateButton.userInteractionEnabled = YES;
                    reset2Button.userInteractionEnabled = YES;
                    calculateButton.highlighted = YES;
                    reset2Button.highlighted = YES;
                    calculateButton.hidden = NO;
                    reset2Button.hidden = NO;
                    resetButton.hidden = YES;
                    
                }
            }
        }// end if indexPath = 1
        
        if(indexPath.row == 2)
        {
            UIViewController *uiViewController = [self.storyboard instantiateViewControllerWithIdentifier:@"designNumberOfGroups"];
            [self.navigationController pushViewController:uiViewController animated:YES];
            UITableViewCell *tb = [tableView cellForRowAtIndexPath:indexPath];
            tb.detailTextLabel.text = appDelegate.numberOfGroups;
            tb.imageView.image = [UIImage imageNamed:@"complete_icon.png"];
            
            if (appDelegate.numberOfGroups == @"") {
                progressView.progress += 0.17;
                int progressValue = progressView.progress*100;
                progressIndicator.text = [NSString stringWithFormat:@"%i%%  Complete", progressValue];
                appDelegate.progressValue = progressView.progress;
                if (progressView.progress == 1.0) {
                    
                    calculateButton.userInteractionEnabled = YES;
                    reset2Button.userInteractionEnabled = YES;
                    calculateButton.highlighted = YES;
                    reset2Button.highlighted = YES;
                    calculateButton.hidden = NO;
                    reset2Button.hidden = NO;
                    resetButton.hidden = YES;
                }
            }
            
            
        }// end if indexPath = 2
        
        
        if(indexPath.row == 3)
        {
            if (appDelegate.numberOfGroups == @"")
                
            {
                
                /**
                 Show an alert window if user tries to enter relative group size before entering the total number of groups required in the study design.
                 */
                
                UIAlertView *messageAlert = [[UIAlertView alloc]
                                             initWithTitle:@"Error" message:@"Please Select Number of Groups First" delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil];
                [messageAlert show];
                
            }
            else
            {
                UIViewController *uiViewController = [self.storyboard instantiateViewControllerWithIdentifier:@"relativeSizeViewController"];
                [self.navigationController pushViewController:uiViewController animated:YES];
                UITableViewCell *tb = [tableView cellForRowAtIndexPath:indexPath];
                tb.detailTextLabel.text = appDelegate.relativeGroupSize;
                tb.imageView.image = [UIImage imageNamed:@"complete_icon.png"];
                
                
                if (appDelegate.relativeGroupSize == @"") {
                    progressView.progress += 0.17;
                    int progressValue = progressView.progress*100;
                    progressIndicator.text = [NSString stringWithFormat:@"%i%%  Complete", progressValue];
                    appDelegate.progressValue = progressView.progress;
                    if (progressView.progress == 1.0) {
                        
                        calculateButton.userInteractionEnabled = YES;
                        reset2Button.userInteractionEnabled = YES;
                        calculateButton.highlighted = YES;
                        reset2Button.highlighted = YES;
                        calculateButton.hidden = NO;
                        reset2Button.hidden = NO;
                        resetButton.hidden = YES;
                        
                    }
                }
            }
        }// end if indexPath = 3
        
        if(indexPath.row == 4)
        {
            UIViewController *uiViewController = [self.storyboard instantiateViewControllerWithIdentifier:@"designSmallestGroupSize"];
            [self.navigationController pushViewController:uiViewController animated:YES];
            UITableViewCell *tb = [tableView cellForRowAtIndexPath:indexPath];
            tb.detailTextLabel.text = appDelegate.smallestGroupSizeStatus;
            
            progressView.progress  = appDelegate.progressValue;
            
            
            int progressValue = progressView.progress*100;
            progressIndicator.text = [NSString stringWithFormat:@"%i%%  Complete", progressValue];
            
            if (progressView.progress == 1.0) {
                
                calculateButton.userInteractionEnabled = YES;
                reset2Button.userInteractionEnabled = YES;
                calculateButton.highlighted = YES;
                reset2Button.highlighted = YES;
                calculateButton.hidden = NO;
                reset2Button.hidden = NO;
                resetButton.hidden = YES;
                
            }
            else {
                resetButton.hidden = NO;
                resetButton.highlighted = YES;
            }
        }// end if indexPath = 4
        
        if(indexPath.row == 5)
        {
            
            /**
             Show an alert window if user tries to enter means and variance for each group before entering the total number of groups required in the study design.
             */
            
            if (appDelegate.numberOfGroups == @"")
                
            {
                UIAlertView *messageAlert = [[UIAlertView alloc]
                                             initWithTitle:@"Error" message:@"Please Select Number of Groups First" delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil];
                [messageAlert show];
                
            }
            
            else
                
            {
                
                UIViewController *uiViewController = [self.storyboard instantiateViewControllerWithIdentifier:@"designMeansAndVariance"];
                [self.navigationController pushViewController:uiViewController animated:YES];
                UITableViewCell *tb = [tableView cellForRowAtIndexPath:indexPath];
                tb.detailTextLabel.text = appDelegate.meansAndVariance;
                
                progressView.progress  = appDelegate.progressValue;
                int progressValue = progressView.progress*100;
                progressIndicator.text = [NSString stringWithFormat:@"%i%%  Complete", progressValue];
                
                if (progressView.progress == 1.0) {
                    
                    calculateButton.userInteractionEnabled = YES;
                    reset2Button.userInteractionEnabled = YES;
                    calculateButton.highlighted = YES;
                    reset2Button.highlighted = YES;
                    calculateButton.hidden = NO;
                    reset2Button.hidden = NO;
                    resetButton.hidden = YES;
                    
                }
                else {
                    resetButton.hidden = NO;
                    resetButton.highlighted = YES;
                }
                //}
            }
        }// end if indexPath = 5
        
        
        
        
    }// end if appDelegate.mode = 0
    
    else if (appDelegate.mode == 1) {
        
        /**
         Table reload with cell contents if solving for power.
         */
        
        
        if(indexPath.row == 0)
        {
            
            UIViewController *uiViewController = [self.storyboard instantiateViewControllerWithIdentifier:@"designSolvingForViewController"];
            [self.navigationController pushViewController:uiViewController animated:YES];
            UITableViewCell *tb = [tableView cellForRowAtIndexPath:indexPath];
            
            tb.detailTextLabel.text = appDelegate.solvingFor;
            tb.imageView.image = [UIImage imageNamed:@"complete_icon.png"];
            
            if (appDelegate.solvingFor == @"") {
                progressView.progress += 0.17;
                int progressValue = progressView.progress*100;
                progressIndicator.text = [NSString stringWithFormat:@"%i%%  Complete", progressValue];
                appDelegate.progressValue = progressView.progress;
                if (progressView.progress == 1.0) {
                    
                    calculateButton.userInteractionEnabled = YES;
                    reset2Button.userInteractionEnabled = YES;
                    calculateButton.highlighted = YES;
                    reset2Button.highlighted = YES;
                    calculateButton.hidden = NO;
                    reset2Button.hidden = NO;
                    resetButton.hidden = YES;
                }
                else {
                    resetButton.hidden = NO;
                    resetButton.highlighted = YES;
                }
            }
            
        }// end if indexPath = 0
        
        if(indexPath.row == 1)
        {
            UIViewController *uiViewController = [self.storyboard instantiateViewControllerWithIdentifier:@"designTypeIErrorRate"];
            [self.navigationController pushViewController:uiViewController animated:YES];
            UITableViewCell *tb = [tableView cellForRowAtIndexPath:indexPath];
            tb.detailTextLabel.text = appDelegate.typeIError;
            tb.imageView.image = [UIImage imageNamed:@"complete_icon.png"];
            
            if (appDelegate.typeIError == @"") {
                progressView.progress += 0.17;
                int progressValue = progressView.progress*100;
                progressIndicator.text = [NSString stringWithFormat:@"%i%%  Complete", progressValue];
                appDelegate.progressValue = progressView.progress;
                if (progressView.progress == 1.0) {
                    
                    calculateButton.userInteractionEnabled = YES;
                    reset2Button.userInteractionEnabled = YES;
                    calculateButton.highlighted = YES;
                    reset2Button.highlighted = YES;
                    calculateButton.hidden = NO;
                    reset2Button.hidden = NO;
                    resetButton.hidden = YES;
                    
                }
                else {
                    resetButton.hidden = NO;
                    resetButton.highlighted = YES;
                }
            }
        }// end if indexPath = 1
        
        if(indexPath.row == 2)
        {
            UIViewController *uiViewController = [self.storyboard instantiateViewControllerWithIdentifier:@"designNumberOfGroups"];
            [self.navigationController pushViewController:uiViewController animated:YES];
            UITableViewCell *tb = [tableView cellForRowAtIndexPath:indexPath];
            tb.detailTextLabel.text = appDelegate.numberOfGroups;
            tb.imageView.image = [UIImage imageNamed:@"complete_icon.png"];
            
            if (appDelegate.numberOfGroups == @"") {
                progressView.progress += 0.17;
                appDelegate.progressValue = progressView.progress;
                int progressValue = progressView.progress*100;
                progressIndicator.text = [NSString stringWithFormat:@"%i%%  Complete", progressValue];
                if (progressView.progress == 1.0) {
                    
                    calculateButton.userInteractionEnabled = YES;
                    reset2Button.userInteractionEnabled = YES;
                    calculateButton.highlighted = YES;
                    reset2Button.highlighted = YES;
                    calculateButton.hidden = NO;
                    reset2Button.hidden = NO;
                    resetButton.hidden = YES;
                    
                }
            }
            
            
        }// end if indexPath = 2
        
        
        if(indexPath.row == 3)
        {
            if (appDelegate.numberOfGroups == @"")
                
            {
                
                
                
                UIAlertView *messageAlert = [[UIAlertView alloc]
                                             initWithTitle:@"Error" message:@"Please Select Number of Groups First" delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil];
                [messageAlert show];
                
            }
            else
            {
                UIViewController *uiViewController = [self.storyboard instantiateViewControllerWithIdentifier:@"relativeSizeViewController"];
                [self.navigationController pushViewController:uiViewController animated:YES];
                UITableViewCell *tb = [tableView cellForRowAtIndexPath:indexPath];
                tb.detailTextLabel.text = appDelegate.relativeGroupSize;
                tb.imageView.image = [UIImage imageNamed:@"complete_icon.png"];
                
                if (appDelegate.relativeGroupSize == @"") {
                    progressView.progress += 0.17;
                    int progressValue = progressView.progress*100;
                    progressIndicator.text = [NSString stringWithFormat:@"%i%%  Complete", progressValue];
                    appDelegate.progressValue = progressView.progress;
                    if (progressView.progress == 1.0) {
                        
                        calculateButton.userInteractionEnabled = YES;
                        reset2Button.userInteractionEnabled = YES;
                        calculateButton.highlighted = YES;
                        reset2Button.highlighted = YES;
                        calculateButton.hidden = NO;
                        reset2Button.hidden = NO;
                        resetButton.hidden = YES;
                        
                    }
                }
            }
        }// end if indexPath = 3
        
        if(indexPath.row == 4)
        {
            UIViewController *uiViewController = [self.storyboard instantiateViewControllerWithIdentifier:@"designSmallestGroupSize"];
            [self.navigationController pushViewController:uiViewController animated:YES];
            UITableViewCell *tb = [tableView cellForRowAtIndexPath:indexPath];
            tb.detailTextLabel.text = appDelegate.smallestGroupSizeStatus;
            
            if (appDelegate.smallestGroupSizeStatus == @"Complete") {
                tb.imageView.image = [UIImage imageNamed:@"complete_icon.png"];
            }
            
            progressView.progress  = appDelegate.progressValue;
            
            
            int progressValue = progressView.progress*100;
            progressIndicator.text = [NSString stringWithFormat:@"%i%%  Complete", progressValue];
            
            if (progressView.progress == 1.0) {
                
                calculateButton.userInteractionEnabled = YES;
                reset2Button.userInteractionEnabled = YES;
                calculateButton.highlighted = YES;
                reset2Button.highlighted = YES;
                calculateButton.hidden = NO;
                reset2Button.hidden = NO;
                resetButton.hidden = YES;
                
            }
            //}
        }// end if indexPath = 4
        
        if(indexPath.row == 5)
        {
            if (appDelegate.numberOfGroups == @"")
                
            {
                UIAlertView *messageAlert = [[UIAlertView alloc]
                                             initWithTitle:@"Error" message:@"Please Select Number of Groups First" delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil];
                [messageAlert show];
                
            }
            
            else
                
            {
                
                UIViewController *uiViewController = [self.storyboard instantiateViewControllerWithIdentifier:@"designMeansAndVariance"];
                [self.navigationController pushViewController:uiViewController animated:YES];
                UITableViewCell *tb = [tableView cellForRowAtIndexPath:indexPath];
                tb.detailTextLabel.text = appDelegate.meansAndVariance;
                
                
                // if (appDelegate.meansAndVariance == @"Complete") {
                
                progressView.progress  = appDelegate.progressValue;
                
                if (appDelegate.meansAndVariance == @"Complete") {
                    tb.imageView.image = [UIImage imageNamed:@"complete_icon.png"];
                    int progressValue = progressView.progress*100;
                    progressIndicator.text = [NSString stringWithFormat:@"%i%%  Complete", progressValue];
                }
                
                if (progressView.progress == 1.0) {
                    
                    calculateButton.userInteractionEnabled = YES;
                    reset2Button.userInteractionEnabled = YES;
                    calculateButton.highlighted = YES;
                    reset2Button.highlighted = YES;
                    calculateButton.hidden = NO;
                    reset2Button.hidden = NO;
                    resetButton.hidden = YES;
                    
                }
                
            }
        }// end if indexPath = 5
        
        
    }// end if appDelegate.mode = 1
    
    else if (appDelegate.mode == 2) {
        
        /**
         Table reload with cell contents if solving for sample size.
         */
        
        if(indexPath.row == 0)
        {
            
            UIViewController *uiViewController = [self.storyboard instantiateViewControllerWithIdentifier:@"designSolvingForViewController"];
            [self.navigationController pushViewController:uiViewController animated:YES];
            UITableViewCell *tb = [tableView cellForRowAtIndexPath:indexPath];
            tb.detailTextLabel.text = appDelegate.solvingFor;
            tb.imageView.image = [UIImage imageNamed:@"complete_icon.png"];
            
            if (appDelegate.solvingFor == @"") {
                progressView.progress += 0.17;
                int progressValue = 0.17*100;
                progressIndicator.text = [NSString stringWithFormat:@"%i%%  Complete", progressValue];
                appDelegate.progressValue = progressView.progress;
                if (progressView.progress == 1.0) {
                    
                    calculateButton.userInteractionEnabled = YES;
                    reset2Button.userInteractionEnabled = YES;
                    calculateButton.highlighted = YES;
                    reset2Button.highlighted = YES;
                    calculateButton.hidden = NO;
                    reset2Button.hidden = NO;
                    resetButton.hidden = YES;
                }
            }
            
        }// end if indexPath = 0
        
        if(indexPath.row == 1)
        {
            UIViewController *uiViewController = [self.storyboard instantiateViewControllerWithIdentifier:@"powerViewController"];
            [self.navigationController pushViewController:uiViewController animated:YES];
            UITableViewCell *tb = [tableView cellForRowAtIndexPath:indexPath];
            tb.detailTextLabel.text = appDelegate.solvingForPower;
            
            if (appDelegate.solvingForPower != @"")
                tb.imageView.image = [UIImage imageNamed:@"complete_icon.png"];
            
            progressView.progress  = appDelegate.progressValue;
            int progressValue = progressView.progress*100;
            progressIndicator.text = [NSString stringWithFormat:@"%i%%  Complete", progressValue];
            
            if (progressView.progress == 1.0) {
                
                calculateButton.userInteractionEnabled = YES;
                reset2Button.userInteractionEnabled = YES;
                calculateButton.highlighted = YES;
                reset2Button.highlighted = YES;
                calculateButton.hidden = NO;
                reset2Button.hidden = NO;
                resetButton.hidden = YES;
            }
            
        }// end if indexPath = 1
        
        if(indexPath.row == 2)
        {
            UIViewController *uiViewController = [self.storyboard instantiateViewControllerWithIdentifier:@"designTypeIErrorRate"];
            [self.navigationController pushViewController:uiViewController animated:YES];
            UITableViewCell *tb = [tableView cellForRowAtIndexPath:indexPath];
            tb.detailTextLabel.text = appDelegate.typeIError;
            tb.imageView.image = [UIImage imageNamed:@"complete_icon.png"];
            
            if (appDelegate.typeIError == @"") {
                progressView.progress += 0.17;
                int progressValue = progressView.progress*100;
                progressIndicator.text = [NSString stringWithFormat:@"%i%%  Complete", progressValue];
                appDelegate.progressValue = progressView.progress;
                if (progressView.progress == 1.0) {
                    
                    calculateButton.userInteractionEnabled = YES;
                    reset2Button.userInteractionEnabled = YES;
                    calculateButton.highlighted = YES;
                    reset2Button.highlighted = YES;
                    calculateButton.hidden = NO;
                    reset2Button.hidden = NO;
                    resetButton.hidden = YES;
                }
            }
        }// end if indexPath = 2
        
        if(indexPath.row == 3)
        {
            UIViewController *uiViewController = [self.storyboard instantiateViewControllerWithIdentifier:@"designNumberOfGroups"];
            [self.navigationController pushViewController:uiViewController animated:YES];
            UITableViewCell *tb = [tableView cellForRowAtIndexPath:indexPath];
            tb.detailTextLabel.text = appDelegate.numberOfGroups;
            tb.imageView.image = [UIImage imageNamed:@"complete_icon.png"];
            
            if (appDelegate.numberOfGroups == @"") {
                progressView.progress += 0.17;
                int progressValue = progressView.progress*100;
                progressIndicator.text = [NSString stringWithFormat:@"%i%%  Complete", progressValue];
                appDelegate.progressValue = progressView.progress;
                if (progressView.progress == 1.0) {
                    
                    calculateButton.userInteractionEnabled = YES;
                    reset2Button.userInteractionEnabled = YES;
                    calculateButton.highlighted = YES;
                    reset2Button.highlighted = YES;
                    calculateButton.hidden = NO;
                    reset2Button.hidden = NO;
                    resetButton.hidden = YES;
                }
            }
            
            
        }// end if indexPath = 3
        
        
        if(indexPath.row == 4)
        {
            if (appDelegate.numberOfGroups == @"")
            {
                /**
                 Show an alert window if user tries to enter relative group sizes before entering the total number of groups required in the study design.
                 */
                
                
                UIAlertView *messageAlert = [[UIAlertView alloc]
                                             initWithTitle:@"Error" message:@"Please Select Number of Groups First" delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil];
                [messageAlert show];
                
            }
            else
            {
                UIViewController *uiViewController = [self.storyboard instantiateViewControllerWithIdentifier:@"relativeSizeViewController"];
                [self.navigationController pushViewController:uiViewController animated:YES];
                UITableViewCell *tb = [tableView cellForRowAtIndexPath:indexPath];
                tb.detailTextLabel.text = appDelegate.relativeGroupSize;
                tb.imageView.image = [UIImage imageNamed:@"complete_icon.png"];
                
                if (appDelegate.relativeGroupSize == @"") {
                    progressView.progress += 0.17;
                    int progressValue = progressView.progress*100;
                    progressIndicator.text = [NSString stringWithFormat:@"%i%%  Complete", progressValue];
                    appDelegate.progressValue = progressView.progress;
                    if (progressView.progress == 1.0) {
                        
                        calculateButton.userInteractionEnabled = YES;
                        reset2Button.userInteractionEnabled = YES;
                        calculateButton.highlighted = YES;
                        reset2Button.highlighted = YES;
                        calculateButton.hidden = NO;
                        reset2Button.hidden = NO;
                        resetButton.hidden = YES;
                    }
                }
            }
        }// end if indexPath = 4
        
        if(indexPath.row == 5)
        {
            
            /**
             Show an alert window if user tries to enter means and variance for each group before entering the total number of groups required in the study design.
             */
            
            if (appDelegate.numberOfGroups == @"")
                
            {
                UIAlertView *messageAlert = [[UIAlertView alloc]
                                             initWithTitle:@"Error" message:@"Please Select Number of Groups First" delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil];
                [messageAlert show];
                
            }
            
            else
                
            {
                
                UIViewController *uiViewController = [self.storyboard instantiateViewControllerWithIdentifier:@"designMeansAndVariance"];
                [self.navigationController pushViewController:uiViewController animated:YES];
                UITableViewCell *tb = [tableView cellForRowAtIndexPath:indexPath];
                tb.detailTextLabel.text = appDelegate.meansAndVariance;
                
                progressView.progress  = appDelegate.progressValue;
                
                
                if (appDelegate.meansAndVariance == @"Complete") {
                    tb.imageView.image = [UIImage imageNamed:@"complete_icon.png"];
                    
                }
                
                if (progressView.progress == 1.0) {
                    
                    calculateButton.userInteractionEnabled = YES;
                    reset2Button.userInteractionEnabled = YES;
                    calculateButton.highlighted = YES;
                    calculateButton.hidden = NO;
                    reset2Button.hidden = NO;
                    reset2Button.highlighted = YES;
                    resetButton.hidden = YES;
                }
                // }
            }
        }// end if indexPath = 6
        
        
        
    }// end if appDelegate.mode = 2
    
}

# pragma mark Actions

- (void) createJSON {
    
    calculateButton.userInteractionEnabled = NO;
    reset2Button.userInteractionEnabled = NO;
    
    [self.view setUserInteractionEnabled:NO];
    
    
    StudyDesign *study = [[StudyDesign alloc] init];
    [study createRequestDictionary];
    
    NSURL *url;
    NSError *error;
    
    /**
     Specify url for sending JSON object, based on if solving for power or sample size.
     */
    
    if (appDelegate.solvingFor == @"Power"){
        NSLog(@"POWER IT IS");
        url  = [NSURL URLWithString:@"http://glimmpse.samplesizeshop.org/power/power"];
    }
    else if (appDelegate.solvingFor == @"Sample Size") {
        NSLog(@"SAMPLE SIZE IT IS");
        url = [NSURL URLWithString:@"http://glimmpse.samplesizeshop.org/power/samplesize"];
    }
    
    
    /**
     Show an alert window if user tries to enter means and variance for each group before entering the total number of groups required in the study design.
     */
    
    
    NSString *filePath = [[NSBundle mainBundle] pathForResource:@"studySample" ofType:@"json"];
    
    NSData *jsonData = [NSData dataWithContentsOfFile:filePath];
    
    /**
     Create a mutablecopy of request dictionary
     */
    
    NSMutableDictionary *requestDataDictionary = [NSJSONSerialization JSONObjectWithData:jsonData options:kNilOptions error:&error];
    
    NSMutableDictionary *mutableCopy = [requestDataDictionary mutableCopy];
    
    
    /**
     Update SolutionTypeEnum in mutablecopy
     */
    
    
    NSString *str;
    if ([appDelegate.solvingFor isEqualToString:@"Power"]) {
        str  = @"POWER";
    }
    else if ([appDelegate.solvingFor isEqualToString:@"Sample Size"]) {
        str = @"SAMPLE_SIZE";
    }
    
    [study.mutableDictionary setValue:str forKey:@"solutionTypeEnum"];
    
    [mutableCopy setValue:str forKey:@"solutionTypeEnum"];
    
    /**
     Update participant label in mutablecopy
     */
    
    [study.mutableDictionary setValue:@"participant" forKey:@"participantLabel"];
    
    [mutableCopy setValue:@"participant" forKey:@"participantLabel"];
    
    
    /**
     Update viewTypeEnum in mutablecopy
     */
    
    [study.mutableDictionary setValue:@"GUIDED_MODE" forKey:@"viewTypeEnum"];
    
    [mutableCopy setValue:@"GUIDED_MODE" forKey:@"viewTypeEnum"];
    
    
    
    
    /**
     Update aplhaValue in mutablecopy
     */
    
    NSMutableDictionary *typeIDictionary = [[NSMutableDictionary alloc] initWithObjectsAndKeys:appDelegate.typeIError,@"alphaValue",@"0",@"idx", nil];
    
    NSMutableArray *typeIArray = [[NSMutableArray alloc] init];
    
    [typeIArray addObject:typeIDictionary];
    
    [study.mutableDictionary setValue:typeIArray forKey:@"alphaList"];
    
    [mutableCopy setValue:typeIArray forKey:@"alphaList"];
    
    /**
     Update betaScaleList in mutablecopy
     */
    
    
    NSMutableDictionary *betaDictionary = [[NSMutableDictionary alloc] initWithObjectsAndKeys:@"1",@"value",@"0",@"idx", nil];
    
    NSMutableArray *betaArray = [[NSMutableArray alloc] init];
    
    [betaArray addObject:betaDictionary];
    
    [study.mutableDictionary setValue:betaArray forKey:@"betaScaleList"];
    
    [mutableCopy setValue:betaArray forKey:@"betaScaleList"];
    
    
    /**
     Update sigmaScaleList in mutablecopy
     */
    
    
    NSMutableDictionary *sigmaDictionary = [[NSMutableDictionary alloc] initWithObjectsAndKeys:@"1",@"value",@"0",@"idx", nil];
    
    NSMutableArray *sigmaArray = [[NSMutableArray alloc] init];
    
    [sigmaArray addObject:sigmaDictionary];
    
    [study.mutableDictionary setValue:sigmaArray forKey:@"sigmaScaleList"];
    [mutableCopy setValue:sigmaArray forKey:@"sigmaScaleList"];
    
    /**
     Update statisticalTestList in mutablecopy
     */
    
    
    NSMutableDictionary *statisticalDictionary = [[NSMutableDictionary alloc] initWithObjectsAndKeys:@"UNIREP",@"type",@"0",@"idx", nil];
    
    NSMutableArray *statisticalArray = [[NSMutableArray alloc] init];
    
    [statisticalArray addObject:statisticalDictionary];
    
    [study.mutableDictionary setValue:statisticalArray forKey:@"statisticalTestList"];
    
    [mutableCopy setValue:statisticalArray forKey:@"statisticalTestList"];
    
    
    /**
     Update responseList in mutablecopy
     */
    
    
    NSMutableDictionary *responseListDictionary = [[NSMutableDictionary alloc] initWithObjectsAndKeys:@"response",@"name",@"0",@"idx", nil];
    
    NSMutableArray *responseListArray = [[NSMutableArray alloc] init];
    
    [responseListArray addObject:responseListDictionary];
    
    [study.mutableDictionary setValue:responseListArray forKey:@"responseList"];
    
    [mutableCopy setValue:responseListArray forKey:@"responseList"];
    
    
    /**
     Convert NSString to integer value
     */
    
    
    NSNumberFormatter * f = [[NSNumberFormatter alloc] init];
    [f setLocale:[NSLocale currentLocale]];
    [f setNumberStyle:NSNumberFormatterDecimalStyle];
    NSNumber *tempNum = [f numberFromString:appDelegate.numberOfGroups];
    
    int groups_ = [tempNum intValue];
    
    NSMutableArray *relativeGroupSizeArray = [[NSMutableArray alloc] init];
    NSMutableArray *categoryGroupListArray = [[NSMutableArray alloc] init];
    
    /**
     Update relativeGroupSizeList in mutablecopy
     */
    
    for (int i= 0; i< groups_; i++) {
        
        
        NSString *valueCategory = [NSString stringWithFormat:@"%i", i+1];
        NSString *relLabelValue = [appDelegate.relSizeLabels objectAtIndex:i];
        NSString *str = [NSString stringWithFormat:@"%@", relLabelValue];
        NSDictionary *relativeGroupSizeDict = [[NSDictionary alloc] initWithObjectsAndKeys:@"0",@"idx",str,@"value",nil];
        NSDictionary *categoryListDict = [[NSDictionary alloc] initWithObjectsAndKeys:@"0",@"idx",valueCategory,@"category", nil];
        [relativeGroupSizeArray addObject:relativeGroupSizeDict];
        [categoryGroupListArray addObject:categoryListDict];
        
    }
    
    [study.mutableDictionary setValue:relativeGroupSizeArray forKey:@"relativeGroupSizeList"];
    
    [mutableCopy setValue:relativeGroupSizeArray forKey:@"relativeGroupSizeList"];
    
    
    /**
     Update sampleSizeList and nominalPowerList in mutablecopy
     */
    
    
    if (appDelegate.solvingFor == @"Power") {
        
        NSLog(@"Inside power array");
        
        NSMutableArray *sampleSizeListArray = [[NSMutableArray alloc] init];
        
        NSDictionary *sampleSizeListDict1 = [[NSDictionary alloc] initWithObjectsAndKeys:@"0",@"idx",appDelegate.smallestGroupSize,@"value",nil];
        NSDictionary *sampleSizeListDict2 = [[NSDictionary alloc] initWithObjectsAndKeys:@"0",@"idx",appDelegate.sampleSizeResult2,@"value",nil];
        NSDictionary *sampleSizeListDict3 = [[NSDictionary alloc] initWithObjectsAndKeys:@"0",@"idx",appDelegate.sampleSizeResult3,@"value",nil];
        NSDictionary *sampleSizeListDict4 = [[NSDictionary alloc] initWithObjectsAndKeys:@"0",@"idx",appDelegate.sampleSizeResult4,@"value",nil];
        
        if ([appDelegate.smallestGroupSize isEqualToString: @""])
            ;
        else
            [sampleSizeListArray addObject:sampleSizeListDict1];
        
        if ([appDelegate.sampleSizeResult2 isEqualToString: @""])
            ;
        else
            [sampleSizeListArray addObject:sampleSizeListDict2];
        
        if ([appDelegate.sampleSizeResult3 isEqualToString: @""])
            ;
        else
            [sampleSizeListArray addObject:sampleSizeListDict3];
        
        if ([appDelegate.sampleSizeResult4 isEqualToString: @""])
            ;
        else
            [sampleSizeListArray addObject:sampleSizeListDict4];
        
        NSLog(@"sample size list is: %@", sampleSizeListArray);
        
        [study.mutableDictionary setValue:sampleSizeListArray forKey:@"sampleSizeList"];
        [study.mutableDictionary setValue:[NSNull null] forKey:@"nominalPowerList"];
        
        [mutableCopy setValue:sampleSizeListArray forKey:@"sampleSizeList"];
        [mutableCopy setValue:[NSNull null] forKey:@"nominalPowerList"];
        
    }
    
    else if (appDelegate.solvingFor == @"Sample Size") {
        
        NSLog(@"Inside sample size array");
        
        NSMutableArray *nominalPowerListArray = [[NSMutableArray alloc] init];
        
        NSDictionary *nominalPowerListDict1 = [[NSDictionary alloc] initWithObjectsAndKeys:@"0",@"idx",@"0.80",@"value",nil];
        NSDictionary *nominalPowerListDict2 = [[NSDictionary alloc] initWithObjectsAndKeys:@"0",@"idx",@"0.90",@"value",nil];
        NSDictionary *nominalPowerListDict3 = [[NSDictionary alloc] initWithObjectsAndKeys:@"0",@"idx",@"0.95",@"value",nil];
        NSDictionary *nominalPowerListDict4 = [[NSDictionary alloc] initWithObjectsAndKeys:@"0",@"idx",@"0.975",@"value",nil];
        
        if ([[appDelegate.powerButtonsStatus objectAtIndex:0] isEqualToString:@"Selected"]   )
        {
            [nominalPowerListArray addObject:nominalPowerListDict1];
            NSLog(@"power 0 selected");
        }
        
        if ([[appDelegate.powerButtonsStatus objectAtIndex:1] isEqualToString:@"Selected"]   )
        {
            [nominalPowerListArray addObject:nominalPowerListDict2];
            NSLog(@"power 1 selected");
        }
        
        if ([[appDelegate.powerButtonsStatus objectAtIndex:2] isEqualToString:@"Selected"]   )
        {
            [nominalPowerListArray addObject:nominalPowerListDict3];
            NSLog(@"power 2 selected");
        }
        
        if ([[appDelegate.powerButtonsStatus objectAtIndex:3] isEqualToString:@"Selected"]   )
        {
            [nominalPowerListArray addObject:nominalPowerListDict4];
            NSLog(@"power 3 selected");
        }
        
        NSLog(@"power list array is: %@", nominalPowerListArray);
        
        [study.mutableDictionary setValue:[NSNull null] forKey:@"sampleSizeList"];
        [study.mutableDictionary setValue:nominalPowerListArray forKey:@"nominalPowerList"];
        
        [mutableCopy setValue:[NSNull null] forKey:@"sampleSizeList"];
        [mutableCopy setValue:nominalPowerListArray forKey:@"nominalPowerList"];
        
    }
    
    
    
    
    /**
     Update betweenParticipantFactorList in mutablecopy
     */
    
    
    NSDictionary *betweenParticipant = [[NSDictionary alloc] initWithObjectsAndKeys:@"0",@"idx",@"group",@"predictorName",categoryGroupListArray,@"categoryList", nil];
    
    NSMutableArray *betweenParticipantArray = [[NSMutableArray alloc] init];
    
    [betweenParticipantArray addObject:betweenParticipant];
    
    
    [study.mutableDictionary setValue:betweenParticipantArray forKey:@"betweenParticipantFactorList"];
    
    [mutableCopy setValue:betweenParticipantArray forKey:@"betweenParticipantFactorList"];
    
    NSDictionary *betweenParticipantFactorDict = [[NSDictionary alloc] initWithObjectsAndKeys:@"NONE",@"type",betweenParticipant,@"betweenParticipantFactor",nil];
    
    NSMutableArray *betweenParticipantFactorArray = [[NSMutableArray alloc]init];
    
    [betweenParticipantFactorArray addObject:betweenParticipantFactorDict];
    
    NSDictionary *standardDeviationDict = [[NSDictionary alloc] initWithObjectsAndKeys:@"0",@"idx",[appDelegate.varianceOfGroups objectAtIndex:0],@"value",nil];
    
    NSMutableArray *standardDeviationArray = [[NSMutableArray alloc] init];
    [standardDeviationArray addObject:standardDeviationDict];
    NSArray *array = [[NSArray alloc] initWithObjects:@"1", nil];
    NSMutableArray *arrayBlob = [[NSMutableArray alloc] init];
    [arrayBlob addObject:array];
    
    NSDictionary *dataDict = [[NSDictionary alloc] initWithObjectsAndKeys:arrayBlob,@"data",nil];
    
    
    /**
     Update covariance in mutablecopy
     */
    
    NSDictionary *covarianceDict = [[NSDictionary alloc] initWithObjectsAndKeys:@"0",@"idx",@"UNSTRUCTURED_CORRELATION",@"type",@"__RESPONSE_COVARIANCE__",@"name",standardDeviationArray,@"standardDeviationList",@"-2",@"rho",@"-1",@"delta",@"1",@"rows",@"1",@"columns",dataDict,@"blob",nil];
    
    NSMutableArray *covarianceArray = [[NSMutableArray alloc] init];
    
    [covarianceArray addObject:covarianceDict];
    
    [study.mutableDictionary setValue:covarianceArray forKey:@"covariance"];
    
    [mutableCopy setValue:covarianceArray forKey:@"covariance"];
    
    
    NSMutableArray *dataArray = [[NSMutableArray alloc] init];
    
    for (int i=0; i<groups_; i++) {
        
        NSArray *array = [[NSArray alloc] initWithObjects:[appDelegate.meanOfGroups objectAtIndex:i], nil];
        [dataArray addObject:array];
        
    }
    
    dataDict = [[NSDictionary alloc] initWithObjectsAndKeys:dataArray,@"data", nil];
    
    
    /**
     Update matrixSet in mutablecopy
     */
    
    NSDictionary *matrixDict = [[NSDictionary alloc] initWithObjectsAndKeys:@"0",@"idx",@"beta",@"name",appDelegate.numberOfGroups,@"rows",@"1",@"columns", dataDict,@"data",nil];
    
    NSMutableArray *dataDictArray = [[NSMutableArray alloc] init];
    
    [dataDictArray addObject:matrixDict];
    
    [study.mutableDictionary setValue:dataDictArray forKey:@"matrixSet"];
    
    [mutableCopy setValue:dataDictArray forKey:@"matrixSet"];
    
    
    NSDictionary *hypothesisDict = [[NSDictionary alloc] initWithObjectsAndKeys:@"0",@"idx",@"MAIN_EFFECT",@"type",betweenParticipantFactorArray,@"betweenParticipantFactorMapList",[NSNull null],@"repeatedMeasuresMapTree",nil];
    
    NSMutableArray *hypothesisArray = [[NSMutableArray alloc] init];
    
    [hypothesisArray addObject:hypothesisDict];
    
    [study.mutableDictionary setValue:hypothesisArray forKey:@"hypothesis"];
    
    [mutableCopy setValue:hypothesisArray forKey:@"hypothesis"];
    
    /**
     Create a JSON object from mutablecopy
     */
    
    
    //NSData *newJSONData = [NSJSONSerialization dataWithJSONObject:mutableCopy options:NSJSONWritingPrettyPrinted error:&error];
    
    NSData *newJSONData = [NSJSONSerialization dataWithJSONObject:study.mutableDictionary options:NSJSONWritingPrettyPrinted error:&error];
    
    appDelegate.jsonFileContents = study.mutableDictionary;
    
    if (newJSONData) {
        NSLog(@"data is generated");
        NSLog(@"%@", study.mutableDictionary);
    }
    
    /**
     Create a JSON string out of JSON data object.
     */
    
    NSString *jsonString = [[NSString alloc] initWithData:newJSONData encoding:NSUTF8StringEncoding];
    
    
    appDelegate.jSONFileContents = jsonString;
    
    
    NSLog(@"jsonData1 from dictionary is: %@", jsonString);
    
    NSDictionary *json_dict = (NSDictionary *)jsonString;
    
    
    NSLog(@"json_dict\n%@",json_dict);
    
    
    /**
     Create an HTTP request and send send to the GLIMMPSE server.
     */
    
    
    NSMutableURLRequest *request = [NSMutableURLRequest requestWithURL:url cachePolicy:NSURLRequestUseProtocolCachePolicy timeoutInterval:60];
    
    [request setHTTPMethod:@"POST"];
    [request setValue:@"application/json" forHTTPHeaderField:@"Accept"];
    [request setValue:@"application/json" forHTTPHeaderField:@"Content-Type"];
    [request setValue:[NSString stringWithFormat:@"%i", [newJSONData length]] forHTTPHeaderField:@"Content-Length"];
    
    [request setHTTPBody:newJSONData];
    
    
    
    
    [[AFJSONRequestOperation JSONRequestOperationWithRequest:request success:^(NSURLRequest *request, NSHTTPURLResponse *response, id JSON) {
        NSLog(@"Request succeeded");
        NSLog(@"Returned JSON is: %@", JSON);
        appDelegate.resultsResponse = @"Request succeeded";
        int number=0;
        NSString *power;
        NSString *actualPower;
        
        
        
        NSString *sampleSize;
        int i=0;
        for (int i=0; i <4; i++) {
            ;//NSLog(@"power: %@", [appDelegate.powerResults objectAtIndex:i]);
            //NSLog(@"sample size: %@", [appDelegate.sampleSizeResults objectAtIndex:i]);
        }
        
        //NSLog(@"SS: %@",appDelegate.smallestGroupSize);
        //NSLog(@"SS: %@",appDelegate.sampleSizeResult2);
        //NSLog(@"SS: %@",appDelegate.sampleSizeResult3);
        //NSLog(@"SS: %@",appDelegate.sampleSizeResult4);
        i=0;
        
        /**
         Parse the JSON response sent by the GLIMMPSE server and extract power and sample size values for the ANOVA study design.
         */
        
        
        for (NSDictionary * day in JSON) {
            
            //NSLog(@"Inside JSON dictionary in the results section");
            
            NSDictionary* target = [JSON objectAtIndex:number];
            sampleSize = [target objectForKey:@"totalSampleSize"];
            if (appDelegate.solvingFor == @"Power") {
                power = [target objectForKey:@"actualPower"];
                
                while (i<4) {
                    //NSLog(@"value of i:%i",i);
                    NSString *str = [appDelegate.sampleSizeResults objectAtIndex:i];
                    NSString *strNew = [NSString stringWithFormat:@"%@",str];
                    if ([strNew isEqualToString:@""]){
                        //NSLog(@"empty power s/b here");
                        [appDelegate.powerResults replaceObjectAtIndex:i withObject:@"N/A"];
                        [appDelegate.nominalPowers replaceObjectAtIndex:i withObject:@"N/A"];
                        [appDelegate.sampleSizeResults replaceObjectAtIndex:i withObject:@"NoSelection"];
                        i++;
                        
                    }
                    else {
                        //NSLog(@"sample size at %i is: %@", i,[appDelegate.sampleSizeResults objectAtIndex:i]);
                        [appDelegate.sampleSizeResults replaceObjectAtIndex:i withObject:sampleSize];
                        NSString *str = power;
                        NSString *strNew = [NSString stringWithFormat:@"%@",str];
                        [appDelegate.powerResults replaceObjectAtIndex:i withObject:strNew];
                        [appDelegate.nominalPowers replaceObjectAtIndex:i withObject:strNew];
                        //NSLog(@"power for this is:%@",[appDelegate.powerResults objectAtIndex:i]);
                        i++;
                        break;
                    }
                }
                
            }
            else if (appDelegate.solvingFor == @"Sample Size") {
                NSDictionary *dict = [target objectForKey:@"nominalPower"];
                power = [dict objectForKey:@"value"];
                actualPower = [target objectForKey:@"actualPower"];
                
                while (i<4) {
                    if ([appDelegate.powerButtonsStatus objectAtIndex:i] == @"Selected") {
                        NSString *str = sampleSize;
                        NSString *strNew = [NSString stringWithFormat:@"%@",str];
                        [appDelegate.sampleSizeResults replaceObjectAtIndex:i withObject:strNew];
                        str = power;
                        strNew = [NSString stringWithFormat:@"%@",str];
                        [appDelegate.powerResults replaceObjectAtIndex:i withObject:strNew];
                        str = actualPower;
                        strNew = [NSString stringWithFormat:@"%@",str];
                        [appDelegate.nominalPowers replaceObjectAtIndex:i withObject:strNew];
                        i++;
                        break;
                    }
                    else {
                        [appDelegate.sampleSizeResults replaceObjectAtIndex:i withObject:@"N/A"];
                        [appDelegate.powerResults replaceObjectAtIndex:i withObject:@"NoSelection"];
                        [appDelegate.nominalPowers replaceObjectAtIndex:i withObject:@"NoSelection"];
                        i++;
                    }
                    
                }
            }
            
            number++;
            
        }
        
        
        
        while (i<4) {
            if (appDelegate.solvingFor == @"Power") {
                [appDelegate.sampleSizeResults replaceObjectAtIndex:i withObject:@"NoSelection"];
                [appDelegate.powerResults replaceObjectAtIndex:i withObject:@"N/A"];
                [appDelegate.nominalPowers replaceObjectAtIndex:i withObject:@"N/A"];
                
            }
            else if (appDelegate.solvingFor == @"Sample Size") {
                [appDelegate.sampleSizeResults replaceObjectAtIndex:i withObject:@"N/A"];
                [appDelegate.powerResults replaceObjectAtIndex:i withObject:@"NoSelection"];
                [appDelegate.nominalPowers replaceObjectAtIndex:i withObject:@"NoSelection"];
                
            }
            i++;
        }
        NSLog(@"out from JSON");
        for (int i=0; i <4; i++) {
            ;//NSLog(@"power: %@", [appDelegate.powerResults objectAtIndex:i]);
            //NSLog(@"sample size: %@", [appDelegate.sampleSizeResults objectAtIndex:i]);
        }
        
        baseAlert = [[UIAlertView alloc]initWithTitle:@"Please wait,\ncalculating power...." message:nil delegate:self cancelButtonTitle:nil otherButtonTitles:nil];
        
        [baseAlert show];
        
        UIActivityIndicatorView *activityIndicator2 = [[UIActivityIndicatorView
                                                        alloc]initWithActivityIndicatorStyle:UIActivityIndicatorViewStyleWhiteLarge];
        activityIndicator2.center = CGPointMake(baseAlert.bounds.size.width /2.0f, baseAlert.bounds.size.height-40.0f);
        
        [activityIndicator2 startAnimating];
        [baseAlert addSubview:activityIndicator2];
        
        [self performSelector:@selector(showProgressDismiss)
                   withObject:nil afterDelay:4.0f];
        
        
        
        
    } failure:^(NSURLRequest *request, NSHTTPURLResponse *response, NSError *error, id JSON) {
        NSLog(@"Request failed: %@", [error localizedDescription]);
        NSLog(@"code:%i", [error code]);
        if ([error code] == -1011) {
            NSLog(@"sample size too large");
            appDelegate.resultsResponse = @"Request failed";
            UIAlertView *messageAlert = [[UIAlertView alloc]
                                         initWithTitle:@"Calculation Error." message:@"Smallest group size too large. Please try another value." delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil];
            [messageAlert show];
        }
        else if ([error code] == -1003) {
            appDelegate.resultsResponse = @"Request failed";
            UIAlertView *messageAlert = [[UIAlertView alloc]
                                         initWithTitle:@"Server Error." message:@"Server not reachable. Please try again." delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil];
            [messageAlert show];
        }
        else if ([error code] == -1009) {
            appDelegate.resultsResponse = @"Request failed";
            UIAlertView *messageAlert = [[UIAlertView alloc]
                                         initWithTitle:@"Connection Error." message:@"Please check your internet connection." delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil];
            [messageAlert show];
        }
        calculateButton.userInteractionEnabled = YES;
        calculateButton.highlighted = YES;
        reset2Button.userInteractionEnabled = YES;
        
        [self.view setUserInteractionEnabled:YES];
    }] start];
    
    
    /**
     Create an activity indicator and show on the design screen
     */
    
    
    
}


- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (IBAction)calculatePressed:(id)sender {
    
    [self createJSON];
    
    
}

# pragma mark Private Methods

-(void) openResultsController {
    
    UIViewController *uiViewController = [self.storyboard instantiateViewControllerWithIdentifier:@"powerResultControllerView"];
    [self.navigationController pushViewController:uiViewController animated:YES];
    
}

- (IBAction)resetPressed {
    
    UIAlertView *messageAlert = [[UIAlertView alloc]
                                 initWithTitle:@"RESET" message:@"Sure you want to Reset the design?" delegate:self cancelButtonTitle:@"Cancel" otherButtonTitles:@"OK",nil];
    [messageAlert show];
    
}

#pragma mark - Alert View

-(void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex
{
    
    
    if (buttonIndex == 0)
    {
        //NSLog(@"user pressed the Cancel Button");
        [self viewWillAppear:YES];
    }
    
    
    else if (buttonIndex == 1)
    {
        NSLog(@"RESET Pressed");
        
        appDelegate.solvingFor = @"";
        appDelegate.typeIError = @"";
        appDelegate.numberOfGroups = @"";
        appDelegate.numberOfGroupsSelected = 0;
        appDelegate.component0 = 0;
        appDelegate.component1 = 0;
        appDelegate.component2 = 0;
        appDelegate.smallestGroupSize = @"";
        appDelegate.smallestGroupSizeStatus = @"";
        appDelegate.relativeGroupSize = @"";
        appDelegate.meansAndVariance = @"";
        appDelegate.solvingForPower = @"";
        appDelegate.solvingForSampleSize = @"";
        progressView.progress = 0;
        appDelegate.progressValue = 0;
        appDelegate.mode=0;
        
        appDelegate.meanOfGroups = [[NSMutableArray alloc] initWithObjects:@"",@"",@"",@"",@"",@"",@"",@"",@"",@"", nil];
        
        appDelegate.varianceOfGroups = [[NSMutableArray alloc] initWithObjects:@"",@"",@"",@"",@"",@"",@"",@"",@"",@"", nil];
        
        appDelegate.relSizeSliders = [[NSMutableArray alloc] initWithObjects:@"1",@"1",@"1",@"1",@"1",@"1",@"1",@"1",@"1",@"",nil];
        
        appDelegate.relSizeLabels = [[NSMutableArray alloc] initWithObjects:@"1",@"1",@"1",@"1",@"1",@"1",@"1",@"1",@"1",@"1", nil];
        
        appDelegate.sampleSizeResults = [[NSMutableArray alloc] initWithObjects:@"",@"",@"",@"", nil];
        
        appDelegate.smallestGroupSize = @"";
        appDelegate.sampleSizeResult2 = @"";
        appDelegate.sampleSizeResult3 = @"";
        appDelegate.sampleSizeResult4 = @"";
        
        appDelegate.powerResults = [[NSMutableArray alloc] initWithObjects:@"",@"",@"",@"", nil];
        
        appDelegate.powerButtonsStatus =  [[NSMutableArray alloc] initWithObjects:@"NotSelected",@"NotSelected",@"NotSelected",@"NotSelected", nil];
        
        items = [[NSArray alloc] initWithObjects:@"Solving For",@"Type I Error", @"Number of Groups",@"Relative Group Size", @"Smallest Group Size", @"Means and Variances", nil];
        data = [[NSMutableArray alloc]initWithObjects:appDelegate.solvingFor, appDelegate.typeIError, appDelegate.numberOfGroups, appDelegate.relativeGroupSize, appDelegate.smallestGroupSizeStatus, appDelegate.meansAndVariance, nil];
        
        reset2Button.hidden = YES;
        calculateButton.hidden = YES;
        resetButton.hidden = NO;
        
        [self viewWillAppear:YES];
        
        
        //NSLog(@"user pressed the OK Button");
        
    }
}

- (void) showProgressDismiss
{
    [baseAlert dismissWithClickedButtonIndex:0 animated:NO];
    calculateButton.highlighted = NO;
    [NSTimer scheduledTimerWithTimeInterval:1.0 target: self selector:@selector(openResultsController) userInfo:nil repeats:NO];
}

- (void) showDismiss
{
    [baseAlert2 dismissWithClickedButtonIndex:0 animated:NO];
    calculateButton.highlighted = NO;
    
}


- (void)dealloc {
    [designTable release];
    [super dealloc];
}
- (void)viewDidUnload {
    [self setDesignTable:nil];
    [super viewDidUnload];
}
@end
