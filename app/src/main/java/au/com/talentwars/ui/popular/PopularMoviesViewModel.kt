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
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
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

    init {
        loadPopularMovies(currentPage)
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
            val genresFlow: Flow<List<Genres>> = genresRepository.allGenresFromDataBase
            val genresList = genresFlow.firstOrNull()

            if (genresList.isNullOrEmpty()) {
                genresRepository.loadGenresFromServer(
                    onSuccess = { genres ->
                        //_uiState.value = PopularMoviesUiState.Success(moviesList.toList())
                        viewModelScope.launch {
                            genresRepository.insert(genres)
                        }
                    },
                    onError = { error ->
                        // _uiState.value = PopularMoviesUiState.Error(error)
                    }
                )
            }
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
}