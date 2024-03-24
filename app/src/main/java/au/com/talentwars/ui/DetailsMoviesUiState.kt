package au.com.talentwars.ui

import au.com.talentwars.data.model.DetailsMovie

sealed interface DetailsMoviesUiState {
    /**
     * Empty state when the screen is first shown
     */
    data object Initial : DetailsMoviesUiState

    /**
     * Still loading
     */
    data object Loading : DetailsMoviesUiState

    /**
     * Details Movies has been loaded
     */
    data class Success(val movies: DetailsMovie) : DetailsMoviesUiState

    /**
     * There was an error
     */
    data class Error(val errorMessage: String) : DetailsMoviesUiState
}