package au.com.talentwars.data

import androidx.annotation.WorkerThread
import au.com.talentwars.Constants.ACCOUNT_ID
import au.com.talentwars.data.database.FavouritesDao
import au.com.talentwars.data.model.Favourites
import au.com.talentwars.data.model.RequestPost
import au.com.talentwars.data.model.rawAddFavourites
import au.com.talentwars.data.network.ApiNetwork
import au.com.talentwars.data.network.ApiService
import au.com.talentwars.data.network.doPostRequest
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import javax.inject.Inject


class FavouritesRepository @Inject constructor(private val favouritesDao: FavouritesDao) {

    private val service: ApiService =
        ApiNetwork.makeRetrofitService().create(ApiService::class.java)

    fun addFavouriteMovieFromServer(
        movieID: Int,
        favorite: Boolean,
        mediaType: String? = "movie",
        onSuccess: (RequestPost) -> Unit, onError: (String) -> Unit
    ) {

        val rawAddFavourites = rawAddFavourites(mediaType!!, movieID, favorite)

        val jsonObj = JSONObject().apply {
            put("media_type", rawAddFavourites.mediaType)
            put("media_id", rawAddFavourites.mediaID)
            put("favorite", rawAddFavourites.favorite)
        }

        val jsonParser = JsonParser()
        val gsonObject = jsonParser.parse(jsonObj.toString()) as JsonObject
        val urlString = "account/$ACCOUNT_ID/favorite"

        try {
            GlobalScope.launch(Dispatchers.IO) {
                try {
                    val (success, response) = doPostRequest(gsonObject, urlString)
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

    suspend fun getFavouritesByID(id: Int):Favourites? {
        return withContext(Dispatchers.IO) {
            favouritesDao.getFavouritesByIds(id)
        }
    }

    suspend fun loadFavouritesFromServer(
        onSuccess: (List<Favourites>) -> Unit, onError: (String) -> Unit
    ) {
        try {
            val request = service.requestFavoriteMoviesFromSourceResponse(ACCOUNT_ID)

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

    @WorkerThread
    suspend fun saveFavourites(favourites: Favourites) {
        withContext(Dispatchers.IO) {
            favouritesDao.insertFavourites(favourites)
        }
    }
    @WorkerThread
    suspend fun deleteFavourites(favourites: Favourites) {
        withContext(Dispatchers.IO) {
            favouritesDao.deleteFavourites(favourites)
        }
    }
}
