package au.com.talentwars.data

import androidx.annotation.WorkerThread
import au.com.talentwars.data.database.FavouritesDao
import au.com.talentwars.data.database.GenresDao
import au.com.talentwars.data.model.Favourites
import au.com.talentwars.data.model.Genres
import au.com.talentwars.data.model.Movies
import au.com.talentwars.data.network.ApiNetwork
import au.com.talentwars.data.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FavouritesRepository @Inject constructor(private val favouritesDao: FavouritesDao) {

    suspend fun saveDBFavourites(favourites: Favourites) {
        insertOrUpdateFavourites(favourites)
    }


    val allFavouritesFromDataBase: Flow<List<Favourites>> = favouritesDao.getAllFavourites()

    suspend fun insertOrUpdateFavourites(favourites: Favourites) {
        val existingFavourites = favouritesDao.getFavouritesById(favourites.id)
        if (existingFavourites != null) {
            withContext(Dispatchers.IO) {
            favouritesDao.updateCountRating(favourites.id, additionalCount = 1)
            }
        } else {
            withContext(Dispatchers.IO) {
            favouritesDao.insertFavourites(favourites)
            }
        }
    }
}
