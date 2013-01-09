/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *  GlimmpsePowerResultViewController.m
 *
 * A simple view controller that displays the power and sample size results for the ANOVA study design returned
 * by the GLIMMPSE server. An Action sheet allows user to either save a screenshot or email the power results as
 * a .csv file.
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

#import "GlimmpsePowerResultViewController.h"

@interface GlimmpsePowerResultViewController ()

@end

@implementation GlimmpsePowerResultViewController
{
    GlimmpseAppDelegate *appDelegate;
}

@synthesize designTable;
@synthesize sectionArray;
@synthesize numberOfRows;

UIButton *sendEmailButton;


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
    

    NSLog(@"%@,%@",self.designTable.delegate, self.designTable.dataSource);
    
    [super viewDidLoad];

    
	// Do any additional setup after loading the view.
}

-(void)viewWillAppear:(BOOL)animated
{
    UIButton* fakeButton = (UIButton *) [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"GlimmpseIconNoBG48x48.png"]];
    UIBarButtonItem *fakeButtonItem = [[UIBarButtonItem alloc] initWithCustomView:fakeButton];
    self.navigationItem.rightBarButtonItem = fakeButtonItem;
}

#pragma mark -
#pragma mark Table view data source

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    
    // Return the number of sections.
    
    return [sectionArray count];
}

// -------------------------------------------------------------------------------
//	tableView:numberOfRowsInSection:
// -------------------------------------------------------------------------------

-(NSInteger) tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    NSNumberFormatter * f = [[NSNumberFormatter alloc] init];
    [f setLocale:[NSLocale currentLocale]];
    [f setNumberStyle:NSNumberFormatterDecimalStyle];
    NSString *numOfRows = [numberOfRows objectAtIndex:section];
    NSNumber *tempNum = [f numberFromString:numOfRows];
    
    return  [tempNum intValue];
}


- (void)loadView
{
    UITableView *tableView = [[UITableView alloc] initWithFrame:[[UIScreen mainScreen] applicationFrame] style:UITableViewStyleGrouped];
    tableView.autoresizingMask = UIViewAutoresizingFlexibleHeight|UIViewAutoresizingFlexibleWidth;
    tableView.delegate = self;
    tableView.dataSource = self;
    
    self.sectionArray = [[NSMutableArray alloc] initWithCapacity:2];
    self.numberOfRows = [[NSMutableArray alloc] initWithCapacity:2];
    [sectionArray addObject:@"ANOVA Results"];
    [sectionArray addObject:@""];
    
    [numberOfRows addObject:@"5"];
    [numberOfRows addObject:@"1"];
    
    [designTable insertSections:[NSIndexSet indexSetWithIndex:0] withRowAnimation:UITableViewRowAnimationTop];
    [designTable insertSections:[NSIndexSet indexSetWithIndex:1] withRowAnimation:UITableViewRowAnimationTop];
    
    
    [tableView reloadData];
    
    self.view = tableView;
}

// -------------------------------------------------------------------------------
//	tableView:cellForRowAtIndexPath:
// -------------------------------------------------------------------------------


