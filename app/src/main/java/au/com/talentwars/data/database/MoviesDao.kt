package au.com.talentwars.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import au.com.talentwars.data.model.Movies
import kotlinx.coroutines.flow.Flow


@Dao
interface MoviesDao {

    @Query("SELECT * FROM source")
    fun getAllSelectedMovies(): Flow<List<Movies>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(vararg movie: Movies)

    @Delete
    fun delete(movie: Movies)
}