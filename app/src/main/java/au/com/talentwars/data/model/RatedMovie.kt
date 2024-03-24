package au.com.talentwars.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

data class RequestRatedMovie(
    val success: Int,
    val status_code: String,
    val status_message: String,
)
