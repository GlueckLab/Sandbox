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

#import <UIKit/UIKit.h>
#import "GlimmpseAppDelegate.h"
#import <MessageUI/MessageUI.h>
#import "QuartzCore/QuartzCore.h"

@interface GlimmpsePowerResultViewController : UIViewController <UITableViewDataSource, UITableViewDelegate, UITextViewDelegate, MFMailComposeViewControllerDelegate, UIActionSheetDelegate>

{
    //IBOutlet UITableView *table;
    NSMutableArray *sectionArray;
    NSMutableArray *numberOfRows;
}

@property (strong, nonatomic) IBOutlet UITableView *designTable;

//@property (strong, nonatomic) IBOutlet UITableView *table;
@property (strong, nonatomic) NSMutableArray *sectionArray;
@property (strong, nonatomic) NSMutableArray *numberOfRows;

- (void) saveResultButtonPressed;

@end
