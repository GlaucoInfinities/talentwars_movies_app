package au.com.talentwars.ui.popular

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import au.com.talentwars.data.GenresRepository
import au.com.talentwars.data.MoviesRepository
import au.com.talentwars.data.model.Genres
import au.com.talentwars.data.model.Movies
import au.com.talentwars.ui.MoviesUiState
import au.com.talentwars.util.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularMoviesViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val genresRepository: GenresRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<MoviesUiState> =
        MutableStateFlow(MoviesUiState.Initial)
    val uiState: StateFlow<MoviesUiState> = _uiState.asStateFlow()

    private var currentPage = 1
    var isLoading = false
    private val moviesList = mutableListOf<Movies>()
    private var genresList = mutableListOf<Genres>()

    val searchText: MutableState<TextFieldValue> = mutableStateOf(TextFieldValue(""))
    val titleText: MutableState<String> = mutableStateOf("Popular Right now")

    fun updateTitleText() {
        if (searchText.value.text.isEmpty()) {
            titleText.value =  "Popular Right now"
        } else {
            titleText.value =  "Your Results"
            loadSearchMovies()
        }
    }
    init {
        _uiState.value = MoviesUiState.Loading
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
                    _uiState.value = MoviesUiState.Success(moviesList.toList())
                    isLoading = false
                },
                onError = { error ->
                    _uiState.value = MoviesUiState.Error(error)
                    isLoading = false
                }
            )
        }
    }

    private fun loadSearchMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            moviesRepository.loadSearchFromServer(
                searchText.value.text,
                onSuccess = { movies ->
                    moviesList.addAll(movies)
                    _uiState.value = MoviesUiState.Success(moviesList.toList())
                    isLoading = false
                },
                onError = { error ->
                    _uiState.value = MoviesUiState.Error(error)
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