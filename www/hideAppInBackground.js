var hideAppInBackground = {
  enable: function (successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, 'HideAppInBackground', 'enable', []);
  },
  disable: function (successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, 'HideAppInBackground', 'disable', []);
  },
  registerListener : function(callback) {
    cordova.exec(callback, callback, 'HideAppInBackground', 'listen', []);
  }
}

cordova.addConstructor(function () {
  if (!window.plugins) {window.plugins = {};}

  window.plugins.hideAppInBackground = hideAppInBackground;
  hideAppInBackground.registerListener(function(me) {
    console.log('received listener:',me);
  });

  return window.plugins.hideAppInBackground;
});