package au.com.talentwars.data

import au.com.talentwars.data.model.RequestRatedMovie
import au.com.talentwars.data.network.ApiNetwork
import au.com.talentwars.data.network.ApiService
import javax.inject.Inject

class RatesRepository @Inject constructor() {

    private val service: ApiService =
        ApiNetwork.makeRetrofitService().create(ApiService::class.java)


    fun rateMovieFromServer(
        movieID: Int,
        onSuccess: (RequestRatedMovie) -> Unit, onError: (String) -> Unit
    ) {
        try {
            /*GlobalScope.launch(Dispatchers.IO) {
                try {
                    val request = service.requestPopularMoviesFromSourceResponse(page = page)

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
            }*/
        } catch (e: Exception) {
            e.printStackTrace()
            onError(e.message ?: "Error")
        }
    }
}