package com.user.easypay.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/*
    Class to check the network connectivity state before performing any operation.
*/
public class NetworkUtils {

    private static NetworkUtils instance  = null;

    public static NetworkUtils getInstance(){
        if(instance == null)
            instance = new NetworkUtils();
        return instance;
    }

    public boolean isNetworkAvailable(Context context) {

        boolean retVal = false;

        ConnectivityManager connectionManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectionManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            retVal = true;
        }

        return retVal;
    }

}
