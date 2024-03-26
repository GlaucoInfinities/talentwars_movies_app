package au.com.talentwars.data.network

import au.com.talentwars.data.model.DetailsMovie
import au.com.talentwars.data.model.RequestFavouritesMovies
import au.com.talentwars.data.model.RequestGenresMovies
import au.com.talentwars.data.model.RequestMovies
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("movie/popular")
    suspend fun requestPopularMoviesFromSourceResponse(
        @Query("page") page: Int
    ): Response<RequestMovies>

    @GET("search/movie")
    suspend fun requestSearchMoviesFromSourceResponse(
        @Query("query") query: String
    ): Response<RequestMovies>

    @GET("movie/{movie_id}")
    suspend fun requestMovieDetailsFromSourceResponse(
        @Path("movie_id") movieId: Int
    ): Response<DetailsMovie>

    @GET("genre/movie/list")
    suspend fun requestGenreMoviesFromSourceResponse(
    ): Response<RequestGenresMovies>

    @GET("account/{account_id}/favorite/movies")
    suspend fun requestFavoriteMoviesFromSourceResponse(
        @Path("account_id") accountID: Int
    ): Response<RequestFavouritesMovies>

    @GET("account/{account_id}/rated/movies")
    suspend fun requestRatedFromSourceResponse(
        @Path("account_id") accountID: Int
    ): Response<RequestFavouritesMovies>

}
