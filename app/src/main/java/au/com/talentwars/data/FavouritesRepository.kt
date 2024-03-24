package au.com.talentwars.data

import au.com.talentwars.data.model.Favourites
import au.com.talentwars.data.model.RequestRatedMovie
import au.com.talentwars.data.network.ApiNetwork
import au.com.talentwars.data.network.ApiService
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import org.json.JSONObject
import javax.inject.Inject


class FavouritesRepository @Inject constructor() {

    private val service: ApiService =
        ApiNetwork.makeRetrofitService().create(ApiService::class.java)

    val accountId: Int = 21123945

    suspend fun loadFavouritesFromServer(
        onSuccess: (List<Favourites>) -> Unit, onError: (String) -> Unit
    ) {
        try {
            val request = service.requestFavouritesFromSourceResponse(accountId)

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
    suspend fun rateMovieFromServer(
        movieID: Int,
        onSuccess: (RequestRatedMovie) -> Unit, onError: (String) -> Unit
    ) {
        try {

         /*   val requestBody = MyData(value = 8.5)
            val call = service.postData(movieID, "{\"value\":10}")

// Execute the call asynchronously
            call.enqueue(object : Callback<RequestRatedMovie> {
                override fun onResponse(call: Call<RequestRatedMovie>, response: Response<RequestRatedMovie>) {
                    if (response.isSuccessful) {
                        val responseData = response.body()
                        // Handle successful response
                    } else {
                        // Handle error response
                    }
                }

                override fun onFailure(call: Call<RequestRatedMovie>, t: Throwable) {
                    Log.d("Error ",t.toString())
                }
            })
*/

         /*   val dataModal: MyData = MyData(8.5)

            // calling a method to create a post and passing our modal class.
            val call: Call<RequestRatedMovie> = service.postData(movieID,dataModal)

            // on below line we are executing our method.
            call!!.enqueue(object : Callback<RequestRatedMovie?> {
                override fun onResponse(call: Call<RequestRatedMovie?>?, response: Response<RequestRatedMovie?>) {

                    // and passing it to our modal class.
                    val response: RequestRatedMovie? = response.body()


                }

                override fun onFailure(call: Call<RequestRatedMovie?>?, t: Throwable) {
                    // setting text to our text view when
                    // we get error response from API.
                    val error = "Error found is : " + t.message
                }
            })

*/


            //Map<String, String>
            //val rawJson = """{"value":10}"""
            //val rawJson = "{\"value\":10}"

            //val map = mapOf(
              //  "value" to 8.5)

            //val requestBody = MyData(value = 8.5)
            //val call = apiService.postData(requestBody)

       /*     val json = "{\"value\":8.5}"

            // Create a MediaType
            val mediaType = "application/json;charset=utf-8".toMediaType()

            // Create a RequestBody
            val requestBody = json.toRequestBody(mediaType)
*/

            val jsonObj_ = JSONObject()
            jsonObj_.put("value", 8.5)
            val jsonParser = JsonParser()
            val gsonObject = jsonParser.parse(jsonObj_.toString()) as JsonObject
            val request = service.requestRateMovie(movieID,gsonObject.toString())

/*
            val task = MyData(8.0)

            val request = service.requestRateMovie(movieID,task)*/

              if (request.isSuccessful) {
                   val complete = request.body()
                   complete?.let { onSuccess(it) }
               } else {
                   onError("Could not load ${request.errorBody()?.string()}")
               }

        } catch (e: Exception) {
            e.printStackTrace()
            onError(e.message ?: "Error")
        }
    }

}
