package au.com.talentwars.ui

import au.com.talentwars.data.model.RequestRatedMovie

sealed interface RatedMoviesUiState {
    /**
     * Empty state when the screen is first shown
     */
    data object Initial : RatedMoviesUiState

    /**
     * Still loading
     */
    data object Loading : RatedMoviesUiState

    /**
     * Rated Movies has been loaded
     */
    data class Success(val ratedMovie: RequestRatedMovie) : RatedMoviesUiState

    /**
     * There was an error
     */
    data class Error(val errorMessage: String) : RatedMoviesUiState
}