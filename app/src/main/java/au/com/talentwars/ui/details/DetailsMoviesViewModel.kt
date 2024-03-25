package au.com.talentwars.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import au.com.talentwars.R
import au.com.talentwars.data.DetailsRepository
import au.com.talentwars.data.FavouritesRepository
import au.com.talentwars.data.GenresRepository
import au.com.talentwars.data.model.DetailsMovie
import au.com.talentwars.data.model.Genres
import au.com.talentwars.data.model.Movies
import au.com.talentwars.ui.DetailsMoviesUiState
import au.com.talentwars.ui.RatedMoviesUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailsMoviesViewModel @Inject constructor(
    private val detailsRepository: DetailsRepository,
    private val favouritesRepository: FavouritesRepository,
    private val genresRepository: GenresRepository


) : ViewModel() {

    private val _uiState: MutableStateFlow<DetailsMoviesUiState> =
        MutableStateFlow(DetailsMoviesUiState.Initial)

    private val _uiStateRatedMovie: MutableStateFlow<RatedMoviesUiState> =
        MutableStateFlow(RatedMoviesUiState.Initial)

    private lateinit var movie: Movies
    private lateinit var movieDetails: DetailsMovie

    private var _topButtonColor = MutableLiveData<Int>(R.color.background_top_rate)
    val topButtonColor: LiveData<Int> = _topButtonColor

    private var _buttonTopText = MutableLiveData<String>("Rate it myself >")
    val buttonTopText: LiveData<String> = _buttonTopText

    private var _buttonBottomText = MutableLiveData<String>("add personal rating")
    val buttonBottomText: LiveData<String> = _buttonBottomText

    //TODO("variable temp to avoid call server")
    var rated = false

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
                    }
                },
                onError = { error ->
                }
            )
        }
    }

    fun onButtonClick() {
        viewModelScope.launch {
            if (!rated) {
                rateMovie()
            } else {
                toggleButton()
            }
        }
    }

    fun setMovie(movie: Movies) {
        this.movie = movie
        loadDataDetailsMovies()
    }

    private fun loadDataDetailsMovies() {
        _uiState.value = DetailsMoviesUiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            detailsRepository.loadMovieDetailsFromServer(
                movie.id,
                onSuccess = { details ->
                    movieDetails = details
                    _uiState.value = DetailsMoviesUiState.Success(details)
                },
                onError = { error ->
                    _uiState.value = DetailsMoviesUiState.Error(error)
                }
            )
        }
    }

    private fun rateMovie() {
        _uiStateRatedMovie.value = RatedMoviesUiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
           /* favouritesRepository.rateMovieFromServer(
                movie.id,
                onSuccess = { ratedMovie ->
                    rated=true
                    _uiStateRatedMovie.value = RatedMoviesUiState.Success(ratedMovie)
                    toggleButton()
                },
                onError = { error ->
                    _uiStateRatedMovie.value = RatedMoviesUiState.Error(error)
                }
            )*/
        }
    }

    private fun toggleButton() {
        GlobalScope.launch() {
            withContext(Dispatchers.Main) {
                val initialTopButtonColor = R.color.background_top_rate
                val toggledTopButtonColorResource = R.color.background_top_rate_pressed
                _topButtonColor.value =
                    if (_topButtonColor.value == initialTopButtonColor) toggledTopButtonColorResource else initialTopButtonColor
                _buttonTopText.value =
                    if (_buttonTopText.value == "Rate it myself >") "You’ve rated this 8.5" else "Rate it myself >"
                _buttonBottomText.value =
                    if (_buttonBottomText.value == "add personal rating") "click to reset" else "add personal rating"
            }
        }
    }
}