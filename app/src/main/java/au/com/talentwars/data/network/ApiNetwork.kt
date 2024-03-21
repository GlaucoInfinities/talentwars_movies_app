package au.com.talentwars.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiNetwork {

    private const val API_V2_URL = "https://api.themoviedb.org/3/"
    fun makeRetrofitService(logging: Boolean = false): Retrofit {
        val client = createHttpClient(logging)
        return Retrofit.Builder()
            .baseUrl(API_V2_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }
}