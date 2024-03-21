package au.com.talentwars.data.network

import au.com.talentwars.data.model.RequestGenresMovies
import au.com.talentwars.data.model.RequestPopularMovies
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("movie/popular")
    suspend fun requestPopularMoviesFromSourceResponse(
        @Query("page") page: Int
    ): Response<RequestPopularMovies>

    @GET("genre/movie/list")
    suspend fun requestGenreMoviesFromSourceResponse(
    ): Response<RequestGenresMovies>
}