#import "HideAppInBackgroundPlugin.h"
@interface HideAppInBackgroundPlugin() {
    CDVInvokedUrlCommand * _eventCommand;
}
@property (nonatomic, assign) BOOL isHideEnabled;
@end

@implementation HideAppInBackgroundPlugin
UIVisualEffect *blurEffect;
UIVisualEffectView *visualEffectView;
- (void)pluginInitialize {
   [[NSNotificationCenter defaultCenter]addObserver:self
                                           selector:@selector(onAppWillResignActive)
                                               name:UIApplicationWillResignActiveNotification
                                             object:nil];
    
   [[NSNotificationCenter defaultCenter] addObserver:self
                                            selector:@selector(onAppDidBecomeActive:)
                                                name:UIApplicationDidBecomeActiveNotification
                                              object:nil];
}

- (void)enable:(CDVInvokedUrlCommand *)command
{
    self.isHideEnabled = YES;
    CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"Enabling hide app in background"];
    [pluginResult setKeepCallbackAsBool:YES];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:_eventCommand.callbackId];
}

-(void)disable:(CDVInvokedUrlCommand*)command {
    self.isHideEnabled = NO;
    CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"Disabling hide app in background"];
    [pluginResult setKeepCallbackAsBool:YES];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:_eventCommand.callbackId];
}

-(void)listen:(CDVInvokedUrlCommand*)command {
    _eventCommand = command;
}

- (void)onAppDidBecomeActive:(UIApplication *)application
{
    if (self.isHideEnabled) {
        if(visualEffectView!=nil) {
            [visualEffectView removeFromSuperview];
        }
        CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"onAppDidBecomeActive"];
        [pluginResult setKeepCallbackAsBool:YES];
        [self.commandDelegate sendPluginResult:pluginResult callbackId:_eventCommand.callbackId];
    }
}

-(void) onAppWillResignActive {
    if(self.isHideEnabled) {
        blurEffect = [UIBlurEffect effectWithStyle:UIBlurEffectStyleLight];
        visualEffectView = [[UIVisualEffectView alloc] initWithEffect:blurEffect];
        [visualEffectView setFrame:self.webView.bounds];
        [self.webView addSubview:visualEffectView];
        CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"onAppWillResignActive"];
        [pluginResult setKeepCallbackAsBool:YES];
        [self.commandDelegate sendPluginResult:pluginResult callbackId:_eventCommand.callbackId];
    }
}

@end