-(UITableViewCell *) tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    // NSLog(@"inside the table function");
    
    UITableViewCell *cell;
    UILabel *powerLabel;
    UILabel *sampleSizeLabel;
    
    
    
    
    //Step 1: Check to see if we can reuse a cell from a row that has just roller out from the screen
    
    cell = [tableView dequeueReusableCellWithIdentifier:@"MyCell"];
    
    //Step 2:
    if(cell == nil)
    {
        cell = [[[UITableViewCell alloc]initWithStyle:UITableViewCellStyleDefault reuseIdentifier:@"MyCell"]autorelease];
        cell.selectionStyle = UITableViewCellSelectionStyleNone;
    }
    
    
    
    
    if (indexPath.section == 0 && indexPath.row == 0) {
        powerLabel = [[UILabel alloc] initWithFrame:CGRectMake(15.0, 10.0, 100.0, 30.0)];
        //[[[UILabel alloc] initWithFrame:CGRectMake(0.0, 0.0, 220.0, 15.0)]];
        powerLabel.font = [UIFont boldSystemFontOfSize:16.0];
        powerLabel.text = @"Power";
        [cell setBackgroundColor:[UIColor lightGrayColor]];
    }
    else if (indexPath.section == 0 && indexPath.row > 0) {
        powerLabel = [[UILabel alloc] initWithFrame:CGRectMake(15.0, 5.0, 100.0, 25.0)];
        //[[[UILabel alloc] initWithFrame:CGRectMake(0.0, 0.0, 220.0, 15.0)]];
        powerLabel.font = [UIFont systemFontOfSize:14.0];
        
        NSString *str = [appDelegate.powerResults objectAtIndex:indexPath.row-1];
        //NSLog(@"power value to send to the label:%@", str);
        NSString *strNew = [NSString stringWithFormat:@"%@",str];
        str = strNew;
        //NSLog(@"power value for label:%@", str);
        
        if (appDelegate.solvingFor == @"Power" && str.length > 5) {
            str = [strNew substringToIndex:5];
            
        }
        if ([str isEqualToString:@"NoSelection"] || [str isEqualToString:@"N/A"]) {
            str = @"-";
        }
        [powerLabel setText:str];
        powerLabel.tag = 1;
        
        
    }
    //powerLabel.textAlignment = NSTextAlignmentCenter;
    //powerLabel.backgroundColor = [UIColor clearColor];
    //[cell.contentView addSubview:powerLabel];
    
    
    if (indexPath.section == 0 && indexPath.row == 0) {
        sampleSizeLabel = [[UILabel alloc] initWithFrame:CGRectMake(150.0, 10.0, 130.0, 30.0)];
        sampleSizeLabel.font = [UIFont boldSystemFontOfSize:16.0];
        sampleSizeLabel.text = @"Sample Size";
    }
    else if (indexPath.section == 0 && indexPath.row > 0){
        sampleSizeLabel = [[UILabel alloc] initWithFrame:CGRectMake(160.0, 5.0, 120.0, 25.0)];
        sampleSizeLabel.font = [UIFont systemFontOfSize:14.0];
        
        NSString *str = [appDelegate.sampleSizeResults objectAtIndex:indexPath.row-1];
        NSString *strNew = [NSString stringWithFormat:@"%@",str];
        str = strNew;
        if ([str isEqualToString:@"NoSelection"] || [str isEqualToString:@"N/A"]) {
            [sampleSizeLabel setText:@"-"];
        }
        else
            [sampleSizeLabel setText:str];
    }
    //sampleSizeLabel.textAlignment = NSTextAlignmentCenter; //UITextAlignmentCenter;
    //sampleSizeLabel.backgroundColor = [UIColor clearColor];
    //[cell.contentView addSubview:sampleSizeLabel];
    
    
    
    if (indexPath.section == 1 && indexPath.row == 0) {
        
        sendEmailButton = [UIButton buttonWithType:UIButtonTypeRoundedRect];
        [sendEmailButton setTitle:@"Save Results" forState:UIControlStateNormal];
        sendEmailButton.titleLabel.font = [UIFont boldSystemFontOfSize:21.0];
        sendEmailButton.frame = CGRectMake(0.0, 0.0, 300.0, 35.0);
        sendEmailButton.highlighted = YES;
        sendEmailButton.tintColor = [UIColor colorWithRed:0.9 green:0.2 blue:0.2 alpha:1.0];
        [sendEmailButton addTarget:self
                            action:@selector(saveResultButtonPressed)
                  forControlEvents:UIControlEventTouchDown];
        [cell.contentView addSubview:sendEmailButton];
    }
    
    
    return cell;
}

- (NSString *)tableView:(UITableView *)tableView titleForHeaderInSection:(NSInteger)section {
    // The header for the section is the region name -- get this from the region at the section index.
    
    return [sectionArray objectAtIndex:section];
}

- (CGFloat) tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (indexPath.section == 0 && indexPath.row == 0)
        return 45;
    else if (indexPath.row > 0)
        return 40;
    
    else
        return 35;
    
    
}

