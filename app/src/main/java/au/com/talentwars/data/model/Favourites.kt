package au.com.talentwars.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourites")
data class Favourites(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "count_rating") val countRating: Int,
    @ColumnInfo(name = "backdrop_path") val backdropPath: String,
)