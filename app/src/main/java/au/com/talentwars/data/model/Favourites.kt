package au.com.talentwars.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable


data class RequestFavouritesMovies(
    val page: Int,
    val results: List<Favourites>,
    val totalPages: Int,
    val totalResults: Int
)

@Entity(tableName = "favourites")
data class Favourites(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "rating") val rating: Double,
    @ColumnInfo(name = "poster_path") val poster_path: String,
)

@Serializable
data class MyData(val value: Double)