- (NSString *)tableView:(UITableView *)tableView titleForFooterInSection:(NSInteger)section {
    if (section == 0)
        return @"Power results returned by GLIMMPSE server.";
    else
        return @"Tap on button for options to save results. ";
}

- (void)dealloc {
    [designTable release];
    [super dealloc];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}


- (void) saveResultButtonPressed
{
    
    // NSLog(@"whoa, that worked");
    UIActionSheet *actionSheet = [[UIActionSheet alloc] initWithTitle:@"Save Results" delegate:self cancelButtonTitle:@"Cancel" destructiveButtonTitle:@"Reset Design" otherButtonTitles:@"Email Results",@"Save As Image", nil];
    
    [actionSheet showFromTabBar:self.tabBarController.tabBar];
    
    sendEmailButton.highlighted = YES;
    
    
}

-(void)actionSheet:(UIActionSheet *)actionSheet clickedButtonAtIndex:(NSInteger)buttonIndex
{
    
    
    if (buttonIndex == 0) {
        
        //NSLog(@"Inside button 0");
        
        UIAlertView *messageAlert = [[UIAlertView alloc]
                                     initWithTitle:@"RESET Design" message:@"Sure you want to continue?" delegate:self cancelButtonTitle:@"Cancel" otherButtonTitles:@"OK",nil];
        [messageAlert show];
        
        sendEmailButton.highlighted = YES;
        
    }
    
    
    else if (buttonIndex == 1) {
        
        //NSLog(@"Inside button 1");
        
        //NSLog(@"Inside email on Results Screen\n");
        
        NSString *csvFileName = @"GLIMMPSEPowerResults.csv";
        
        NSMutableString *str = [[NSMutableString alloc] init];
        
        
        [str appendString:@"test,"];
        [str appendString:@"actualPower,"];
        [str appendString:@"Total Sample Size,"];
        [str appendString:@"betaScale,"];
        [str appendString:@"sigmaScale,"];
        [str appendString:@"alpha,"];
        [str appendString:@"nominalPower,"];
        [str appendString:@"powerMethod,"];
        [str appendString:@"quantile,"];
        [str appendString:@"ciLower,"];
        [str appendString:@"ciUpper\n"];
        
        
        
        for (int i=0; i<4; i++) {
            
            if (appDelegate.solvingFor == @"Power") {
                
                if ([appDelegate.powerResults objectAtIndex:i] != @"N/A" && [appDelegate.sampleSizeResults objectAtIndex:i] != @"NoSelection") {
                    //NSLog(@"power is %@",[appDelegate.powerResults objectAtIndex:i]);
                    NSString *power = [NSString stringWithFormat:@"%@",[appDelegate.powerResults objectAtIndex:i]];
                    
                    [str appendString:@"UNIREP"];
                    [str appendString:@","];
                    [str appendString:power];
                    [str appendString:@","];
                    NSString *sampleSize = [NSString stringWithFormat:@"%@",[appDelegate.sampleSizeResults objectAtIndex:i]];
                    [str appendString:sampleSize];
                    [str appendString:@","];
                    [str appendString:@"1.0"];
                    [str appendString:@","];
                    [str appendString:@"1.0"];
                    [str appendString:@","];
                    [str appendString:appDelegate.typeIError];
                    [str appendString:@","];
                    [str appendString:power];
                    [str appendString:@","];
                    [str appendString:@"CONDITIONAL"];
                    [str appendString:@","];
                    [str appendString:@"null"];
                    [str appendString:@","];
                    [str appendString:@"null"];
                    [str appendString:@","];
                    [str appendString:@"null"];
                    [str appendString:@"\n"];
                }
                
            }
            else if (appDelegate.solvingFor == @"Sample Size") {
                if ([appDelegate.powerButtonsStatus objectAtIndex:i] == @"Selected" && [appDelegate.sampleSizeResults objectAtIndex:i] != @"N/A") {
                    //NSLog(@"power is %@",[appDelegate.powerResults objectAtIndex:i]);
                    NSString *power = [NSString stringWithFormat:@"%@",[appDelegate.powerResults objectAtIndex:i]];
                    NSString *actualPower = [NSString stringWithFormat:@"%@", [appDelegate.nominalPowers objectAtIndex:i]];
                    [str appendString:@"UNIREP"];
                    [str appendString:@","];
                    [str appendString:actualPower];
                    [str appendString:@","];
                    NSString *sampleSize = [NSString stringWithFormat:@"%@",[appDelegate.sampleSizeResults objectAtIndex:i]];
                    [str appendString:sampleSize];
                    [str appendString:@","];
                    [str appendString:@"1.0"];
                    [str appendString:@","];
                    [str appendString:@"1.0"];
                    [str appendString:@","];
                    [str appendString:appDelegate.typeIError];
                    [str appendString:@","];
                    [str appendString:power];
                    [str appendString:@","];
                    [str appendString:@"CONDITIONAL"];
                    [str appendString:@","];
                    [str appendString:@"null"];
                    [str appendString:@","];
                    [str appendString:@"null"];
                    [str appendString:@","];
                    [str appendString:@"null"];
                    [str appendString:@"\n"];
                }
                else {
                    ;
                }
            }
            
            
            sendEmailButton.highlighted = YES;
            
        }
        
        NSData *csvFile = [str dataUsingEncoding:NSUTF8StringEncoding];
        
        NSError *error;
        
        NSData *newJSONData = [NSJSONSerialization dataWithJSONObject:appDelegate.jsonFileContents options:kNilOptions error:&error];
        
        
        //NSData *jsonDataObject = [appDelegate.jsonFileContents dataUsingEncoding:NSUTF8StringEncoding];
        
        if ([MFMailComposeViewController canSendMail]) {
            
            MFMailComposeViewController *mailComposer;
            NSString *emailTitle = @"GLIMMPSE Lite Power Results";
            NSString *messageBody = @"Thank you for using the GLIMMPSE Lite application. Your power results are attached to this email. \n To learn more about power and sample size, please visit http://samplesizeshop.org.";
            mailComposer =[[MFMailComposeViewController alloc]init];
            mailComposer.mailComposeDelegate = self;
            NSArray *emailAddresses = [[NSArray alloc]initWithObjects:@"aarti.munjal@ucdenver.edu", nil]; //samplesizeshop@gmail.com
            [mailComposer setSubject:emailTitle];
            [mailComposer setMessageBody:messageBody isHTML:YES];
            [mailComposer addAttachmentData:csvFile mimeType:@"application/octet-stream" fileName:csvFileName];
            [mailComposer addAttachmentData:newJSONData mimeType:@"application/octet-stream" fileName:@"GLIMMPSELiteStudyDesign.json"];
            [mailComposer setToRecipients:emailAddresses];
            [self presentModalViewController:mailComposer animated:YES];
        }
        else {
            //NSLog(@"please setup an email ID first");
            UIAlertView *messageAlert = [[UIAlertView alloc]
                                         initWithTitle:@"Error" message:@"Please Set up an email accout on your device first." delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil];
            [messageAlert show];
            
            sendEmailButton.highlighted = YES;
        }
        
    }
    else if (buttonIndex == 2) {
        
        //NSLog(@"Inside button 2");
        UIGraphicsBeginImageContext(self.view.bounds.size);
        [self.view.layer renderInContext:UIGraphicsGetCurrentContext()];
        UIImage *viewImage = UIGraphicsGetImageFromCurrentImageContext();
        UIGraphicsEndImageContext();
        UIImageWriteToSavedPhotosAlbum(viewImage, nil, nil, nil);
        
        sendEmailButton.highlighted = YES;
    }
    
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
        
        
        [self viewWillAppear:YES];
        
        
        //NSLog(@"user pressed the OK Button");
        
    }
}


-(void) mailComposeController:(MFMailComposeViewController *)controller didFinishWithResult:(MFMailComposeResult)result error:(NSError *)error
{
    [self dismissModalViewControllerAnimated:YES];
}



@end
