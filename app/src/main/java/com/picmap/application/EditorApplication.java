package com.picmap.application;

import android.app.Application;

import com.adobe.creativesdk.foundation.AdobeCSDKFoundation;
import com.adobe.creativesdk.foundation.auth.IAdobeAuthClientCredentials;


/**
 * Created by Yur on 24/08/16.
 */
public class EditorApplication extends Application implements IAdobeAuthClientCredentials {
    private static final String CREATIVE_SDK_CLIENT_ID = "4c069322041a4c80b2a52c1829984e14";
    private static final String CREATIVE_SDK_CLIENT_SECRET = "513653a0-ec10-4360-af6a-b9120ed7a718";

    @Override
    public void onCreate() {
        super.onCreate();
        AdobeCSDKFoundation.initializeCSDKFoundation(getApplicationContext());

    }




    @Override
    public String getClientID() {
        return  CREATIVE_SDK_CLIENT_ID;
    }

    @Override
    public String getClientSecret() {
        return CREATIVE_SDK_CLIENT_SECRET;
    }
}
