package au.com.talentwars.ui

import au.com.talentwars.data.model.Movies

sealed interface MoviesUiState {
    /**
     * Empty state when the screen is first shown
     */
    data object Initial : MoviesUiState

    /**
     * Still loading
     */
    data object Loading : MoviesUiState

    /**
     * Popular Movies has been loaded
     */
    data class Success(val movies: List<Movies>) : MoviesUiState

    /**
     * There was an error
     */
    data class Error(val errorMessage: String) : MoviesUiState
}