package com.ns.pu.users.repository.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi

interface NetworkState {
    fun isConnected(): Boolean
}

class NetworkStateImpl(private val context: Context) : NetworkState {

    override fun isConnected() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        isConnectedSinceM()
    } else {
        isConnectedBeforeM()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun isConnectedSinceM(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val actNw = connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
        return when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

    private fun isConnectedBeforeM(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        return cm?.activeNetworkInfo != null && cm.activeNetworkInfo?.isConnected == true
    }
}