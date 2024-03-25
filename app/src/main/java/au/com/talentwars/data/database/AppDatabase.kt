package au.com.talentwars.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import au.com.talentwars.data.model.Favourites
import au.com.talentwars.data.model.Genres

@Database(entities = [Genres::class, Favourites::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun genresDao(): GenresDao
    abstract fun favouritesDao(): FavouritesDao


    companion object {
        // Singleton prevents multiple instances of database opening at the same time.
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "movies_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}