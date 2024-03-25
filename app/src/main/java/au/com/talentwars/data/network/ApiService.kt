package au.com.talentwars.data.network

import au.com.talentwars.data.model.DetailsMovie
import au.com.talentwars.data.model.RequestGenresMovies
import au.com.talentwars.data.model.RequestPopularMovies
import retrofit2.Response
import retrofit2.http.GET
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


  /*  @GET("account/{account_id}/rated/movies")
    suspend fun requestRatedFromSourceResponse(
        @Path("account_id") accountID: Int
    ): Response<RequestFavouritesMovies>*/

}
