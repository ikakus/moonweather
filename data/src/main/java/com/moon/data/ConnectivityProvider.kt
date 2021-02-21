package com.moon.data

interface ConnectivityProvider {
    fun isInternetAvailable(): Boolean
}

class ConnectivityProviderImpl : ConnectivityProvider {
    override fun isInternetAvailable(): Boolean {
        return false
    }
}
