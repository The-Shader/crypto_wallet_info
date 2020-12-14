package com.fireblade.repository.network.services

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Inject

class HttpClientBuilder @Inject constructor(private val applicationContext: Context) {

    fun build(cacheSize: Int = DEFAULT_CACHE_SIZE): OkHttpClient {

        val cacheSizeInBytes = (cacheSize * MEGABYTES).toLong() // 50 MB Cache
        val localCache = Cache(applicationContext.cacheDir, cacheSizeInBytes)

        return OkHttpClient.Builder()
            .cache(localCache)
            .addInterceptor { chain ->

                val request : Request = chain.request()

                if (hasNetworkAccess()) {
                    request.newBuilder().header("Cache-Control", "public, max-age=$MAX_AGE_IN_SECONDS").build()
                }
                else {
                    request.newBuilder().header("Cache-Control", "public, only-if-cached, " +
                            "max-stale=${SECONDS_IN_MINUTE * MINUTES_IN_HOUR * HOURS_IN_DAY * DAYS_IN_WEEK}").build()
                }
                chain.proceed(request)
            }
            .build()
    }

    private fun hasNetworkAccess() : Boolean {
        val activeNetwork: NetworkInfo? =
            (applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo
        return activeNetwork?.isConnected ?: false
    }

    companion object {
        const val MEGABYTES = 1024 * 1024
        const val MAX_AGE_IN_SECONDS = 5
        const val SECONDS_IN_MINUTE = 60
        const val MINUTES_IN_HOUR = 60
        const val HOURS_IN_DAY = 24
        const val DAYS_IN_WEEK = 7
        const val DEFAULT_CACHE_SIZE = 50
    }
}