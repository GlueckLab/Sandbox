//
//  ViewController.m
//  Trial_Comm
//
//  Created by Aarti Munjal on 8/7/12.
//  Copyright (c) 2012 Deborah Glueck. All rights reserved.
//

#import "ViewController.h"

#import "AFNetworking.h"

#import "AFPropertyListRequestOperation.h"

@interface ViewController ()



@end

@implementation ViewController
@synthesize activityLoading;

- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view, typically from a nib.
    
    NSString *filePath = [[NSBundle mainBundle] pathForResource:@"EqualGroupSizeOneWayANOVA" ofType:@"json"];
    
    NSError *error;
    
    NSData *jsonData = [NSData dataWithContentsOfFile:filePath];
    
    NSDictionary *requestDataInDictionary = [NSJSONSerialization JSONObjectWithData:jsonData options:kNilOptions error:&error];
    
    for (id key in requestDataInDictionary) {
        
        NSLog(@"key: %@, value: %@", key, [requestDataInDictionary objectForKey:key]);
    }
    
    
    NSData *newJSONData = [NSJSONSerialization dataWithJSONObject:requestDataInDictionary options:NSJSONWritingPrettyPrinted error:&error];
    
    
    
    NSString *jsonString = [[NSString alloc] initWithData:newJSONData encoding:NSUTF8StringEncoding];
    
    NSLog(@"new derived JSON data is: %@", jsonString);
    
    NSURL *url = [NSURL URLWithString:@"http://glimmpsebeta.samplesizeshop.com/power2/power"];
    
    NSMutableURLRequest *request = [NSMutableURLRequest requestWithURL:url cachePolicy:NSURLRequestUseProtocolCachePolicy timeoutInterval:60];
    
    [request setHTTPMethod:@"POST"];
    [request setValue:@"application/json" forHTTPHeaderField:@"Accept"];
    [request setValue:@"application/json" forHTTPHeaderField:@"Content-Type"];
    [request setValue:[NSString stringWithFormat:@"%i", [jsonData length]] forHTTPHeaderField:@"Content-Length"];
    [request setHTTPBody:jsonData];
    
    
    //activityLoading.userInteractionEnabled
    
    [[AFJSONRequestOperation JSONRequestOperationWithRequest:request success:^(NSURLRequest *request, NSHTTPURLResponse *response, id JSON) {
       // NSLog(@"Request succeeded: %@", JSON);
        
        
        
        //NSLog(@"Length of the object: %d", [JSON length]);
        
       // NSLog(@"Here is the object%@", [JSON objectAtIndex:1]);
        
        NSDictionary* target = [JSON objectAtIndex:1];
        
        NSString *power = [target objectForKey:@"actualPower"];
        
        NSString *samplesize = [target objectForKey:@"totalSampleSize"];
        
        NSLog(@"power is: %@", power);
        
        NSLog(@"samplesize is: %@", samplesize);
        
    } failure:^(NSURLRequest *request, NSHTTPURLResponse *response, NSError *error, id JSON) {
        NSLog(@"Request failed: %@", [error localizedDescription]);
    }] start];

    
}



- (void)viewDidUnload
{
    [self setActivityLoading:nil];
    [super viewDidUnload];
    // Release any retained subviews of the main view.
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    return (interfaceOrientation != UIInterfaceOrientationPortraitUpsideDown);
}

@end
