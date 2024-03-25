package au.com.talentwars.data.network

import au.com.talentwars.Constants.ACCEPT
import au.com.talentwars.Constants.API_V2_URL
import au.com.talentwars.Constants.BEARER
import au.com.talentwars.Constants.CONTENT_TYPE
import au.com.talentwars.data.model.RequestPost
import com.google.gson.Gson
import com.google.gson.JsonObject
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.io.BufferedOutputStream
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

fun createHttpClient(logging: Boolean = false): OkHttpClient {
    val httpClient = OkHttpClient.Builder()

    if (logging) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        httpClient.addInterceptor(loggingInterceptor)
    }

    httpClient.addInterceptor { chain ->
        val request = chain.request().newBuilder()
            .addHeader(
                "Authorization", BEARER
            )
            .addHeader("accept", ACCEPT)
            .build()
        chain.proceed(request)
    }

    return httpClient.build()
}

//Post
//Retrofit was not accepting POST call
fun doPostRequest(rawBody: JsonObject, urlString: String): Pair<Boolean, RequestPost?> {
    var result = ""
    var connection: HttpURLConnection? = null
    var success = false

    val url = URL(API_V2_URL + urlString)

    try {
        connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "POST"
        connection.setRequestProperty(
            "Authorization",
            BEARER
        )
        connection.setRequestProperty("Content-Type", CONTENT_TYPE)
        connection.setRequestProperty("Accept", ACCEPT)
        connection.doOutput = true

        BufferedOutputStream(connection.outputStream).use { outputStream ->
            outputStream.write(rawBody.toString().toByteArray())
            outputStream.flush()
        }

        val responseCode = connection.responseCode
        success =
            responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED

        BufferedReader(InputStreamReader(connection.inputStream)).use { reader ->
            result = reader.readText()
        }

    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        connection?.disconnect()
    }
    val requestRatedMovie: RequestPost = Gson().fromJson(result, RequestPost::class.java)
    return Pair(success, requestRatedMovie)
}
