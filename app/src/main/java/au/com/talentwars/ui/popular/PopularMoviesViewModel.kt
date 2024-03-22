package au.com.talentwars.ui.popular

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import au.com.talentwars.data.GenresRepository
import au.com.talentwars.data.MoviesRepository
import au.com.talentwars.data.model.Genres
import au.com.talentwars.data.model.Movies
import au.com.talentwars.ui.PopularMoviesUiState
import au.com.talentwars.util.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularMoviesViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val genresRepository: GenresRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<PopularMoviesUiState> =
        MutableStateFlow(PopularMoviesUiState.Initial)
    val uiState: StateFlow<PopularMoviesUiState> = _uiState.asStateFlow()

    private var currentPage = 1
    var isLoading = false
    private val moviesList = mutableListOf<Movies>()
    private var genresList = mutableListOf<Genres>()

    init {
        loadPopularGenres()
    }

    private fun loadPopularGenres() {
        viewModelScope.launch(Dispatchers.IO) {
            genresRepository.loadGenresFromServer(
                onSuccess = { genres ->
                    viewModelScope.launch {
                        genresList.addAll(genres)
                        loadPopularMovies(currentPage)
                    }
                },
                onError = { error ->
                }
            )
        }
    }

    private fun loadPopularMovies(page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            isLoading = true

            moviesRepository.loadMoviesFromServer(
                page,
                onSuccess = { movies ->
                    moviesList.addAll(movies)
                    _uiState.value = PopularMoviesUiState.Success(moviesList.toList())
                    isLoading = false
                },
                onError = { error ->
                    _uiState.value = PopularMoviesUiState.Error(error)
                    isLoading = false
                }
            )
        }
    }

    fun loadNextPage() {
        currentPage++
        loadPopularMovies(currentPage)
    }

    fun calculatePercent(voteAverage: Double): Int {
        return Utils.moviePercentageCalculator(voteAverage)
    }

    fun getMovieYear(dateString: String): String {
        return Utils.movieYear(dateString)
    }

    fun getMovieGenres(ids: List<Int>): List<Genres> {
        return genresList.filter { ids.contains(it.id) }
    }
}