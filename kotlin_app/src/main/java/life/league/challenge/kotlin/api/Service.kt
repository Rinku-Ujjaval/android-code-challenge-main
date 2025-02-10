package life.league.challenge.kotlin.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Service {

    private const val HOST = "https://engineering.league.dev/challenge/api/"
    private const val TAG = "Service"

    // request response logger
    val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
        this.level = HttpLoggingInterceptor.Level.BODY
    }

    val okHttpClient = OkHttpClient.Builder().apply {
        addInterceptor(httpLoggingInterceptor)
    }

    val api: Api by lazy {
        val retrofit = Retrofit.Builder()
            .client(okHttpClient.build())
            .baseUrl(HOST)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create<Api>(Api::class.java)
    }
}
