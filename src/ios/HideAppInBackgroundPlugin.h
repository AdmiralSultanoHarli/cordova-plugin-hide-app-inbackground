#import <Foundation/Foundation.h>
#import <Cordova/CDV.h>

@interface HideAppInBackgroundPlugin : CDVPlugin

- (void)enable:(CDVInvokedUrlCommand*)command;
- (void)disable:(CDVInvokedUrlCommand*)command;
- (void)listen:(CDVInvokedUrlCommand*)command;

@end
