package au.com.talentwars.data.network

import au.com.talentwars.data.model.RequestRatedMovie
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
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
                "Authorization",
                "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI4OGMxZTZjNjYzOGRkMzI5OTAyMGQwZDBmMDRmMGEzMCIsInN1YiI6IjY1ZmIyZTViNTQ1MDhkMDE3Y2Y4ZTM5MiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.gH3QwNvGKwWPQIUrVHNl2d0y8oGIwkgNOSx6-wYE2MI"
            )
            .addHeader("accept", "application/json")
            .build()
        chain.proceed(request)
    }

    return httpClient.build()
}

fun doRateVideoRequest(movieID: Int): Pair<Boolean, RequestRatedMovie?> {
    //Retrofit was not accepting rate call
    val API_V2_URL = "https://api.themoviedb.org/3/"
    var result = ""
    var connection: HttpURLConnection? = null
    var success = false

    val jsonObj = JSONObject()
    jsonObj.put("value", 8.5)
    val jsonParser = JsonParser()
    val gsonObject = jsonParser.parse(jsonObj.toString()) as JsonObject
    val urlString = "movie/$movieID/rating"
    val url = URL(API_V2_URL + urlString)

    try {
        connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "POST"
        connection.setRequestProperty(
            "Authorization",
            "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI4OGMxZTZjNjYzOGRkMzI5OTAyMGQwZDBmMDRmMGEzMCIsInN1YiI6IjY1ZmIyZTViNTQ1MDhkMDE3Y2Y4ZTM5MiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.gH3QwNvGKwWPQIUrVHNl2d0y8oGIwkgNOSx6-wYE2MI"
        )
        connection.setRequestProperty("Content-Type", "application/json; charset=utf-8")
        connection.setRequestProperty("Accept", "application/json")
        connection.doOutput = true

        BufferedOutputStream(connection.outputStream).use { outputStream ->
            outputStream.write(gsonObject.toString().toByteArray())
            outputStream.flush()
        }

        val responseCode = connection.responseCode
        success = responseCode == HttpURLConnection.HTTP_CREATED

        BufferedReader(InputStreamReader(connection.inputStream)).use { reader ->
            result = reader.readText()
        }

    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        connection?.disconnect()
    }
    val requestRatedMovie: RequestRatedMovie = Gson().fromJson(result, RequestRatedMovie::class.java)
    return Pair(success,requestRatedMovie)
}