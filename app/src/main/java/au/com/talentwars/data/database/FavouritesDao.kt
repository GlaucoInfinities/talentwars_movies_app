package au.com.talentwars.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import au.com.talentwars.data.model.Favourites
import kotlinx.coroutines.flow.Flow


@Dao
interface FavouritesDao {

    @Query("SELECT * FROM favourites")
    fun getAllFavourites(): Flow<List<Favourites>>

    @Query("SELECT * FROM favourites WHERE id = :id")
    suspend fun getFavouritesByIds(id: Int): Favourites

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFavourites(favourites: Favourites)

    @Delete
    fun deleteFavourites(favourites: Favourites)
}