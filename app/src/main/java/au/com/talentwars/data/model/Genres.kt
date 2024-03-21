package au.com.talentwars.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


data class RequestGenresMovies(
    val genres: List<Genres>,
)

@Entity(tableName = "genres")
data class Genres(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String
)