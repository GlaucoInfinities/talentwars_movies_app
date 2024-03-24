package au.com.talentwars.data

import au.com.talentwars.data.model.Favourites
import au.com.talentwars.data.model.RequestRatedMovie
import au.com.talentwars.data.network.ApiNetwork
import au.com.talentwars.data.network.ApiService
import au.com.talentwars.data.network.doRateVideoRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
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

    fun rateMovieFromServer(
        movieID: Int,
        onSuccess: (RequestRatedMovie) -> Unit, onError: (String) -> Unit
    ) {
        try {
            GlobalScope.launch(Dispatchers.IO) {
                try {
                    val (success, response) = doRateVideoRequest(movieID)
                    if (success) {
                        response?.let { onSuccess(it) }
                    } else {
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
