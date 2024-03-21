package au.com.talentwars.data.di

import android.content.Context
import au.com.talentwars.data.database.AppDatabase
import au.com.talentwars.data.database.GenresDao

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

 /*   @Singleton
    @Provides
    fun provideMoviesDao(db: AppDatabase): MoviesDao {
        return db.moviesDao()
    }
*/
    @Singleton
    @Provides
    fun provideGenresDao(db: AppDatabase): GenresDao {
        return db.genresDao()
    }
}