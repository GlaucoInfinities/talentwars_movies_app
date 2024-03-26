package au.com.talentwars.data

import au.com.talentwars.data.model.Genres
import au.com.talentwars.data.network.ApiNetwork
import au.com.talentwars.data.network.ApiService
import javax.inject.Inject

class GenresRepository @Inject constructor() {

    private val retrofitService: ApiService =
        ApiNetwork.makeRetrofitService().create(ApiService::class.java)

    suspend fun loadGenresFromServer(
        onSuccess: (List<Genres>) -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            val request = retrofitService.requestGenreMoviesFromSourceResponse()
            if (request.isSuccessful) {
                    val complete = request.body()
                    complete?.let { onSuccess(it.genres) }
            } else {
                onError("Could not load ${request.errorBody()}")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            onError(e.message ?: "Error")
        }
    }
}
