package au.com.talentwars.data.network

import au.com.talentwars.Constants.API_V2_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiNetwork {

    fun makeRetrofitService(logging: Boolean = false): Retrofit {
        val client = createHttpClient(logging)
        return Retrofit.Builder()
            .baseUrl(API_V2_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }
}