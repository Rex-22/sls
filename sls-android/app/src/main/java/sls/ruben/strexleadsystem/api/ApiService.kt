package sls.ruben.strexleadsystem.api

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import tech.bitcube.sabu.network.OnConnectionTimeoutListeners
import java.io.IOException
import java.net.SocketTimeoutException
import java.util.concurrent.TimeUnit

object ApiService {

    private const val API_VERSION: String = "v1"
    //    val API_URL = "http://127.0.0.1:3000/$API_VERSION/" // When using an emulator, use this one
    const val API_URL = "http://192.168.100.197:3000/$API_VERSION/"

    private var listeners = ArrayList<OnConnectionTimeoutListeners>()

    var api: Retrofit? = null
        private set

    init {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC

        val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor { onOnIntercept(it); }
                .addInterceptor(loggingInterceptor)
                .build()

        api = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(API_URL)
                .client(okHttpClient)
                .build()
    }

    /**
     * Adds a listener to the event queue for when a connection times out
     * */
    fun add(listener: OnConnectionTimeoutListeners) {
        listeners.add(listener)
    }

    @Throws(IOException::class)
    private fun onOnIntercept(chain: Interceptor.Chain): Response {
        try {
            return chain.proceed(chain.request())
        } catch (exception: SocketTimeoutException) {
            exception.printStackTrace()
            for (listener in ApiService.listeners) {
                listener.onConnectionTimeout()
            }
        }

        return chain.proceed(chain.request())
    }

}