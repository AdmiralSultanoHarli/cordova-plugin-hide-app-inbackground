package com.mirdev.hideippinbackground;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.RenderEffect;
import android.graphics.Shader;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.view.ViewTreeObserver;

import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;

public class HideAppInBackgroundPlugin extends CordovaPlugin implements ViewTreeObserver.OnWindowFocusChangeListener{
    private com.mirdev.hideippinbackground.HideAppInBackgroundPlugin mContext;
    public static CordovaWebView mWebView;
    protected CallbackContext context;
    boolean isHideEnabled = false;
    boolean isListenEnabled = false;

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        Activity activity = this.cordova.getActivity();
        mWebView = webView;
        mWebView.getView().getViewTreeObserver().addOnWindowFocusChangeListener(this);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (isListenEnabled) {
            if (hasFocus) {
                this.context.sendPluginResult(createPluginResult(true));
            } else {
                this.context.sendPluginResult(createPluginResult(false));
            }
        }
        if (isHideEnabled) {
            int color = Color.parseColor("#FFFFFF");
            Drawable drawable = new ColorDrawable(color);
            if (hasFocus) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    this.webView.getView().setRenderEffect(null);
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
                    this.webView.getView().setForeground(null);
                } else {
                    this.webView.getView().setVisibility(View.VISIBLE);
                }
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    this.webView.getView().setRenderEffect(
                            RenderEffect.createBlurEffect(
                                    90f, //Radius X
                                    90f, //Radius Y
                                    Shader.TileMode.MIRROR
                            ));
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
                    this.webView.getView().setForeground(drawable);
                } else {
                    this.webView.getView().setVisibility(View.GONE);
                }
            }
        }
    }

    @Override
    public boolean execute(String action, JSONArray data, final CallbackContext callbackContext) throws JSONException {
        mContext = this;
        if (action.equals("enable")) {
            mContext.cordova.getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    try{
                        // Enable to hide the app when its on background
                        isHideEnabled = true;
                        callbackContext.success("Success");
                    }catch(Exception e){
                        callbackContext.error(e.toString());
                    }
                }
            });

            return true;
        }else if (action.equals("disable")) {
            mContext.cordova.getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    try{
                        // Disable to hide the app when its on background
                        isHideEnabled = false;
                        callbackContext.success("Success");
                    }catch(Exception e){
                        callbackContext.error(e.toString());
                    }
                }
            });
            return true;
        } else if (action.equals("listen")) {
            this.context = callbackContext;
            mContext.cordova.getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    isListenEnabled = true;
                }
            });
            return true;
        }
        else{
            return false;
        }

    }

    public PluginResult createPluginResult(boolean message) {
        PluginResult pluginResult = new PluginResult(PluginResult.Status.OK, message);
        pluginResult.setKeepCallback(true); // keep callback
        return pluginResult;
    }
}
