package au.com.talentwars.data.di

import android.content.Context
import au.com.talentwars.data.database.AppDatabase
import au.com.talentwars.data.database.FavouritesDao

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

    @Singleton
    @Provides
    fun provideFavouritesDao(db: AppDatabase): FavouritesDao {
        return db.favouritesDao()
    }
}