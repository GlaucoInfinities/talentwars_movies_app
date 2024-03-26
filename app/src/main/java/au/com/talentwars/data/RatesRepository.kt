package au.com.talentwars.data

import au.com.talentwars.Constants
import au.com.talentwars.data.model.Movies
import au.com.talentwars.data.model.RequestPost
import au.com.talentwars.data.network.ApiNetwork
import au.com.talentwars.data.network.ApiService
import au.com.talentwars.data.network.doPostRequest
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

class RatesRepository @Inject constructor() {

    private val service: ApiService =
        ApiNetwork.makeRetrofitService().create(ApiService::class.java)

    suspend fun loadRatedFromServer(
        onSuccess: (List<Movies>) -> Unit, onError: (String) -> Unit
    ) {
        try {
            val request = service.requestRatedFromSourceResponse(Constants.ACCOUNT_ID)

            if (request.isSuccessful) {
                val complete = request.body()
                complete?.let { onSuccess(it.results) }
            } else {
                onError("Could not load ${request.errorBody()?.string()}")
            }

        } catch (e: Exception) {
            e.printStackTrace()
            onError(e.message ?: "Error")
        }
    }

    fun rateMovieFromServer(
        movieID: Int,
        onSuccess: (RequestPost) -> Unit, onError: (String) -> Unit
    ) {
        try {
            GlobalScope.launch(Dispatchers.IO) {
                try {
                    val jsonObj = JSONObject()
                    jsonObj.put("value", 8.5)
                    val jsonParser = JsonParser()
                    val gsonObject = jsonParser.parse(jsonObj.toString()) as JsonObject
                    val urlString = "movie/$movieID/rating"

                    val (success, response) = doPostRequest(gsonObject, urlString)
                    if (success) {
                        response?.let { onSuccess(it) }
                    } else {
                        // Handle error response
                        onError("Request failed")
                    }
                } catch (e: Exception) {
                    onError("An error occurred: ${e.message}")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            onError(e.message ?: "Error")
        }
    }
}