package au.com.talentwars

import android.app.Application
import android.content.Context
import coil.ImageLoader
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
@HiltAndroidApp
class TalentwarsApplication : Application() {

    @Provides
    @Singleton // Add this annotation to indicate that ImageLoader should be a singleton
    fun provideImageLoader(context: Context): ImageLoader {
        val cacheSize = 10 * 1024 * 1024 // 10 MB cache size (adjust as needed)
        return ImageLoader.Builder(context)
            .okHttpClient {
                OkHttpClient.Builder()
                    .cache(Cache(context.cacheDir, cacheSize.toLong())) // Use context.cacheDir
                    .build()
            }
            .respectCacheHeaders(true) // Enable respecting cache headers
            .build()
    }
}