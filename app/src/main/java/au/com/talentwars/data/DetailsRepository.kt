package au.com.talentwars.data

import au.com.talentwars.data.model.Details
import au.com.talentwars.data.model.Movies
import au.com.talentwars.data.network.ApiNetwork
import au.com.talentwars.data.network.ApiService
import javax.inject.Inject

class DetailsRepository @Inject constructor() {

    private val service: ApiService =
        ApiNetwork.makeRetrofitService().create(ApiService::class.java)

    suspend fun loadMovieDetailsFromServer(
        movieID: Int,
        onSuccess: (Details) -> Unit, onError: (String) -> Unit
    ) {
        try {
            val request = service.requestMovieDetailsFromSourceResponse(movieID)

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