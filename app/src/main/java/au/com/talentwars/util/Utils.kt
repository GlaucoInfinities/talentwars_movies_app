package au.com.talentwars.util

import java.text.SimpleDateFormat
import java.util.Date
import kotlin.math.roundToInt

class Utils {
    companion object {
        fun moviePercentageCalculator(voteAverage: Double): Int {
            return ((voteAverage / 10.0) * 100.0).roundToInt()
        }

        fun movieYear(releaseDate: String): String {
            val releaseDate = releaseDate ?: ""
            val dateFormat = SimpleDateFormat("yyyy-MM-dd")
            val date: Date? = try {
                dateFormat.parse(releaseDate)
            } catch (e: Exception) {
                null
            }
            return  date?.let { SimpleDateFormat("yyyy").format(it) } ?: ""
        }
    }
}