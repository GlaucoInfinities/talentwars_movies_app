package au.com.talentwars.data.network

import au.com.talentwars.data.model.DetailsMovie
import au.com.talentwars.data.model.MyData
import au.com.talentwars.data.model.RequestFavouritesMovies
import au.com.talentwars.data.model.RequestGenresMovies
import au.com.talentwars.data.model.RequestPopularMovies
import au.com.talentwars.data.model.RequestRatedMovie
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("movie/popular")
    suspend fun requestPopularMoviesFromSourceResponse(
        @Query("page") page: Int
    ): Response<RequestPopularMovies>

    @GET("search/movie")
    suspend fun requestSearchMoviesFromSourceResponse(
        @Query("query") query: String
    ): Response<RequestPopularMovies>

    @GET("movie/{movie_id}")
    suspend fun requestMovieDetailsFromSourceResponse(
        @Path("movie_id") movieId: Int
    ): Response<DetailsMovie>

    @GET("genre/movie/list")
    suspend fun requestGenreMoviesFromSourceResponse(
    ): Response<RequestGenresMovies>

    @GET("account/{account_id}/rated/movies")
    suspend fun requestFavouritesFromSourceResponse(
        @Path("account_id") accountID: Int
    ): Response<RequestFavouritesMovies>

    @Headers("Content-Type: application/json;charset=utf-8")
    @POST("movie/{movie_id}/rating")
    suspend fun requestRateMovie(
        @Path("movie_id") movieId: Int,
        @Body data: String
    ): Response<RequestRatedMovie>

    @Headers("Content-Type: application/json;charset=utf-8")
    @POST("movie/{movie_id}/rating")
    fun postData(
        @Path("movie_id") movieId: Int,
        @Body data: MyData
    ): Call<RequestRatedMovie>

}
//.addHeader("Content-Type", "application/json;charset=utf-8")