package com.nguyenven299.vpn_easy_one_click.helpers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class CheckInternetConnection {
    /**
     * Check internet status
     * @param context
     * @return: internet connection status
     */
    public boolean netCheck(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nInfo = cm.getActiveNetworkInfo();

        boolean isConnected = nInfo != null && nInfo.isConnectedOrConnecting();
        return isConnected;

    }
}
