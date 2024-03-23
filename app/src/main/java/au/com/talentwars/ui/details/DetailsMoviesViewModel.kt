package au.com.talentwars.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import au.com.talentwars.data.DetailsRepository
import au.com.talentwars.data.MoviesRepository
import au.com.talentwars.data.model.Details
import au.com.talentwars.data.model.Movies
import au.com.talentwars.ui.DetailsMoviesUiState
import au.com.talentwars.ui.PopularMoviesUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsMoviesViewModel @Inject constructor(
    private val detailsRepository: DetailsRepository,
) : ViewModel() {

    private val _uiState: MutableStateFlow<DetailsMoviesUiState> =
        MutableStateFlow(DetailsMoviesUiState.Initial)
    val uiState: StateFlow<DetailsMoviesUiState> = _uiState.asStateFlow()

    private lateinit var movie: Movies
    private lateinit var movieDetails: Details

    fun setMovie(movie: Movies) {
        this.movie = movie
        loadData()
    }

    private fun loadData() {
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
}