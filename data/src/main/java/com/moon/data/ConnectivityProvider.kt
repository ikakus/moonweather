package com.moon.data

import android.content.Context
import android.net.ConnectivityManager


interface ConnectivityProvider {
    fun isInternetAvailable(): Boolean
}

class ConnectivityProviderImpl(val context: Context) : ConnectivityProvider {

    override fun isInternetAvailable(): Boolean {
        return isNetworkAvailable()
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val activeNetworkInfo = connectivityManager!!.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}
