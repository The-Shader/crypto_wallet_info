package com.fireblade.persistence.network.services

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Inject

class HttpClientBuilder @Inject constructor(private val context: Context) {

    fun build(cacheSize: Int = 50): OkHttpClient {

        val cacheSizeInBytes = (cacheSize * 1024 * 1024).toLong() // 50 MB Cache
        val localCache = Cache(context.cacheDir, cacheSizeInBytes)

        return OkHttpClient.Builder()
            .cache(localCache)
            .addInterceptor { chain ->

                val request : Request = chain.request()

                if (hasNetworkAccess()) {
                    request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
                }
                else {
                    request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build()
                }
                chain.proceed(request)
            }
            .build()
    }

    private fun hasNetworkAccess() : Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        if (activeNetwork != null && activeNetwork.isConnected) {
            return true
        }
        return false
    }
}