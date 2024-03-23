package au.com.talentwars.ui

import au.com.talentwars.data.model.Movies

sealed interface PopularMoviesUiState {
    /**
     * Empty state when the screen is first shown
     */
    data object Initial : PopularMoviesUiState

    /**
     * Still loading
     */
    data object Loading : PopularMoviesUiState

    /**
     * Popular Movies has been loaded
     */
    data class Success(val movies: List<Movies>) : PopularMoviesUiState

    /**
     * There was an error
     */
    data class Error(val errorMessage: String) : PopularMoviesUiState
}