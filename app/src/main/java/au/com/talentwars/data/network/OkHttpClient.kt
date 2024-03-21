package au.com.talentwars.data.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

fun createHttpClient(logging: Boolean = false): OkHttpClient {
    val httpClient = OkHttpClient.Builder()

    if (logging) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        httpClient.addInterceptor(loggingInterceptor)
    }

    // Add headers here
    httpClient.addInterceptor { chain ->
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI4OGMxZTZjNjYzOGRkMzI5OTAyMGQwZDBmMDRmMGEzMCIsInN1YiI6IjY1ZmIyZTViNTQ1MDhkMDE3Y2Y4ZTM5MiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.gH3QwNvGKwWPQIUrVHNl2d0y8oGIwkgNOSx6-wYE2MI") // Example header
            .addHeader("accept", "application/json") // Example header
            .build()
        chain.proceed(request)
    }

    return httpClient.build()
}