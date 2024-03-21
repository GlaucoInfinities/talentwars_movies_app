package au.com.talentwars.data

import androidx.annotation.WorkerThread
import au.com.talentwars.data.database.GenresDao
import au.com.talentwars.data.model.Genres
import au.com.talentwars.data.network.ApiNetwork
import au.com.talentwars.data.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GenresRepository @Inject constructor(private val genresDao: GenresDao) {

    private val retrofitService: ApiService =
        ApiNetwork.makeRetrofitService().create(ApiService::class.java)

    suspend fun loadGenresFromServer(
        onSuccess: (List<Genres>) -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            val request = retrofitService.requestGenreMoviesFromSourceResponse()
            if (request.isSuccessful) {
                allGenresFromDataBase.collect {
                    val complete = request.body()
                    complete?.let { onSuccess(it.genres) }
                }
            } else {
                onError("Could not load ${request.errorBody()}")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            onError(e.message ?: "Error")
        }
    }

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allGenresFromDataBase: Flow<List<Genres>> = genresDao.getAllGenres()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(genres: List<Genres>) {
        withContext(Dispatchers.IO) {
            genresDao.insertGenres(genres)
        }
    }
}
