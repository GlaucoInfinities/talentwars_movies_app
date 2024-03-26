package au.com.talentwars.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import au.com.talentwars.R
import au.com.talentwars.data.DetailsRepository
import au.com.talentwars.data.GenresRepository
import au.com.talentwars.data.RatesRepository
import au.com.talentwars.data.model.DetailsMovie
import au.com.talentwars.data.model.Genres
import au.com.talentwars.data.model.Movies
import au.com.talentwars.ui.DetailsMoviesUiState
import au.com.talentwars.ui.RatedMoviesUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailsMoviesViewModel @Inject constructor(
    private val detailsRepository: DetailsRepository,
    private val ratesRepository: RatesRepository,
    private val genresRepository: GenresRepository


) : ViewModel() {

    private val _uiState: MutableStateFlow<DetailsMoviesUiState> =
        MutableStateFlow(DetailsMoviesUiState.Initial)

    private val _uiStateRatedMovie: MutableStateFlow<RatedMoviesUiState> =
        MutableStateFlow(RatedMoviesUiState.Initial)
    val uiStateRatedMovie: StateFlow<RatedMoviesUiState> = _uiStateRatedMovie.asStateFlow()

    private val _modalVisible = MutableStateFlow(false)
    val modalVisible: StateFlow<Boolean> = _modalVisible

    private lateinit var movie: Movies
    private lateinit var movieDetails: DetailsMovie

    private var _topButtonColor = MutableLiveData<Int>(R.color.background_top_rate)
    val topButtonColor: LiveData<Int> = _topButtonColor

    private var _topButtonTextColor = MutableLiveData<Int>(R.color.white)
    val topButtonTextColor: LiveData<Int> = _topButtonTextColor

    private var _buttonTopText = MutableLiveData<String>("Rate it myself >")
    val buttonTopText: LiveData<String> = _buttonTopText

    private var _buttonBottomText = MutableLiveData<String>("add personal rating")
    val buttonBottomText: LiveData<String> = _buttonBottomText

    //TODO("variable temp to avoid call server")
    var rated = false

    private val _sliderValue = MutableStateFlow(50.0)
    val sliderValue: StateFlow<Double> = _sliderValue

    fun setSliderValue(value: Double) {
        _sliderValue.value = value
    }

    private var genresList = mutableListOf<Genres>()


    fun showModal() {
        _modalVisible.value = true
    }

    // Method to hide the modal
    fun hideModal() {
        _modalVisible.value = false
    }

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
                _uiState.value = DetailsMoviesUiState.Loading
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
            ratesRepository.rateMovieFromServer(
                movie.id,
                sliderValue.value,
                onSuccess = { ratedMovie ->
                    rated = true
                    _uiStateRatedMovie.value = RatedMoviesUiState.Success(ratedMovie)
                    toggleButton()
                },
                onError = { error ->
                    _uiStateRatedMovie.value = RatedMoviesUiState.Error(error)
                }
            )
        }
    }

    private fun toggleButton() {
        GlobalScope.launch() {
            withContext(Dispatchers.Main) {
                val initialTopButtonColor = R.color.background_top_rate
                val initialTopButtonTextColor = R.color.white

                val toggledTopButtonColorResource = R.color.background_top_rate_rating_now
                val toggledTopButtonTextColorResource = R.color.background_top_rate_rating_now_text

                _topButtonTextColor.value =
                    if (topButtonTextColor.value == initialTopButtonTextColor) toggledTopButtonTextColorResource else initialTopButtonTextColor
                _topButtonColor.value =
                    if (_topButtonColor.value == initialTopButtonColor) toggledTopButtonColorResource else initialTopButtonColor
                _buttonTopText.value =
                    if (_buttonTopText.value == "Rate it myself >") "Rating Now" else "Rate it myself >"
                _buttonBottomText.value =
                    if (_buttonBottomText.value == "using modal") "click to reset" else "add personal rating"
            }
        }
    }
}