# cordova-plugin-hide-app-inbackground

This is a cordova plugin to enable/disable hiding app on background in android and ios (Mobile)

## Supported Platforms

- Android API all versions
- IOS all versions

## Installation

Cordova local build:
    cordova plugin add <GIT URL PATH>

## Usage in javascript

```js
document.addEventListener("deviceready", onDeviceReady, false);
// Enable
function onDeviceReady() {
  window.plugins.hideAppInBackground.enable(successCallback, errorCallback);
}
// Disable
function onDeviceReady() {
  window.plugins.hideAppInBackground.disable(successCallback, errorCallback);
}

function successCallback(result) {
  console.log(result); // true - enabled, false - disabled
}

function errorCallback(error) {
  console.log(error);
}

```



## Usage in typescript

```ts

// Enable
  (<any>window).plugins.hideAppInBackground.enable((a) => this.successCallback(a), (b) => this.errorCallback(b));

// Disable
  (<any>window).plugins.hideAppInBackground.disable((a) => this.successCallback(a), (b) => this.errorCallback(b));

  successCallback(result) {
    console.log(result); // true - enabled, false - disabled
  }

  errorCallback(error) {
    console.log(error);
  }

```
