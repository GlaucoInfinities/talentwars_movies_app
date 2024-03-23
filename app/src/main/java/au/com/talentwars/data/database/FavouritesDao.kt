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

    @Query("SELECT * FROM favourites WHERE id = :favouritesId")
    suspend fun getFavouritesById(favouritesId: Int): Favourites?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFavourites(favourites: Favourites)

    @Query("UPDATE favourites SET count_rating = count_rating + :additionalCount WHERE id = :favouritesId")
    suspend fun updateCountRating(favouritesId: Int, additionalCount: Int)
}