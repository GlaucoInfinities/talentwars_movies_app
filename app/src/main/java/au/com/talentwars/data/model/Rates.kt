package au.com.talentwars.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


data class RequestRatesMovies(
    val page: Int,
    val results: List<Favourites>,
    val totalPages: Int,
    val totalResults: Int
)

@Entity(tableName = "Rates")
data class Rates(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "rating") val rating: Double,
    @ColumnInfo(name = "poster_path") val poster_path: String,
)
