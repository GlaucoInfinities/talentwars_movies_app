package au.com.talentwars.data

import au.com.talentwars.data.model.Movies
import au.com.talentwars.data.network.ApiNetwork
import au.com.talentwars.data.network.ApiService
import javax.inject.Inject

class MoviesRepository @Inject constructor() {

    private val service: ApiService =
        ApiNetwork.makeRetrofitService().create(ApiService::class.java)

    suspend fun loadMoviesFromServer(
        page: Int,
        onSuccess: (List<Movies>) -> Unit, onError: (String) -> Unit
    ) {
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
    }

    suspend fun loadSearchFromServer(
        query: String,
        onSuccess: (List<Movies>) -> Unit, onError: (String) -> Unit
    ) {
        try {
            val request = service.requestSearchMoviesFromSourceResponse(query = query)

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

}