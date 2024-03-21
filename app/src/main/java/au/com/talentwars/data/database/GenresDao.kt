package au.com.talentwars.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import au.com.talentwars.data.model.Genres
import kotlinx.coroutines.flow.Flow


@Dao
interface GenresDao {

    @Query("SELECT * FROM genres")
    fun getAllGenres(): Flow<List<Genres>>

    @Query("SELECT * FROM genres WHERE id IN (:ids)")
    suspend fun getGenresByIds(ids: List<Int>): List<Genres>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertGenres(genres: List<Genres>)

    @Delete
    fun delete(genres: Genres)
}